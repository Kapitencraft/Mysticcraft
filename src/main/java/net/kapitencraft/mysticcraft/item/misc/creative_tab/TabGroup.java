package net.kapitencraft.mysticcraft.item.misc.creative_tab;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * used to group items into {@link net.minecraft.world.item.CreativeModeTab}s
 */
public class TabGroup {
    private static final List<TabGroup> groups = new ArrayList<>();
    public static final TabGroup BUILDING_MATERIAL = new TabGroup(TabRegister.TabTypes.BUILDING_MATERIALS);
    public static final TabGroup MATERIAL = new TabGroup(TabRegister.TabTypes.MOD_MATERIALS);
    public static final TabGroup COMBAT = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    public static final TabGroup CRIMSON_MATERIAL = new TabGroup(TabRegister.TabTypes.MOD_MATERIALS);
    public static final TabGroup TERROR_MATERIAL = new TabGroup(TabRegister.TabTypes.MOD_MATERIALS);
    public static final TabGroup UTILITIES = new TabGroup(TabRegister.TabTypes.TOOLS_AND_UTILITIES);
    public static final TabGroup DECO = new TabGroup(TabRegister.TabTypes.DECO);
    public static final TabGroup GOLDEN_DECO = new TabGroup(TabRegister.TabTypes.DECO);
    public static final TabGroup OPERATOR = new TabGroup(TabRegister.TabTypes.OPERATOR);
    protected final List<TabRegister.TabTypes> types;
    protected final List<RegistryObject<? extends Item>> items = new ArrayList<>();

    public TabGroup(TabRegister.TabTypes... type) {
        this.types = Arrays.asList(type);
        groups.add(this);
    }

    private TabGroup(List<TabRegister.TabTypes> list) {
        this.types = list;
    }

    public TabGroup add(RegistryObject<? extends Item> toAdd) {
        items.add(toAdd);
        return this;
    }

    public void register(TabRegister.TabTypes type, Consumer<Collection<ItemStack>> consumer) {
        if (types.contains(type)) {
            consumer.accept(items.stream().map(RegistryObject::get).map(ItemStack::new).toList());
        }
    }

    public static void registerAll(TabRegister.TabTypes types, Consumer<Collection<ItemStack>> consumer) {
        groups.forEach(group -> group.register(types, consumer));
    }

    public TabGroup andThen(TabGroup other) {
        List<TabRegister.TabTypes> own = types;
        own.addAll(other.types);
        return new TabGroup(own);
    }
}