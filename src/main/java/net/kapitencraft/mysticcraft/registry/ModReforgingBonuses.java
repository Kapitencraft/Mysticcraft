package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.capability.reforging.bonuses.SacredBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistryKeys;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Consumer;

public interface ModReforgingBonuses {
    DeferredRegister<ReforgingBonus> REGISTRY = MysticcraftMod.registry(ModRegistryKeys.REFORGE_BONUSES);

    RegistryObject<ReforgingBonus> EMPTY = REGISTRY.register("empty", ()-> new ReforgingBonus("Empty") {
        @Override
        public Consumer<List<Component>> getDisplay() {
            return list -> list.add(Component.literal("empty"));
        }
    });
    RegistryObject<SacredBonus> SACRED_BONUS = REGISTRY.register("sacred", SacredBonus::new);
}
