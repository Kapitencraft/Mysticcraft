package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.CountEnchantment;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.Vec3;

public class LightningLordEnchantment extends CountEnchantment {
    public LightningLordEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, MiscUtils.WEAPON_SLOT, "lightningLordMap", CountType.NORMAL, CalculationType.ALL, ProcessPriority.LOW);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected int getCountAmount(int level) {
        return (int) (3 + level * 0.4);
    }

    @Override
    protected double mainExecute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damageAmount, int curTick, DamageSource source) {
        if (attacker.level instanceof ServerLevel serverLevel) {
            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(serverLevel);
            assert entityToSpawn != null;
            entityToSpawn.moveTo(Vec3.atBottomCenterOf(attacked.getOnPos()));
            entityToSpawn.setVisualOnly(true);
            serverLevel.addFreshEntity(entityToSpawn);
        }
        damageAmount *= (1 + level * 0.1);
        return damageAmount;
    }


    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {level*10, (int) (3 + level * 0.4)};
    }
}
