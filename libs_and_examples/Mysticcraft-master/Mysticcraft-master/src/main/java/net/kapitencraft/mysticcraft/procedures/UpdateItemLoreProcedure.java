package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraft.world.item.ItemStack;

import net.kapitencraft.item.SlivyraItem;
import net.kapitencraft.item.BeamMeUpSpellItem;
import net.minecraft.nbt.CompoundTag;


@Mod.EventBusSubscriber
public class UpdateItemLoreProcedure {
	@SubscribeEvent
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
		if (event.getLeft() instanceof SlivyraItem && event.getRight().is(ItemTags.create(new ResourceLocation("mysticcraft:item_spells")))) {
			ItemStack spell = event.getRight();
			CompoundTag itemtag = spell.getTag;
		}
	}
}
