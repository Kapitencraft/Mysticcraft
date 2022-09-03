package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class InstantTransmissionSpell extends Spell {


    public InstantTransmissionSpell() {
        super(50, "instant_transmission", Spells.RELEASE);
    }

    @Override
    public void execute(Entity user, ItemStack stack) {
        ArrayList<Vec3> sight = MISCTools.LineOfSight(user, 8, 0.1);
        for (Vec3 vec3 : sight) {
            BlockPos pos = new BlockPos(vec3);
            if (!user.level.getBlockState(pos).canOcclude()) {
                int loc = sight.indexOf(vec3);
                ArrayList<Vec3> invsight = MISCTools.invertList(sight);
                for (int i = 0; i < invsight.indexOf(vec3); i++) {
                    Vec3 mem = new Vec3(invsight.get(i).x, invsight.get(i).y + 1, invsight.get(i).z);
                    if (user.level.getBlockState(new BlockPos(mem)).canOcclude()) {
                        user.teleportTo(invsight.get(i).x, invsight.get(i).y, invsight.get(i).z);
                    }

                }
            }
        }

    }
}
