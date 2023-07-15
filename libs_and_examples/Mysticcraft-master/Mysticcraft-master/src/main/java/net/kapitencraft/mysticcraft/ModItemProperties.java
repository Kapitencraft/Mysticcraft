package net.kapitencraft.mysticcraft;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.kapitencraft.mysticcraft.init.MysticcraftModItems;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.kapitencraft.mysticcraft.init.MysticcraftModEnchantments;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemProperties {
	public static void addCustomItemProperties() {
		makeBow(MysticcraftModItems.LONG_BOW.get());
	}

	public static void makeBow(Item bow) {
	ItemProperties.register(bow, new ResourceLocation("pull"), (itemstack, p_174636_, p_174637_, p_174638_) -> {
         if (p_174637_ == null) {
            return 0.0F;
         } else {
            return p_174637_.getUseItem() != itemstack ? 0.0F : (float)((itemstack.getUseDuration() - p_174637_.getUseItemRemainingTicks()) * (1- (0.1 * EnchantmentHelper.getItemEnchantmentLevel(MysticcraftModEnchantments.ELVISH_MASTERY.get(), itemstack))) / 20.0F);
         }
    });
    ItemProperties.register(bow, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
         return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
    });
}}
