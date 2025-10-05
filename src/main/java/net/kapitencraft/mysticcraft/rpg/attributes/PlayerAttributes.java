package net.kapitencraft.mysticcraft.rpg.attributes;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerAttributes {
    private static final UUID ENTRY_UUID = UUID.fromString("87b42d58-9ad7-461f-8b1e-4d295a389467");

    Map<Type, Integer> entries = new HashMap<>();

    public enum Type implements StringRepresentable {
        CONSTITUTION(
                new AttributeEntry(ExtraAttributes.VITALITY.get(), 2)
        ),
        INTELLIGENCE(
                new AttributeEntry(ExtraAttributes.MAX_MANA.get(), 4),
                new AttributeEntry(ExtraAttributes.WISDOM.get(), 1)
        ),
        STRENGHT(
                new AttributeEntry(ExtraAttributes.STRENGTH.get(), 3),
                new AttributeEntry(ExtraAttributes.MINING_SPEED.get(), 1)
        ),
        ;

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        private final AttributeEntry[] entries;

        Type(AttributeEntry... entries) {
            this.entries = entries;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }

        public void update(Player player, int level) {
            AttributeMap attributes = player.getAttributes();
            for (AttributeEntry entry : this.entries) {
                AttributeInstance instance = attributes.getInstance(entry.attribute);
                if (instance == null) throw new IllegalStateException("unknown attribute on entity: " + ForgeRegistries.ATTRIBUTES.getKey(entry.attribute));
                instance.removeModifier(ENTRY_UUID);
                instance.addPermanentModifier(new AttributeModifier(ENTRY_UUID, "player_attribute:" + getSerializedName(), entry.scale * level, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    private record AttributeEntry(Attribute attribute, double scale) {
    }
}
