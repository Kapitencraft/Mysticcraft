package net.kapitencraft.mysticcraft.item.sword;

import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ModdedSword extends SwordItem {
    public ModdedSword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    public abstract double getStrenght();
    public abstract double getCritDamage();

    @Override
    public @NotNull Rarity getRarity(ItemStack stack) {
        if (!stack.isEnchanted()) {
            return super.getRarity(stack);
        } else {
            final Rarity rarity = MISCTools.getItemRarity(this);
            if (rarity == Rarity.COMMON) {
                return Rarity.UNCOMMON;
            } else if (rarity == Rarity.UNCOMMON) {
                return Rarity.RARE;
            } else if (rarity == Rarity.RARE) {
                return Rarity.EPIC;
            } else if (rarity == Rarity.EPIC) {
                return FormattingCodes.LEGENDARY;
            } else if (rarity == FormattingCodes.LEGENDARY) {
                return FormattingCodes.MYTHIC;
            } else if (rarity == FormattingCodes.MYTHIC) {
                return FormattingCodes.DIVINE;
            } else {
                return Rarity.COMMON;
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        if (stack.getItem() instanceof IGemstoneApplicable applicable) {
            applicable.getDisplay(stack, list);
        }
        list.add(Component.literal(""));
    }
}
