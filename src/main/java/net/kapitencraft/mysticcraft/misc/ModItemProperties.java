package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.weapon.ranged.bow.ModdedBows;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeBow((ModdedBows) ModItems.LONGBOW.get());
        creatingArmor(ModItems.ENDER_KNIGHT_ARMOR);
    }

    private static void makeBow(ModdedBows item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)((p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / (item.DIVIDER * (1 - (p_174635_.getEnchantmentLevel(ModEnchantments.ELVISH_MASTERY.get()) * 0.1))));
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);

        ItemProperties.register(item, new ResourceLocation("loaded"), (p_174676_, p_174677_, p_174678_, p_174679_) -> p_174678_.getProjectile(new ItemStack(item)) != ItemStack.EMPTY && p_174678_.getProjectile(new ItemStack(item)).getItem() instanceof ArrowItem ? 1.0f : 0.0f);
    }

    private static void creatingArmor(HashMap<EquipmentSlot, RegistryObject<Item>> armorHashMao) {
        for (RegistryObject<Item> registryObject : armorHashMao.values()) {
            Item armorItem = registryObject.get();
            ItemProperties.register(armorItem, new ResourceLocation("dimension"), ((stack, level, living, i) -> {
                ResourceKey<Level> dimension = living.level.dimension();
                if (dimension == Level.END) {
                    return 2;
                } else if (dimension == Level.NETHER) {
                    return 1;
                } else if (dimension == Level.OVERWORLD) {
                    return 0;
                } else {
                    return 0;
                }
            }));
        }
    }
}
