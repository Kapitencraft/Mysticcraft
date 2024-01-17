package net.kapitencraft.mysticcraft.enchantments.extras;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantment;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnchantmentDescriptionManager {

    @SubscribeEvent
    public static void registerTooltip(ItemTooltipEvent event) {
        onItemTooltip(event.getItemStack(), event.getToolTip(), event.getFlags());
    }

    private static void onItemTooltip(ItemStack stack, List<Component> tooltip, TooltipFlag ignoredTooltipFlag) {
        Set<Enchantment> enchantments = EnchantmentHelper.getEnchantments(stack).keySet();
        boolean fromBook = stack.getItem() instanceof EnchantedBookItem;
        if (!enchantments.isEmpty()) {
            if (!Screen.hasShiftDown() && !fromBook) {
                tooltip.add(GemstoneHelper.hasCapability(stack) ? 2:1, Component.translatable("mysticcraft.ench_desc.shift").withStyle(ChatFormatting.DARK_GRAY));
            } else {
                for (Enchantment enchantment : enchantments) {
                    for (Component line : tooltip) {
                        ComponentContents contents = line.getContents();
                        if (contents instanceof TranslatableContents translatable) {
                            if (translatable.getKey().equals(enchantment.getDescriptionId())) {
                                if (fromBook) {
                                    if (!enchantment.isTradeable()) tooltip.add(tooltip.indexOf(line) + 1, Component.translatable("mysticcraft.ench_desc.not_tradeable").withStyle(ChatFormatting.YELLOW));
                                    if (enchantment.isTreasureOnly()) tooltip.add(tooltip.indexOf(line) + 1, Component.translatable("mysticcraft.ench_desc.treasure").withStyle(ChatFormatting.YELLOW));
                                }
                                tooltip.addAll(tooltip.indexOf(line) + 1, getDescription(stack, enchantment));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static List<Component> getDescription(ItemStack stack, Enchantment ench) {
        int level = stack.getItem() instanceof EnchantedBookItem ? (EnchantmentHelper.deserializeEnchantments(EnchantedBookItem.getEnchantments(stack)).get(ench)) : EnchantmentHelper.getTagEnchantmentLevel(ench, stack);
        String[] objects = ench instanceof ModEnchantment modEnchantment ? modEnchantment.getDescriptionMods(level) : new String[]{String.valueOf(level)};
        Stream<String> stream = Arrays.stream(objects);
        return TextHelper.getAllMatchingFilter(integer -> {
            String descId = ench.getDescriptionId() + ".desc";
            if (!I18n.exists(descId)) descId += "ription";
            if (integer != 0) {
                descId += integer;
            }
            return descId;
        }, component -> component.withStyle(ChatFormatting.DARK_GRAY), stream.map(TextHelper::wrapInRed).toArray());
    }
}