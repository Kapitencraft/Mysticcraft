package net.kapitencraft.mysticcraft.tags;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags {

    public interface Items {
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
        TagKey<Item> CATALYST = create("magic_catalyst");


        private static TagKey<Item> create(String s) {
            return ModTags.makeModKey(Registries.ITEM, s);
        }

        private static TagKey<Item> createWeapon(String key) {
            return create("weapon/" + key);
        }

        private static TagKey<Item> forge(String s) {
            return ModTags.makeForgeKey(Registries.ITEM, s);
        }
    }

    public interface Blocks {
        TagKey<Block> FARMABLE = createModKey("farmable_blocks");
        TagKey<Block> FORAGEABLE = createModKey("forageable_blocks");
        TagKey<Block> MINEABLE = createModKey("mineable_blocks");

        static TagKey<Block> createModKey(String id) {
            return ModTags.makeModKey(Registries.BLOCK, id);
        }
    }

    public interface Entities {
        TagKey<EntityType<?>> OG_NETHER_MOBS = createForge("og_nether_mobs");
        TagKey<EntityType<?>> NETHER_MOBS = createForge("nether_mobs");

        private static TagKey<EntityType<?>> createForge(String name) {
            return ModTags.makeForgeKey(Registries.ENTITY_TYPE, name);
        }
    }

    public interface Fluids {
        TagKey<Fluid> MANA_FLUID = ModTags.makeModKey(Registries.FLUID, "mana_fluid");
    }

    public static <T> TagKey<T> makeKey(ResourceKey<Registry<T>> key, String id) {
        return TagKey.create(key, new ResourceLocation(id));
    }

    public static <T> TagKey<T> makeModKey(ResourceKey<Registry<T>> key, String id) {
        return TagKey.create(key, MysticcraftMod.res(id));
    }

    public static <T> TagKey<T> makeForgeKey(ResourceKey<Registry<T>> key, String id) {
        return makeKey(key, "forge:" + id);
    }
}
