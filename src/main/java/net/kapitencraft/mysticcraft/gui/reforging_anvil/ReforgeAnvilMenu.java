package net.kapitencraft.mysticcraft.gui.reforging_anvil;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.IMenu;
import net.kapitencraft.mysticcraft.gui.NoBEMenu;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.item.capability.dungeon.IReAnUpgradeable;
import net.kapitencraft.mysticcraft.item.capability.essence.IEssenceData;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.item.material.EssenceItem;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.C2S.ReforgeItemPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReforgeAnvilMenu extends NoBEMenu<ReforgeAnvilMenu.ReforgeAnvilContainer> implements IMenu {
    private static final int SLOTS_Y = 30;

    public ReforgeAnvilMenu(int containerId, Inventory inventory) {
        this(containerId, inventory.player, ContainerLevelAccess.NULL);
    }

    public ReforgeAnvilMenu(int containerId, Player player, ContainerLevelAccess access) {
        this(containerId, player, new ReforgeAnvilContainer(), access);
    }

    public ReforgeAnvilMenu(int containerId, Player player, ReforgeAnvilContainer container, ContainerLevelAccess access) {
        super(ModMenuTypes.REFORGING_ANVIL.get(), containerId, container, player.level, player, access);

        this.addPlayerInventories(player.getInventory(), 0, 0);

        this.addSlot(new AnvilSlot(this.container, 0, 43, SLOTS_Y));
        this.addSlot(new AnvilSlot(this.container, 1, 115, SLOTS_Y));
    }

    public String reforge() {
        return this.container.reforge(this.player);
    }

    public List<ItemStack> getMatCost() {
        ItemStack upgrade = getUpgradeStack();
        if (upgrade.isEmpty() || !(upgrade.getItem() instanceof IPrestigeAbleItem)) return List.of();
        return ((IPrestigeAbleItem) upgrade.getItem()).getMatCost(upgrade);
    }

    public List<ItemStack> getRemaining() {
        return InventoryHelper.getRemaining(getMatCost(), this.player);
    }

    public IReAnUpgradeable upgradeable() {
        ItemStack stack = getUpgradeStack();
        return stack.isEmpty() ? null : stack.getItem() instanceof IReAnUpgradeable upgradeable ? upgradeable : null;
    }

    public ItemStack getUpgradeStack() {
        return this.container.getStack(true);
    }

    public void upgrade() {
        ItemStack upgrade = getUpgradeStack();
        Item item = upgrade.getItem();
        if (item instanceof IPrestigeAbleItem prestigeAbleItem) {
            if (prestigeAbleItem.mayUpgrade(upgrade) && (this.getRemaining().isEmpty() || InventoryHelper.isCreativeMode(this.player))) {
                if (!InventoryHelper.isCreativeMode(this.player)) removeItems(prestigeAbleItem.getMatCost(upgrade));
                prestigeAbleItem.upgrade(upgrade);
            }
        }
    }

    private void removeItems(List<ItemStack> toRemove) {
        List<ItemStack> allEssence = toRemove.stream().filter(stack -> stack.getItem() instanceof EssenceItem).toList();
        this.player.getCapability(CapabilityHelper.ESSENCE).ifPresent(essenceHolder ->
                allEssence.forEach(stack ->
                        essenceHolder.remove(IEssenceData.read(stack), stack.getCount())
                )
        );
        toRemove.forEach(stack -> InventoryHelper.removeFromInventory(stack, player));
    }

    @Override
    protected Block getBlock() {
        return ModBlocks.REFORGING_ANVIL.getBlock();
    }

    public void send(String exeRet) {
        this.access.execute((level1, pos) -> ModMessages.sendToServer(new ReforgeItemPacket(exeRet)));
    }

    public void reforgeForId(String reforgeId, ServerPlayer player) {
        Reforge reforge = Reforges.byName(reforgeId);
        if (reforge.isOnlyFromStone()) {
            MysticcraftMod.LOGGER.error("{} tried to apply reforge-stone reforge on an reforge table! disconnecting", player.getGameProfile().getName());
            player.connection.disconnect(Component.translatable("disconnect.invalid_reforge"));
        }
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    //TODO fix reforge not being updated

    private static class AnvilSlot extends Slot {

        public AnvilSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack pStack) {
            return this.getSlotIndex() == 0 && Reforges.getReforge(pStack) == null && Reforges.canBeReforged(pStack) ||
                    this.getSlotIndex() == 1 && pStack.getItem() instanceof IReAnUpgradeable upgradeable && upgradeable.mayUpgrade(pStack);
        }
    }

    public static class ReforgeAnvilContainer extends SimpleContainer {
        public ReforgeAnvilContainer() {
            super(2);
        }

        public ItemStack getStack(boolean upgrade) {
            return this.getItem(upgrade ? 1 : 0);
        }

        public String reforge(Player player) {
            ItemStack stack = getStack(false);
            if (stack == null || stack.isEmpty()) {
                return null;
            }
            if (InventoryHelper.isCreativeMode(player) || InventoryHelper.hasInInventory(List.of(new ItemStack(Items.EMERALD, 5)), player) && Reforges.canBeReforged(stack)) {
                if (!InventoryHelper.isCreativeMode(player)) InventoryHelper.removeFromInventory(new ItemStack(Items.EMERALD, 5), player);
                return Reforges.applyRandom(false, stack).getRegistryName();
            }
            return null;
        }
    }
}
