package net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger;

import net.kapitencraft.mysticcraft.item.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.item_bonus.ExtraBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IItemBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.piece.ShadowDaggerBonus;
import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import org.jetbrains.annotations.Nullable;

public class ShadowDagger extends DarkDagger implements IItemBonusItem, ISpellItem {
    private static final ShadowDaggerBonus BONUS = new ShadowDaggerBonus();

    public ShadowDagger() {
        super(ModRarities.LEGENDARY, 3);
    }

    @Override
    public double getStrenght() {
        return 35;
    }

    @Override
    public double getCritDamage() {
        return 100;
    }

    @Override
    public @Nullable PieceBonus getBonus() {
        return null;
    }

    @Override
    public @Nullable ExtraBonus getExtraBonus() {
        return BONUS;
    }

    @Override
    public int getSlotAmount() {
        return 1;
    }

    @Override
    public void generateSlots(SpellHelper stack) {
        stack.setSlot(0, new SpellSlot(Spells.SHADOW_STEP));
    }
}
