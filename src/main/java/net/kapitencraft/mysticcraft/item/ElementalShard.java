package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.spell.Element;
import net.kapitencraft.mysticcraft.spell.Elements;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class ElementalShard extends Item {
    private final Element element;

    public ElementalShard(Element element) {
        super(new Properties().rarity(Rarity.EPIC));
        this.element = element;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack p_41458_) {
        return Component.translatable("item.mysticcraft.elemental_shard").append(" ").append(Component.translatable("element." + element.getName()));
    }

    public static HashMap<Element, RegistryObject<Item>> registerElementShards() {
        return ModItems.createRegistry(ElementalShard::new, value -> "elemental_shard_of_" + value.getName(), List.of(Elements.values()), ModItems.TabTypes.MOD_MATERIALS);
    }
}