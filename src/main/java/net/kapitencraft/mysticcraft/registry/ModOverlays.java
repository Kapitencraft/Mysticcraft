package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.client.overlay.OverlayProperties;
import net.kapitencraft.kap_lib.registry.custom.core.ExtraRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModOverlays {

    DeferredRegister<OverlayProperties> REGISTRY = MysticcraftMod.registry(ExtraRegistries.Keys.OVERLAY_PROPERTIES);

    RegistryObject<OverlayProperties> CAST_CHARGE = REGISTRY.register("cast_charge", () -> new OverlayProperties(-50, 50, 1, 1, OverlayProperties.Alignment.MIDDLE, OverlayProperties.Alignment.BOTTOM_RIGHT));
    RegistryObject<OverlayProperties> SPELL_SELECTION = REGISTRY.register("spell_selection", () -> new OverlayProperties(142, 1, 1, 1, OverlayProperties.Alignment.BOTTOM_RIGHT, OverlayProperties.Alignment.TOP_LEFT));
}
