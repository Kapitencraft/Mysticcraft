package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.item.ItemStack;

public class AOTEBaseManaProcedure {
	public static void execute(ItemStack itemstack) {
		itemstack.getOrCreateTag().putDouble("manause", 50);
	}
}
