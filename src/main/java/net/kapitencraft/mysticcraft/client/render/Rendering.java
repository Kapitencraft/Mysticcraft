package net.kapitencraft.mysticcraft.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.MiscRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class Rendering {
    private static final int baseWidth = 427;
    private static final int baseHeight = 240;
    private static final PoseStack BIG_SYMBOLS = new PoseStack();
    private static final PoseStack MEDIUM_SYMBOLS = new PoseStack();
    private static final PoseStack SMALL_SYMBOLS = new PoseStack();

    private static final List<RenderHolder> list = new ArrayList<>();

    static {
        MEDIUM_SYMBOLS.scale(0.75f, 0.75f, 0.75f);
        SMALL_SYMBOLS.scale(0.5f, 0.5f, 0.5f);
        addRenderer(new RenderHolder(
                new PositionHolder(-203, -116),
                player -> "§9" + MathHelper.round(player.getAttributeValue(ModAttributes.MANA.get()), 1) + " [+" + MathHelper.round(player.getPersistentData().getDouble(MiscRegister.OVERFLOW_MANA_ID), 1) + " Overflow] §r / §1" + player.getAttributeValue(ModAttributes.MAX_MANA.get()) + " (+" + MathHelper.round(player.getPersistentData().getDouble("manaRegen") * 20, 2) + "/s)",
                RenderType.BIG
        ));
        addRenderer(new RenderHolder(
                new PositionHolder(-90, 330),
                player -> "§1Protection: " + getDamageProtection(player) + "%",
                RenderType.SMALL
        ));
        addRenderer(new RenderHolder(
                new Rendering.PositionHolder(-90, 340),
                player -> "§3Effective HP: " + MathHelper.defRound(player.getHealth() * 100 / (100 - getDamageProtection(player))),
                RenderType.SMALL
        ));
        addRenderer(new RenderHolder(
                new PositionHolder(-90, 350),
                player -> "Current Speed: " + MathHelper.defRound(player.getDeltaMovement().length()) + " b/s",
                RenderType.SMALL
        ));

    }
    private static double getDamageProtection(LivingEntity living) {
        return MathHelper.defRound(100 - MathHelper.calculateDamage(100, living.getAttributeValue(Attributes.ARMOR), living.getAttributeValue(Attributes.ARMOR_TOUGHNESS)));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void overlays(RenderGuiOverlayEvent.Pre event) {
        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();
        int posX = w * (w / baseWidth) / 2;
        int posY = h * (h / baseHeight) / 2;
        Player entity = Minecraft.getInstance().player;
        if (entity != null) {
            list.forEach(renderHolder -> {
                Vec2 pos = renderHolder.pos.makePos();
                render(getStack(renderHolder.type), renderHolder.provider.apply(entity), posX + pos.x, posY + pos.y);
            });
        }
    }

    public static void addRenderer(RenderHolder holder) {
        list.add(holder);
    }

    private static void render(PoseStack stack, String toWrite, float x, float y) {
        Minecraft.getInstance().font.draw(stack, toWrite, x, y, -1);
    }

    private static PoseStack getStack(RenderType type) {
        return switch (type) {
            case BIG -> BIG_SYMBOLS;
            case SMALL -> SMALL_SYMBOLS;
            case MEDIUM -> MEDIUM_SYMBOLS;
        };
    }

    public static class RenderHolder {
        private final PositionHolder pos;
        private final Function<Player, String> provider;
        private final RenderType type;

        public RenderHolder(PositionHolder pos, Function<Player, String> provider, RenderType type) {
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
        BIG,
        MEDIUM,
        SMALL
    }
}
