package net.kapitencraft.mysticcraft.guild;

import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class Guild {

    private final String name;
    private boolean isPublic = false;
    private final Player owner;
    private final List<Player> members = new ArrayList<>();
    private final HashMap<Player, GuildRank> ranks = new HashMap<>();
    private final HashMap<Player, String> invites = new HashMap<>();
    private final List<Guild> inWar = new ArrayList<>();
    private final ItemStack banner;
    private final GuildUpgradeInstance instance;

    private boolean removed = false;


    public Guild(String name, Player owner, ItemStack banner, GuildUpgradeInstance instance) {
        this.name = name;
        this.owner = owner;
        this.banner = banner;
        this.instance = instance;
    }

    public void setRank(Player player, GuildRank rank) {
        if (!ranks.containsKey(player) && !removed) {
            ranks.put(player, rank);
        }
    }

    public int getProtectionRange() {
        return 16 + (8 * this.instance.getUpgradeLevel(GuildUpgrades.RANGE));
    }

    public boolean upgrade(GuildUpgrade toUpgrade) {
        return this.instance.upgrade(toUpgrade);
    }

    public int getMemberAmount() {
        return members.size();
    }

    public final Player getOwner() {
        return owner;
    }



    public String addInvitation(Player player) {
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
            if (this.invites.containsKey(player) && Objects.equals(this.invites.get(player), inviteKey)) {
                this.addMember(player);
                this.invites.remove(player);
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

    public GuildRank getRank(Player player) {
        return this.ranks.get(player);
    }

    public String getName() {
        return name;
    }

    public boolean kickMember(Player member) {
        if (containsMember(member) && !removed) {
            removeMember(member);
            member.sendSystemMessage(Component.translatable("guild.kick"));
            return true;
        }
        return false;
    }

    public void addMember(@NotNull Player newMember) {
        if (!removed) {
            members.add(newMember);
            ranks.put(newMember, GuildRank.DEFAULT);
            newMember.getPersistentData().putString("GuildName", this.getName());
        }
    }

    private void removeMember(Player player) {
        if (!removed) {
            members.remove(player);
            ranks.remove(player);
            player.getPersistentData().putString("GuildName", "");
        }
    }

    public boolean isPublic() {
        return isPublic && !removed;
    }

    public void setPublic(boolean aPublic) {
        if (!removed) isPublic = aPublic;
    }

    public List<Player> getAllMembers() {
        return members;
    }

    public boolean containsMember(Player member) {
        return members.contains(member);
    }

    public CompoundTag saveToTag() {
        CompoundTag tag = new CompoundTag();
        if (removed) return tag;
        tag.putString("owner", owner.getStringUUID());
        tag.put("banner", this.banner.save(new CompoundTag()));
        tag.putString("name", this.name);
        tag.put("upgrades", this.instance.save());
        int i = 0;
        for (Player player : getAllMembers()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.putString("name", player.getStringUUID());
            playerTag.putString("rank", ranks.get(player).getRegistryName());
            tag.put("Player" + i, playerTag);
        }
        tag.putInt("size", i);
        return tag;
    }

    public String promotePlayer(Player player) {
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

    public static Guild loadFromTag(CompoundTag tag, MinecraftServer server) {
        PlayerList playerList = server.getPlayerList();
        int i = 0;
        Guild guild = new Guild(tag.getString("name"), playerList.getPlayer(UUID.fromString(tag.getString("owner"))), ItemStack.of(tag.getCompound("banner")), GuildUpgradeInstance.load(tag.getCompound("upgrades")));
        while (tag.contains("Player" + i, 10)) {
            CompoundTag tag1 = tag.getCompound("Player" + i);
            Player player = playerList.getPlayer(UUID.fromString(tag1.getString("name")));
            guild.addMember(player);
            guild.setRank(player, GuildRank.getByName(tag1.getString("rank")));
        }
        if (tag.getInt("size") == i) return guild;
        throw new RuntimeException("loaded tag without real guild");
    }

    public enum GuildRank {
        DEFAULT("default", "Default"),
        MOD("moderator", "Moderator"),
        ADMIN("admin", "Admin"),
        OWNER("owner", "Owner");


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
    }
}