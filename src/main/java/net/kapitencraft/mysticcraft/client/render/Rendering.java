package net.kapitencraft.mysticcraft.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.MiscRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Rendering {

    private static final List<RenderHolder> list = new ArrayList<>();

    static {
        addRenderer(new RenderHolder(
                new PositionHolder(-203, -116),
                player -> Component.literal(MathHelper.round(player.getAttributeValue(ModAttributes.MANA.get()), 1) + " [+" + MathHelper.round(player.getPersistentData().getDouble(MiscRegister.OVERFLOW_MANA_ID), 1) + " Overflow] §r / §1" + player.getAttributeValue(ModAttributes.MAX_MANA.get()) + " (+" + MathHelper.round(player.getPersistentData().getDouble("manaRegen") * 20, 2) + "/s)").withStyle(ChatFormatting.BLUE),
                RenderType.BIG
        ));
        addRenderer(new RenderHolder(
                new PositionHolder(-90, 330),
                player -> Component.literal("Protection: " + getDamageProtection(player) + "%").withStyle(ChatFormatting.DARK_BLUE),
                RenderType.SMALL
        ));
        addRenderer(new RenderHolder(
                new Rendering.PositionHolder(-90, 340),
                player -> Component.literal("Effective HP: " + MathHelper.defRound(player.getHealth() * 100 / (100 - getDamageProtection(player)))).withStyle(ChatFormatting.DARK_AQUA),
                RenderType.SMALL
        ));
        addRenderer(new RenderHolder(
                new PositionHolder(-90, 350),
                player -> Component.literal("Current Speed: " + MathHelper.defRound(player.getDeltaMovement().length()) + " b/s").withStyle(ChatFormatting.YELLOW),
                RenderType.SMALL
        ));

    }
    private static double getDamageProtection(LivingEntity living) {
        return MathHelper.defRound(100 - MathHelper.calculateDamage(100, living.getAttributeValue(Attributes.ARMOR), living.getAttributeValue(Attributes.ARMOR_TOUGHNESS)));
    }

    @SubscribeEvent
    public static void overlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("main", Rendering::render);
    }

    private static void render(ForgeGui forgeGui, PoseStack stack, float partialTicks, int screenWidth, int screenHeight) {
        int posX = screenWidth / 2;
        int posY = screenHeight / 2;
        Player entity = Minecraft.getInstance().player;
        if (entity != null) {
            list.forEach(renderHolder -> {
                Vec2 pos = renderHolder.pos.makePos();
                renderHolder.type.onInit.accept(stack);
                render(stack, renderHolder.provider.apply(entity), posX + pos.x, posY + pos.y);
                renderHolder.type.onDone.accept(stack);
                });
        }

    }

    public static void addRenderer(RenderHolder holder) {
        list.add(holder);
    }

    private static void render(PoseStack stack, Component toWrite, float x, float y) {
        Minecraft.getInstance().font.draw(stack, toWrite, x, y, -1);
    }

    public static class RenderHolder {
        private final PositionHolder pos;
        private final Function<Player, Component> provider;
        private final RenderType type;

        public RenderHolder(PositionHolder pos, Function<Player, Component> provider, RenderType type) {
            this.pos = pos;
            this.provider = provider;
            this.type = type;
        }
    }

    public static class PositionHolder {
        private final Supplier<Float> x;
        private final Supplier<Float> y;

        public PositionHolder(Supplier<Float> x, Supplier<Float> y) {
            this.x = x;
            this.y = y;
        }

        public PositionHolder(float x, float y) {
            this.x = () -> x;
            this.y = () -> y;
        }

        public Vec2 makePos() {
            return new Vec2(x.get(), y.get());
        }
    }

    public enum RenderType {
        BIG(stack -> {}, stack -> {}),
        MEDIUM(stack -> stack.scale(0.75f, 0.75f, 0.75f), stack -> stack.scale((float) (1/0.75), (float) (1/0.75), (float) (1/0.75))),
        SMALL(stack -> stack.scale(0.5f, 0.5f, 0.5f), stack -> stack.scale(2, 2, 2));

        private final Consumer<PoseStack> onInit;
        private final Consumer<PoseStack> onDone;

        RenderType(Consumer<PoseStack> onInit, Consumer<PoseStack> onDone) {
            this.onInit = onInit;
            this.onDone = onDone;
        }
    }
}
