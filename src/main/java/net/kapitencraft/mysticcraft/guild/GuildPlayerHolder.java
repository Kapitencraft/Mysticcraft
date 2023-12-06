package net.kapitencraft.mysticcraft.guild;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.helpers.TagHelper;

import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
public class GuildPlayerHolder {
    public static final Codec<GuildPlayerHolder> CODEC = RecordCodecBuilder.create(guildPlayerHolderInstance ->
            guildPlayerHolderInstance.group(
                    TagHelper.UUID_CODEC.fieldOf("name").forGetter(GuildPlayerHolder::getPlayerId),
                    Guild.GuildRank.CODEC.fieldOf("rank").forGetter(GuildPlayerHolder::getRank)
            ).apply(guildPlayerHolderInstance, GuildPlayerHolder::new)
    );
    private final UUID playerId;
    private final Guild.GuildRank rank;

    public GuildPlayerHolder(UUID playerId, Guild.GuildRank rank) {
        this.playerId = playerId;
        this.rank = rank;
    }

    public Guild.GuildRank getRank() {
        return rank;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
