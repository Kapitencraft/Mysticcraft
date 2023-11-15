package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.item.data.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.item.data.dungeon.IStarAbleItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public interface ITieredItem extends IStarAbleItem, IPrestigeAbleItem {

    List<ItemTier> getAvailableTiers();

    @Override
    default ItemStack prestige(ItemStack stack) {
        ItemTier tier = getTier(stack);
        if (tier == ItemTier.DEFAULT) {
            fromDefault().saveToStack(stack);
        } else if (tier.next != null) {
            tier.next.saveToStack(stack);
        }
        return stack;
    }

    @Override
    default boolean mayPrestige(ItemStack stack, boolean fromCommand) {
        ItemTier tier = getTier(stack);
        return (tier == ItemTier.DEFAULT || tier.next != null) && (IStarAbleItem.getStars(stack) == getMaxStars(stack) || fromCommand);
    }

    static @NotNull ItemTier getTier(ItemStack stack) {
        return ItemTier.getByName(stack.getOrCreateTag().getString("tier"));
    }

    @Override
    default int getMaxStars(ItemStack stack) {
        return ITieredItem.getTier(stack).getStarAmount();
    }


    ItemTier fromDefault();

    enum ItemTier {
        INFERNAL("infernal", 1.521379, 4, null, 25),
        FIERY("fiery", 1, 3, INFERNAL, 20),
        BURNING("burning", 0.586206, 2, FIERY, 20),
        HOT("hot", 0.260689, 1, BURNING, 15),
        DEFAULT("default", 0, 0, null, 10);

        public static final List<ItemTier> NETHER_ARMOR_TIERS = List.of(ItemTier.HOT, ItemTier.BURNING, ItemTier.FIERY, ItemTier.INFERNAL);


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

        public void saveToStack(ItemStack stack) {
            stack.getOrCreateTag().putString("tier", this.getRegName());
        }

        public String getRegName() {
            return name;
        }
    }
}
