package net.kapitencraft.mysticcraft.client.render.holder;

import net.kapitencraft.mysticcraft.client.render.OverlayRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.List;
import java.util.function.Function;

public class MultiHolder extends RenderHolder {
    private final float yChange;
    private final List<Function<LocalPlayer, Component>> list;
    public MultiHolder(OverlayRenderer.PositionHolder pos, OverlayRenderer.RenderType type, float yChange, List<Function<LocalPlayer, Component>> allText) {
        super(pos, type);
        this.yChange = yChange;
        this.list = allText;
    }

    @Override
    public float getWidth(LocalPlayer player, Font font) {
        float f = 0;
        for (Function<LocalPlayer, Component> func : list) {
            Component comp = func.apply(player);
            float f1 = font.width(comp);
            if (f < f1) f = f1;
        }
        return f;
    }

    @Override
    public float getHeight(LocalPlayer player, Font font) {
        return list.size() * -yChange;
    }

    @Override
    public void render(float posX, float posY, LocalPlayer player) {
        Vec2 loc = getLoc(posX, posY);
        for (int i = 0; i < list.size(); i++) {
            Function<LocalPlayer, Component> mapper = list.get(i);
            renderString(mapper.apply(player), loc.x, loc.y - yChange * i);
        }
    }
}
