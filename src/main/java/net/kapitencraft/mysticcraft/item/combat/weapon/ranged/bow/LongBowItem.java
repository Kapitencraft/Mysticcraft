package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.world.item.Rarity;

public class LongBowItem extends ModBowItem {
    public final double DIVIDER = 40;
    public static final double ARROW_SPEED_MUL = 5;


    public LongBowItem() {
        super(MiscHelper.rarity(Rarity.RARE)
                .durability(1320)
                .attributes(createAttributes(5))
                .component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.OFFENCE, GemstoneSlot.Type.DRAW_SPEED))
        );
    }


    @Override
    public double getDivider() {
        return DIVIDER;
    }

    @Override
    public int getKB() {
        return 3;
    }
}
