package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class RNGDropHelper {



    public static void calculateAndDrop(ItemStack stack, float chance, LivingEntity source, Vec3 spawnPos) {
        double magicFind = source.getAttributeValue(ModAttributes.MAGIC_FIND.get());
        if (Math.random() <= chance * (1 + magicFind / 100)) {
            if (source instanceof Player player) {
                player.sendSystemMessage(createRareDropMessage(stack, magicFind));
            }
            source.level.addFreshEntity(new ItemEntity(source.level, spawnPos.x, spawnPos.y, spawnPos.z, stack));
        }
    }

    private static Component createRareDropMessage(ItemStack drop, double magic_find) {
        return Component.literal(FormattingCodes.BOLD + FormattingCodes.LIGHT_PURPLE + "VERY CRAZY RARE DROP" + FormattingCodes.RESET + FormattingCodes.BOLD + ": " + FormattingCodes.RESET).append(drop.getDisplayName()).append(Component.literal(FormattingCodes.AQUA + " (+" + magic_find + ")"));
    }

}
