package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class TallinBow extends ShortBowItem {
    public TallinBow() {
        super(new Properties().rarity(ModRarities.LEGENDARY).durability(1500));
    }

    @Override
    public int getKB() {
        return 1;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        multimap.putAll(super.getAttributeModifiers(slot, stack));
        if (slot == EquipmentSlot.MAINHAND) multimap.put(ModAttributes.ARROW_COUNT.get(), AttributeHelper.createModifier("TallinExtraArrows", AttributeModifier.Operation.ADDITION, 2));
        return multimap;
    }

    @Override
    public double getDamage() {
        return 3;
    }

    @Override
    public float getShotCooldown() {
        return 0.3f;
    }
}
