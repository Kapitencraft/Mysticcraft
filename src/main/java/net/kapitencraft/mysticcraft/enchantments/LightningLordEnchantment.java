package net.kapitencraft.mysticcraft.enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class LightningLordEnchantment extends CountEnchantment {
    public LightningLordEnchantment() {
        super(Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, "lightningLordMap", countType.NORMAL);
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
    protected double mainExecute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damageAmount, int curTick) {
        if (attacker.level instanceof ServerLevel serverLevel) {
            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(serverLevel);
            entityToSpawn.moveTo(Vec3.atBottomCenterOf(attacked.getOnPos()));
            entityToSpawn.setVisualOnly(true);
            serverLevel.addFreshEntity(entityToSpawn);
        }
        damageAmount *= (1 + level * 0.1);
        return damageAmount;
    }
}
