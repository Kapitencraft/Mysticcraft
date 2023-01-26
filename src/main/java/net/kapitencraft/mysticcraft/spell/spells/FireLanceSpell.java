package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.item.spells.FireLance;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class FireLanceSpell extends Spell {
    public FireLanceSpell() {
        super(5, "Fire Lance", "1011100", Spells.CYCLE, Rarity.UNCOMMON, "fire_lance");
    }

    @Override
    public void execute(LivingEntity user, ItemStack stack) {
        ArrayList<Vec3> lineOfSight = MISCTools.LineOfSight(user, 10, 0.1);
        for (Vec3 vec3 : lineOfSight) {
            List<LivingEntity> entities = user.level.getEntitiesOfClass(LivingEntity.class, new AABB(vec3.x - 0.1, vec3.y - 0.1, vec3.z - 0.1, vec3.x + 0.1, vec3.y + 0.1, vec3.z + 0.1));
            MISCTools.sendParticles(user.level, ParticleTypes.SMALL_FLAME, false, vec3, 10, 0.1/8, 0.1/8, 0.1/8, 0);
            for (LivingEntity living : entities) {
                if (living != user) {
                    living.hurt(new EntityDamageSource("inFire", user).setIsFire(), 2);
                }
            }
        }
    }

    @Override
    public boolean canApply(Item stack) {
        return stack instanceof FireLance;
    }

    @Override
    public List<Component> getDescription() {
        return null;
    }
}
