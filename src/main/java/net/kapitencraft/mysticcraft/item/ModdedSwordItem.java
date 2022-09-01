package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.logging.Level;

public abstract class ModdedSwordItem extends SwordItem {
    public ModdedSwordItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeleft) {

    }
}
