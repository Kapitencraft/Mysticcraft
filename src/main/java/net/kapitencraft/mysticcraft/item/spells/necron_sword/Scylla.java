package net.kapitencraft.mysticcraft.item.spells.necron_sword;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class Scylla extends NecronSword {
    public Scylla() {
        super(NecronSword.REFINED_BASE_DAMAGE, NecronSword.BASE_INTEL, NecronSword.BASE_FEROCITY, NecronSword.BASE_STRENGHT);
    }

    @Override
    protected Multimap<Attribute, AttributeModifier> getAdditionalModifiers() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(ModAttributes.CRIT_DAMAGE.get(), MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, 35, EquipmentSlot.MAINHAND));
        return builder.build();
    }
}
