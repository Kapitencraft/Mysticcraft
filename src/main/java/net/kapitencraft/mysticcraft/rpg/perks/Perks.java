package net.kapitencraft.mysticcraft.rpg.perks;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class Perks {
    ResourceKey<Perk> DAGGER_COMBAT = key("combat/dagger");
    ResourceKey<Perk> SWORD_COMBAT = key("combat/sword");
    ResourceKey<Perk> LONGSWORD_COMBAT = key("combat/longsword");
    ResourceKey<Perk> ARCHERY = key("combat/archery");
    ResourceKey<Perk> RIDING = key("riding");

    private ResourceKey<Perk> key(String name) {
        return ResourceKey.create(ModRegistries.Keys.PERKS, MysticcraftMod.res(name));
    }

    public static void bootstrap(BootstrapContext<Perk> perkBootstrapContext) {

    }
}
