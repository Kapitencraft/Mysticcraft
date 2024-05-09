package net.kapitencraft.mysticcraft.init.custom;

import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

/**
 * class that contains the {@link IForgeRegistry}s for the custom registries
 */
public interface ModRegistries {

    IForgeRegistry<GlyphEffect> GLYPH_REGISTRY = RegistryManager.ACTIVE.getRegistry(ModRegistryKeys.GLYPH_EFFECTS);
    IForgeRegistry<Requirement<?>> REQUIREMENT_REGISTRY = RegistryManager.ACTIVE.getRegistry(ModRegistryKeys.REQUIREMENTS);
    IForgeRegistry<IRequestable<?, ?>> REQUESTABLES_REGISTRY = RegistryManager.ACTIVE.getRegistry(ModRegistryKeys.REQUESTABLES);
    IForgeRegistry<ReforgingBonus> REFORGE_BONUSES_REGISTRY = RegistryManager.ACTIVE.getRegistry(ModRegistryKeys.REFORGE_BONUSES);
}