package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.mysticcraft.tech.block.entity.GenericFueledGeneratorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GenericFueledGeneratorMenu extends UpgradableBEMenu<GenericFueledGeneratorBlockEntity> {
    protected GenericFueledGeneratorMenu(MenuType<?> menuType, int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(menuType, containerId, inventory, (GenericFueledGeneratorBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    protected GenericFueledGeneratorMenu(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, GenericFueledGeneratorBlockEntity provider) {
        super(menuType, containerId, 1, inventory, provider);

        this.addSlot(new SlotItemHandler(provider.getItems(), 0, 80, 40) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return provider.isValidFuel(stack);
            }
        });
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

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        if (index < 36) {
            ItemStack stack = slots.get(index).getItem();
            if (this.blockEntity.isValidFuel(stack)) {
                this.slots.get(44).safeInsert(stack);
                return ItemStack.EMPTY;
            }
        }
        return super.quickMoveStack(playerIn, index);
    }
}
