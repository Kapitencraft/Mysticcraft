package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ExplosionPacket extends ClientboundExplodePacket implements ModPacket {
    private final boolean breakBlocks;
    private final boolean hurtEntities;
    public ExplosionPacket(double p_132115_, double p_132116_, double p_132117_, float p_132118_, List<BlockPos> p_132119_, @Nullable Vec3 p_132120_, boolean breakBlocks, boolean hurtEntities) {
        super(p_132115_, p_132116_, p_132117_, p_132118_, p_132119_, p_132120_);
        this.breakBlocks = breakBlocks;
        this.hurtEntities = hurtEntities;
    }

    public ExplosionPacket(FriendlyByteBuf buf) {
        super(buf);
        this.breakBlocks = buf.readBoolean();
        this.hurtEntities = buf.readBoolean();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        super.write(buf);
        buf.writeBoolean(breakBlocks);
        buf.writeBoolean(hurtEntities);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        return false;
    }
}
