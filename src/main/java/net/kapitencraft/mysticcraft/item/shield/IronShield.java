package net.kapitencraft.mysticcraft.item.shield;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class IronShield extends ModShieldItem {
    public IronShield() {
        super(new Properties().rarity(Rarity.UNCOMMON), 621);
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack p_41402_, ItemStack p_41403_) {
        return p_41403_.is(Items.IRON_INGOT);
    }
}
