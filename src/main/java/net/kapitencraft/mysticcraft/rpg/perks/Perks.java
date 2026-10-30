package net.kapitencraft.mysticcraft.rpg.perks;

import net.kapitencraft.kap_lib.core.string_converter.converter.TextToIntConverter;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

import java.util.List;

public interface Perks {

    ResourceKey<Perk> DAGGER_COMBAT = key("combat/dagger");
    ResourceKey<Perk> SWORD_COMBAT = key("combat/sword");
    ResourceKey<Perk> AXE_COMBAT = key("combat/axe");
    ResourceKey<Perk> LONGSWORD_COMBAT = key("combat/longsword");
    ResourceKey<Perk> CLEAVER_COMBAT = key("combat/cleaver");
    ResourceKey<Perk> HALBERD_COMBAT = key("combat/halberd");
    ResourceKey<Perk> LANCE_COMBAT = key("combat/lance");
    ResourceKey<Perk> SPEAR_COMBAT = key("combat/spear");

    ResourceKey<Perk> ARCHERY = key("combat/archery");
    ResourceKey<Perk> RIDING = key("riding");
    ResourceKey<Perk> HAGGLING = key("haggling");

    DataMapType<Item, Holder<Perk>> COMBAT_PERKS = DataMapType.builder(
            MysticcraftMod.res("combat_perks"),
            Registries.ITEM,
            RegistryFixedCodec.create(ModRegistries.Keys.PERKS)
    ).build();

    private static ResourceKey<Perk> key(String name) {
        return ResourceKey.create(ModRegistries.Keys.PERKS, MysticcraftMod.res(name));
    }

    static void bootstrap(BootstrapContext<Perk> perkBootstrapContext) {
        perkBootstrapContext.register(DAGGER_COMBAT, new Perk(
                new Perk.Progression(
                        200,
                        List.of(50, 100, 150, 200),
                        new TextToIntConverter("l * l")
                )
        ));
        perkBootstrapContext.register(SWORD_COMBAT, new Perk(
                new Perk.Progression(
                        200,
                        List.of(50, 100, 150, 200),
                        new TextToIntConverter("l * l")
                )
        ));
        perkBootstrapContext.register(AXE_COMBAT, new Perk(
                new Perk.Progression(
                        200,
                        List.of(50, 100, 150, 200),
                        new TextToIntConverter("l * l")
                )
        ));
        perkBootstrapContext.register(LONGSWORD_COMBAT, new Perk(
                new Perk.Progression(
                        200,
                        List.of(50, 100, 150, 200),
                        new TextToIntConverter("l * l")
                )
        ));
        perkBootstrapContext.register(CLEAVER_COMBAT, new Perk(
                new Perk.Progression(
                        200,
                        List.of(50, 100, 150, 200),
                        new TextToIntConverter("l * l")
                )
        ));
        perkBootstrapContext.register(HALBERD_COMBAT, new Perk(
                new Perk.Progression(
                        200,
                        List.of(50, 100, 150, 200),
                        new TextToIntConverter("l * l")
                )
        ));
        perkBootstrapContext.register(LANCE_COMBAT, new Perk(
                new Perk.Progression(
                        200,
                        List.of(50, 100, 150, 200),
                        new TextToIntConverter("l * l")
                )
        ));
        perkBootstrapContext.register(SPEAR_COMBAT, new Perk(
                new Perk.Progression(
                        200,
                        List.of(50, 100, 150, 200),
                        new TextToIntConverter("l * l")
                )
        ));
    }
}
