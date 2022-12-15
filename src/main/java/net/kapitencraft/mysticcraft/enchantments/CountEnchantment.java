package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.HashMap;
import java.util.UUID;

public abstract class CountEnchantment extends ExtendedCalculationEnchantment implements IWeaponEnchantment {
    private final String mapName;
    private final countType type;
    protected CountEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_, String mapName, countType type) {
        super(p_44676_, p_44677_, p_44678_);
        this.mapName = mapName;
        this.type = type;
    }

    protected abstract int getCountAmount(int level);

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damageAmount) {
        CompoundTag attackerTag = attacker.getPersistentData();
        HashMap<UUID, Integer> map = attackerTag.get(this.mapName) != null ? MISCTools.getHashMapTag((CompoundTag) attackerTag.get("lightningLordMap")) : new HashMap<>();
        if (!map.containsKey(attacked.getUUID())) {
            map.put(attacked.getUUID(), 0);
        }
        int integer = map.get(attacked.getUUID());
        boolean flag1 = (this.type == countType.NORMAL && integer >= this.getCountAmount(level));
        boolean flag2 = (this.type == countType.EXCEPT && integer <= this.getCountAmount(level));
        if (flag1 || flag2) {
            damageAmount = this.mainExecute(level, enchanted, attacker, attacked, damageAmount);
            if (flag1) {
                integer = 0;
            } else {
                integer++;
            }
        } else {
            integer++;
        }
        map.put(attacked.getUUID(), integer);
        attackerTag.put(this.mapName, MISCTools.putHashMapTag(map));
        return damageAmount;
    }

    protected abstract double mainExecute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damageAmount);

    protected enum countType {
        NORMAL,
        EXCEPT;

        countType() {}
    }
}
