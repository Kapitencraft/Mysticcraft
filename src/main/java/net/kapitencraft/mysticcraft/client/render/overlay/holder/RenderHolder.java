package net.kapitencraft.mysticcraft.client.render.overlay.holder;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.render.overlay.PositionHolder;
import net.kapitencraft.mysticcraft.client.render.overlay.box.ResizeBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

public abstract class RenderHolder {
    private final PositionHolder pos;
    protected final PoseStack poseStack = new PoseStack();

    public RenderHolder(PositionHolder holder) {
        this.pos = holder;
    }

    public Vec2 getLoc(float screenWidth, float screenHeight) {
        return pos.getLoc(screenWidth, screenHeight);
    }

    public void scale(float x, float y) {
        this.poseStack.scale(x, y, 0);
    }

    public void move(Vec2 toAdd) {
        this.pos.add(toAdd);
    }

    protected void renderString(Component toWrite, float x, float y) {
        Minecraft.getInstance().font.draw(poseStack, toWrite, x, y, -1);
    }

    public ResizeBox newBox(float screenWidth, float screenHeight, LocalPlayer player, Font font) {
        Vec2 loc = this.getLoc(screenWidth, screenHeight);
        float width = this.getWidth(player, font);
        float height = this.getHeight(player, font);
        return new ResizeBox(loc.add(new Vec2(-1, -2)), loc.add(new Vec2(width + 1, height)), this);
    }

    public abstract float getWidth(LocalPlayer player, Font font);
    public abstract float getHeight(LocalPlayer player, Font font);


    public abstract void render(float posX, float posY, LocalPlayer player);

    public PositionHolder getPos() {
        return pos;
    }
}
