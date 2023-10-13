package net.kapitencraft.mysticcraft.enchantments.weapon;

import net.kapitencraft.mysticcraft.enchantments.abstracts.CountEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantmentCategories;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.kapitencraft.mysticcraft.misc.particle_help.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class InfernoEnchantment extends CountEnchantment implements IUltimateEnchantment {
    public InfernoEnchantment() {
        super(Rarity.VERY_RARE, ModEnchantmentCategories.ALL_WEAPONS, MiscHelper.WEAPON_SLOT, "infernoMap", CountType.NORMAL, CalculationType.ALL, ProcessPriority.LOWEST);
    }

    @Override
    protected int getCountAmount(int level) {
        return 10;
    }

    @Override
    protected double mainExecute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damageAmount, int curTick, DamageSource source) {
        if (!source.getMsgId().equals("inferno")) {
            ParticleHelper.createWithTargetHeight("inferno", attacked, ParticleHelper.Type.ORBIT, ParticleHelper.createOrbitProperties(0, 200, 0, 0, 4, ParticleTypes.DRIPPING_LAVA, 0.75f));
            MiscHelper.increaseEffectDuration(attacked, ModMobEffects.STUN.get(), 80);
            tick(attacked, attacker, 0, (float) (damageAmount * (100 + level * 25) / 100));
        }
        return damageAmount;
    }

    private void tick(LivingEntity attacked, LivingEntity attacker, int tick, float damage) {
        MiscHelper.delayed(20, () -> {
            attacked.hurt(new EntityDamageSource("inferno", attacker), damage);
                if (tick < 5) {
                tick(attacked, attacker, tick + 1, damage);
            }
        });
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }


    @Override
    public String[] getDescriptionMods(int level) {
        return new String[]{(100 + level * 25) + "%"};
    }
}