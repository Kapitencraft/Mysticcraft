package net.kapitencraft.mysticcraft.enchantments.armor;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IArmorEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.mysticcraft.misc.cooldown.Cooldowns;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.kapitencraft.mysticcraft.utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BonkEnchantment extends ExtendedCalculationEnchantment implements IUltimateEnchantment, IArmorEnchantment {
    public static final String BONK_ID = "Bonk Enchantment";

    protected BonkEnchantment() {
        super(DEFAULT_RARITY, EnchantmentCategory.ARMOR, DEFAULT_SLOTS, CalculationType.ALL, ProcessPriority.LOWEST);
    }

    @Override
    protected double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        CompoundTag tag = attacked.getPersistentData();
        CompoundTag tag1 = tag.getCompound(BONK_ID);
        EquipmentSlot slot = enchanted.getItem() instanceof ArmorItem armorItem ? armorItem.getSlot() : null;
        if (slot != null) {
            String name = slot.getName();
            boolean isActive = tag1.getBoolean(name);
            if (isActive) {
                ParticleUtils.sendParticles(attacked.level, ParticleTypes.EXPLOSION, false, MathUtils.getPosition(attacked), 2, 0, 0, 0, 0);
                Cooldowns.BONK_ENCHANTMENT(slot).applyCooldown(attacked);
                return 0;
            }
        }
        return damage;
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[0];
    }
}