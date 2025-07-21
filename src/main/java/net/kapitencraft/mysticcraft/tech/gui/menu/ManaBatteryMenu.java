package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.kap_lib.client.gui.BlockEntityMenu;
import net.kapitencraft.mysticcraft.registry.ModMenuTypes;
import net.kapitencraft.mysticcraft.tech.block.entity.ManaBatteryBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class ManaBatteryMenu extends BlockEntityMenu<ManaBatteryBlockEntity> {
    //TODO add slots as soon as items become chargeable

    public ManaBatteryMenu(int containerId, Inventory inventory, ManaBatteryBlockEntity provider) {
        super(ModMenuTypes.MANA_BATTERY.get(), containerId, 0, inventory, provider);

        this.addPlayerInventories(inventory, 0, 0);
    }

    public ManaBatteryMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, (ManaBatteryBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public int getManaTankScaledFillPercentage() {
        return 74 * this.blockEntity.getManaStored() / this.blockEntity.getMaxManaStored();
    }

    public Component getManaTooltip() {
        return this.blockEntity.getStorageTooltip();
    }
}
