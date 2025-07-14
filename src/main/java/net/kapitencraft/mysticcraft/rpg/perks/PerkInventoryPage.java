package net.kapitencraft.mysticcraft.rpg.perks;

import net.kapitencraft.kap_lib.inventory.menu.SlotAdder;
import net.kapitencraft.kap_lib.inventory.page.InventoryPage;
import net.kapitencraft.mysticcraft.registry.ModInventoryPages;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class PerkInventoryPage extends InventoryPage {

    private static final ItemStack symbol = new ItemStack(Items.RED_DYE);

    public PerkInventoryPage(Player player, SlotAdder adder) {
        super(ModInventoryPages.PERKS.get());
    }

    @Override
    public @NotNull ItemStack symbol() {
        return symbol;
    }

    @Override
    public boolean withInventory() {
        return false;
    }
}
