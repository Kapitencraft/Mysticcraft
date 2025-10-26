package net.kapitencraft.mysticcraft.rpg.skill;

import net.kapitencraft.kap_lib.inventory.menu.SlotAdder;
import net.kapitencraft.kap_lib.inventory.page.InventoryPage;
import net.kapitencraft.mysticcraft.registry.ModInventoryPageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class SkillsInventoryPage extends InventoryPage {
    private final Player player;

    public SkillsInventoryPage(Player player, SlotAdder adder) {
        super(ModInventoryPageTypes.SKILLS.get());
        this.player = player;
    }

    @Override
    public @NotNull ItemStack symbol() {
        return new ItemStack(Items.DIAMOND_SWORD);
    }

    @Override
    public boolean withInventory() {
        return false;
    }

    public Player getPlayer() {
        return player;
    }
}
