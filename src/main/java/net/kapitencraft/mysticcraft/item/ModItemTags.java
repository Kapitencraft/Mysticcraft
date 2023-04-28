package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> DEFAULT_HAMMER = create("default_hammer");
    public static final TagKey<Item> TIER_1_HAMMER = create("tier_1_hammer");
    public static final TagKey<Item> TIER_2_HAMMER = create("tier_2_hammer");



    private static TagKey<Item> create(String s) {
        return TagKey.create(Registries.ITEM , new ResourceLocation(MysticcraftMod.MOD_ID, s));
    }
}
