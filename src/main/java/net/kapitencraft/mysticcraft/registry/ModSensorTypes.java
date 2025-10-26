package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.ai.sensor.DragonAttackablesSensor;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModSensorTypes {
    DeferredRegister<SensorType<?>> REGISTRY = MysticcraftMod.registry(Registries.SENSOR_TYPE);

    private static <T extends Sensor<?>> Supplier<SensorType<T>> register(String name, Supplier<T> sup) {
        return REGISTRY.register(name, () -> new SensorType<>(sup));
    }

    Supplier<SensorType<TemptingSensor>> DRAGON_TEMPTATIONS = register("dragon_temptations", () -> new TemptingSensor(Ingredient.of(ModTags.Items.DRAGON_TEMPTING)));
    Supplier<SensorType<DragonAttackablesSensor>> DRAGON_ATTACKABLES = register("dragon_attackables", DragonAttackablesSensor::new);
}
