package net.kapitencraft.mysticcraft.item.misc.creative_tab;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;

public class ArmorTabGroup extends TabGroup {

    public ArmorTabGroup(TabRegister.TabTypes... type) {
        super(type);
    }
    private static final Comparator<ArmorItem> comparator = Comparator.comparingDouble(value -> 4 - value.getEquipmentSlot().getIndex());

    @Override
    public void register(TabRegister.TabTypes type, Consumer<Collection<ItemStack>> consumer) {
        if (types.contains(type)) {
            consumer.accept(items.stream().map(RegistryObject::get).map(item -> item instanceof ArmorItem armorItem ? armorItem : null).filter(Objects::nonNull).sorted(comparator).map(ItemStack::new).toList());
        }
    }
}
