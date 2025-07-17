package net.kapitencraft.mysticcraft.client;

import net.kapitencraft.mysticcraft.event.custom.RegisterItemCategoriesEvent;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ItemCategory {
    private final Predicate<ItemStack> appliedPredicate;
    private final Component display;

    private ItemCategory(Predicate<ItemStack> appliedPredicate, Component display) {
        this.appliedPredicate = appliedPredicate;
        this.display = display;
    }

    public static ItemCategory create(Predicate<ItemStack> predicate, Component display) {
        return new ItemCategory(predicate, display);
    }

    public static ItemCategory tag(TagKey<Item> key, Component display) {
        return create(s -> s.is(key), display);
    }

    public static class Registry {
        private static final List<ItemCategory> categories = new ArrayList<>();

        public static void register() {
            categories.add(ItemCategory.tag(ModTags.Items.CATALYST, Component.translatable("item.indicator.spell_catalyst")));
            categories.add(ItemCategory.tag(ItemTags.AXES, Component.translatable("item.indicator.axe")));
            categories.add(ItemCategory.tag(ItemTags.SWORDS, Component.translatable("item.indicator.sword")));
            categories.add(ItemCategory.tag(ItemTags.PICKAXES, Component.translatable("item.indicator.pickaxe")));
            categories.add(ItemCategory.tag(Tags.Items.ARMORS_BOOTS, Component.translatable("item.indicator.boots")));
            categories.add(ItemCategory.tag(Tags.Items.ARMORS_CHESTPLATES, Component.translatable("item.indicator.chestplate")));
            categories.add(ItemCategory.tag(Tags.Items.ARMORS_LEGGINGS, Component.translatable("item.indicator.legs")));
            categories.add(ItemCategory.tag(Tags.Items.ARMORS_HELMETS, Component.translatable("item.indicator.helmet")));
            categories.add(ItemCategory.tag(ItemTags.BOATS, Component.translatable("item.indicator.boots")));
            categories.add(ItemCategory.tag(Tags.Items.TOOLS_BOWS, Component.translatable("item.indicator.bow")));
            categories.add(ItemCategory.tag(Tags.Items.TOOLS_CROSSBOWS, Component.translatable("item.indicator.crossbow")));
            categories.add(ItemCategory.tag(Tags.Items.TOOLS_SHIELDS, Component.translatable("item.indicator.shield")));
            categories.add(ItemCategory.tag(Tags.Items.TOOLS_TRIDENTS, Component.translatable("item.indicator.trident")));
            categories.add(ItemCategory.create(s -> s.is(Items.ENCHANTED_BOOK), Component.translatable("item.indicator.enchanted_book")));
            categories.add(ItemCategory.tag(ItemTags.HOES, Component.translatable("item.indicator.hoe")));
            categories.add(ItemCategory.tag(ItemTags.SHOVELS, Component.translatable("item.indicator.shovel")));
            categories.add(ItemCategory.create(s -> s.getItem() instanceof BlockItem, Component.translatable("item.indicator.block")));
            ModLoader.get().postEvent(new RegisterItemCategoriesEvent(categories));
        }

        public static void appendDisplay(MutableComponent component, ItemStack stack) {
            categories.forEach(itemCategory -> {
                if (itemCategory.appliedPredicate.test(stack)) component.append(" ").append(itemCategory.display);
            });
        }
    }
}
