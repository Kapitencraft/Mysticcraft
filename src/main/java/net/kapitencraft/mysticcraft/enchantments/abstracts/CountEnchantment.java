package net.kapitencraft.mysticcraft.enchantments.abstracts;

import net.kapitencraft.mysticcraft.misc.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.HashMap;
import java.util.UUID;

public abstract class CountEnchantment extends ExtendedCalculationEnchantment implements IWeaponEnchantment {
    private final String mapName;
    private final CountType type;
    protected CountEnchantment(Rarity p_44676_, EquipmentSlot[] p_44678_, String mapName, CountType type, CalculationType calculationType, CalculationPriority priority) {
        super(p_44676_, EnchantmentCategory.WEAPON, p_44678_, calculationType, priority);
        this.mapName = mapName;
        this.type = type;
    }

    protected abstract int getCountAmount(int level);

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damageAmount, DamageSource source) {
        CompoundTag attackerTag = attacker.getPersistentData();
        HashMap<UUID, Integer> map = attackerTag.get(this.mapName) != null ? TagUtils.getHashMapTag((CompoundTag) attackerTag.get("lightningLordMap")) : new HashMap<>();
        if (!map.containsKey(attacked.getUUID())) {
            map.put(attacked.getUUID(), 0);
        }
        int integer = map.get(attacked.getUUID());
        if (integer >= this.getCountAmount(level)) {
            integer = 0;
            if (this.type == CountType.NORMAL) {
                damageAmount = this.mainExecute(level, enchanted, attacker, attacked, damageAmount, integer, source);
            }
        } else {
            integer++;
            if (this.type == CountType.EXCEPT) {
                damageAmount = this.mainExecute(level, enchanted, attacker, attacked, damageAmount, integer, source);
            }
        }
        map.put(attacked.getUUID(), integer);
        attackerTag.put(this.mapName, TagUtils.putHashMapTag(map));
        return damageAmount;
    }

    protected abstract double mainExecute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damageAmount, int curHit, DamageSource source);

    protected enum CountType {
        NORMAL,
        EXCEPT;
    }
}
