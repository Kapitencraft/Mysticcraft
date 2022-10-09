package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SpellScrollItem extends Item {

    private final Spell spell;

    public SpellScrollItem(Properties p_41383_, Spell assignedSpell) {
        super(p_41383_.rarity(assignedSpell.RARITY));
        this.spell = assignedSpell;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack itemStack, @Nullable Level level, @Nonnull List<Component> list, @Nonnull TooltipFlag flag) {
        list.add(Component.literal(""));
        list.addAll(spell.createDescription(this));
    }
}
