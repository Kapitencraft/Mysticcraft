package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class Hyperion extends NecronSword {

    public Hyperion() {
        super(BASE_DAMAGE, 350, BASE_FEROCITY, BASE_STRENGHT);
        this.addSlot(new SpellSlot(Spells.WITHER_IMPACT));
    }

    @Override
    protected Multimap<Attribute, AttributeModifier> getAdditionalModifiers() {
        return null;
    }
}