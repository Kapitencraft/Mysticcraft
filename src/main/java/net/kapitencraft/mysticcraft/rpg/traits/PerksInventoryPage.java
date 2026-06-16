package net.kapitencraft.mysticcraft.rpg.traits;

import net.kapitencraft.kap_lib.inventory_page.menu.SlotAdder;
import net.kapitencraft.kap_lib.inventory_page.page.InventoryPage;
import net.kapitencraft.mysticcraft.registry.ModInventoryPageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class PerksInventoryPage extends InventoryPage {
    private final Player player;

    public PerksInventoryPage(Player player, SlotAdder adder) {
        super(ModInventoryPageTypes.PERKS.get());
        this.player = player;
    }

    @Override
    public @NotNull ItemStack symbol() {
        return new ItemStack(Items.BOOK);
    }

    @Override
    public boolean withInventory() {
        return false;
    }
}
