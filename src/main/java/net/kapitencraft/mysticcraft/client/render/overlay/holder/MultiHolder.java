package net.kapitencraft.mysticcraft.client.render.overlay.holder;

import net.kapitencraft.mysticcraft.client.render.overlay.PositionHolder;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.List;
import java.util.function.Function;

public class MultiHolder extends RenderHolder {
    private final float yChange;
    private final List<Function<LocalPlayer, Component>> list;
    public MultiHolder(PositionHolder pos, float yChange, List<Function<LocalPlayer, Component>> allText) {
        super(pos);
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
    public void render(float width, float height, LocalPlayer player) {
        Vec2 loc = getLoc(width, height);
        for (int i = 0; i < list.size(); i++) {
            Function<LocalPlayer, Component> mapper = list.get(i);
            renderString(mapper.apply(player), loc.x, loc.y - yChange * i);
        }
    }
}
