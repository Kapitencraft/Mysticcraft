package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ShortBowItem;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.C2S.UseShortBowPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
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
    public void invalidateGuildCache(EntityLeaveLevelEvent event) {
        if (event.getEntity() == Minecraft.getInstance().player) {
            GuildHandler.getClientInstance().invalidate();
        }
    }
}
