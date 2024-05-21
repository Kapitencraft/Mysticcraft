package net.kapitencraft.mysticcraft.tags;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public interface ModItemTags {
    TagKey<Item> DEFAULT_HAMMER = create("default_hammer");
    TagKey<Item> TIER_1_HAMMER = create("tier_1_hammer");
    TagKey<Item> TIER_2_HAMMER = create("tier_2_hammer");
    TagKey<Item> STRIPPED_LOG = forge("stripped_logs");
    TagKey<Item> ENDER_HITTABLE = create("ender_hittable");
    TagKey<Item> CLEAVER = createWeapon("cleaver");
    TagKey<Item> DAGGER = createWeapon("dagger");
    TagKey<Item> HALBERD = createWeapon("halberd");
    TagKey<Item> LANCE = createWeapon("lance");
    TagKey<Item> SPEAR = createWeapon("spear");
    TagKey<Item> SWORD = createWeapon("sword");


    private static TagKey<Item> create(String s) {
        return Tags.makeKey(Registries.ITEM, MysticcraftMod.MOD_ID + ":" + s);
    }

    private static TagKey<Item> createWeapon(String key) {
        return create("weapon/" + key);
    }

    private static TagKey<Item> forge(String s) {
        return Tags.makeKey(Registries.ITEM, "forge:" + s);
    }
}
