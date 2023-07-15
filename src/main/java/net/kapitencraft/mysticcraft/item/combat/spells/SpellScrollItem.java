package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellScrollItem extends Item {
    private final Spell SPELL;
    public SpellScrollItem(Spell spell) {
        super(new Properties().rarity(spell.getRarity()));
        SPELL = spell;
    }

    public Spell getSpell() {
        return SPELL;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.literal("Spell Ability: " + this.SPELL.getName()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        list.addAll(this.SPELL.getDescription());
    }
}
