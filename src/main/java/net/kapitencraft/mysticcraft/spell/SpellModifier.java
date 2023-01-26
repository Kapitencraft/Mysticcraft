package net.kapitencraft.mysticcraft.spell;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Map;

public class SpellModifier {

    private Map<Attribute, AttributeModifier> modifier;


    public SpellModifier(Map<Attribute, AttributeModifier> modifier) {
        this.modifier = modifier;
    }

    public Map<Attribute, AttributeModifier> getModifier() {
        return modifier;
    }
}
