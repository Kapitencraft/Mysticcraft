package net.kapitencraft.mysticcraft.tech;

import com.mojang.datafixers.util.Pair;
import net.kapitencraft.mysticcraft.capability.mana.IManaStorage;
import net.kapitencraft.mysticcraft.tech.block.entity.ManaPortBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ManaDistributionNetwork {

    private final List<Node> nodes = new ArrayList<>();
    private final Map<BlockPos, Node> byBlock = new HashMap<>();

    public int size() {
        return nodes.size();
    }

    public void addAll(ManaDistributionNetwork other) {
        this.nodes.addAll(other.nodes);
        this.byBlock.putAll(other.byBlock);
    }

    public void add(BlockPos pos, Node.Type type) {
        if (contains(pos)) throw new IllegalStateException("attempted to register a duplicate node at position " + pos);
        Node node = new Node(pos, type);
        this.nodes.add(node);
        this.byBlock.put(pos, node);
    }

    public boolean contains(BlockPos pos) {
        return byBlock.containsKey(pos);
    }

    public Node getNode(BlockPos pos) {
        return this.byBlock.get(pos);
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public void attach(BlockPos pPos, IManaStorage storage) {
        Objects.requireNonNull(this.getNode(pPos), "could not find node at " + pPos).attached = storage;
    }

    public void detach(BlockPos pos) {
        Objects.requireNonNull(this.getNode(pos), "could not find node at " + pos).attached = null;
    }

    @SuppressWarnings("DataFlowIssue")
    public void notifyMove(ManaDistributionNetwork other, Level level) {
        this.nodes.forEach(node -> {
            if (node.type == Node.Type.PORT) {
                ManaPortBlockEntity entity = (ManaPortBlockEntity) level.getBlockEntity(node.position);
                entity.setNetwork(other);
            }
        });
    }

    public void connect(BlockPos pos1, BlockPos pos2) {
        Node node1 = this.getNode(pos1);
        Node node2 = this.getNode(pos2);
        node1.connections.add(node2);
        node2.connections.add(node1);
    }

    public void remove(BlockPos worldPosition) {
        Node node = this.byBlock.remove(worldPosition);
        if (node != null) {
            node.connections.forEach(node1 -> node1.connections.remove(node));
            this.nodes.remove(node);
        }
    }

    public boolean empty() {
        return this.nodes.isEmpty();
    }

    public static class Node {
        private final BlockPos position;
        private final Type type;
        private final List<Node> connections = new ArrayList<>();
        private IManaStorage attached;

        public Node(BlockPos position, Type type) {
            this.position = position.immutable();
            this.type = type;
        }

        public static Node fromNbt(CompoundTag tag) {
            BlockPos pos = BlockPos.of(tag.getLong("position"));
            Type type = Type.CODEC.byName(tag.getString("type"));
            return new Node(pos, type);
        }

        public void addConnection(Node other) {
            this.connections.add(other);
        }

        public BlockPos getPosition() {
            return position;
        }

        public CompoundTag toNbt(List<Node> nodes) {
            CompoundTag tag = new CompoundTag();
            tag.putLong("position", this.position.asLong());
            tag.putString("type", this.type.getSerializedName());

            ListTag connections = new ListTag();
            this.connections.forEach(n -> connections.add(IntTag.valueOf(nodes.indexOf(n))));
            tag.put("connections", connections);
            return tag;
        }

        public IManaStorage getAttached(Level level) {
            if (attached != null) return attached;
            if (this.type == Type.PORT) {
                ((ManaPortBlockEntity) level.getBlockEntity(this.position)).checkAttached();
            }
            return null;
        }

        public List<Node> getConnected() {
            return connections;
        }

        public enum Type implements StringRepresentable {
            RELAY,
            PORT;

            @SuppressWarnings("deprecation")
            public static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

            @Override
            public @NotNull String getSerializedName() {
                return name().toLowerCase();
            }
        }
    }

    public static ManaDistributionNetwork create(ListTag tag) {
        List<Pair<CompoundTag, Node>> nodes = new ArrayList<>();
        for (int i = 0; i < tag.size(); i++) {
            CompoundTag cTag = tag.getCompound(i);
            nodes.add(Pair.of(cTag, ManaDistributionNetwork.Node.fromNbt(cTag)));
        }
        ManaDistributionNetwork network = new ManaDistributionNetwork();
        for (Pair<CompoundTag, ManaDistributionNetwork.Node> pair : nodes) {
            ListTag connections = pair.getFirst().getList("connections", Tag.TAG_INT);
            Node node = pair.getSecond();
            for (int j = 0; j < connections.size(); j++) {
                node.addConnection(nodes.get(connections.getInt(j)).getSecond());
            }
            network.nodes.add(node);
            network.byBlock.put(node.position, node);
        }
        return network;
    }

    public ListTag save() {
        ListTag tag = new ListTag();
        this.nodes.forEach(node -> tag.add(node.toNbt(this.nodes)));
        return tag;
    }

    /**
     * geographically the shortest path between two nodes using <b>Dijkstra</b>'s algorithm, suggested by gpt
     */
    public static List<Node> getPath(Node start, Node goal) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(goal)) {
                // Reconstruct path
                List<Node> path = new LinkedList<>();
                while (current != null) {
                    path.add(0, current);
                    current = previous.get(current);
                }
                return path;
            }

            for (Node neighbor : current.connections) {
                double newDist = distances.get(current) + current.position.distToCenterSqr(neighbor.position.getCenter());
                if (newDist < distances.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return null; // No path found
    }
}
