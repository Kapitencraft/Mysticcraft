package net.kapitencraft.mysticcraft.misc;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class MiscEvents {
    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void DescriptionRegister(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            Rarity rarity = stack.getItem().getRarity(stack);
            boolean flag = rarity != MISCTools.getItemRarity(stack.getItem());
            String NameMod = FormattingCodes.OBFUSCATED + "A" + FormattingCodes.RESET;
            event.getToolTip().add(Component.literal(""));
            event.getToolTip().add(Component.literal((flag ? NameMod + " " : "") + rarity + " " + MISCTools.getNameModifier(stack) + (flag ? " " + NameMod : "")).withStyle(rarity.getStyleModifier()));
        }

    }

    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.NEXT_SPELL_KEY);
            event.register(KeyBinding.PREVIOUS_SPELL_KEY);
        }
    }

}
