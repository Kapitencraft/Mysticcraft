package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.item.ItemStack;

public class ShadowWarpBaseManaProcedure {
	public static void execute(ItemStack itemstack) {
		itemstack.getOrCreateTag().putDouble("manause", 300);
	}
}
