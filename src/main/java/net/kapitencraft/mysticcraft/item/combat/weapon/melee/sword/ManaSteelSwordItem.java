package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaSteelSwordItem extends LongSwordItem implements IGemstoneApplicable {
    public ManaSteelSwordItem() {
        super(ModTiers.MANA_STEEL, 3, new Properties().durability(1800).rarity(FormattingCodes.MYTHIC));
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
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        super.appendHoverTextWithPlayer(itemStack, level, list, flag, player);
        list.add(Component.literal("Regenerate 2 hp on hit").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }

    @Override
    public GemstoneSlot[] getDefaultSlots() {
        return GemstoneSlot.of(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT);
    }
}