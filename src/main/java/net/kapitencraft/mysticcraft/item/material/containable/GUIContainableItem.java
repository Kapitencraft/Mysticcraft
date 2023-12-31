package net.kapitencraft.mysticcraft.item.material.containable;

import net.kapitencraft.mysticcraft.gui.containable.ContainableMenu;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class GUIContainableItem<T extends Item> extends Item implements IModItem {

    //TODO add menu and screen

    private final int stackSize;
    private final int stackAmount;
    private final String nameTransKey;

    public GUIContainableItem(Properties properties, int stackSize, int stackAmount, String nameTransKey) {
        super(properties);
        this.stackSize = stackSize;
        this.stackAmount = stackAmount;
        this.nameTransKey = nameTransKey;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new MenuProviderImpl(stack));
        }
        return super.use(level, player, hand);
    }

    public int getStackAmount() {
        return stackAmount;
    }

    public int getStackSize() {
        return stackSize;
    }


    private class MenuProviderImpl implements MenuProvider {
        private final ItemStack stack;

        private MenuProviderImpl(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.translatable(GUIContainableItem.this.nameTransKey);
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
            return new ContainableMenu<T>(id, inv, GUIContainableItem.this, stack);
        }
    }
}
