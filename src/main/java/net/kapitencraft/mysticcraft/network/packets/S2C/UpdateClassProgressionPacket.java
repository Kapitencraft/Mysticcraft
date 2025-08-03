package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.client.rpg.classes.ClientClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateClassProgressionPacket implements SimplePacket {
    private final int level;
    private final float xp;

    public UpdateClassProgressionPacket(int level, float xp) {
        this.level = level;
        this.xp = xp;
    }

    public UpdateClassProgressionPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readFloat());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.level);
        buf.writeFloat(this.xp);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> sup) {
        sup.get().enqueueWork(() -> {
            ClientClass clientClass = ClientClass.getInstance();
            clientClass.setLevel(this.level);
            clientClass.setXp(this.xp);
        });
    }
}
