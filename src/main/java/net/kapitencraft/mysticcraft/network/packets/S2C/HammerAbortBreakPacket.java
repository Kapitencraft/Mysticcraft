package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.item.tools.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HammerAbortBreakPacket implements SimplePacket {
    private final BlockPos pos;
    private final Direction direction;

    public HammerAbortBreakPacket(BlockPos pos, Direction direction) {
        this.pos = pos;
        this.direction = direction;
    }

    public HammerAbortBreakPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readEnum(Direction.class));
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeEnum(direction);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        sup.get().enqueueWork(() -> HammerItem.abort(pos, direction));
        return true;
    }
}
