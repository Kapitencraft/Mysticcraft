package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.spells.CrystalWarpSpell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class SlivyraItem extends SpellItem {

    public final int spellslotamount = 1;
    private int activespell;
    SpellSlot[] spellSlots = new SpellSlot[spellslotamount];

    public SlivyraItem(Properties p_43272_) {
        super(p_43272_);
        this.spellSlots[1] = new SpellSlot(Spells.CRYSTALWARP, null);
    }


    @Override
    public void execute(Entity user) {

    }
    @Override
    public SpellSlot[] getSpellSlots() {
        return this.spellSlots;
    }

    @Override
    public int getSpellslotamount() {
        return this.spellslotamount;
    }

    @Override
    public int getActivespell() {
        return 1;
    }
}
