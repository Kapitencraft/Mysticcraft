package net.kapitencraft.mysticcraft.item.sword;

import net.kapitencraft.mysticcraft.item.ModTiers;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaSteelSword extends LongSwordItem {
    public ManaSteelSword() {
        super(ModTiers.MANA_STEEL, 3, 0.8f, new Properties().durability(1800).rarity(FormattingCodes.MYTHIC));
    }

    @Override
    public double getReachMod() {
        return 3;
    }

    @Override
    public double getStrenght() {
        return 150;
    }

    @Override
    public double getCritDamage() {
        return 50;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> list, @NotNull TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
        list.add(Component.literal("Regenerate 2 hp on hit").withStyle(ChatFormatting.GREEN));
        list.add(Component.literal(""));
    }
}