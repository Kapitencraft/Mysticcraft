package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public  class  GemstoneItem extends Item {
    private final GemstoneType.Rarity RARITY;
    public final String gemstoneName;
    public GemstoneItem(GemstoneType.Rarity rarity, String gemstoneName) {
        super(new Properties().rarity(getRarity(rarity)));
        this.RARITY = rarity;
        this.gemstoneName = gemstoneName;
    }

    public String createDisplay() {
        return this.RARITY + " " + this.gemstoneName;
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
