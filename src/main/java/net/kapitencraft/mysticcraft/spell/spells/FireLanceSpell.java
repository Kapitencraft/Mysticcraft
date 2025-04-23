package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.misc.damage_source.AbilityDamageSource;
import net.kapitencraft.mysticcraft.registry.ModMobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class FireLanceSpell {

    public static boolean execute(LivingEntity user, ItemStack ignored) {
        Vec3 offset = SpellHelper.getCastOffset(new Vec2(0, user.yBodyRot), user.getUsedItemHand() == InteractionHand.OFF_HAND);
        ArrayList<Vec3> lineOfSight = MathHelper.lineOfSight(user, 10, 0.05);
        lineOfSight.stream()
                .map(vec3 -> FireLanceSpell.merge(vec3.add(offset), user))
                .flatMap(Collection::stream)
                .forEach(living -> {
                    if (living.getLastDamageSource() instanceof AbilityDamageSource abilitySource && Objects.equals(abilitySource.getSpellType(), "fire_lance")) {
                        living.invulnerableTime = 0;
                    }
                    living.hurt(AbilityDamageSource.create(ModDamageTypes.FIRE_LANCE, user, 0.2f, "fire_lance"), 4);
                    living.addEffect(new MobEffectInstance(ModMobEffects.BLAZING.get(), 40, 2));
                });
        return true;
    }

    private static List<LivingEntity> merge(Vec3 source, LivingEntity user) {
        ParticleHelper.sendParticles(user.level(), ParticleTypes.SMALL_FLAME, false, source, 10, 0.1/8, 0.1/8, 0.1/8, 0);
        return MathHelper.getEntitiesAround(LivingEntity.class, user.level(), source, 0.1).stream()
                .filter(living -> living != user).toList();
    }

    public static List<Component> getDescription() {
        return List.of(Component.literal("Fires a line of fire where you are looking and deals 40 Base Ability Damage per seconds"));
    }
}
