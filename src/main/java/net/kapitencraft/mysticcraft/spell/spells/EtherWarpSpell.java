package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EtherWarpSpell {
    public static boolean execute(LivingEntity living, ItemStack ignoredStack) {
        MiscHelper.saveTeleport(living, 57);
        return true;
    }

    public static List<Component> getDescription() {
        return List.of(Component.literal("teleports to your targeted block up to 57 blocks away"));
    }
}
