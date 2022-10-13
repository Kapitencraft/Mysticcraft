package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class InstantTransmissionSpell extends Spell {
    private final Component[] description = new Component[]{Component.literal("teleports you 8 blocks ahead")};


    public InstantTransmissionSpell() {
        super(50, "instant_transmission", Spells.RELEASE, Rarity.COMMON, "instant_transmission");
    }

    @Override
    public void execute(Entity user, ItemStack stack) {
        ArrayList<Vec3> sight = MISCTools.LineOfSight(user, 8, 0.1);
        for (Vec3 vec3 : sight) {
            BlockPos pos = new BlockPos(vec3);
            if (!user.level.getBlockState(pos).canOcclude()) {
                int loc = sight.indexOf(vec3);
                ArrayList<Vec3> inv_sight = MISCTools.invertList(sight);
                for (int i = 0; i < inv_sight.indexOf(vec3); i++) {
                    Vec3 mem = new Vec3(inv_sight.get(i).x, inv_sight.get(i).y + 1, inv_sight.get(i).z);
                    if (user.level.getBlockState(new BlockPos(mem)).canOcclude()) {
                        user.teleportTo(inv_sight.get(i).x, inv_sight.get(i).y, inv_sight.get(i).z);
                    }

                }
            }
        }

    }

    @Override
    public List<Component> getDescription() {
        return List.of(description);
    }

    @Override
    public String getName() {
        return "Instant Transmission";
    }
}
