package net.kapitencraft.mysticcraft.network.packets.C2S;

import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.rpg.perks.PlayerPerks;
import net.kapitencraft.mysticcraft.rpg.perks.ServerPerksManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpgradePerkPacket implements SimplePacket {
    private final ResourceLocation perk, tree;

    public UpgradePerkPacket(ResourceLocation perk, ResourceLocation tree) {
        this.perk = perk;
        this.tree = tree;
    }

    public UpgradePerkPacket(FriendlyByteBuf buf) {
        this(buf.readResourceLocation(), buf.readResourceLocation());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(perk);
        buf.writeResourceLocation(tree);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> sup) {
        ServerPlayer player = sup.get().getSender();
        if (player != null) {
            ServerPerksManager manager = ServerPerksManager.getOrCreateInstance();
            PlayerPerks playerPerks = manager.getPerks(player);
            playerPerks.upgrade(this.perk, this.tree, 1);
        }
    }
}
