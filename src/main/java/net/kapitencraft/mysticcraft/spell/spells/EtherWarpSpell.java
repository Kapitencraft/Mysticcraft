package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EtherWarpSpell {
    public static void execute(LivingEntity living, ItemStack ignoredStack) {
        MiscUtils.saveTeleport(living, 57);
    }

    public static List<Component> getDescription() {
        return List.of(Component.literal("teleports to your targeted block up to 57 blocks away"));
    }
}
