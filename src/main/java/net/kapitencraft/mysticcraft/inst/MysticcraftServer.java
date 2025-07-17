package net.kapitencraft.mysticcraft.inst;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.elytra.ElytraCapability;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneCapability;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneCapabilityProvider;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneData;
import net.kapitencraft.mysticcraft.capability.item_stat.ItemStatCapability;
import net.kapitencraft.mysticcraft.capability.item_stat.ItemStatCapabilityProvider;
import net.kapitencraft.mysticcraft.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.item.material.containable.ContainableItem;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.kapitencraft.mysticcraft.worldgen.gemstone.GemstoneDecorator;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.util.Arrays;

@Mod.EventBusSubscriber
public class MysticcraftServer {
    private final MinecraftServer server;
    private static MysticcraftServer INSTANCE;

    @SubscribeEvent
    public static void startInstance(ServerAboutToStartEvent event) {
        MysticcraftMod.LOGGER.info("starting Mysticcraft-Server");
        INSTANCE = new MysticcraftServer(event.getServer());
    }

    public MysticcraftServer(MinecraftServer server) {
        this.server = server;
        this.serverFiles = new File(this.server.getServerDirectory(), MysticcraftMod.MOD_ID);
    }

    public static MysticcraftServer getInstance() {
        return INSTANCE;
    }
    private static final GemstoneData data = new GemstoneData();
    public final File serverFiles;
    public final GemstoneDecorator decorator = new GemstoneDecorator();

    public static GemstoneData getData() {
        return data;
    }

    @SubscribeEvent
    public static void registerStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack obj = event.getObject();
        Item item = obj.getItem();
        if (data.has(item)) {
            GemstoneCapability capability = new GemstoneCapability(obj);
            capability.setDefault(data.get(item));
            event.addCapability(MysticcraftMod.res("gemstone"), new GemstoneCapabilityProvider(capability));
        }
        if (item instanceof ElytraItem) {
            event.addCapability(MysticcraftMod.res("elytra"), ElytraCapability.create());
        }
        if (item instanceof ContainableItem<?, ?> containableItem) {
            event.addCapability(MysticcraftMod.res("content"), containableItem.makeCapabilityProvider());
        }
        if (obj.is(ModTags.Items.CATALYST)) {
            event.addCapability(MysticcraftMod.res("spells"),
                    item instanceof ISpellItem spellItem ?
                            spellItem.createSpells() :
                            SpellCapabilityProvider.empty()
            );
        }
        ItemStatCapability capability = new ItemStatCapability();
        Arrays.stream(ItemStatCapability.Type.values()).filter(type -> type.test(item))
                .forEach(capability::add);
        if (capability.has())
            event.addCapability(MysticcraftMod.res("stats"), new ItemStatCapabilityProvider(capability));
    }
}
