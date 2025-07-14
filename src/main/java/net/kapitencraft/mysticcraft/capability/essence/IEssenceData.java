package net.kapitencraft.mysticcraft.capability.essence;

import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.capability.ItemData;
import net.kapitencraft.mysticcraft.capability.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.S2C.SyncEssenceDataPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public interface IEssenceData extends ItemData<EssenceType, IEssenceData> {
    @Override
    default EssenceType loadData(ItemStack stack, Consumer<IEssenceData> ifNull) {
        return EssenceType.CODEC.byName(stack.getOrCreateTag().getString(getTagId()), EssenceType.UNDEAD);
    }

    static EssenceType loadData(ItemStack stack) {
        return ((IEssenceData) stack.getItem()).loadData(stack, data -> {});
    }

    static void apply(ItemStack stack, EssenceType type) {
        if (stack.getItem() instanceof IEssenceData data) {
            data.saveData(stack, type);
        }
    }

    static void increaseEssence(Player source, ItemStack stack) {
        if (!(stack.getItem() instanceof IEssenceData)) return;
        source.getCapability(CapabilityHelper.ESSENCE).ifPresent(essenceHolder -> {
            EssenceType type = loadData(stack);
            essenceHolder.add(type, stack.getCount());
            if (source instanceof ServerPlayer serverPlayer) ModMessages.sendToClientPlayer(new SyncEssenceDataPacket(essenceHolder), serverPlayer);
            source.displayClientMessage(Component.translatable("essence.pickup", type.getName(), stack.getCount()).withStyle(ChatFormatting.LIGHT_PURPLE), true);
            InventoryHelper.removeFromInventory(stack, source);
        });
    }

    static ItemStack getAllFromPlayer(Player player, ItemStack stack) {
        if (!(stack.getItem() instanceof IEssenceData)) return ItemStack.EMPTY;
        EssenceType type = IEssenceData.loadData(stack);
        Reference<Integer> ref = Reference.of(0);
        player.getCapability(CapabilityHelper.ESSENCE).ifPresent(essenceHolder -> ref.setValue(essenceHolder.getContent().get(type)));
        return IPrestigeAbleItem.essence(type, ref.getIntValue());
    }

    static EssenceType read(ItemStack stack) {
        if (stack.getItem() instanceof IEssenceData) {
            return loadData(stack);
        }
        return EssenceType.UNDEAD;
    }

    @Override
    default void getDisplay(ItemStack stack, List<Component> list) {
    }

    @Override
    default String getTagId() {
        return "EssenceData";
    }

    @Override
    default void saveData(ItemStack stack, EssenceType type) {
        stack.getOrCreateTag().putString(getTagId(), type.getSerializedName());
    }
}
