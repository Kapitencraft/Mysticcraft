package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModTiers {
    public static final Tier MANA_STEEL = new Tier() {
        @Override
        public int getUses() {
            return 1890;
        }

        @Override
        public float getSpeed() {
            return 4f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 5;
        }

        @Override
        public int getLevel() {
            return 7;
        }

        @Override
        public int getEnchantmentValue() {
            return 20;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ModItems.MANA_STEEL_INGOT.get());
        }
    };
    public static final Tier SPELL_TIER = new Tier() {
        @Override
        public int getUses() {
            return 1000;
        }

        @Override
        public float getSpeed() {
            return 2;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public int getLevel() {
            return 7;
        }

        @Override
        public int getEnchantmentValue() {
            return 19;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of((ItemLike) ModItems.ELEMENTAL_SHARDS.values().stream().map(RegistryObject::get).toList());
        }
    };
}
