package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantment;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Mod.EventBusSubscriber
public class EnchantmentDescriptionManager {
    private static final Map<String, Object[]> descriptions = new HashMap<>();

    @SubscribeEvent
    public static void registerTooltip(ItemTooltipEvent event) {
        onItemTooltip(event.getItemStack(), event.getToolTip(), event.getFlags());
    }

    private static void onItemTooltip(ItemStack stack, List<Component> tooltip, TooltipFlag ignoredTooltipFlag) {
        Set<Enchantment> enchantments = EnchantmentHelper.getEnchantments(stack).keySet();
        boolean fromBook = stack.getItem() instanceof EnchantedBookItem;
        if (!enchantments.isEmpty()) {
            if (!Screen.hasShiftDown() && !fromBook) {
                tooltip.add(stack.getItem() instanceof IGemstoneApplicable ? 2:1, Component.translatable("mysticcraft.ench_desc.shift").withStyle(ChatFormatting.DARK_GRAY));
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
        if (!I18n.exists(descriptionKey) && I18n.exists(ench.getDescriptionId() + ".description")) {
            descriptionKey = ench.getDescriptionId() + ".description";
        }
        int level = stack.getItem() instanceof EnchantedBookItem ? (EnchantmentHelper.deserializeEnchantments(EnchantedBookItem.getEnchantments(stack)).get(ench)) : EnchantmentHelper.getTagEnchantmentLevel(ench, stack);
        Object[] objects = ench instanceof ModEnchantment modEnchantment ? modEnchantment.getDescriptionMods(level) : new Object[]{level};
        return Component.translatable(descriptionKey, objects).withStyle(ChatFormatting.DARK_GRAY);
    }
}