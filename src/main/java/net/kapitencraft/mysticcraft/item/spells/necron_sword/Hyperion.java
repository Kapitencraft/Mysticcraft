package net.kapitencraft.mysticcraft.item.spells.necron_sword;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class Hyperion extends NecronSword {

    public Hyperion() {
        super(NecronSword.BASE_DAMAGE, 350, NecronSword.BASE_FEROCITY, NecronSword.BASE_STRENGHT);
    }

    @Override
    protected Multimap<Attribute, AttributeModifier> getAdditionalModifiers() {
        return null;
    }
}