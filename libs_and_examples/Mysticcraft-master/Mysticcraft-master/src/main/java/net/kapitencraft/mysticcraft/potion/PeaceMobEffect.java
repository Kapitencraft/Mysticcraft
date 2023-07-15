
package net.kapitencraft.mysticcraft.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class PeaceMobEffect extends MobEffect {
	public PeaceMobEffect() {
		super(MobEffectCategory.HARMFUL, -13395457);
	}

	@Override
	public String getDescriptionId() {
		return "effect.mysticcraft.peace";
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
