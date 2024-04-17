package net.kapitencraft.mysticcraft.client.render.overlay;

import net.minecraft.MethodsReturnNonnullByDefault;

import java.util.UUID;

@MethodsReturnNonnullByDefault
public interface OverlayLocation {
    UUID getUUID();
    PositionHolder getDefault();
}
