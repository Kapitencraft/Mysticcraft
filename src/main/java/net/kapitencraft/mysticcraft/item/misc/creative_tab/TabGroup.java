package net.kapitencraft.mysticcraft.item.misc.creative_tab;

import net.kapitencraft.kap_lib.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * used to group items into {@link net.minecraft.world.item.CreativeModeTab}s
 */
//TODO rework
public class TabGroup {
    private static final List<TabGroup> groups = new ArrayList<>();
    public static final TabGroup TECHNOLOGY = TabGroup.builder().tab(ModCreativeModTabs.TECHNOLOGY).build();
    public static final TabGroup BUILDING_MATERIAL = TabGroup.builder().tab(CreativeModeTabs.BUILDING_BLOCKS).build();
    public static final TabGroup MATERIAL = TabGroup.builder().tab(ModCreativeModTabs.MATERIALS).build();
    public static final TabGroup COMBAT = TabGroup.builder().tab(ModCreativeModTabs.WEAPONS_AND_TOOLS).build();
    public static final TabGroup CRIMSON_MATERIAL = TabGroup.builder().tab(ModCreativeModTabs.MATERIALS).build();
    public static final TabGroup TERROR_MATERIAL = TabGroup.builder().tab(ModCreativeModTabs.MATERIALS).build();
    public static final TabGroup UTILITIES = TabGroup.builder().tab(CreativeModeTabs.TOOLS_AND_UTILITIES).build();
    public static final TabGroup DECO = TabGroup.builder().tab(ModCreativeModTabs.DECORATION).build();
    public static final TabGroup GOLDEN_DECO = TabGroup.builder().tab(ModCreativeModTabs.DECORATION).build();
    public static final TabGroup OPERATOR = TabGroup.builder().tab(CreativeModeTabs.OP_BLOCKS).build();

    public static final TabGroup PERIDOT_SYCAMORE = new TabGroup(ModCreativeModTabs.MATERIALS.getKey());

    protected final ResourceKey<CreativeModeTab>[] types;
    protected final List<RegistryObject<? extends Item>> items = new ArrayList<>();

    @SafeVarargs
    public TabGroup(ResourceKey<CreativeModeTab>... type) {
        this.types = type;
        groups.add(this);
    }

    public TabGroup add(RegistryObject<? extends Item> toAdd) {
        items.add(toAdd);
        return this;
    }

    public void register(ResourceKey<CreativeModeTab> type, Consumer<Collection<ItemStack>> consumer) {
        if (CollectionHelper.arrayContains(types, type)) {
            consumer.accept(items.stream().map(RegistryObject::get).map(ItemStack::new).toList());
        }
    }

    public static void registerAll(ResourceKey<CreativeModeTab> type, Consumer<Collection<ItemStack>> consumer) {
        groups.forEach(group -> group.register(type, consumer));
    }


    private static TabGroup.Builder builder() {
        return new Builder();
    }

    public static class Builder {
        List<ResourceKey<CreativeModeTab>> key;

        private Builder() {}

        public Builder tab(ResourceKey<CreativeModeTab> key) {
            this.key.add(key);
            return this;
        }

        public Builder tab(RegistryObject<CreativeModeTab> tab) {
            this.key.add(tab.getKey());
            return this;
        }

        @SuppressWarnings("unchecked")
        public TabGroup build() {
            return new TabGroup(key.toArray(ResourceKey[]::new));
        }
    }
}