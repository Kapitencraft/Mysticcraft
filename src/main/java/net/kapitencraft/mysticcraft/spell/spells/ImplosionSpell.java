package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.misc.ManaAOE;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ImplosionSpell {

    public static void execute(LivingEntity user, ItemStack stack) {
        ManaAOE.execute(user, "implosion", 0.1f, 5, 5);
    }
    public static List<Component> getDescription() {
        return List.of(Component.literal("Deals 5 Damage to Enemies 5 Blocks Around you"));
    }
}
