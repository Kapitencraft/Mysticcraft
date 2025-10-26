package net.kapitencraft.mysticcraft.rpg.traits;

import net.kapitencraft.kap_lib.inventory.menu.SlotAdder;
import net.kapitencraft.kap_lib.inventory.page.InventoryPage;
import net.kapitencraft.mysticcraft.registry.ModInventoryPageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class TraitsInventoryPage extends InventoryPage {
    private final Player player;

    public TraitsInventoryPage(Player player, SlotAdder adder) {
        super(ModInventoryPageTypes.ATTRIBUTES.get());
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
