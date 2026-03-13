package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.network.packets.C2S.SetTargetPriorityPacket;
import net.kapitencraft.mysticcraft.tech.block.UpgradableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public abstract class AbstractTurretBlockEntity extends UpgradableBlockEntity {
    protected final AABB checkArea; //TODO un-finalize when upgrades are added
    private UUID owner;
    protected Entity target;
    protected final TargetSelector targetSelector = new TargetSelector();

    public AbstractTurretBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, double radius) {
        super(pType, pPos, pBlockState);
        this.checkArea = new AABB(pPos.getX() - radius, pPos.getY() - radius, pPos.getZ() - radius, pPos.getX() + radius, pPos.getY() + radius, pPos.getZ() + radius);
    }

    protected void updateTarget() {
        if (this.target != null) {
            if (!checkArea.intersects(this.target.getBoundingBox())) {
                this.unselectTarget();
                this.target = null; //set target to null so super-classes do not implement that behaviour by themselves
            }
        }
        if (this.target == null && this.level != null) {
            this.selectTarget();
        }
    }

    /**
     * called when no target could be found and the turret has no target
     */
    protected abstract void unselectTarget();

    @SuppressWarnings("DataFlowIssue")
    protected void selectTarget() {
        List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, checkArea, living -> living.getUUID() != owner && !living.isRemoved() && !living.isDeadOrDying() && !living.fireImmune());
        if (entities.isEmpty()) return;
        entities.sort(this.targetSelector.comparator);
        int i = 0;
        while (entities.size() > i && !canTarget(entities.get(i))) i++;
        if (entities.size() > i) //only update the target when there's actually a entity to target
            this.target = entities.get(i);
    }

    @Override
    public boolean canUpgrade(ItemStack upgradeModule) {
        return false;
    }

    protected boolean canTarget(LivingEntity living) {
        return this.targetSelector.test(living);
    }

    @Override
    public void upgrade(ItemStack stack) {

    }

    @Override
    public int upgradeSlots() {
        return 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("Owner", this.owner.toString());
        CompoundTag selector = new CompoundTag();
        this.targetSelector.serialize(selector);
        selector.put("Selector", selector);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.owner = UUID.fromString(tag.getString("Owner"));
        this.targetSelector.deserialize(tag.getCompound("Selector"));
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
        List<UUID> toIgnore = this.targetSelector.predicate.playersToIgnore;
        toIgnore.clear(); //clear entries and reset
        toIgnore.add(uuid);
        this.setChanged();
    }

    public TargetSelector getSelector() {
        return this.targetSelector;
    }

    public void setTargetPriority(int slot, int index) {
        this.targetSelector.orders[slot] = TargetPriority.values()[index];
        this.targetSelector.recalculateComparator();
    }

    public static class TargetPredicate implements Predicate<LivingEntity> {
        private final List<UUID> playersToIgnore = new ArrayList<>();

        public void addPlayer(Player player) {
            this.playersToIgnore.add(player.getUUID());
        }

        public void deserialize(CompoundTag tag) {
            ListTag playersToIgnore = tag.getList("PlayersToIgnore", 11);
            playersToIgnore.stream().map(Tag::getAsString).map(UUID::fromString).forEach(this.playersToIgnore::add);
        }

        private void serialize(CompoundTag tag) {
            ListTag listTag = new ListTag();
            playersToIgnore.stream().map(UUID::toString).map(StringTag::valueOf).forEach(listTag::add);
            tag.put("PlayersToIgnore", listTag);
        }

        /**
         * @return whether the given entity may be targeted by the turret
         */
        @Override
        public boolean test(LivingEntity living) {
            return !(living instanceof Player player) || !playersToIgnore.contains(player.getUUID());
        }
    }

    public enum TargetPriority implements StringRepresentable {
        CLOSEST(Comparator.comparingDouble(l -> 0)),
        MOST_HEALTH(Comparator.comparingDouble(LivingEntity::getHealth)),
        MOST_ARMOR(Comparator.comparingInt(LivingEntity::getArmorValue)),
        LEAST_HEALTH(Comparator.comparingDouble(LivingEntity::getHealth).reversed()),
        LEAST_ARMOR(Comparator.comparingInt(LivingEntity::getArmorValue).reversed()),
        FURTHEST_FROM_DEATH(Comparator.comparingDouble(l -> l.getHealth() / l.getMaxHealth())),
        CLOSEST_TO_DEATH(FURTHEST_FROM_DEATH.comparator.reversed()),
        FASTEST(Comparator.comparingDouble(l -> l.getAttributeValue(Attributes.MOVEMENT_SPEED))),
        SLOWEST(FASTEST.comparator.reversed());

        private final Comparator<LivingEntity> comparator;

        static final EnumCodec<TargetPriority> CODEC = StringRepresentable.fromEnum(TargetPriority::values);

        TargetPriority(Comparator<LivingEntity> comparator) {
            this.comparator = comparator;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }

        public String getTranslationKey() {
            return "target_priority." + getSerializedName();
        }
    }

    public class TargetSelector {
        private final TargetPredicate predicate;
        private final TargetPriority[] orders = new TargetPriority[]{TargetPriority.CLOSEST, TargetPriority.CLOSEST, TargetPriority.CLOSEST};
        private Comparator<LivingEntity> comparator;

        private TargetSelector() {
            this.predicate = new TargetPredicate();
            this.recalculateComparator();
        }

        /**
         * recalculates the chained comparator used when selecting a target
         */
        private void recalculateComparator() {
            Comparator<LivingEntity> c = null;
            Set<TargetPriority> prev = new HashSet<>(3);
            for (TargetPriority order : orders) {
                if (prev.contains(order)) continue; //skip multiple
                if (c == null) {
                    c = getComparator(order);
                } else {
                    c = c.thenComparing(getComparator(order));
                }
                prev.add(order);
            }
            this.comparator = c;
        }

        private Comparator<LivingEntity> getComparator(TargetPriority order) {
            if (order == TargetPriority.CLOSEST) {
                Vec3 center = worldPosition.getCenter();
                return Comparator.comparingDouble(l -> l.distanceToSqr(center));
            }
            return order.comparator;
        }

        public void cycleOrder(int index, boolean forward) {
            TargetPriority[] values = TargetPriority.values();
            TargetPriority order = orders[index];
            int ordinal = order.ordinal();
            if (forward) {
                if (++ordinal == values.length) ordinal = 0;
            } else {
                if (--ordinal < 0) {
                    ordinal = values.length - 1;
                }
            }
            orders[index] = values[ordinal];
            PacketDistributor.sendToServer(new SetTargetPriorityPacket(getBlockPos(), index, ordinal));
            this.recalculateComparator();
            setChanged();
        }

        public void serialize(CompoundTag tag) {
            CompoundTag predicateTag = new CompoundTag();
            this.predicate.serialize(predicateTag);
            tag.put("Predicate", predicateTag);
            ListTag orders = new ListTag();
            for (TargetPriority order : this.orders) {
                orders.add(StringTag.valueOf(order.getSerializedName()));
            }
            tag.put("Orders", orders);
        }

        public void deserialize(CompoundTag tag) {
            this.predicate.deserialize(tag.getCompound("Predicate"));
            ListTag list = tag.getList("Orders", 8);
            for (int i = 0; i < list.size(); i++) {
                orders[i] = TargetPriority.CODEC.byName(list.getString(i), TargetPriority.CLOSEST);
            }
            this.recalculateComparator();
        }

        public TargetPriority[] getOrders() {
            return orders;
        }

        protected boolean test(LivingEntity living) {
            return this.predicate.test(living);
        }
    }
}