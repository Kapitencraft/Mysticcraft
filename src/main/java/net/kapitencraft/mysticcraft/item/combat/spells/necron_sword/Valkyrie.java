package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class Valkyrie extends NecronSword {
    public Valkyrie() {
        super(REFINED_BASE_DAMAGE, 60, 60, 145);
    }

    @Override
    protected Multimap<Attribute, AttributeModifier> getAdditionalModifiers() {
        return null;
    }
}
