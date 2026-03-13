package net.kapitencraft.mysticcraft.item.tools.fishing_rods;

import net.kapitencraft.kap_lib.item.entity.fishing.AbstractFishingHook;
import net.kapitencraft.kap_lib.item.tools.fishing.ModFishingRod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.entity.LavaFishingHook;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class LavaFishingRod extends ModFishingRod {
    public LavaFishingRod(Rarity rarity) {
        super(new Properties().rarity(rarity)
                .component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.FISHING_SPEED))
        );
    }

    @Override
    public AbstractFishingHook create(Player player, Level level, int luck, int lureSpeed) {
        return LavaFishingHook.create(player, level, luck, lureSpeed);
    }
}
