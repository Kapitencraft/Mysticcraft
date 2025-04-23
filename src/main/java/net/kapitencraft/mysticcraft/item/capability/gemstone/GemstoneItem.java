package net.kapitencraft.mysticcraft.item.capability.gemstone;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GemstoneItem extends Item implements IModItem, IGemstoneItem {
    public static final TabGroup GROUP = new TabGroup(TabRegister.TabTypes.SPELL);
    public GemstoneItem() {
        super(MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return MiscHelper.buildComponent(
                Component.translatable("gem_rarity." + IGemstoneItem.getGemRarity(stack).getSerializedName()),
                Component.literal(" "),
                Component.translatable("gem_type." + IGemstoneItem.getGemId(stack)),
                Component.literal(" "),
                Component.translatable("item.gemstone")
        );
    }


    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        list.add(Component.translatable("gemstone_item.translation", makeAttributeName(itemStack)).withStyle(ChatFormatting.GREEN));
    }

    private Component makeAttributeName(ItemStack stack) {
        return Component.translatable(IGemstoneItem.getGemstone(stack).getModifiedAttribute().get().getDescriptionId());
    }

    public static ItemStack of(GemstoneSlot slot) {
        return slot.toItem();
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
