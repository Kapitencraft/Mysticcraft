package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.misc.damage_source.IAbilitySource;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class ModProtectionEnchantment extends Enchantment {
    private final Type type;

    protected ModProtectionEnchantment(Rarity p_44676_, Type type) {
        super(p_44676_, EnchantmentCategory.ARMOR, MiscUtils.ARMOR_EQUIPMENT);
        this.type = type;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getDamageProtection(int level, @NotNull DamageSource source) {
        if (source.isBypassEnchantments()) return 0;
        if (source.getMsgId().equals("true_damage") && this.type == Type.TRUE) {
            return level;
        } else if (source instanceof IAbilitySource && this.type == Type.MAGIC) {
            return 2 * level;
        }
        return 0;
    }


    protected enum Type {
        TRUE,
        MAGIC
    }
}