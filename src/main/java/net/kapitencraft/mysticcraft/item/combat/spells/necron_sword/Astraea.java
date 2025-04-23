package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Astraea extends NecronSword {
    public Astraea() {
        super(REFINED_BASE_DAMAGE, BASE_INTEL, BASE_FEROCITY, BASE_STRENGHT);
    }

    @Override
    protected @NotNull Consumer<Multimap<Attribute, AttributeModifier>> getAdditionalModifiers() {
        return multimap -> multimap.put(Attributes.ARMOR, AttributeHelper.createModifier("Astraea Modifiers", AttributeModifier.Operation.ADDITION, 7));
    }
}
