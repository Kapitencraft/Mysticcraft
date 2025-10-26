package net.kapitencraft.mysticcraft.registry.custom;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.classes.RPGClass;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface ModRegistries {

    @ApiStatus.Internal
    List<Registry<?>> registries = new ArrayList<>();

    @ApiStatus.Internal
    static void registerAll(Consumer<Registry<?>> register) {
        registries.forEach(register);
    }

    Registry<Spell> SPELLS = reg(Keys.SPELLS, RegistryBuilder::withIntrusiveHolders);

    private static <T> Registry<T> reg(ResourceKey<Registry<T>> key, UnaryOperator<RegistryBuilder<T>> upgrades) {
        Registry<T> registry = upgrades.apply(new RegistryBuilder<>(key)).create();
        registries.add(registry);
        return registry;
    }

    private static <T> Registry<T> reg(ResourceKey<Registry<T>> key) {
        return reg(key, b -> b);
    }

    interface Keys {


        ResourceKey<Registry<Spell>> SPELLS = createRegistry("spells");
        ResourceKey<Registry<RPGClass>> CLASSES = createRegistry("classes");

        private static <T> ResourceKey<Registry<T>> createRegistry(String id) {
            return ResourceKey.createRegistryKey(MysticcraftMod.res(id));
        }
    }
}