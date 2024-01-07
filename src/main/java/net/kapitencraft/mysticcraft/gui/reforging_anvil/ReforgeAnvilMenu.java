package net.kapitencraft.mysticcraft.gui.reforging_anvil;

import net.kapitencraft.mysticcraft.block.entity.ReforgingAnvilBlockEntity;
import net.kapitencraft.mysticcraft.gui.ModMenu;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.kapitencraft.mysticcraft.item.data.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.item.data.essence.IEssenceData;
import net.kapitencraft.mysticcraft.item.material.EssenceItem;
import net.kapitencraft.mysticcraft.misc.content.EssenceHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReforgeAnvilMenu extends ModMenu<ReforgingAnvilBlockEntity> {
    private static final int SLOTS_Y = 30;

    public ReforgeAnvilMenu(int containerId, Inventory inventory, ReforgingAnvilBlockEntity re) {
        super(ModMenuTypes.REFORGING_ANVIL.get(), containerId, 1, inventory, re);
        this.addPlayerInventories(inventory, 0, 0);
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 43, SLOTS_Y));
            this.addSlot(new SlotItemHandler(handler, 1, 115, SLOTS_Y));
        });
    }

    //TODO fix prestige upgrades cost not working

    public ReforgeAnvilMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (ReforgingAnvilBlockEntity) inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public String handleButtonPress() {
        return this.blockEntity.updateButtonPress(this.player);
    }

    public List<ItemStack> getMatCost() {
        ItemStack upgrade = this.blockEntity.getStack(true);
        if (upgrade.isEmpty()) return List.of();
        return ((IPrestigeAbleItem) upgrade.getItem()).getMatCost(upgrade);
    }

    public List<ItemStack> getRemaining() {
        return InventoryHelper.getRemaining(getMatCost(), this.player);
    }

    public void upgrade() {
        ItemStack upgrade = this.blockEntity.getStack(true);
        Item item = upgrade.getItem();
        if (item instanceof IPrestigeAbleItem prestigeAbleItem) {
            if (prestigeAbleItem.mayPrestige(upgrade, false) && this.getRemaining().isEmpty()) {
                removeItems(prestigeAbleItem.getMatCost(upgrade));
                prestigeAbleItem.prestige(upgrade);
            }
        }
    }

    private void removeItems(List<ItemStack> toRemove) {
        List<ItemStack> allEssence = toRemove.stream().filter(stack -> stack.getItem() instanceof EssenceItem).toList();
        this.player.getCapability(EssenceHolder.ESSENCE).ifPresent(essenceHolder ->
                allEssence.forEach(stack ->
                        essenceHolder.remove(IEssenceData.read(stack), stack.getCount())
                )
        );
        CollectionHelper.sync(toRemove.stream(), this.player, InventoryHelper::removeFromInventory);
    }

    @Override
    public boolean stillValid(@NotNull Player p_38874_) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.REFORGING_ANVIL.getBlock());
    }
}
