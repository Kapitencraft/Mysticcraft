package net.kapitencraft.mysticcraft.init.custom;

import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.RegistryBuilder;

public interface ModRegistryBuilders {
    RegistryBuilder<GlyphEffect> GLYPH_EFFECT_REGISTRY_BUILDER = makeBuilder(ModRegistryKeys.GLYPH_EFFECTS);
    RegistryBuilder<Requirement<?>> REQUIREMENT_REGISTRY_BUILDER = makeBuilder(ModRegistryKeys.REQUIREMENTS);
    RegistryBuilder<IRequestable<?, ?>> REQUESTABLE_REGISTRY_BUILDER = makeBuilder(ModRegistryKeys.REQUESTABLES);
    RegistryBuilder<ReforgingBonus> REFORGE_BONUSES_REGISTRY_BUILDER = makeBuilder(ModRegistryKeys.REFORGE_BONUSES);

    private static <T> RegistryBuilder<T> makeBuilder(ResourceKey<Registry<T>> location) {
        return new RegistryBuilder<T>().setName(location.location());
    }
}