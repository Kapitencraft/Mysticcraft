package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Valkyrie extends NecronSword {
    public Valkyrie() {
        super(REFINED_BASE_DAMAGE, 60, 60, 145);
    }

    @Override
    protected @NotNull Consumer<Multimap<Attribute, AttributeModifier>> getAdditionalModifiers() {
        return multimap -> {};
    }
}
