package net.kapitencraft.mysticcraft.client.render.overlay.holder;

import net.kapitencraft.mysticcraft.client.render.OverlayRenderer;
import net.kapitencraft.mysticcraft.client.render.overlay.PositionHolder;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.function.Function;

public class SimpleHolder extends RenderHolder {
    private final Function<LocalPlayer, Component> mapper;
    public SimpleHolder(PositionHolder pos, Function<LocalPlayer, Component> mapper, OverlayRenderer.RenderType type) {
        super(pos, type);
        this.mapper = mapper;
    }

    @Override
    public float getWidth(LocalPlayer player, Font font) {
        return font.width(mapper.apply(player));
    }

    @Override
    public float getHeight(LocalPlayer player, Font font) {
        return 9;
    }

    @Override
    public void render(float posX, float posY, LocalPlayer player) {
        Vec2 loc = getLoc(posX, posY);
        renderString(mapper.apply(player), loc.x, loc.y);
    }
}
