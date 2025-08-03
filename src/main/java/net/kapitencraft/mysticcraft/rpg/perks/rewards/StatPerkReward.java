package net.kapitencraft.mysticcraft.rpg.perks.rewards;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.kap_lib.registry.vanilla.VanillaAttributeModifierTypes;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class StatPerkReward implements PerkReward {
    public static final Codec<StatPerkReward> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.unboundedMap(ForgeRegistries.ATTRIBUTES.getCodec(), Entry.CODEC.listOf()).fieldOf("entries").forGetter(StatPerkReward::getEntries)
    ).apply(i, StatPerkReward::new));

    private final Map<Attribute, List<Entry>> entries;

    private StatPerkReward(Map<Attribute, List<Entry>> entries) {
        this.entries = entries;
    }

    public static StatPerkReward.Builder builder() {
        return new Builder();
    }

    @Override
    public void grant(int level, Player player) {
        player.getAttributes().addTransientAttributeModifiers(getModifiers(level));
    }

    private Multimap<Attribute, AttributeModifier> getModifiers(int level) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        this.entries.forEach((attribute, entries1) -> entries1.stream().map(e -> e.apply(level)).forEach(m -> modifiers.put(attribute, m)));
        return modifiers;
    }

    @Override
    public void revoke(Player player) {
        player.getAttributes().removeAttributeModifiers(getModifiers(0)); //only care about the IDs, amount is irrelevant
    }

    @Override
    public Codec<? extends PerkReward> getCodec() {
        return CODEC;
    }

    private Map<Attribute, List<Entry>> getEntries() {
        return entries;
    }

    private record Entry(AttributeModifier.Operation operation, double perLevel, UUID id) {
        private static final Codec<Entry> CODEC = RecordCodecBuilder.create(i -> i.group(
                VanillaAttributeModifierTypes.OPERATION_CODEC.fieldOf("operation").forGetter(Entry::operation),
                Codec.DOUBLE.fieldOf("perLevel").forGetter(Entry::perLevel),
                UUIDUtil.STRING_CODEC.fieldOf("id").forGetter(Entry::id)
        ).apply(i, Entry::new));

        public AttributeModifier apply(int level) {
            return new AttributeModifier(id, "StatPerkReward", perLevel * level, operation);
        }
    }

    public static class Builder implements PerkReward.Builder {
        Map<Attribute, List<Entry>> entries = new HashMap<>();

        public Builder add(Attribute attribute, double perLevel, AttributeModifier.Operation operation) {
            return this.add(attribute, perLevel, operation, UUID.randomUUID());
        }

        public Builder add(Attribute attribute, double perLevel, AttributeModifier.Operation operation, UUID id) {
            entries.computeIfAbsent(attribute, attribute1 -> new ArrayList<>()).add(new Entry(operation, perLevel, id));
            return this;
        }

        @Override
        public PerkReward build() {
            return new StatPerkReward(entries);
        }
    }
}
