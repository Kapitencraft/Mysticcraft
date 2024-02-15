package net.kapitencraft.mysticcraft.client.render.holder;

import net.kapitencraft.mysticcraft.client.render.RenderController;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.CollectorHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.List;
import java.util.function.Function;

public class MultiHolder extends RenderHolder {
    private final float yChange;
    private final List<Function<LocalPlayer, Component>> list;
    public MultiHolder(RenderController.PositionHolder pos, RenderController.RenderType type, float yChange, List<Function<LocalPlayer, Component>> allText) {
        super(pos, type);
        this.yChange = yChange;
        this.list = allText;
    }

    @Override
    public float getHeight(LocalPlayer player, Font font) {
        return list.size() * yChange;
    }

    @Override
    public float getWidth(LocalPlayer player, Font font) {
        return list.stream().map(function -> function.apply(player)).collect(CollectorHelper.getBiggest(font::width));
    }

    @Override
    public void render(int posX, int posY, LocalPlayer player) {
        Vec2 loc = getLoc(posX, posY);
        for (int i = 0; i < list.size(); i++) {
            Function<LocalPlayer, Component> mapper = list.get(i);
            renderString(mapper.apply(player), loc.x, loc.y - yChange * i);
        }
    }
}
