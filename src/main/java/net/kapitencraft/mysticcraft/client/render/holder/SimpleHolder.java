package net.kapitencraft.mysticcraft.client.render.holder;

import net.kapitencraft.mysticcraft.client.render.RenderController;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.function.Function;

public class SimpleHolder extends RenderHolder {
    private final Function<LocalPlayer, Component> mapper;
    public SimpleHolder(RenderController.PositionHolder pos, Function<LocalPlayer, Component> mapper, RenderController.RenderType type) {
        super(pos, type);
        this.mapper = mapper;
    }

    @Override
    public void render(int posX, int posY, LocalPlayer player) {
        Vec2 loc = getLoc(posX, posY);
        renderString(mapper.apply(player), loc.x, loc.y);
    }
}
