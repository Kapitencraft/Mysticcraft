package net.kapitencraft.mysticcraft.item.misc;

import com.google.common.base.Suppliers;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModTiers implements Tier {
    MANA_STEEL(ModTags.Blocks.INCORRECT_FOR_MANA_STEEL_TOOL, ModTags.Blocks.NEEDS_MANA_STEEL_TOOL, 1890, 4f, 5, 20, () -> Ingredient.of(ModItems.MANA_STEEL_INGOT.get())),
    SPELL_TIER(null, null, 1000, 2f, 0, 19, () -> Ingredient.of(ModItems.ELEMENTAL_SHARDS.values().stream().map(DeferredItem::get).map(ItemStack::new))),
    GHOSTLY_TIER(null, null, 500, 3f, 0, 20, () -> Ingredient.of(Items.GHAST_TEAR)),
    SHADOW_TIER(null, null, 520, 3.4f, 2, 15, () -> Ingredient.of(ModItems.SHADOW_CRYSTAL.get()));

    private final TagKey<Block> incorrectBlocksForDrops;
    private final TagKey<Block> needForDrops;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModTiers(TagKey<Block> incorrectBlockForDrops, TagKey<Block> needForDrops, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.incorrectBlocksForDrops = incorrectBlockForDrops;
        this.needForDrops = needForDrops;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public @NotNull TagKey<Block> getTag() {
        return this.needForDrops;
    }

}
