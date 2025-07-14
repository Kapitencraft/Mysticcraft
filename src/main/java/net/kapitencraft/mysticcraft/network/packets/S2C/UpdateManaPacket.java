package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.mana.IManaStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateManaPacket implements SimplePacket {
    private final BlockPos pos;
    private final int mana;

    public UpdateManaPacket(BlockPos pos, int mana) {
        this.pos = pos;
        this.mana = mana;
    }

    public UpdateManaPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readInt());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeInt(this.mana);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        sup.get().enqueueWork(()-> {
            try {
                ((IManaStorage) Minecraft.getInstance().level.getBlockEntity(pos)).setMana(this.mana);
            } catch (Exception e) {
                MysticcraftMod.LOGGER.warn("unable to sync mana: {}", e.getMessage());
            }
        });
        return true;
    }
}
