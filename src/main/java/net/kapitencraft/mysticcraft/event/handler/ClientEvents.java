package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.kap_lib.event.custom.client.RegisterEnchantmentApplicableCharsEvent;
import net.kapitencraft.mysticcraft.client.ModKeyMappings;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ShortBowItem;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.C2S.UseShortBowPacket;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.spell.capability.SelectSpellCastScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void sendLeftClickShortBow(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (event.isAttack() && player != null && player.getMainHandItem().getItem() instanceof ShortBowItem) {
            event.setCanceled(true);
            player.swing(InteractionHand.MAIN_HAND);
            ModMessages.sendToServer(new UseShortBowPacket());
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
        event.register(ModItems.DIAMOND_HAMMER.get());
    }
}
