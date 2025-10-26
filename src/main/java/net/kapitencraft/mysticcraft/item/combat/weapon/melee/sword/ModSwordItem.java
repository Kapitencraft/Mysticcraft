package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.attribute.BaseAttributeLocations;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public abstract class ModSwordItem extends SwordItem {
    public static final float DEFAULT_ATTACK_SPEED = -2.4f;
    public static final TabGroup SWORD_GROUP = TabGroup.create(ModCreativeModTabs.WEAPONS_AND_TOOLS);
    public ModSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    public ParticleOptions getSweepParticle(ItemStack stack) {
        return ParticleTypes.SWEEP_ATTACK;
    }

    @Override
    public @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
        return super.getSweepHitBox(stack, player, target).inflate((player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) - 3) * 1/3);
    }

    protected static ItemAttributeModifiers createAttributes(Tier tier, int damage, float speed, int strength, int critDamage) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        ExtraAttributes.STRENGTH,
                        new AttributeModifier(
                                BaseAttributeLocations.STRENGTH, strength, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        ExtraAttributes.CRIT_DAMAGE,
                        new AttributeModifier(
                                BaseAttributeLocations.CRIT_DAMAGE, critDamage, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).build();
    }
}