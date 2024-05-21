package net.kapitencraft.mysticcraft.networking.packets.S2C.abstracts;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.capability.ICapability;
import net.kapitencraft.mysticcraft.item.capability.ModCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public abstract class SyncCapabilityToBlockPacket<T extends ModCapability<T, K>, K extends ICapability<T>> extends SyncCapabilityPacket<T, K> {
    private final BlockPos pos;

    public SyncCapabilityToBlockPacket(BlockPos pos, Map<Integer, T> capForSlotId) {
        super(capForSlotId);
        this.pos = pos;
    }

    public SyncCapabilityToBlockPacket(FriendlyByteBuf buf) {
        super(buf);
        this.pos = buf.readBlockPos();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        super.toBytes(buf);
        buf.writeBlockPos(this.pos);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        sup.get().enqueueWork(()-> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level == null) return;
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity == null) throw new IllegalStateException("unable to sync block data: BE not found");
            entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
                for (Map.Entry<Integer, T> entry : capForSlotId.entrySet()) {
                    ItemStack stack = iItemHandler.getStackInSlot(entry.getKey());
                    stack.getCapability(getCapability()).ifPresent(iGemstoneHandler -> iGemstoneHandler.copy(entry.getValue()));
                }
            });
            MysticcraftMod.LOGGER.info("synced {} Items to block {}", capForSlotId.size(), level.getBlockState(pos).getBlock());
        });
        return true;
    }
}
