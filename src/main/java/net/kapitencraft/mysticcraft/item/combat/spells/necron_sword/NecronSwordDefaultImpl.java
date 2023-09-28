package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class NecronSwordDefaultImpl extends NecronSword {
    public NecronSwordDefaultImpl() {
        super(BASE_DAMAGE, BASE_INTEL, BASE_FEROCITY, BASE_STRENGHT);
    }

    @Override
    protected Multimap<Attribute, AttributeModifier> getAdditionalModifiers() {
        return HashMultimap.create();
    }
}
