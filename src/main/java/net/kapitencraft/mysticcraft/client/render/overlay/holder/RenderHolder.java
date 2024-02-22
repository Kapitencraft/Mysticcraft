package net.kapitencraft.mysticcraft.client.render.overlay.holder;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.render.OverlayRenderer;
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
    private final OverlayRenderer.RenderType type;

    public RenderHolder(PositionHolder pos, OverlayRenderer.RenderType type) {
        this.pos = pos;
        this.type = type;
        if (type.getSize() != 1) this.poseStack.scale(type.getSize(), type.getSize(), 1);
        this.poseStack.scale(pos.getSX(), pos.getSY(), 1);
    }

    private float getSizeScale() {
        return 1 / this.type.getSize();
    }

    public Vec2 getLoc(float screenX, float screenY) {
        return pos.getLoc(screenX, screenY);
    }

    protected void renderString(Component toWrite, float x, float y) {
        Minecraft.getInstance().font.draw(poseStack, toWrite, x, y, -1);
    }

    public ResizeBox newBox(float posX, float posY, LocalPlayer player, Font font) {
        Vec2 loc = this.getLoc(posX, posY);
        float width = this.getWidth(player, font);
        float height = this.getHeight(player, font);
        return new ResizeBox(loc.add(-1f), loc.add(new Vec2(width, height)).add(1f), this.poseStack, 1.5f * getSizeScale(), getSizeScale());
    }

    public abstract float getWidth(LocalPlayer player, Font font);
    public abstract float getHeight(LocalPlayer player, Font font);


    public abstract void render(float posX, float posY, LocalPlayer player);

    public PositionHolder getPos() {
        return pos;
    }
}
