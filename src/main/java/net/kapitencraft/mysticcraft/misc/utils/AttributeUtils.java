package net.kapitencraft.mysticcraft.misc.utils;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

public class AttributeUtils {
    public static ImmutableMultimap<Attribute, AttributeModifier> increaseByPercent(ImmutableMultimap<Attribute, AttributeModifier> multimap, double percent, AttributeModifier.Operation[] operations, @Nullable Attribute attributeReq) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> toReturn = new ImmutableMultimap.Builder<>();
        Collection<AttributeModifier> attributeModifiers;
        for (Attribute attribute : multimap.keys()) {
            if (attributeReq == null || attribute == attributeReq) {
                attributeModifiers = multimap.get(attribute);
                for (AttributeModifier modifier : attributeModifiers) {
                    if (MiscUtils.arrayContains(operations, modifier.getOperation())) {
                        toReturn.put(attribute, new AttributeModifier(modifier.getId(), modifier.getName(), modifier.getAmount() * (1 + percent), modifier.getOperation()));
                    } else {
                        toReturn.put(attribute, modifier);
                    }
                }
            } else {
                attributeModifiers = multimap.get(attribute);
                for (AttributeModifier modifier : attributeModifiers) {
                    toReturn.put(attribute, modifier);
                }
            }
        }
        return toReturn.build();
    }

    public static ImmutableMultimap<Attribute, AttributeModifier> increaseByAmount(ImmutableMultimap<Attribute, AttributeModifier> multimap, double amount, AttributeModifier.Operation operation, @Nullable Attribute attributeReq) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> toReturn = new ImmutableMultimap.Builder<>();
        boolean hasBeenAdded = attributeReq == null;
        Collection<AttributeModifier> attributeModifiers;
        for (Attribute attribute : multimap.keys()) {
            attributeModifiers = multimap.get(attribute);
            for (AttributeModifier modifier : attributeModifiers) {
                if (!hasBeenAdded && attribute == attributeReq && operation == modifier.getOperation()) {
                    toReturn.put(attribute, new AttributeModifier(modifier.getId(), modifier.getName(), modifier.getAmount() + amount, modifier.getOperation()));
                    hasBeenAdded = true;
                } else {
                    toReturn.put(attribute, modifier);
                }
            }
        }
        if (!hasBeenAdded) {
            toReturn.put(attributeReq, new AttributeModifier(UUID.randomUUID(), "Custom Attribute", amount, AttributeModifier.Operation.ADDITION));
        }
        multimap = toReturn.build();
        return multimap;
    }

    public static double getSaveAttributeValue(Attribute attribute, LivingEntity living) {
        if (living.getAttribute(attribute) != null) {
            return living.getAttributeValue(attribute);
        }
        return -1;
    }

    public static double getAttributeValue(@Nullable AttributeInstance instance, double baseValue) {
        if (instance == null) {
            return 0;
        }
        double d0 = baseValue + instance.getBaseValue();

        for (AttributeModifier attributemodifier : instance.getModifiers(AttributeModifier.Operation.ADDITION)) {
            d0 += attributemodifier.getAmount();
        }

        double d1 = d0;

        for (AttributeModifier attributeModifier1 : instance.getModifiers(AttributeModifier.Operation.MULTIPLY_BASE)) {
            d1 += d0 * attributeModifier1.getAmount();
        }

        for (AttributeModifier attributeModifier2 : instance.getModifiers(AttributeModifier.Operation.MULTIPLY_TOTAL)) {
            d1 *= 1.0D + attributeModifier2.getAmount();
        }
        return instance.getAttribute().sanitizeValue(d1);
    }

}
