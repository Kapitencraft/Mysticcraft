package net.kapitencraft.mysticcraft.potion;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ModPotionBrewing {
    private static final List<Mix> POTION_MIXES = new ArrayList<>();

    private static final Item DURATION = Items.REDSTONE;
    private static final Item STRENGTH = Items.GLOWSTONE_DUST;

    static {
        try {
            addMix(Potions.AWKWARD, ModItems.HARDENED_TEAR.get(), ModPotions.STUN.get());
            addMix(ModPotions.STUN.get(), DURATION, ModPotions.LONG_STUN.get());
            addMix(Potions.AWKWARD, Items.CHORUS_FRUIT, ModPotions.DISPLACEMENT.get());
        } catch (Throwable throwable) {
            MysticcraftMod.LOGGER.warn("failed to load Potions: {}", throwable.getMessage());
        }
    }


    protected static boolean isPotionIngredient(ItemStack p_43523_) {
        int i = 0;

        for(int j = POTION_MIXES.size(); i < j; ++i) {
            if ((POTION_MIXES.get(i)).ingredient.test(p_43523_)) {
                return true;
            }
        }

        return false;
    }

    public static ItemStack mix(ItemStack p_43530_, ItemStack p_43531_) {
        if (!p_43531_.isEmpty()) {
            Potion potion = PotionUtils.getPotion(p_43531_);
            Item item = p_43531_.getItem();
            int i = 0;

            for(int k = POTION_MIXES.size(); i < k; ++i) {
                Mix mix = POTION_MIXES.get(i);
                if (mix.from == potion && mix.ingredient.test(p_43530_)) {
                    return PotionUtils.setPotion(new ItemStack(item), mix.to);
                }
            }
        }

        return p_43531_;
    }

    private static void addMix(Potion in, Item ingredient, Potion out) {
        POTION_MIXES.add(new Mix(in, Ingredient.of(ingredient), out));
    }


    public static class Mix {
        public final Potion from;
        public final Ingredient ingredient;
        public final Potion to;

        public Mix(Potion in, Ingredient ingredient, Potion out) {
            this.from = in;
            this.ingredient = ingredient;
            this.to = out;
        }
    }
}
