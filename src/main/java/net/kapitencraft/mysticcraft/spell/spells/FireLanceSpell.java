package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.spells.FireLance;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.misc.damage_source.AbilityDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireLanceSpell {
    public static void execute(LivingEntity user, ItemStack stack) {
        ArrayList<Vec3> lineOfSight = MISCTools.LineOfSight(user, 10, 0.05);
        for (Vec3 vec3 : lineOfSight) {
            if (user.level.getBlockState(new BlockPos(vec3)).canOcclude()) {
                break;
            }
            List<LivingEntity> entities = user.level.getEntitiesOfClass(LivingEntity.class, new AABB(vec3.x - 0.1, vec3.y - 0.1, vec3.z - 0.1, vec3.x + 0.1, vec3.y + 0.1, vec3.z + 0.1));
            MISCTools.sendParticles(user.level, ParticleTypes.SMALL_FLAME, false, vec3, 10, 0.1/8, 0.1/8, 0.1/8, 0);
            for (LivingEntity living : entities) {
                if (living != user) {
                    if (living.getLastDamageSource() instanceof AbilityDamageSource abilitySource && Objects.equals(abilitySource.getSpellType(), "fire_lance")) {
                        living.invulnerableTime = 0;
                    }
                    living.hurt(new AbilityDamageSource(user, 0.2f, "fire_lance").setIsFire(), 2);
                }
            }
        }
    }

    public static List<Component> getDescription() {
        return List.of(Component.literal("Fires a line of fire where you are looking and deals 40 Base Ability Damage per seconds"));
    }
}
