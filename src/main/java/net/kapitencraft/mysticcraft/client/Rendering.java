package net.kapitencraft.mysticcraft.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MiscRegister;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Provider;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class Rendering {
    private static final PoseStack BIG_SYMBOLS = new PoseStack();
    private static final PoseStack MEDIUM_SYMBOLS = new PoseStack();
    private static final PoseStack SMALL_SYMBOLS = new PoseStack();

    private static final List<RenderHolder> list = new ArrayList<>();

    static {
        MEDIUM_SYMBOLS.scale(0.75f, 0.75f, 0.75f);
        SMALL_SYMBOLS.scale(0.5f, 0.5f, 0.5f);
        addRenderer(new RenderHolder(
                new Vec2(-203, -116),
                value -> FormattingCodes.BLUE + MathUtils.round(value.getAttributeValue(ModAttributes.MANA.get()), 1) + " [+" + MathUtils.round(value.getPersistentData().getDouble(MiscRegister.OVERFLOW_MANA_ID), 1) + " Overflow] " + FormattingCodes.RESET + " / " + FormattingCodes.DARK_BLUE + value.getAttributeValue(ModAttributes.MAX_MANA.get()) + " (+" + MathUtils.round(value.getPersistentData().getDouble("manaRegen") * 20, 2) + "/s)",
                RenderType.BIG
        ));
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void overlays(RenderGuiOverlayEvent.Pre event) {
        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();
        int posX = w / 2;
        int posY = h / 2;
        Player entity = Minecraft.getInstance().player;
        if (entity != null) {
            list.forEach(renderHolder -> {
                render(getStack(renderHolder.type), renderHolder.provider.provide(entity), posX + renderHolder.pos.x, posY + renderHolder.pos.y, -1);
            });
        }
    }

    public static void addRenderer(RenderHolder holder) {
        list.add(holder);
    }

    private static void render(PoseStack stack, String toWrite, float x, float y, int idk) {
        Minecraft.getInstance().font.draw(stack, toWrite, x, y, idk);
    }

    private static PoseStack getStack(RenderType type) {
        return switch (type) {
            case BIG -> BIG_SYMBOLS;
            case SMALL -> SMALL_SYMBOLS;
            case MEDIUM -> MEDIUM_SYMBOLS;
        };
    }


    public static class RenderHolder {
        private final Vec2 pos;
        private final Provider<String, Player> provider;
        private final RenderType type;

        public RenderHolder(Vec2 pos, Provider<String, Player> provider, RenderType type) {
            this.pos = pos;
            this.provider = provider;
            this.type = type;
        }
    }

    public enum RenderType {
        BIG,
        MEDIUM,
        SMALL
    }
}
