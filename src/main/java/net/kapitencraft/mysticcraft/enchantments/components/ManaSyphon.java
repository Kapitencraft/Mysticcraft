package net.kapitencraft.mysticcraft.enchantments.components;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.ManaHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public class ManaSyphon implements EnchantmentEntityEffect {
    public static final MapCodec<ManaSyphon> CODEC = MapCodec.unit(ManaSyphon::new);

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (entity instanceof LivingEntity living) {
            double maxMana = AttributeHelper.getSaveAttributeValue(ExtraAttributes.MAX_MANA, living);
            if (maxMana > 0) {
                double newMana = ManaHandler.getMana(living) + maxMana * 0.0025 * enchantmentLevel;
                ManaHandler.setMana(living, Math.min(maxMana, newMana));
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
