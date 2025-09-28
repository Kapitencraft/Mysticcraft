package net.kapitencraft.mysticcraft.item.misc.creative_tab;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

public class ArmorTabGroup extends TabGroup {
    private static final Comparator<ArmorItem> comparator = Comparator.comparingInt(value -> 4 - value.getEquipmentSlot().getIndex());

    @SafeVarargs
    public ArmorTabGroup(Pair<Either<Supplier<Item>, Supplier<Item>>, ResourceKey<CreativeModeTab>>... type) {
        super(type);
    }

    public static ArmorTabGroup create() {
        return ArmorTabGroup.createBuilder().build();
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    @Override
    public void register(ResourceKey<CreativeModeTab> type, MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> content) {
        this.items.removeIf(o -> {
            if (!(o.get() instanceof ArmorItem)) {
                MysticcraftMod.LOGGER.warn("non-armor item has been added to armor tabgroup: {}", o.getKey());
                return true;
            }
            return false;
        });
        this.items.sort((o1, o2) -> comparator.compare((ArmorItem) o1.get(), (ArmorItem) o2.get()));
        super.register(type, content);
    }

    public static class Builder {
        private final List<Pair<Either<Supplier<Item>, Supplier<Item>>, ResourceKey<CreativeModeTab>>> keys = new ArrayList<>();

        private Builder() {
            tabAfter(() -> Items.NETHERITE_BOOTS, CreativeModeTabs.COMBAT);
        }

        public Builder tab(ResourceKey<CreativeModeTab> key) {
            this.keys.add(Pair.of(null, key));
            return this;
        }

        public Builder tabAfter(Supplier<Item> after, ResourceKey<CreativeModeTab> key) {
            this.keys.add(Pair.of(Either.right(after), key));
            return this;
        }

        public Builder tabBefore(Supplier<Item> before, ResourceKey<CreativeModeTab> key) {
            this.keys.add(Pair.of(Either.left(before), key));
            return this;
        }

        public Builder tab(RegistryObject<CreativeModeTab> tab) {
            return this.tab(tab.getKey());
        }

        public Builder tabAfter(Supplier<Item> after, RegistryObject<CreativeModeTab> value) {
            return this.tabAfter(after, value.getKey());
        }

        public Builder tabBefore(Supplier<Item> before, RegistryObject<CreativeModeTab> value) {
            return this.tabBefore(before, value.getKey());
        }

        @SuppressWarnings("unchecked")
        public ArmorTabGroup build() {
            return new ArmorTabGroup(keys.toArray(Pair[]::new));
        }
    }
}
