package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwingPacket implements ModPacket {
    private final InteractionHand hand;
    private final int entityId;

    public SwingPacket(InteractionHand hand, int entityId) {
        this.hand = hand;
        this.entityId = entityId;
    }

    public SwingPacket(FriendlyByteBuf buf) {
        this(buf.readEnum(InteractionHand.class), buf.readInt());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(hand);
        buf.writeInt(entityId);
    }

    @Override @SuppressWarnings("all")
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        sup.get().enqueueWork(()-> {
            Entity entity = Minecraft.getInstance().level.getEntity(this.entityId);
            if (entity instanceof LivingEntity living) {
                living.swing(hand);
            }
        });
        return false;
    }
}
