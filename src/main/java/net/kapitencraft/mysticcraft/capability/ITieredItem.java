package net.kapitencraft.mysticcraft.capability;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.capability.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public interface ITieredItem extends IStarAbleItem, IPrestigeAbleItem {
    String ID = "Tier";

    List<ItemTier> getAvailableTiers();

    @SuppressWarnings("all")
    @Override
    default ItemStack upgrade(ItemStack stack) {
        if (IStarAbleItem.super.mayUpgrade(stack)) {
            IStarAbleItem.super.upgrade(stack);
            return stack;
        }
        ItemTier tier = getTier(stack);
        if (tier == ItemTier.DEFAULT) {
            fromDefault().saveToStack(stack);
        } else {
            tier.next.saveToStack(stack);
        }
        IStarAbleItem.setStars(stack, 0);
        return stack;
    }

    @Override
    default boolean mayUpgrade(ItemStack stack) {
        ItemTier tier = getTier(stack);
        return (tier == ItemTier.DEFAULT || tier.next != null) || IStarAbleItem.super.mayUpgrade(stack);
    }

    static @NotNull ItemTier getTier(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.TIER, ItemTier.DEFAULT);
    }

    @Override
    default int getMaxStars(ItemStack stack) {
        return ITieredItem.getTier(stack).getStarAmount();
    }

    ItemTier fromDefault();

    enum ItemTier implements StringRepresentable {
        INFERNAL("infernal", 4, 4, null, 25),
        FIERY("fiery", 2.5, 3, INFERNAL, 20),
        BURNING("burning", 1.6, 2, FIERY, 20),
        HOT("hot", 1.25, 1, BURNING, 15),
        DEFAULT("default", 1, 0, null, 10);

        public static final List<ItemTier> NETHER_ARMOR_TIERS = List.of(ItemTier.HOT, ItemTier.BURNING, ItemTier.FIERY, ItemTier.INFERNAL);
        public static final Codec<ItemTier> CODEC = StringRepresentable.fromEnum(ItemTier::values);


        final String name;
        final double valueMul;
        final int number;
        final @Nullable ItemTier next;
        final int starAmount;

        ItemTier(String name, double valueMul, int number, @Nullable ItemTier next, int starAmount) {
            this.name = name;
            this.valueMul = valueMul;
            this.number = number;
            this.next = next;
            this.starAmount = starAmount;
        }

        public int getNumber() {
            return number;
        }

        public MutableComponent getName() {
            return Component.translatable("item_tier." + this.name);
        }

        public static ItemTier getByName(String name) {
            for (ItemTier armorTier : values()) {
                if (Objects.equals(armorTier.name, name)) {
                    return armorTier;
                }
            }
            return DEFAULT;
        }

        public int getStarAmount() {
            return starAmount;
        }

        public double getValueMul() {
            return valueMul;
        }

        public CompoundTag createTag() {
            CompoundTag tag = new CompoundTag();
            tag.putString(ID, this.getRegName());
            return tag;
        }

        public void saveToStack(ItemStack stack) {
            stack.set(ModDataComponentTypes.TIER, this);
        }

        public String getRegName() {
            return name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
