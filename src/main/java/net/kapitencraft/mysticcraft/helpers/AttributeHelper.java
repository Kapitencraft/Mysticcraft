package net.kapitencraft.mysticcraft.helpers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.misc.ChangingAttributeModifier;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Provider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.annotation.Nullable;
import java.util.*;

public class AttributeHelper {

    public static Multimap<Attribute, AttributeModifier> increaseByPercent(Multimap<Attribute, AttributeModifier> multimap, double percent, AttributeModifier.Operation[] operations, @Nullable Attribute attributeReq) {
        HashMultimap<Attribute, AttributeModifier> toReturn = HashMultimap.create();
        Collection<AttributeModifier> attributeModifiers;
        for (Attribute attribute : multimap.keys()) {
            if (attributeReq == null || attribute == attributeReq) {
                attributeModifiers = multimap.get(attribute);
                for (AttributeModifier modifier : attributeModifiers) {
                    if (CollectionHelper.arrayContains(operations, modifier.getOperation())) {
                        toReturn.put(attribute, copyWithValue(modifier, modifier.getAmount() * (1 + percent)));
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
        return toReturn;
    }

    public static Multimap<Attribute, AttributeModifier> increaseByAmount(Multimap<Attribute, AttributeModifier> multimap, double amount, AttributeModifier.Operation operation, @Nullable Attribute attributeReq) {
        HashMultimap<Attribute, AttributeModifier> toReturn = HashMultimap.create();
        boolean hasBeenAdded = attributeReq == null;
        Collection<AttributeModifier> attributeModifiers;
        for (Attribute attribute : multimap.keys()) {
            attributeModifiers = multimap.get(attribute);
            for (AttributeModifier modifier : attributeModifiers) {
                if (!hasBeenAdded && attribute == attributeReq && operation == modifier.getOperation()) {
                    toReturn.put(attribute, copyWithValue(modifier, modifier.getAmount() + amount));
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
        multimap = toReturn;
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

    public static double getSaveAttributeValue(Attribute attribute, @Nullable LivingEntity living) {
        if (living != null && living.getAttribute(attribute) != null) {
            return living.getAttributeValue(attribute);
        }
        return 0;
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

    public static AttributeModifier addLiquidModifier(@Nullable UUID uuid, String name, AttributeModifier.Operation operation, Provider<Double, LivingEntity> transfer, LivingEntity living) {
        if (modUUIDs.containsKey(name) && modUUIDs.get(name) == uuid) return new ChangingAttributeModifier(uuid, name, operation, living, transfer);
        else {
            modUUIDs.put(name, Objects.requireNonNullElseGet(uuid, UUID::randomUUID));
        }
        return new ChangingAttributeModifier(modUUIDs.get(name), name, operation, living, transfer);
    }

    public static <T, K> Multimap<T, K> fromMap(Map<T, K> map) {
        Multimap<T, K> multimap = HashMultimap.create();
        for (T t : map.keySet()) {
            multimap.put(t, map.get(t));
        }
        return multimap;
    }

    private static final HashMap<String, UUID> modUUIDs = new HashMap<>();
    private static final HashMap<EquipmentSlot, HashMap<String, UUID>> slotModUUIDs = new HashMap<>();

    public static UUID getByName(String name) {
        return modUUIDs.get(name);
    }

    public static AttributeModifier createModifier(String name, AttributeModifier.Operation operation, double amount) {
        if (!modUUIDs.containsKey(name)) {
            modUUIDs.put(name, UUID.randomUUID());
        }
        return new AttributeModifier(modUUIDs.get(name), name, amount, operation);
    }

    public static AttributeModifier createModifierForSlot(String name, AttributeModifier.Operation operation, double am, EquipmentSlot slot) {
        if (!slotModUUIDs.containsKey(slot)) {
            slotModUUIDs.put(slot, new HashMap<>());
        }
        HashMap<String, UUID> slotUUIDs = slotModUUIDs.get(slot);
        if (!slotUUIDs.containsKey(name)) {
            slotUUIDs.put(name, UUID.randomUUID());
        }
        return new AttributeModifier(slotUUIDs.get(name), name, am, operation);
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