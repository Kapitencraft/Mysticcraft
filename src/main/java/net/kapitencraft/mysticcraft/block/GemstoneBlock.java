package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class GemstoneBlock extends Block {
    public static final double VERY_LOW_STRENGHT = 4;
    public static final double LOW_STRENGHT = 5;
    public static final double LOW_MEDIUM_STRENGHT = 6;
    public static final double MEDIUM_STRENGHT = 7;
    public static final double HIGH_MEDIUM_STRENGHT = 8;
    public static final double HIGH_STRENGHT = 9;
    public static final double VERY_HIGH_STRENGHT = 10;

    private final GemstoneType type;
    public GemstoneBlock(GemstoneType gemstoneName) {
        super(Properties.of(Material.HEAVY_METAL).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().strength((float) gemstoneName.getBlockStrength()));
        this.type = gemstoneName;
    }

    public GemstoneItem getItem() {
        return ModItems.GEMSTONES.get(type).get(GemstoneType.Rarity.ROUGH).get();
    }
}
