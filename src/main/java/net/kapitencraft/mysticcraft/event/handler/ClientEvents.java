package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.kap_lib.enchantment.event.custom.RegisterEnchantmentApplicableCharsEvent;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.ModKeyMappings;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ShortBowItem;
import net.kapitencraft.mysticcraft.network.packets.C2S.UseShortBowPacket;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.spell.capability.SelectSpellCastScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void sendLeftClickShortBow(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (event.isAttack() && player != null && player.getMainHandItem().getItem() instanceof ShortBowItem) {
            event.setCanceled(true);
            player.swing(InteractionHand.MAIN_HAND);
            PacketDistributor.sendToServer(new UseShortBowPacket());
        }
    }

    @SubscribeEvent
    public static void onScreenKeyInput(InputEvent.Key event) {
        if (ModKeyMappings.SELECT_SPELL_CAST.consumeClick()) {
            Minecraft.getInstance().setScreen(new SelectSpellCastScreen());
        }
    }

    @SubscribeEvent
    public static void onRegisterEnchantmentApplicableChars(RegisterEnchantmentApplicableCharsEvent event) {
        event.register(ModItems.DIAMOND_HAMMER.get(), MysticcraftMod.res("item/hammer/diamond"));
    }
}
