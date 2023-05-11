package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public  class  GemstoneItem extends Item implements DyeableLeatherItem {
    private final GemstoneType.Rarity RARITY;
    public final String gemstoneName;
    public GemstoneItem(GemstoneType.Rarity rarity, String gemstoneName) {
        super(new Properties().rarity(getRarity(rarity)));
        this.RARITY = rarity;
        this.gemstoneName = gemstoneName;
    }

    @Override
    public int getColor(@NotNull ItemStack p_41122_) {
        return this.getGemstone().getColour();
    }

    @Override
    public void clearColor(@NotNull ItemStack p_41124_) {
    }

    @Override
    public boolean hasCustomColor(@NotNull ItemStack p_41114_) {
        return false;
    }

    @Override
    public void setColor(@NotNull ItemStack p_41116_, int p_41117_) {
    }

    public static Rarity getRarity(GemstoneType.Rarity rarity) {
        net.minecraft.world.item.Rarity rarity1 = net.minecraft.world.item.Rarity.COMMON;
        if (GemstoneType.Rarity.FINE.equals(rarity) || GemstoneType.Rarity.FLAWLESS.equals(rarity)) {
            rarity1 = net.minecraft.world.item.Rarity.UNCOMMON;
        } else if (GemstoneType.Rarity.PERFECT.equals(rarity)) {
            rarity1 = net.minecraft.world.item.Rarity.RARE;
        }
        return rarity1;
    }


    public static GemstoneItem of(GemstoneSlot slot) {
        if (slot.getAppliedGemstone() == null || slot.getType() == null) {
            return null;
        }
        return ModItems.GEMSTONES.get(slot.getAppliedGemstone()).get(slot.getGemRarity()).get();
    }

    public GemstoneType.Rarity getRarity() {
        return this.RARITY;
    }

    public GemstoneType getGemstone() {
        return GemstoneType.getById(this.gemstoneName);
    }

}
