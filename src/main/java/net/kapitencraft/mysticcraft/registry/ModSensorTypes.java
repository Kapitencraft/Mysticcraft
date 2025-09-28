package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public interface ModSensorTypes {
    DeferredRegister<SensorType<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.SENSOR_TYPES);

    private static <T extends Sensor<?>> RegistryObject<SensorType<T>> register(String name, Supplier<T> sup) {
        return REGISTRY.register(name, () -> new SensorType<>(sup));
    }

    RegistryObject<SensorType<TemptingSensor>> DRAGON_TEMPTATIONS = register("dragon_temptations", () -> new TemptingSensor(Ingredient.of(ModTags.Items.DRAGON_TEMPTING)));
}
