package net.kapitencraft.mysticcraft.potion;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
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
            addMix(Potions.AWKWARD, ModItems.HARDENED_TEAR.get(), ModPotions.STUN);
            addMix(ModPotions.STUN, DURATION, ModPotions.LONG_STUN);
            addMix(Potions.AWKWARD, Items.CHORUS_FRUIT, ModPotions.DISPLACEMENT);
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

    public static ItemStack mix(ItemStack p_43530_, ItemStack stack) {
        if (!stack.isEmpty()) {
            PotionContents potion = stack.get(DataComponents.POTION_CONTENTS);
            Item item = stack.getItem();
            int i = 0;
            if (potion != null && potion.potion().isPresent()) {
                for (int k = POTION_MIXES.size(); i < k; ++i) {
                    Mix mix = POTION_MIXES.get(i);
                    if (mix.from == potion.potion().get() && mix.ingredient.test(p_43530_)) {
                        return PotionContents.createItemStack(item, mix.to);
                    }
                }
            }
        }

        return stack;
    }

    private static void addMix(Holder<Potion> in, Item ingredient, Holder<Potion> out) {
        POTION_MIXES.add(new Mix(in, Ingredient.of(ingredient), out));
    }


    public static class Mix {
        public final Holder<Potion> from;
        public final Ingredient ingredient;
        public final Holder<Potion> to;

        public Mix(Holder<Potion> in, Ingredient ingredient, Holder<Potion> out) {
            this.from = in;
            this.ingredient = ingredient;
            this.to = out;
        }
    }
}
