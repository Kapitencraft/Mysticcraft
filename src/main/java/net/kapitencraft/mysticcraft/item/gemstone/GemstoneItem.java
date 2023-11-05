package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GemstoneItem extends Item implements DyeableLeatherItem, IModItem {
    public static final TabGroup GROUP = new TabGroup(TabRegister.TabTypes.SPELL_AND_GEMSTONE);
    private final GemstoneType.Rarity RARITY;
    public final String gemstoneName;
    public GemstoneItem(GemstoneType.Rarity rarity, String gemstoneName) {
        super(new Properties().rarity(getRarity(rarity)));
        this.RARITY = rarity;
        this.gemstoneName = gemstoneName;
    }

    @Override
    public int getColor(@NotNull ItemStack p_41122_) {
        return this.getGemstone().getColour();
    }

    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        list.add(Component.translatable("gemstone_item.translation", makeAttributeName()).withStyle(ChatFormatting.GREEN));
    }

    private Component makeAttributeName() {
        return Component.translatable(this.getGemstone().getModifiedAttribute().get().getDescriptionId());
    }

    @Override
    public void clearColor(@NotNull ItemStack p_41124_) {
    }

    @Override
    public boolean hasCustomColor(@NotNull ItemStack p_41114_) {
        return false;
    }

    @Override
    public void setColor(@NotNull ItemStack p_41116_, int p_41117_) {
    }

    public static Rarity getRarity(GemstoneType.Rarity rarity) {
        return switch (rarity) {
            case FINE, FLAWLESS -> Rarity.UNCOMMON;
            case PERFECT -> Rarity.RARE;
            default -> Rarity.COMMON;
        };
    }


    public static GemstoneItem of(GemstoneSlot slot) {
        return slot.toItem();
    }

    public GemstoneType.Rarity getRarity() {
        return this.RARITY;
    }

    public GemstoneType getGemstone() {
        return GemstoneType.getById(this.gemstoneName);
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
