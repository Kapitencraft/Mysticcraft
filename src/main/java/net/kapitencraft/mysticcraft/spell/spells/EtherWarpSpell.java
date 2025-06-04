package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EtherWarpSpell implements Spell {
    public static boolean execute(LivingEntity living, ItemStack ignoredStack) {
        MiscHelper.saveTeleport(living, 57);
        return true;
    }

    @Override
    public void cast(SpellCastContext context) {
        MiscHelper.saveTeleport(context.getCaster(), 57);
    }

    @Override
    public double manaCost() {
        return 50;
    }

    @Override
    public Type getType() {
        return Type.RELEASE;
    }

    @Override
    public int getCooldownTime() {
        return 0;
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
