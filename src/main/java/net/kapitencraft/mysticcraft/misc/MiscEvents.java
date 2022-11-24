package net.kapitencraft.mysticcraft.misc;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.renderer.FrozenBlazeRenderer;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.item.bow.ShortBowItem;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;

public class MiscEvents {
    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void DescriptionRegister(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            List<Component> toolTip = event.getToolTip();
            Player player = event.getEntity();
            Component searched = Component.literal(FormattingCodes.GRAY.UNICODE + "Mana-Cost: " + FormattingCodes.DARK_RED.UNICODE);
            if (toolTip.contains(searched) && stack.getItem() instanceof SpellItem spellItem) {
                boolean flag = player != null && stack != player.getMainHandItem();
                if (flag) {
                    AttributeInstance cost_instance = player.getAttribute(ModAttributes.MANA_COST.get());
                    assert cost_instance != null;
                    cost_instance.removeModifier(SpellItem.MANA_COST_MOD);
                    cost_instance.removeModifier(SpellItem.ULTIMATE_WISE_MOD);
                    if (stack.getEnchantmentLevel(ModEnchantments.ULTIMATE_WISE.get()) > 0) {
                        cost_instance.addTransientModifier(Objects.requireNonNull(AttributeGeneration.UltimateWiseMod(stack)));
                    }
                    cost_instance.addTransientModifier(new AttributeModifier(SpellItem.MANA_COST_MOD, "Tooltip", spellItem.getManaCost(), AttributeModifier.Operation.ADDITION));

                }
                Component found = toolTip.get(toolTip.lastIndexOf(searched));
                if (found instanceof MutableComponent mutable && player != null) {
                    mutable.append(FormattingCodes.DARK_RED.UNICODE + player.getAttributeValue(ModAttributes.MANA_COST.get()));
                }
                if (flag) {
                    AttributeInstance cost_instance = player.getAttribute(ModAttributes.MANA_COST.get());
                    assert cost_instance != null;
                    cost_instance.removeModifier(SpellItem.MANA_COST_MOD);
                    cost_instance.removeModifier(SpellItem.ULTIMATE_WISE_MOD);
                }
            }
            Rarity rarity = stack.getItem().getRarity(stack);
            boolean flag = rarity != MISCTools.getItemRarity(stack.getItem());
            String NameMod = FormattingCodes.OBFUSCATED + "A" + FormattingCodes.RESET;
            if (stack.getItem() instanceof ShortBowItem) {
                toolTip.add(Component.literal(""));
                toolTip.add(Component.literal("Short Bow: Instantly Shoots!").withStyle(ChatFormatting.DARK_PURPLE));
            }
            if (!(stack.getItem() instanceof IGuiHelper)) {
                toolTip.add(Component.literal(""));
                toolTip.add(Component.literal((flag ? NameMod + " " : "") + rarity + " " + MISCTools.getNameModifier(stack) + (flag ? " " + NameMod : "")).withStyle(rarity.getStyleModifier()).withStyle(ChatFormatting.BOLD));
            }
        }

        @SubscribeEvent
        public static void EnderHitEvent(EntityTeleportEvent ignoredEvent) {

        }


    }

    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.NEXT_SPELL_KEY);
            event.register(KeyBinding.PREVIOUS_SPELL_KEY);
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeRenderer::new);
        }
    }

}
