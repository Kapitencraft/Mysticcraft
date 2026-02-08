package net.kapitencraft.mysticcraft.rpg.traits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.kapitencraft.kap_lib.helpers.ExtraStreamCodecs;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentSync;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PlayerAttributes {
    private static final ResourceLocation ENTRY_ID = MysticcraftMod.res("player_traits");

    public static final Codec<PlayerAttributes> CODEC  = RecordCodecBuilder.create(i -> i.group(
            Codec.unboundedMap(Type.CODEC, Codec.INT).fieldOf("entries").forGetter(t -> t.entries),
            Codec.INT.fieldOf("available").forGetter(t -> t.availableTokens)
    ).apply(i, PlayerAttributes::fromCodec));
    public static final StreamCodec<ByteBuf, PlayerAttributes> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT.apply(ExtraStreamCodecs.map(Type.STREAM_CODEC)), t -> t.entries,
            ByteBufCodecs.INT, t -> t.availableTokens,
            PlayerAttributes::fromCodec
    );

    private static PlayerAttributes fromCodec(Map<Type, Integer> typeIntegerMap, int availableTokens) {
        PlayerAttributes playerAttributes = new PlayerAttributes();
        playerAttributes.entries.putAll(typeIntegerMap);
        playerAttributes.availableTokens = availableTokens;
        return playerAttributes;
    }

    private final Map<Type, Integer> entries = new HashMap<>();
    private int availableTokens = 0;

    @SuppressWarnings("UnstableApiUsage")
    public void tryUpdate(Type traitType, Player player) {
        if (this.availableTokens > 0) {
            this.availableTokens--;
            entries.computeIfPresent(traitType, (type, integer) -> type.update(player, integer + 1));
            AttachmentSync.syncEntityUpdate(player, ModAttachmentTypes.TRAITS.get());
        }
    }

    public void increaseAvailable() {
        this.availableTokens++;
    }

    public enum Type implements StringRepresentable {
        CONSTITUTION(
                new AttributeEntry(ExtraAttributes.VITALITY, 2),
                new AttributeEntry(ExtraAttributes.MANA_REGEN, .1)
        ),
        INTELLIGENCE(
                new AttributeEntry(ExtraAttributes.MAX_MANA, 4),
                new AttributeEntry(ExtraAttributes.WISDOM, 1)
        ),
        STRENGHT(
                new AttributeEntry(ExtraAttributes.STRENGTH, 3),
                new AttributeEntry(Attributes.BLOCK_BREAK_SPEED, 1),
                new AttributeEntry(ExtraAttributes.RANGED_DAMAGE, .5)
        ),
        DEXTERITY(
                new AttributeEntry(Attributes.ATTACK_SPEED, .2),
                new AttributeEntry(Attributes.MOVEMENT_SPEED, .01)
        );

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        public static final StreamCodec<ByteBuf, Type> STREAM_CODEC = ExtraStreamCodecs.enumCodec(values());
        private final AttributeEntry[] entries;

        Type(AttributeEntry... entries) {
            this.entries = entries;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }

        public int update(Player player, int level) {
            AttributeMap attributes = player.getAttributes();
            for (AttributeEntry entry : this.entries) {
                AttributeInstance instance = attributes.getInstance(entry.attribute);
                if (instance == null) throw new IllegalStateException("unknown attribute on entity: " + entry.attribute.getKey().location());
                instance.removeModifier(ENTRY_ID);
                instance.addPermanentModifier(new AttributeModifier(ENTRY_ID, entry.value * level, AttributeModifier.Operation.ADD_VALUE));
            }
            return level;
        }

        private record AttributeEntry(Holder<Attribute> attribute, double value) {
        }
    }
}
