package net.kapitencraft.mysticcraft.item.shield;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class GoldenShield extends ModShieldItem {
    public GoldenShield() {
        super(new Properties().rarity(Rarity.RARE), 678);
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack p_43091_, ItemStack p_43092_) {
        return p_43092_.is(Items.GOLD_INGOT);
    }
}
