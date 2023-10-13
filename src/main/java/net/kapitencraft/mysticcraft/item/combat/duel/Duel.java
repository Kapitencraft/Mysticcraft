package net.kapitencraft.mysticcraft.item.combat.duel;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Duel {
    private final List<ServerPlayer> team1 = new ArrayList<>();
    private final List<ServerPlayer> team2 = new ArrayList<>();
    private final ServerPlayer owner;

    private boolean completed = false;

    public Duel(ServerPlayer owner) {
        this.owner = owner;
        this.team1.add(owner);
    }

    public static Duel load(CompoundTag tag, MinecraftServer server) {
        CompoundTag teamTag = tag.getCompound("team1");
        PlayerList list = server.getPlayerList();
        ServerPlayer player = list.getPlayer(UUID.fromString(teamTag.getString("owner")));
        Duel duel = new Duel(player);
        int i = 0;
        while (teamTag.contains("player" + i)) {
            duel.addToTeam(player, list.getPlayer(UUID.fromString(teamTag.getString("player" + i))));
            i++;
        }
        i = 0;
        teamTag = tag.getCompound("team2");
        while (teamTag.contains("player" + i)) {
            duel.addToEnemyTeam(player, list.getPlayer(UUID.fromString(teamTag.getString("player" + i))));
            i++;
        }
        return duel;
    }

    public boolean addToTeam(ServerPlayer newTeammate, ServerPlayer member) {
        if (team1.contains(member)) {
            team1.add(newTeammate);
        } else if (team2.contains(member)) {
            team2.add(newTeammate);
        } else {
            return false;
        }
        return true;
    }

    public boolean addToEnemyTeam(ServerPlayer newMember, ServerPlayer oldMember) {
        if (team1.contains(oldMember)) {
            team2.add(newMember);
        } else if (team2.contains(oldMember)) {
            team1.add(newMember);
        } else {
            return false;
        }
        return true;
    }

    public CompoundTag save() {
        if (!completed) {
            CompoundTag tag = new CompoundTag();
            CompoundTag team1Tag = new CompoundTag();
            List<String> playerUUIDs = team1.stream().map(ServerPlayer::getStringUUID).toList();
            for (int i = 0; i < playerUUIDs.size(); i++) {
                if (Objects.equals(playerUUIDs.get(i), owner.getStringUUID())) {
                    team1Tag.putString("owner", playerUUIDs.get(i));
                } else {
                    team1Tag.putString("player" + i, playerUUIDs.get(i));
                }
            }
            tag.put("team1", team1Tag);
            CompoundTag team2Tag = new CompoundTag();
            List<String> player2UUIDs = team1.stream().map(ServerPlayer::getStringUUID).toList();
            for (int i = 0; i < playerUUIDs.size(); i++) {
                team2Tag.putString("player" + i, player2UUIDs.get(i));
            }
            tag.put("team2", team2Tag);
            return tag;
        }
        return null;
    }

    public boolean isMember(ServerPlayer player) {
        return team1.contains(player) || team2.contains(player);
    }

    public boolean isOwner(ServerPlayer player) {
        return owner == player;
    }

    public boolean close() {
        if (completed) return false;
        this.completed = true;
        return true;
    }

    public int getMemberCount() {
        return team1.size() + team2.size();
    }
}
