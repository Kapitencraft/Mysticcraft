package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.ModdedBows;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeBow((ModdedBows) ModItems.LONGBOW.get());
    }

    private static void makeBow(ModdedBows item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)((p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / (item.DIVIDER * (1 - (p_174635_.getEnchantmentLevel(ModEnchantments.ELVISH_MASTERY.get()) * 0.1))));
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });

        ItemProperties.register(item, new ResourceLocation("loaded"), (p_174676_, p_174677_, p_174678_, p_174679_) -> {
            return p_174678_.getProjectile(new ItemStack(item)) != ItemStack.EMPTY && p_174678_.getProjectile(new ItemStack(item)).getItem() instanceof ArrowItem ? 1.0f : 0.0f;
        });
    }
}
