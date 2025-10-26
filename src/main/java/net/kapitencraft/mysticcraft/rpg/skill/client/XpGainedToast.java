package net.kapitencraft.mysticcraft.rpg.skill.client;

import net.kapitencraft.mysticcraft.rpg.skill.Skill;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class XpGainedToast implements Toast {
    private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("toast/recipe");
    private static final DecimalFormat FORMAT = new DecimalFormat("#.#");

    private long lastChanged;
    private boolean changed;
    private float xp, gainedXp;
    private int maxXp, level;
    private final Skill skill;

    public XpGainedToast(Skill skill, float xp, int maxXp, int level, float gainedXp) {
        this.skill = skill;
        this.xp = xp;
        this.maxXp = maxXp;
        this.level = level;
        this.gainedXp = gainedXp;
    }

    @Override
    public @NotNull Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
        if (changed) {
            lastChanged = timeSinceLastVisible;
            changed = false;
        }
        guiGraphics.blitSprite(BACKGROUND_SPRITE, 0, 0, this.width(), this.height());
        guiGraphics.fill(4, this.height() - 5, (int) ((this.width() - 4) * this.xp / this.maxXp), this.height() - 3, 0xFF00FF1B);
        Font font = toastComponent.getMinecraft().font;
        guiGraphics.drawString(font, Component.translatable("skill.toast.gained_xp.title", Component.translatable("skill." + this.skill.getSerializedName())), 30, 7, -11534256, false);
        guiGraphics.drawString(font, Component.translatable("skill.toast.gained_xp.format", FORMAT.format(this.gainedXp)), 30, 18, 0xFFFFFF, false);
        if (level > 0) {
            MutableComponent component = Component.translatable("skill.toast.level_up", level);
            int width = font.width(component);
            guiGraphics.drawString(font, component, this.width() - width - 5, 18, 0xFF00FF1B);
        }
        guiGraphics.renderFakeItem(new ItemStack(this.skill.getItem()), 4, 4);

        return (double)(timeSinceLastVisible - this.lastChanged) >= 5000.0 * toastComponent.getNotificationDisplayTimeMultiplier()
                ? Toast.Visibility.HIDE
                : Toast.Visibility.SHOW;
    }

    @Override
    public @NotNull Skill getToken() {
        return this.skill;
    }

    public static void addOrUpdate(ToastComponent toastComponent, Skill skill, float xp, int maxXp, int level, float gainedXp) {
        XpGainedToast toast = toastComponent.getToast(XpGainedToast.class, skill);
        if (toast == null) {
            toastComponent.addToast(new XpGainedToast(skill, xp, maxXp, level, gainedXp));
        } else {
            toast.update(xp, maxXp, level, gainedXp);
        }
    }

    private void update(float xp, int maxXp, int level, float gainedXp) {
        this.xp = xp;
        this.maxXp = maxXp;
        this.level += level;
        this.gainedXp += gainedXp;
        this.changed = true;
    }
}
