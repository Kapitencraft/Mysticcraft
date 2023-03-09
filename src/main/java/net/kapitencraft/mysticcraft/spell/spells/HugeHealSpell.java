package net.kapitencraft.mysticcraft.spell.spells;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class HugeHealSpell {
    public static void execute(LivingEntity user, ItemStack stack) {
        user.heal(5f);
    }
    public static List<Component> getDescription() {
        return List.of(Component.literal("Heals for 5 health"));
    }
}
