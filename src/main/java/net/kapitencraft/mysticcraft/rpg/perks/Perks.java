package net.kapitencraft.mysticcraft.rpg.perks;

import com.mojang.datafixers.util.Pair;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;

public interface Perks {

    ResourceKey<Perk> DAGGER_COMBAT = key("combat/dagger");
    ResourceKey<Perk> SWORD_COMBAT = key("combat/sword");
    ResourceKey<Perk> AXE_COMBAT = key("combat/axe");
    ResourceKey<Perk> LONGSWORD_COMBAT = key("combat/longsword");
    ResourceKey<Perk> CLEAVER_COMBAT = key("combat/cleaver");
    ResourceKey<Perk> HALBERD = key("combat/halberd");
    ResourceKey<Perk> LANCE = key("combat/lance");
    ResourceKey<Perk> SPEAR = key("combat/spear");

    ResourceKey<Perk> ARCHERY = key("combat/archery");
    ResourceKey<Perk> RIDING = key("riding");
    ResourceKey<Perk> HAGGLING = key("haggling");

    List<Pair<ResourceKey<Perk>, TagKey<Item>>> COMBAT_PERKS = List.of(
            Pair.of(SWORD_COMBAT, ItemTags.SWORDS),
            Pair.of(DAGGER_COMBAT, ModTags.Items.DAGGER),
            Pair.of(AXE_COMBAT, ItemTags.AXES),
            Pair.of(LONGSWORD_COMBAT, ModTags.Items.LONGSWORD),
            Pair.of(CLEAVER_COMBAT, ModTags.Items.CLEAVER),
            Pair.of(HALBERD, ModTags.Items.HALBERD),
            Pair.of(LANCE, ModTags.Items.LANCE),
            Pair.of(SPEAR, ModTags.Items.SPEAR)
    );

    private static ResourceKey<Perk> key(String name) {
        return ResourceKey.create(ModRegistries.Keys.PERKS, MysticcraftMod.res(name));
    }

    static void bootstrap(BootstrapContext<Perk> perkBootstrapContext) {
        //perkBootstrapContext.register();
    }
}
