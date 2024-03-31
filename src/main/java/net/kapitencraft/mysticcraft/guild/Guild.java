package net.kapitencraft.mysticcraft.guild;

import com.mojang.authlib.GameProfile;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public class Guild {

    //Fields
    private final String name;
    private final MemberContainer container;
    private boolean isPublic = false;
    private final WarInstance wars;
    private final ItemStack banner;
    private final GuildUpgradeInstance instance;

    private boolean removed = false;

    public Guild(String name, UUID ownerId, ItemStack banner, GuildUpgradeInstance instance) {
        this.name = name;
        this.container = new MemberContainer(ownerId);
        this.banner = banner;
        this.instance = instance;
        this.wars = new WarInstance(Lists.newArrayList());
    }

    public Guild(CompoundTag in) {
        this.name = in.getString("Name");
        this.container = loadMembers(in.getCompound("Members"));
        this.banner = ItemStack.of(in.getCompound("Banner"));
        this.instance = GuildUpgradeInstance.load(in.getCompound("Upgrades"));
        this.wars = loadWarOpponents(in.getCompound("Wars"));
        if (in.contains("PlayerNames", 9)) {
            this.container.memberNames.putAll(IOHelper.readMap(in, "PlayerNames", CompoundTag::getUUID, CompoundTag::getString).toMap());
        }
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", name);
        tag.put("Members", container.save());
        tag.put("Banner", this.banner.save(new CompoundTag()));
        tag.put("Upgrades", instance.save());
        tag.put("Wars", wars.save());
        return tag;
    }

    public CompoundTag saveWithPlayers() {
        CompoundTag tag = save();
        tag.put("PlayerNames", IOHelper.writeMap(this.container.memberNames, CompoundTag::putUUID, CompoundTag::putString));
        return tag;
    }


    public void setRank(Player player, GuildRank rank) {
        this.container.addMember(player, rank);
    }

    public int getProtectionRange() {
        return 16 + (8 * this.instance.getUpgradeLevel(GuildUpgrades.RANGE));
    }

    public boolean upgrade(GuildUpgrades toUpgrade) {
        return this.instance.upgrade(toUpgrade);
    }

    public int getMemberAmount() {
        return container.size();
    }

    public String addInvitation(Player player) {
        return this.container.addInvitation(player);
    }

    public boolean isOwner(Player player) {
        return this.container.cachedOwner == player;
    }

    boolean isMember(Player player) {
        return this.container.onlineMembers.containsKey(player);
    }

    public Guild reviveMembers(MinecraftServer server) {
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        if (level != null) {
            this.container.load(level);
            server.getPlayerList().getPlayers().forEach(this.container::setOnline);
        }
        return this;
    }

    public void reviveWarOpponents(GuildHandler handler) {
        this.wars.load(handler);
    }

    void setOffline(Player player) {
        this.container.setOffline(player);
    }

    public class MemberContainer {
        public static final String PLAYER_GUILD_NAME_TAG = "GuildName";

        private final Map<UUID, String> invites = new HashMap<>();
        private final Map<UUID, String> memberNames = new HashMap<>();
        private final Map<UUID, Guild.GuildRank> rawMembers = new HashMap<>();
        private final UUID rawOwner;
        Player cachedOwner = null;
        private final Map<Player, Guild.GuildRank> onlineMembers = new HashMap<>();

        public MemberContainer(UUID rawOwner) {
            this.rawOwner = rawOwner;
            this.rawMembers.put(rawOwner, GuildRank.OWNER);
        }

        int size() {
            return rawMembers.size();
        }

        String getOwnerName() {
            return memberNames.get(this.rawOwner);
        }

        public void load(ServerLevel level) {
            MinecraftServer server = level.getServer();
            GameProfileCache cache = server.getProfileCache();
            MysticcraftMod.LOGGER.info("Guild {} is loading their members", Guild.this.getName());
            this.memberNames.putAll(MapStream.of(this.rawMembers.keySet().stream().collect(Collectors.toMap(uuid -> uuid, uuid -> uuid)))
                    .mapValues(cache::get)
                    .mapValues(CollectionHelper::getOptionalOrNull)
                    .filterValues(Objects::nonNull)
                    .mapValues(GameProfile::getName).toMap());
        }

        void setOnline(Player player) {
            if (this.rawMembers.containsKey(player.getUUID())) {
                GuildRank rank = rawMembers.get(player.getUUID());
                this.onlineMembers.put(player, rawMembers.get(player.getUUID()));
                if (rank == GuildRank.OWNER) {
                    if (cachedOwner != null) {
                        throw new IllegalStateException("Found guild (" + Guild.this.getName() + ") with multiple owners (" + cachedOwner.getGameProfile().getName() + " & " + player.getGameProfile().getName() + ")");
                    }
                    cachedOwner = player;
                }
                if (!Objects.equals(player.getGameProfile().getName(), this.memberNames.get(player.getUUID()))) {
                    this.memberNames.put(player.getUUID(), player.getGameProfile().getName());
                }
            }
        }

        public void setOffline(Player player) {
            this.onlineMembers.remove(player);
            if (player == cachedOwner) cachedOwner = null;
        }

        public void disband() {
            this.onlineMembers.keySet().forEach(player -> player.getPersistentData().remove(PLAYER_GUILD_NAME_TAG));
        }

        int getOnlineMembers() {
            return this.onlineMembers.size();
        }

        public String addInvitation(Player player) {
            if (rawMembers.containsKey(player.getUUID())) {
                return "isMember";
            } else if (invites.containsKey(player.getUUID())) {
                return "isInvited";
            }
            String inviteKey = TextHelper.createRandom(8);
            invites.put(player.getUUID(), inviteKey);
            return inviteKey;
        }

        void sendMSGtoAllMembers(Component toSend) {
            this.onlineMembers.keySet().forEach(CollectionHelper.biUsage(toSend, Player::sendSystemMessage));
        }

        public void addMember(Player player) {
            insertNewMember(player, GuildRank.DEFAULT);
        }

        private void insertNewMember(Player player, GuildRank rank) {
            this.onlineMembers.put(player, rank);
            this.rawMembers.put(player.getUUID(), rank);
            player.getPersistentData().putString(PLAYER_GUILD_NAME_TAG, Guild.this.getName());
        }

        public void addMember(Player player, Guild.GuildRank rank) {
            insertNewMember(player, rank);
        }

        boolean acceptInvite(Player player, String inviteKey) {
            UUID playerId = player.getUUID();
            if (this.invites.containsKey(playerId) && Objects.equals(this.invites.get(playerId), inviteKey)) {
                this.addMember(player);
                this.invites.remove(playerId);
                return true;
            }
            return false;
        }

        public void removeMember(Player player) {
            this.onlineMembers.remove(player);
            this.rawMembers.remove(player.getUUID());
            player.getPersistentData().remove(PLAYER_GUILD_NAME_TAG);
        }

        public String promotePlayer(Player player, @Nullable GuildRank rank) {
            if (onlineMembers.containsKey(player) && !removed) {
                GuildRank nextRank;
                GuildRank currentRank = onlineMembers.get(player);
                if (rank != null) {
                    if (currentRank == rank) {
                        return "isSame";
                    }
                    nextRank = rank;
                } else {
                    switch (currentRank) {
                        case DEFAULT -> nextRank = GuildRank.APPRENTICE;
                        case APPRENTICE -> nextRank = GuildRank.OFFICER;
                        case OFFICER -> nextRank = GuildRank.MASTER;
                        default -> {
                            return "alreadyMax";
                        }
                    }
                }
                onlineMembers.put(player, nextRank);
                rawMembers.put(player.getUUID(), nextRank);
                return "success";
            }
            return player == cachedOwner ? "isOwner" : "notMember";
        }

        public CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            tag.putUUID("Owner", rawOwner);
            tag.put("Invites", IOHelper.writeMap(this.invites, CompoundTag::putUUID, CompoundTag::putString));
            tag.put("Members", IOHelper.writeMap(this.rawMembers, CompoundTag::putUUID, GuildRank::saveToTag));
            return tag;
        }
    }

    public void setOnline(Player player) {
        this.container.setOnline(player);
    }

    MemberContainer loadMembers(CompoundTag tag) {
        UUID owner = tag.getUUID("Owner");
        Map<UUID, String> invites = IOHelper.readMap(tag, "Invites", CompoundTag::getUUID, CompoundTag::getString).toMap();
        Map<UUID, GuildRank> members = IOHelper.readMap(tag, "Members", CompoundTag::getUUID, GuildRank::readFromTag).toMap();
        MemberContainer container = new MemberContainer(owner);
        container.rawMembers.putAll(members);
        container.invites.putAll(invites);
        return container;
    }

    public void disband() {
        this.container.disband();
    }

    WarInstance loadWarOpponents(CompoundTag tag) {
        return new WarInstance(IOHelper.readList(tag, "Opponents", String.class, 8).toList());
    }

    public boolean acceptInvitation(Player player, String inviteKey) {
        return this.container.acceptInvite(player, inviteKey);
    }

    public String promote(Player player, @Nullable GuildRank rank) {
        return this.container.promotePlayer(player, rank);
    }

    public WarInstance getWarInstance() {
        return wars;
    }

    public class WarInstance {
        private final List<Guild> opponents = new ArrayList<>();
        private final List<String> rawOpponents;

        public WarInstance(List<String> rawOpponents) {
            this.rawOpponents = rawOpponents;
        }

        public String finalizeWar(Guild opponent) {
            if (opponents.contains(opponent)) {
                opponent.wars.opponents.remove(Guild.this);
                opponents.remove(opponent);
                return "success";
            }
            return "noOpponent";
        }

        void load(GuildHandler handler) {
            rawOpponents.stream().map(handler::getGuild).forEach(opponents::add);
        }

        public String startWar(Guild other) {
            if (checkStartPossible()) {
                Guild.this.container.sendMSGtoAllMembers(Component.translatable("guild.war.start", other.display()));
                return "success";
            }
            return "fail";
        }

        private boolean checkStartPossible() {
            return Guild.this.container.getOnlineMembers() > Guild.this.container.size() / 2;
        }

        public CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            tag.put("Opponents", IOHelper.writeList(rawOpponents, StringTag::valueOf));
            return tag;
        }
    }


    private Component display() {
        return Component.literal(this.getName()).withStyle(Style.EMPTY
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextHelper.listToPlainText(description(false)))));
    }

    private List<Component> description(boolean suppressed) {
        List<Component> list = Lists.newArrayList();
        list.add(Component.literal(getName()));
        list.add(Component.translatable("guild.owner", this.container.getOwnerName()));
        list.add(Component.translatable("guild.member_count", getMemberAmount()));
        if (suppressed || getMemberAmount() > 5) {

        }
        return list;
    }

    public void remove() {
        isPublic = false;
        removed = true;
    }

    public GuildRank getRank(Player player) {
        return this.container.onlineMembers.get(player);
    }

    public void addMember(Player player) {
        this.container.addMember(player);
    }

    public String getName() {
        return name;
    }

    public boolean memberLeave(Player member) {
        if (containsMember(member.getUUID())) {
            removeMember(member);
            return true;
        }
        return false;
    }

    public boolean kickMember(Player member) {
        if (containsMember(member.getUUID()) && !removed) {
            removeMember(member);
            member.sendSystemMessage(Component.translatable("guild.kick", this.getName()));
            return true;
        }
        return false;
    }

    private void removeMember(Player player) {
        this.container.removeMember(player);
    }

    public boolean isPublic() {
        return isPublic && !removed;
    }

    public void setPublic(boolean aPublic) {
        if (!removed) isPublic = aPublic;
    }

    public boolean containsMember(UUID member) {
        return this.container.rawMembers.containsKey(member);
    }

    public ItemStack getBanner() {
        return banner;
    }

    @SuppressWarnings("ALL")
    public static Guild loadFromTag(CompoundTag tag) {
        try {
            return new Guild(tag);
        } catch (Exception e) {
            MysticcraftMod.LOGGER.warn(Markers.GUILD, "unable to read Guild: {}", e.getMessage());
            return null;
        }
    }

    public enum GuildRank implements StringRepresentable {
        DEFAULT("default", "Default"),
        APPRENTICE("apprentice", "Apprentice"),
        OFFICER("officer", "Officer"),
        MASTER("master", "Master"),
        OWNER("owner", "Owner");


        public static GuildRank readFromTag(CompoundTag tag, String id) {
            return CODEC.byName(tag.getString(id), GuildRank.DEFAULT);
        }

        static final EnumCodec<GuildRank> CODEC = StringRepresentable.fromEnum(GuildRank::values);
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

        public static void saveToTag(CompoundTag tag, String s, GuildRank rank) {
            tag.putString(s, rank.registryName);
        }
    }
}