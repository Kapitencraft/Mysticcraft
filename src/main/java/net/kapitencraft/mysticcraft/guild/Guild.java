package net.kapitencraft.mysticcraft.guild;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class Guild {
    private static final Codec<Guild> CODEC = RecordCodecBuilder.create(guildInstance -> guildInstance.group(
            Codec.STRING.fieldOf("name").forGetter(Guild::getName),
            TagHelper.UUID_CODEC.fieldOf("owner").forGetter(Guild::getOwner),
            ItemStack.CODEC.fieldOf("banner").forGetter(Guild::getBanner),
            GuildUpgradeInstance.CODEC.fieldOf("upgrades").forGetter(Guild::getUpgrades),
            GuildPlayerHolder.CODEC.listOf().fieldOf("players").forGetter(Guild::save)
        ).apply(guildInstance, Guild::createFromData)
    );

    private static Guild createFromData(String name, UUID owner, ItemStack banner, GuildUpgradeInstance instance, List<GuildPlayerHolder> holders) {
        Guild guild = new Guild(name, owner, banner, instance);
        guild.recreate(holders);
        return guild;
    }

    private final String name;
    private boolean isPublic = false;
    private final UUID owner;
    private final List<UUID> members = new ArrayList<>();
    private final HashMap<UUID, GuildRank> ranks = new HashMap<>();
    private final HashMap<UUID, String> invites = new HashMap<>();
    private final List<Guild> inWar = new ArrayList<>();
    private final ItemStack banner;
    private final GuildUpgradeInstance instance;

    private boolean removed = false;

    public Guild(String name, UUID owner, ItemStack banner, GuildUpgradeInstance instance) {
        this.name = name;
        this.owner = owner;
        this.banner = banner;
        this.instance = instance;
    }

    public void setRank(UUID player, GuildRank rank) {
        if (!ranks.containsKey(player) && !removed) {
            ranks.put(player, rank);
        }
    }

    private void recreate(List<GuildPlayerHolder> list) {
        list.forEach(this::add);
    }

    private List<GuildPlayerHolder> save() {
        return MapStream.of(this.ranks).map(GuildPlayerHolder::new).toList();
    }

    private void add(GuildPlayerHolder holder) {
        this.setMember(holder.getPlayerId());
        this.setRank(holder.getPlayerId(), holder.getRank());
    }


    private GuildUpgradeInstance getUpgrades() {
        return instance;
    }
    public final UUID getOwner() {
        return owner;
    }

    public int getProtectionRange() {
        return 16 + (8 * this.instance.getUpgradeLevel(GuildUpgrades.RANGE));
    }

    public boolean upgrade(GuildUpgrades toUpgrade) {
        return this.instance.upgrade(toUpgrade);
    }

    public int getMemberAmount() {
        return members.size();
    }




    public String addInvitation(UUID player) {
        if (!removed) {
            if (members.contains(player)) {
                return "isMember";
            } else if (invites.containsKey(player)) {
                return "isInvited";
            }
            String inviteKey = TextHelper.createRandom(8);
            invites.put(player, inviteKey);
            return inviteKey;
        }
        return "removed";
    }

    public boolean acceptInvitation(Player player, String inviteKey) {
        if (!removed) {
            UUID playerId = player.getUUID();
            if (this.invites.containsKey(playerId) && Objects.equals(this.invites.get(playerId), inviteKey)) {
                this.addMember(player);
                this.invites.remove(playerId);
                return true;
            }
        }
        return false;
    }

    public List<Guild> getInWar() {
        return inWar;
    }

    public void declareWar(Guild guild) {
        if (!removed) {
            inWar.add(guild);
            guild.sendDeclareWar(this);
        }
    }

    public void remove() {
        this.members.clear();
        this.invites.clear();
        this.inWar.clear();
        this.ranks.clear();
        isPublic = false;
        removed = true;
    }

    private void sendDeclareWar(Guild warOpponent) {
        if (!removed) inWar.add(warOpponent);
    }

    public GuildRank getRank(UUID player) {
        return this.ranks.get(player);
    }

    public String getName() {
        return name;
    }

    public boolean kickMember(Player member) {
        if (containsMember(member.getUUID()) && !removed) {
            removeMember(member);
            member.sendSystemMessage(Component.translatable("guild.kick"));
            return true;
        }
        return false;
    }

    public void addMember(@NotNull Player newMember) {
        if (!removed) {
            UUID playerId = newMember.getUUID();
            members.add(playerId);
            ranks.put(playerId, GuildRank.DEFAULT);
            newMember.getPersistentData().putString("GuildName", this.getName());
        }
    }

    private void setMember(UUID member) {
        members.add(member);
        ranks.put(member, GuildRank.DEFAULT);
    }

    private void removeMember(Player player) {
        if (!removed) {
            UUID playerId = player.getUUID();
            members.remove(playerId);
            ranks.remove(playerId);
            player.getPersistentData().remove("GuildName");
        }
    }

    public boolean isPublic() {
        return isPublic && !removed;
    }

    public void setPublic(boolean aPublic) {
        if (!removed) isPublic = aPublic;
    }

    public List<UUID> getAllMembers() {
        return members;
    }

    public boolean containsMember(UUID member) {
        return members.contains(member);
    }

    public CompoundTag saveToTag() {
        CompoundTag tag = new CompoundTag();
        if (removed) return tag;
        tag.putUUID("owner", owner);
        tag.put("banner", this.banner.save(new CompoundTag()));
        tag.putString("name", this.name);
        tag.put("upgrades", this.instance.save());
        int i = 0;
        for (UUID player : getAllMembers()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.putUUID("name", player);
            playerTag.putString("rank", ranks.get(player).getRegistryName());
            tag.put("Player" + i, playerTag);
        }
        tag.putInt("size", i);
        return tag;
    }

    public String promotePlayer(UUID player) {
        if (members.contains(player) && !removed) {
            GuildRank currentRank = ranks.get(player);
            ranks.remove(player);
            GuildRank nextRank;
            switch (currentRank) {
                case MOD -> nextRank = GuildRank.ADMIN;
                case DEFAULT -> nextRank = GuildRank.MOD;
                default -> {
                    return "alreadyMax";
                }
            }
            ranks.put(player, nextRank);
            return "success";
        }
        return player == owner ? "owner" : "notMember";
    }


    public ItemStack getBanner() {
        return banner;
    }

    @SuppressWarnings("ALL")
    public static Guild loadFromTag(CompoundTag tag, MinecraftServer server) {
        PlayerList playerList = server.getPlayerList();
        int i = 0;
        Guild guild = new Guild(tag.getString("name"), UUID.fromString(tag.getString("owner")), ItemStack.of(tag.getCompound("banner")), GuildUpgradeInstance.load(tag.getCompound("upgrades")));
        while (tag.contains("Player" + i, 10)) {
            CompoundTag tag1 = tag.getCompound("Player" + i);
            UUID player = UUID.fromString(tag1.getString("name"));
            guild.setMember(player);
            guild.setRank(player, GuildRank.getByName(tag1.getString("rank")));
        }
        if (tag.getInt("size") == i) return guild;
        throw new RuntimeException("loaded tag without real guild");
    }

    public enum GuildRank implements StringRepresentable {
        DEFAULT("default", "Default"),
        MOD("moderator", "Moderator"),
        ADMIN("admin", "Admin"),
        OWNER("owner", "Owner");


        static final Codec<GuildRank> CODEC = StringRepresentable.fromEnum(GuildRank::values);
        private final String registryName;
        private final String name;


        GuildRank(String registryName, String name) {
            this.registryName = registryName;
            this.name = name;
        }

        public String getRegistryName() {
            return registryName;
        }

        public String getIGName() {
            return name;
        }

        public static GuildRank getByName(String name) {
            for (GuildRank rank : values()) {
                if (Objects.equals(rank.registryName, name)) {
                    return rank;
                }
            }
            return DEFAULT;
        }

        @Override
        public @NotNull String getSerializedName() {
            return registryName;
        }
    }
}