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



    private static TagKey<Item> create(String s) {
        return Tags.makeKey(Registries.ITEM, MysticcraftMod.MOD_ID + ":" + s);
    }

    private static TagKey<Item> forge(String s) {
        return Tags.makeKey(Registries.ITEM, "forge:" + s);
    }
}
