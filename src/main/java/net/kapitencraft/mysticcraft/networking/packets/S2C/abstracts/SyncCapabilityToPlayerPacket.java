package net.kapitencraft.mysticcraft.networking.packets.S2C.abstracts;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.ICapability;
import net.kapitencraft.mysticcraft.item.capability.ModCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class SyncCapabilityToPlayerPacket<T extends ModCapability<T, K>, K extends ICapability<T>> extends SyncCapabilityPacket<T, K> {

    public SyncCapabilityToPlayerPacket(Map<Integer, T> capForSlotId) {
        super(capForSlotId);
    }

    protected SyncCapabilityToPlayerPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        sup.get().enqueueWork(()-> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer == null) return;
            Inventory inventory = localPlayer.getInventory();
            for (Map.Entry<Integer, T> entry : capForSlotId.entrySet()) {
                ItemStack stack = inventory.getItem(entry.getKey());
                stack.getCapability(getCapability()).ifPresent(iGemstoneHandler -> iGemstoneHandler.copy(entry.getValue()));
            }
            MysticcraftMod.LOGGER.info("synced {} {} Items", capForSlotId.size(), getLogId());
        });
        return true;
    }

    public static <T extends SyncCapabilityPacket<K, L>, K extends ModCapability<K, L>, L extends ICapability<K>> T create(Function<Map<Integer, K>, T> constructor, ServerPlayer serverPlayer, Predicate<ItemStack> hasPredicate, Function<ItemStack, K> mapper) {
        Inventory inventory = serverPlayer.getInventory();

        Map<Integer, ItemStack> map = InventoryHelper.getAllContent(inventory);
        MapStream<Integer, ItemStack> stream = MapStream.of(map);
        return constructor.apply(stream.filter((integer, stack) -> hasPredicate.test(stack))
                .mapValues(mapper)
                .filterNulls().toMap());
    }

    public static <T extends SyncCapabilityPacket<K, L>, K extends ModCapability<K, L>, L extends ICapability<K>> T createFromCapability(Function<Map<Integer, K>, T> constructor, ServerPlayer player, Capability<L> capability) {
        return create(constructor, player, CapabilityHelper.hasCapPredicate(capability), CapabilityHelper.getCapFunc(capability));
    }

}
