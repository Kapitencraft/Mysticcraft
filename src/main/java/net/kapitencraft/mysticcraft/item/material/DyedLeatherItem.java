package net.kapitencraft.mysticcraft.item.material;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class DyedLeatherItem extends Item implements DyeableLeatherItem {
    public static final int SHADOW_LEATHER_ID = 3355189;
    public DyedLeatherItem() {
        super(new Properties().rarity(Rarity.RARE));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return this.getColor(stack) == SHADOW_LEATHER_ID ? Component.translatable("item.mysticcraft.shadow_leather") : super.getName(stack);
    }
}