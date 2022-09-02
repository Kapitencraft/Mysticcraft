package net.kapitencraft.mysticcraft.spell;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Map;

public class SpellModdifier {

    private Map<Attribute, AttributeModifier> modifier;


    public SpellModdifier(Map<Attribute, AttributeModifier> modifier) {
        this.modifier = modifier;
    }

    public Map<Attribute, AttributeModifier> getModifier() {
        return modifier;
    }
}
