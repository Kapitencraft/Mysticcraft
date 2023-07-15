package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Astraea extends NecronSword {
    public Astraea() {
        super(REFINED_BASE_DAMAGE, BASE_INTEL, BASE_FEROCITY, BASE_STRENGHT);
    }

    @Override
    protected Multimap<Attribute, AttributeModifier> getAdditionalModifiers() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(Attributes.ARMOR, MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, 7, EquipmentSlot.MAINHAND));
        return builder.build();
    }
}
