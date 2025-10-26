package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;

public class Valkyrie extends NecronSword {

    public Valkyrie() {
        super(new Properties()
                .attributes(createNecronAttributes(NecronSword.REFINED_BASE_DAMAGE, 60, 60, 145).build())
                .component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.STRENGTH))
        );
    }
}
