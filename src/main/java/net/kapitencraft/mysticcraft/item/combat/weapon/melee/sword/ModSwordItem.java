package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

public abstract class ModSwordItem extends SwordItem {
    public static final float DEFAULT_ATTACK_SPEED = -2.4f;
    public static final TabGroup SWORD_GROUP = TabGroup.builder().tab(ModCreativeModTabs.WEAPONS_AND_TOOLS).build();
    public ModSwordItem(Tier p_43269_, int attackDamage, float attackSpeed, Properties p_43272_) {
        super(p_43269_, attackDamage, attackSpeed, p_43272_);
    }

    public abstract double getStrenght();
    public abstract double getCritDamage();

    public ParticleOptions getSweepParticle(ItemStack stack) {
        return ParticleTypes.SWEEP_ATTACK;
    }

    @Override
    public @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
        return super.getSweepHitBox(stack, player, target).inflate((player.getAttributeValue(ForgeMod.ENTITY_REACH.get()) - 3) * 1/3);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(ExtraAttributes.STRENGTH.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[5], "Default Attribute modification", this.getStrenght(), AttributeModifier.Operation.ADDITION));
            builder.put(ExtraAttributes.CRIT_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[5], "Default Attribute modification", this.getCritDamage(), AttributeModifier.Operation.ADDITION));
        }
        return builder;
    }
}