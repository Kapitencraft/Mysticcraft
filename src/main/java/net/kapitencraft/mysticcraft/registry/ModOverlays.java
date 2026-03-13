package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.overlay.OverlayProperties;
import net.kapitencraft.kap_lib.overlay.registry.custom.OverlayRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModOverlays {

    DeferredRegister<OverlayProperties> REGISTRY = MysticcraftMod.registry(OverlayRegistries.Keys.OVERLAY_PROPERTIES);

    Holder<OverlayProperties> CAST_CHARGE = REGISTRY.register("cast_charge", () -> new OverlayProperties(-50, 50, 1, 1, OverlayProperties.Alignment.MIDDLE, OverlayProperties.Alignment.BOTTOM_RIGHT));
    Holder<OverlayProperties> SPELL_SELECTION = REGISTRY.register("spell_selection", () -> new OverlayProperties(142, 1, 1, 1, OverlayProperties.Alignment.BOTTOM_RIGHT, OverlayProperties.Alignment.TOP_LEFT));
}
