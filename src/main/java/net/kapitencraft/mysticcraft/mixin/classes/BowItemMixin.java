package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BowItem.class)
public class BowItemMixin extends Item {

    public BowItemMixin(Properties p_41383_) {
        super(p_41383_);
    }
}