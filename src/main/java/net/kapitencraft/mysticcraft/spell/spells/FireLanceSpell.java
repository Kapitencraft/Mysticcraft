package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.kapitencraft.mysticcraft.misc.damage_source.AbilityDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireLanceSpell {
    //TODO fix cycle spells not being executed
    public static void execute(LivingEntity user, ItemStack stack) {
        ArrayList<Vec3> lineOfSight = MathHelper.lineOfSight(user, 10, 0.05);
        List<LivingEntity> hit = new ArrayList<>();
        for (Vec3 vec3 : lineOfSight) {
            if (user.level.getBlockState(new BlockPos(vec3)).canOcclude()) {
                break;
            }
            List<LivingEntity> entities = user.level.getEntitiesOfClass(LivingEntity.class, new AABB(vec3.x - 0.1, vec3.y - 0.1, vec3.z - 0.1, vec3.x + 0.1, vec3.y + 0.1, vec3.z + 0.1));
            ParticleHelper.sendParticles(user.level, ParticleTypes.SMALL_FLAME, false, vec3, 10, 0.1/8, 0.1/8, 0.1/8, 0);
            for (LivingEntity living : entities) {
                if (living != user && !hit.contains(living)) {
                    if (living.getLastDamageSource() instanceof AbilityDamageSource abilitySource && Objects.equals(abilitySource.getSpellType(), "fire_lance")) {
                        living.invulnerableTime = 0;
                    }
                    living.hurt(new AbilityDamageSource(user, 0.2f, "fire_lance"), 4);
                    living.addEffect(new MobEffectInstance(ModMobEffects.IGNITION.get(), 40, 2));
                    hit.add(living);
                }
            }
        }
    }

    public static List<Component> getDescription() {
        return List.of(Component.literal("Fires a line of fire where you are looking and deals 40 Base Ability Damage per seconds"));
    }
}
