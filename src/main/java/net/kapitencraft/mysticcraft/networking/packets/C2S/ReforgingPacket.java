package net.kapitencraft.mysticcraft.networking.packets.C2S;

import net.kapitencraft.mysticcraft.block.entity.ReforgingAnvilBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReforgingPacket {
    private final BlockPos BEPos;
    private final String reforgeName;

    public ReforgingPacket(BlockPos pos, String reforgeName) {
        this.BEPos = pos;
        this.reforgeName = reforgeName;
    }
    public ReforgingPacket(FriendlyByteBuf buf) {
        this.BEPos = buf.readBlockPos();
        this.reforgeName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.BEPos);
        buf.writeUtf(this.reforgeName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()-> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ServerLevel serverLevel = player.getLevel();
                BlockEntity entity = serverLevel.getBlockEntity(BEPos);
                if (entity instanceof ReforgingAnvilBlockEntity rAEntity) {
                    if (this.reforgeName != null) {
                        rAEntity.updateButtonPress(this.reforgeName);
                    } else {
                        rAEntity.updateButtonPress(player);
                    }
                }
            }
        });
        return true;
    }
}
