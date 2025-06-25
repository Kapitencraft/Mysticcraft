package net.kapitencraft.mysticcraft.item.tools;

import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

//TODO 3x3 break? & fix textures
public class HammerItem extends Item {
    public static final TabGroup HAMMER_GROUP = new TabGroup(TabRegister.TabTypes.MOD_MATERIALS);
    public HammerItem(Properties p_41383_, int durability) {
        super(p_41383_.durability(durability));
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.getDamageValue() < this.getMaxDamage(stack);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack old = itemStack.copy();
        old.setDamageValue(old.getDamageValue()+1);
        return old;
    }
}
