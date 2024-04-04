package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ResetCooldownsPacket implements ModPacket {
    @Override
    public void toBytes(FriendlyByteBuf buf) {
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context context = sup.get();
        context.enqueueWork(()-> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.getPersistentData().put("Cooldowns", new CompoundTag());
            }
        });
        return false;
    }

    public static void resetCooldowns(@Nullable Player player) {
        if (player == null) return;
        player.getPersistentData().put("Cooldowns", new CompoundTag());
    }
}
