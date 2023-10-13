package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> DEFAULT_HAMMER = create("default_hammer");
    public static final TagKey<Item> TIER_1_HAMMER = create("tier_1_hammer");
    public static final TagKey<Item> TIER_2_HAMMER = create("tier_2_hammer");
    public static final TagKey<Item> STRIPPED_LOG = forge("stripped_logs");



    private static TagKey<Item> create(String s) {
        return TagKey.create(Registries.ITEM , MysticcraftMod.res(s));
    }

    private static TagKey<Item> forge(String s) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("forge", s));
    }
}
