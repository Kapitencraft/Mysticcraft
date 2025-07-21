package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.kap_lib.client.gui.BlockEntityMenu;
import net.kapitencraft.mysticcraft.tech.block.entity.GenericFueledGeneratorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class GenericFueledGeneratorMenu extends BlockEntityMenu<GenericFueledGeneratorBlockEntity> {
    protected GenericFueledGeneratorMenu(MenuType<?> menuType, int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(menuType, containerId, inventory, (GenericFueledGeneratorBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    protected GenericFueledGeneratorMenu(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, GenericFueledGeneratorBlockEntity provider) {
        super(menuType, containerId, 1, inventory, provider);

        addPlayerInventories(inventory, 0, 0);
        this.addSlot(new SlotItemHandler(provider.getItems(), 0, 80, 40));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public boolean isLit() {
        return this.blockEntity.isLit();
    }

    public int getLitProgress() {
        return this.blockEntity.getLitProgress();
    }

    public int getManaTankScaledFillPercentage() {
        return 69 * this.blockEntity.getManaStored() / this.blockEntity.getMaxManaStored();
    }

    public Component getManaTooltip() {
        return this.blockEntity.getStorageTooltip();
    }
}
