package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class RNGDropHelper {



    public static void calculateAndDrop(ItemStack stack, float chance, LivingEntity source, Vec3 spawnPos) {
        double magicFind = source.getAttributeValue(ModAttributes.MAGIC_FIND.get());
        if (Math.random() <= getFinalChance(chance, magicFind)) {
            if (source instanceof Player player) {
                player.sendSystemMessage(createRareDropMessage(stack, magicFind));
            }
            if (source instanceof Player player && player.getMainHandItem().getEnchantmentLevel(ModEnchantments.TELEKINESIS.get()) > 0){
                player.getInventory().add(stack);
            } else {
                source.level.addFreshEntity(new ItemEntity(source.level, spawnPos.x, spawnPos.y, spawnPos.z, stack));
            }
        }
    }

    private static double getFinalChance(float chance, double magicFind) {
        return chance * (1 + magicFind) / 100;
    }

    public static ItemStack dontDrop(Item item, int maxAmount, LivingEntity source, float chance) {
        double magicFind = source.getAttributeValue(ModAttributes.MAGIC_FIND.get());
        int amount = 0;
        for (int i = 0; i < maxAmount; i++) {
            if (Math.random() <= getFinalChance(chance, magicFind)) {
                amount++;
            }
        }
        ItemStack stack = new ItemStack(item, amount);
        createRareDropMessage(stack, magicFind);
        return stack;
    }

    private static Component getStackNameWithoutBrackets(ItemStack stack) {
        MutableComponent mutablecomponent = Component.empty().append(stack.getHoverName());
        if (stack.hasCustomHoverName()) {
            mutablecomponent.withStyle(ChatFormatting.ITALIC);
        }

        mutablecomponent.withStyle(stack.getRarity().getStyleModifier()).withStyle((p_220170_) ->
                p_220170_.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(stack))));
        return mutablecomponent;
    }

    private static Component createRareDropMessage(ItemStack drop, double magic_find) {
        return Component.literal(FormattingCodes.BOLD + FormattingCodes.LIGHT_PURPLE + "VERY CRAZY RARE DROP" + FormattingCodes.RESET + FormattingCodes.BOLD + ": " + FormattingCodes.RESET).append(getStackNameWithoutBrackets(drop)).append(FormattingCodes.AQUA + " (+" + magic_find + ")");
    }
}