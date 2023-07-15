package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Supplier;
import java.util.Map;

public class ReforgeAnvilPDProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double rand = 0;
		ItemStack Slot1 = ItemStack.EMPTY;
		Slot1 = (entity instanceof ServerPlayer _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr
				&& _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY);
		if (!Slot1.getOrCreateTag().getBoolean("isreforged")) {
			rand = Math.random();
			if (rand >= 0.9) {
				Slot1.getOrCreateTag().putString("reforge", "strong");
			} else if (rand >= 0.8) {
				Slot1.getOrCreateTag().putString("reforge", "fierz");
			} else if (rand >= 0.7) {
				Slot1.getOrCreateTag().putString("reforge", "heavy");
			} else if (rand >= 0.6) {
				Slot1.getOrCreateTag().putString("reforge", "smart");
			} else if (rand >= 0.5) {
				Slot1.getOrCreateTag().putString("reforge", "intelligent");
			} else if (rand >= 0.4) {
				Slot1.getOrCreateTag().putString("reforge", "wise");
			} else if (rand >= 0.3) {
				Slot1.getOrCreateTag().putString("reforge", "healthy");
			} else if (rand >= 0.2) {
				Slot1.getOrCreateTag().putString("reforge", "tanky");
			} else if (rand >= 0.1) {
				Slot1.getOrCreateTag().putString("reforge", "speedy");
			} else {
				Slot1.getOrCreateTag().putString("reforge", "tagValue");
			}
			(Slot1).setHoverName(new TextComponent(
					((Slot1.getOrCreateTag().getString("reforge") + "" + (" " + (Slot1.getDisplayName().getString()).replace("[", ""))).replace("]",
							""))));
		}
	}
}
