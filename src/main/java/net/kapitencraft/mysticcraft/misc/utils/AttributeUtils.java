package net.kapitencraft.mysticcraft.misc.utils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class AttributeUtils {

    private static final HashMap<UUID, Function<LivingEntity, Double>> map = new HashMap<>();

    public static ImmutableMultimap<Attribute, AttributeModifier> increaseByPercent(Multimap<Attribute, AttributeModifier> multimap, double percent, AttributeModifier.Operation[] operations, @Nullable Attribute attributeReq) {
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

    public static Multimap<Attribute, AttributeModifier> increaseByAmount(Multimap<Attribute, AttributeModifier> multimap, double amount, AttributeModifier.Operation operation, @Nullable Attribute attributeReq) {
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
            if (attributeReq == Attributes.MOVEMENT_SPEED && operation == AttributeModifier.Operation.ADDITION) {
                operation = AttributeModifier.Operation.MULTIPLY_BASE;
                amount *= 0.01;
            }
            toReturn.put(attributeReq, new AttributeModifier(UUID.randomUUID(), "Custom Attribute", amount, operation));
        }
        multimap = toReturn.build();
        return multimap;
    }

    public static Multimap<Attribute, AttributeModifier> increaseAllByAmount(Multimap<Attribute, AttributeModifier> list, Map<Attribute, AttributeModifier> toMerge) {
        for (Attribute attribute : toMerge.keySet()) {
            for (AttributeModifier modifier : List.of(toMerge.get(attribute)))
                list = increaseByAmount(list, modifier.getAmount(), modifier.getOperation(), attribute);
        }
        return list;
    }

    public static Multimap<Attribute, AttributeModifier> increaseAllByAmount(Multimap<Attribute, AttributeModifier> map, Multimap<Attribute, AttributeModifier> toMerge) {
        for (Attribute attribute : toMerge.keySet()) {
            for (AttributeModifier modifier : toMerge.get(attribute)) {
                map = increaseByAmount(map, modifier.getAmount(), modifier.getOperation(), attribute);
            }
        }
        return map;
    }

    public static double getSaveAttributeValue(Attribute attribute, LivingEntity living) {
        if (living.getAttribute(attribute) != null) {
            return living.getAttributeValue(attribute);
        }
        return -1;
    }

    public static double getAttributeValue(@Nullable AttributeInstance instance, double baseValue) {
        if (instance == null) {
            return baseValue;
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

    public static AttributeModifier copyWithValue(AttributeModifier modifier, double value) {
        return new AttributeModifier(modifier.getId(), modifier.getName(), value, modifier.getOperation());
    }

    public static AttributeModifier addLiquidModifier(@Nullable UUID uuid, AttributeModifier.Operation operation, Function<LivingEntity, Double> transfer) {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        map.put(uuid, transfer);
        return new AttributeModifier(uuid, "LiquidModifier", 0, operation);
    }

    public static <T, K> Multimap<T, K> fromMap(Map<T, K> map) {
        Multimap<T, K> multimap = HashMultimap.create();
        for (T t : map.keySet()) {
            multimap.put(t, map.get(t));
        }
        return multimap;
    }

    public static void removeLiquidModifierTracking(UUID uuid) {
        map.remove(uuid);
    }

    public static void updateLiquidModifiers(LivingEntity living) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = living.getItemBySlot(slot);
            Multimap<Attribute, AttributeModifier> modifiers = stack.getAttributeModifiers(slot);
            for (Attribute attribute : modifiers.keySet()) {
                for (AttributeModifier modifier : modifiers.get(attribute)) {
                    if (modifier.getName().equals("LiquidModifier")) {
                        UUID uuid = modifier.getId();
                        if (map.containsKey(uuid)) {
                            modifiers.remove(attribute, modifier);
                            modifiers.put(attribute, copyWithValue(modifier, map.get(uuid).apply(living)));
                        }
                    }
                }
            }
        }
    }

    public static class AttributeBuilder {
        private Multimap<Attribute, AttributeModifier> modifiers;


        public AttributeBuilder(Multimap<Attribute, AttributeModifier> multimap) {
            this.modifiers = multimap;
        }

        public AttributeBuilder() {
            this.modifiers = HashMultimap.create();
        }

        public void merge(Multimap<Attribute, AttributeModifier> toMerge) {
            this.modifiers = increaseAllByAmount(modifiers, toMerge);
        }

        public void merge(HashMap<Attribute, Double> toMerge, AttributeModifier.Operation operation) {
            for (Attribute attribute : toMerge.keySet()) {
                this.modifiers = increaseByAmount(this.modifiers, toMerge.get(attribute), operation, attribute);
            }
        }

        public Multimap<Attribute, AttributeModifier> build() {
            return this.modifiers;
        }
    }
}