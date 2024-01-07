package net.kapitencraft.mysticcraft.gui.reforging_anvil;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.ReforgingAnvilBlockEntity;
import net.kapitencraft.mysticcraft.gui.screen.HoverTooltip;
import net.kapitencraft.mysticcraft.gui.screen.ModScreen;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.C2S.ReforgingPacket;
import net.kapitencraft.mysticcraft.networking.packets.C2S.UpgradeItemPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReforgeAnvilScreen extends ModScreen<ReforgingAnvilBlockEntity, ReforgeAnvilMenu> {
    private static final int BUTTON_Y_OFFSET = 53;
    private List<ItemStack> cachedRequirements = new ArrayList<>();
    private List<Component> cachedRequireText = new ArrayList<>();

    private static final ResourceLocation BUTTON_LOCATION = MysticcraftMod.res("textures/gui/reforge_icon.png");
    public ReforgeAnvilScreen(ReforgeAnvilMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected @NotNull String getTextureName() {
        return "reforging_anvil";
    }

    @Override
    protected void init() {
        super.init();
        ImageButton reforge = new ImageButton(this.leftPos + 43, this.topPos + BUTTON_Y_OFFSET, 16, 16, 0, 0, 16, BUTTON_LOCATION, 16, 16, this::reforgeUse);
        this.addHoverTooltip(new HoverTooltip(43, BUTTON_Y_OFFSET, 16, 16, List.of(
                Component.translatable("reforge.button"),
                Component.translatable("reforge.cost")
        )));
        this.addHoverTooltip(new HoverTooltip(115, BUTTON_Y_OFFSET, 16, 16, cachedRequireText));
        ImageButton upgrade = new ImageButton(this.leftPos + 115, this.topPos + BUTTON_Y_OFFSET, 16, 16, 0, 0, 16, BUTTON_LOCATION, 16, 16, this::upgradeUse);
        this.addRenderableWidget(reforge);
        this.addRenderableWidget(upgrade);
    }

    @Override
    protected void containerTick() {
        tickRequirements();
    }

    public void tickRequirements() {
        List<ItemStack> remaining = this.menu.getRemaining();
        if (cachedRequirements.equals(remaining) && !cachedRequireText.isEmpty()) return;
        cachedRequirements = remaining;
        List<Component> costExtra = new ArrayList<>();
        costExtra.add(Component.translatable("upgrade.button"));
        if (!remaining.isEmpty()) {
            costExtra.add(Component.translatable("upgrade.cost").withStyle(ChatFormatting.RED));
            costExtra.addAll(remaining.stream().map(ReforgeAnvilScreen::hoverNameWithCount).map(component -> component.withStyle(ChatFormatting.RED)).toList());
        }
        cachedRequireText = costExtra;
        rebuildWidgets();
    }

    private static MutableComponent hoverNameWithCount(ItemStack stack) {
        return Component.literal(String.valueOf(stack.getCount())).append(" ").append(stack.getHoverName());
    }

    private void reforgeUse(Button ignored) {
        String exeRet = this.menu.handleButtonPress();
        if (exeRet == null) return;
        ModMessages.sendToServer(new ReforgingPacket(this.menu.getCapabilityProvider().getBlockPos(), exeRet));
    }
    private void upgradeUse(Button ignored) {
        this.menu.upgrade();
        ModMessages.sendToServer(new UpgradeItemPacket());
    }
}