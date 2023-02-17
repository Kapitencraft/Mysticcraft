package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.misc.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class InfernoEnchantment extends CountEnchantment implements IWeaponEnchantment, IUltimateEnchantment {
    public InfernoEnchantment() {
        super(Rarity.VERY_RARE, MISCTools.WEAPON_SLOT, "infernoMap", countType.EXCEPT);
    }

    @Override
    protected int getCountAmount(int level) {
        return 10;
    }

    @Override
    protected double mainExecute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damageAmount, int curTick) {
        ParticleHelper.createWithTargetHeight("inferno", attacked, ParticleHelper.Type.ORBIT, ParticleHelper.createOrbitProperties(0, 200, 0, 0, 4, ParticleTypes.DRIPPING_LAVA));
        return damageAmount;
    }
}
