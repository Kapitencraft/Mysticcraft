package net.kapitencraft.mysticcraft.mixin.classes;


import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Objects;

@Mixin(Attributes.class)
public abstract class AttributesMixin {
    private static String currentAttribute;
    @ModifyConstant(method = "<clinit>")
    private static String stringAttribute(String value) {
        currentAttribute = value;
        return value;
    }

    @ModifyConstant(method = "<clinit>")
    private static double attribute(double value) {
        if (value == 30 || (Objects.equals(currentAttribute, "attribute.name.generic.max_health") && value == 1024)) {
            return Double.MAX_VALUE;
        }
        return value;
    }
}
