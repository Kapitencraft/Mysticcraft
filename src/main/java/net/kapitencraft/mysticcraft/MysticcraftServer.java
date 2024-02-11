package net.kapitencraft.mysticcraft;

import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraCapability;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneCapability;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneData;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MysticcraftServer {
    private static final MysticcraftServer INSTANCE = new MysticcraftServer();
    public static MysticcraftServer getInstance() {
        return INSTANCE;
    }
    private final GemstoneData data = new GemstoneData();

    public GemstoneData getData() {
        return data;
    }

    @SubscribeEvent
    public void registerStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack obj = event.getObject();
        Item item = obj.getItem();
        if (data.has(item)) {
            GemstoneCapability capability = new GemstoneCapability();
            capability.setDefault(data.get(item));
            event.addCapability(MysticcraftMod.res("gemstone"), capability);
        }
        if (item instanceof ElytraItem) {
            event.addCapability(MysticcraftMod.res("elytra"), ElytraCapability.create());
        }
    }
}
