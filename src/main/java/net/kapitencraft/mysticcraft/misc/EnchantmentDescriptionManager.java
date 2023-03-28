package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.enchantments.abstracts.CountEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Mod.EventBusSubscriber
public class EnchantmentDescriptionManager {
    private static final Map<Enchantment, MutableComponent> descriptions = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void registerTooltip(ItemTooltipEvent event) {
        onItemTooltip(event.getItemStack(), event.getToolTip(), event.getFlags());

    }

    private static void onItemTooltip(ItemStack stack, List<Component> tooltip, TooltipFlag ignoredTooltipFlag) {
        Set<Enchantment> enchantments = EnchantmentHelper.getEnchantments(stack).keySet();
        if (!enchantments.isEmpty()) {
            if (!Screen.hasShiftDown() && !(stack.getItem() instanceof EnchantedBookItem)) {
                tooltip.add(1, Component.translatable("mysticcraft.ench_desc.shift").withStyle(ChatFormatting.DARK_GRAY));
            } else {
                for (Enchantment enchantment : enchantments) {
                    for (Component line : tooltip) {
                        ComponentContents contents = line.getContents();
                        if (contents instanceof TranslatableContents translatable) {
                            if (translatable.getKey().equals(enchantment.getDescriptionId())) {
                                Component descriptionText = getDescription(stack, enchantment);
                                tooltip.add(tooltip.indexOf(line) + 1, descriptionText);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static MutableComponent getDescription(ItemStack stack, Enchantment ench) {
        String descriptionKey = ench.getDescriptionId() + ".desc";
        double change = 1;
        boolean isPercentage = false;
        int level = stack.getItem() instanceof EnchantedBookItem ? (EnchantmentHelper.deserializeEnchantments(EnchantedBookItem.getEnchantments(stack)).get(ench)) : EnchantmentHelper.getTagEnchantmentLevel(ench, stack);
        if (ench instanceof ModEnchantment modEnchantment) {
            change = modEnchantment.getValueMultiplier();
            isPercentage = modEnchantment.isPercentage();
        }
        boolean flag = ench instanceof CountEnchantment;
        double value = level * change;
        return Component.translatable(descriptionKey, Component.literal((value > 0 ? ("+" + value) : value) + (isPercentage ? "%" : "")), flag ? (stack.getEnchantmentLevel(ench) * 0.4) : 0).withStyle(ChatFormatting.DARK_GRAY);
    }
}