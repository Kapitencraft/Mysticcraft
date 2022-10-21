package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GemGrinderSlotMessage {
    private final int slotID, x, y, z, changeType, meta;

    public GemGrinderSlotMessage(int slotID, int x, int y, int z, int changeType, int meta) {
        this.slotID = slotID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.changeType = changeType;
        this.meta = meta;
    }

    public GemGrinderSlotMessage(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    public static void buffer(GemGrinderSlotMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.slotID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
        buffer.writeInt(message.changeType);
        buffer.writeInt(message.meta);
    }

    public static void handler(GemGrinderSlotMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player entity = context.getSender();
            int slotID = message.slotID;
            int changeType = message.changeType;
            int meta = message.meta;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            handleSlotAction(entity, slotID, changeType, meta, x, y, z, null);
        });
        context.setPacketHandled(true);
    }

    public static void handleSlotAction(Player entity, int slotID, int changeType, int meta, int x, int y, int z, @Nullable Container container) {
        Level world = entity.level;
        HashMap gui_state = GemGrinderMenu.GUI_STATE;
        // security measure to prevent arbitrary chunk generation
        if (!(world.hasChunkAt(new BlockPos(x, y, z)) || container != null))
            return;
        if (changeType == 0) {
            //Run code here
            Item item = container.getItem(GemGrinderMenu.TE_INVENTORY_FIRST_SLOT_INDEX).getItem();
            if (item instanceof IGemstoneApplicable applicable) {
                applicable.getGemstoneSlots()[0].putGemstone(container.getItem(GemGrinderMenu.TE_INVENTORY_FIRST_SLOT_INDEX + slotID).getItem() instanceof GemstoneItem gemstoneItem ? gemstoneItem.toGemstone() : null);
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        MysticcraftMod.addNetworkMessage(GemGrinderSlotMessage.class, GemGrinderSlotMessage::buffer,
                GemGrinderSlotMessage::new, GemGrinderSlotMessage::handler);
    }
}
