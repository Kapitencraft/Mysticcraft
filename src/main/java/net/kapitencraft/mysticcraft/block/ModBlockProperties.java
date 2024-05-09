package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.block.special.GemstoneSeed;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockProperties {
    public static final BooleanProperty PLAYER_PLACED = BooleanProperty.create("player_placed");
    public static final EnumProperty<GemstoneType> GEMSTONE_TYPE = EnumProperty.create("gemstone", GemstoneType.class);
    public static final EnumProperty<GemstoneSeed.MaterialType> STONE_TYPE = EnumProperty.create("stone_type", GemstoneSeed.MaterialType.class);
}
