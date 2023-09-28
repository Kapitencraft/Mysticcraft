package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Provider;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public enum ModifierProviders {
    ;


    private final String name;
    private final Provider<Double, LivingEntity> provider;


    ModifierProviders(String name, Provider<Double, LivingEntity> provider) {
        this.name = name;
        this.provider = provider;
    }

    public static Provider<Double, LivingEntity> getByName(String s) {
        for (ModifierProviders providers : values()) {
            if (Objects.equals(providers.name, s)) {
                return providers.provider;
            }
        }
        return value -> 1.;
    }

    public static String getByProvider(Provider<Double, LivingEntity> provider) {
        for (ModifierProviders providers : values()) {
            if (providers.provider == provider) {
                return providers.name;
            }
        }
        return "null";
    }
}
