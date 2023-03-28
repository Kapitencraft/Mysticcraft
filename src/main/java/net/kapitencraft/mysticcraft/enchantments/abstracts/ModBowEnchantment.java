package net.kapitencraft.mysticcraft.enchantments.abstracts;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.HashMap;

public abstract class ModBowEnchantment extends Enchantment implements ModEnchantment {
    private static final HashMap<String, Execution> executionMap = new HashMap<>();
    private final String tagName;
    protected ModBowEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_, String tagName) {
        super(p_44676_, p_44677_, p_44678_);
        this.tagName = tagName + "Enchant";
        executionMap.put(this.tagName, this::execute);
    }

    public interface Execution {
        float execute(LivingEntity target, CompoundTag tag, ExecuteType type, float oldDamage, AbstractArrow arrow);
    }

    public static float loadFromTag(LivingEntity target, CompoundTag tag, ExecuteType type, float oldDamage, AbstractArrow arrow) {
        for (String string : executionMap.keySet()) {
            if (tag.contains(string, 10)) {
                oldDamage = executionMap.get(string).execute(target, tag, type, oldDamage, arrow);
            }
        }
        return oldDamage;
    }

    public abstract CompoundTag write(int level, ItemStack bow, LivingEntity owner, AbstractArrow arrow);
    public abstract float execute(LivingEntity target, CompoundTag tag, ExecuteType type, float oldDamage, AbstractArrow arrow);

    public String getTagName() {
        return tagName;
    }

    public abstract boolean shouldTick();

    public enum ExecuteType {
        TICK,
        HIT;
    }
}
