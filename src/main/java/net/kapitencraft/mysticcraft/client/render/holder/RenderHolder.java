package net.kapitencraft.mysticcraft.client.render.holder;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.render.RenderController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

public abstract class RenderHolder {
    private final RenderController.PositionHolder pos;
    protected final PoseStack poseStack = new PoseStack();

    public RenderHolder(RenderController.PositionHolder pos, RenderController.RenderType type) {
        this.pos = pos;
        type.onInit.accept(this.poseStack);
        this.poseStack.scale(pos.getSX(), pos.getSY(), 1);
    }

    protected Vec2 getLoc(int posX, int posY) {
        return new Vec2(posX + pos.getX(), posY + pos.getY());
    }

    protected void renderString(Component toWrite, float x, float y) {
        Minecraft.getInstance().font.draw(poseStack, toWrite, x, y, -1);
    }


    public abstract void render(int posX, int posY, LocalPlayer player);

    public RenderController.PositionHolder getPos() {
        return pos;
    }
}
