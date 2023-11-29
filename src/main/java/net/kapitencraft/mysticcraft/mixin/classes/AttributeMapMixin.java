package net.kapitencraft.mysticcraft.mixin.classes;


import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AttributeMap.class)
public interface AttributeMapMixin {

    @Accessor
    Map<Attribute, AttributeInstance> getAttributes();
}
