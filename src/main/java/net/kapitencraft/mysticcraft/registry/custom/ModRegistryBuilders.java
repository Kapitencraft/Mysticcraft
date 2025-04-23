package net.kapitencraft.mysticcraft.registry.custom;

import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.RegistryBuilder;

public interface ModRegistryBuilders {
    RegistryBuilder<ReforgingBonus> REFORGE_BONUSES_REGISTRY_BUILDER = makeBuilder(ModRegistryKeys.REFORGE_BONUSES);

    private static <T> RegistryBuilder<T> makeBuilder(ResourceKey<Registry<T>> location) {
        return new RegistryBuilder<T>().setName(location.location());
    }
}