package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;

public class Hyperion extends NecronSword {
    public Hyperion(Properties properties) {
        super(properties
                .attributes(createNecronAttributes(NecronSword.BASE_DAMAGE, 350, NecronSword.BASE_FEROCITY, NecronSword.BASE_STRENGTH).build())
                .component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.INTELLIGENCE))
        );
    }
}
