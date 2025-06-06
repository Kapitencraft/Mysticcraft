package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Scylla extends NecronSword {
    public Scylla() {
        super(REFINED_BASE_DAMAGE, BASE_INTEL, BASE_FEROCITY, BASE_STRENGHT);
    }

    @Override
    protected @NotNull Consumer<Multimap<Attribute, AttributeModifier>> getAdditionalModifiers() {
        return multimap -> multimap.put(ExtraAttributes.CRIT_DAMAGE.get(), AttributeHelper.createModifier("Scylla Modifiers", AttributeModifier.Operation.ADDITION, 35));
    }
}
