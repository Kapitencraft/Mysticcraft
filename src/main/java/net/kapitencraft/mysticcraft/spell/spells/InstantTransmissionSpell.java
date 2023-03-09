package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InstantTransmissionSpell {
    private static final Component[] description = new Component[]{Component.literal("teleports you 8 blocks ahead")};

    public static void execute(LivingEntity user, ItemStack ignoredStack) {
        MISCTools.saveTeleport(user, 8);
    }

    public static List<Component> getDescription() {
        return List.of(description);
    }
}
