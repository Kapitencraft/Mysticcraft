package net.kapitencraft.mysticcraft.mixin.classes;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Attributes.class)
public abstract class AttributesMixin {

    @ModifyConstant(method = "<clinit>")
    private static double attribute(double value) {
        if (value == 30) {
            MysticcraftMod.sendInfo("changed Value");
            return Double.MAX_VALUE;
        }
        return value;
    }
}
