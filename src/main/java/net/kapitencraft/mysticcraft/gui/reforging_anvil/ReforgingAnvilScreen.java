package net.kapitencraft.mysticcraft.gui.reforging_anvil;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.ReforgingAnvilBlockEntity;
import net.kapitencraft.mysticcraft.gui.ModScreen;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.C2S.ReforgingPacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ReforgingAnvilScreen extends ModScreen<ReforgingAnvilBlockEntity, ReforgingAnvilMenu> {
    private static final ResourceLocation BUTTON_LOCATION = MysticcraftMod.res("textures/gui/reforge_icon.png");
    public ReforgingAnvilScreen(ReforgingAnvilMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected @NotNull String getTextureName() {
        return "reforging_anvil";
    }

    @Override
    protected void init() {
        super.init();
        ImageButton button = new ImageButton(this.leftPos + 78, this.topPos + 54, 16, 16, 0, 0, 16, BUTTON_LOCATION, 16, 16, press);
        button.setTooltip(Tooltip.create(Component.translatable("reforging.button")));
        this.addRenderableWidget(button);
    }

    private final Button.OnPress press = (button) -> {
        ModMessages.sendToServer(new ReforgingPacket(this.menu.getBlockEntity().getBlockPos(), this.menu.handleButtonPress()));
    };
}