package net.kapitencraft.mysticcraft.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {

    public static final ForgeTier SLIVYRA_SWORD = new ForgeTier(4, 1500, 2f, 1, 19, BlockTags.WITHER_IMMUNE, () -> Ingredient.of(Items.WITHER_ROSE));
}
