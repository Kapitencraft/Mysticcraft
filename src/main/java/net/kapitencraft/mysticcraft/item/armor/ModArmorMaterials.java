package net.kapitencraft.mysticcraft.item.armor;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    WIZARD_HAT("wizard_hat", 5, new int[]{0, 0, 0, 7}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(Items.LEATHER)),
    FROZEN_BLAZE("frozen_blaze", 8, new int[]{6, 9, 13, 7}, 29, SoundEvents.ARMOR_EQUIP_ELYTRA, 0.9f, 0.1f, () -> Ingredient.of(Items.BLAZE_ROD)),
    ENDER_KNIGHT("ender_knight", 25, new int[]{8, 12, 19, 9}, 12, SoundEvents.ARMOR_EQUIP_NETHERITE, 1.2f, 0.9f, () -> Ingredient.of(Items.NETHERITE_INGOT)),
    MANA_STEEL("mana_steel", 12, new int[]{6, 9, 13, 6}, 4, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.1f, 0f, () -> Ingredient.of(ModItems.MANA_STEEL_INGOT.get())),
    SHADOW_ASSASSIN("shadow_assassin", 17, new int[]{7, 11, 15, 6}, 6, SoundEvents.ARMOR_EQUIP_LEATHER, 0.5f, 0f, ()-> Ingredient.of(Items.LEATHER)),
    SOUL_MAGE("soul_mage", 24, new int[]{8,11, 18, 7}, 34, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.8f, 0.2f, ()-> Ingredient.of(Items.SOUL_SOIL)),
    CRIMSON("crimson", 30, new int[]{9, 13, 16, 8}, 14, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.3f, 1f, ()-> Ingredient.of(Items.LEATHER));
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockBackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durMul, int[] slotProtections, int enchantVal, SoundEvent p_40478_, float toughness, float kbResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durMul;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantVal;
        this.sound = p_40478_;
        this.toughness = toughness;
        this.knockBackResistance = kbResistance;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    public int getDurabilityForSlot(EquipmentSlot p_40484_) {
        return HEALTH_PER_SLOT[p_40484_.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot p_40487_) {
        return this.slotProtections[p_40487_.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public @NotNull SoundEvent getEquipSound() {
        return this.sound;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public @NotNull String getName() {
        return MysticcraftMod.MOD_ID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockBackResistance;
    }
}
