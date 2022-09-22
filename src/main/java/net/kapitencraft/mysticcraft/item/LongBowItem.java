package net.kapitencraft.mysticcraft.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LongBowItem extends ModdedBows {
    public final double DIVIDER = 40;
    public static final double ARROW_SPEED_MUL = 5;

    public LongBowItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1320).rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
    }
}
