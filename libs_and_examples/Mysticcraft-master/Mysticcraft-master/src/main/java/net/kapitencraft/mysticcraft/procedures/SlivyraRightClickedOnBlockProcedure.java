package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.entity.Entity;

public class SlivyraRightClickedOnBlockProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getPersistentData().getDouble("spellslot") < 2) {
			entity.getPersistentData().putDouble("spellslot", (entity.getPersistentData().getDouble("spellslot") + 1));
		} else {
			entity.getPersistentData().putDouble("spellslot", 0);
		}
	}
}
