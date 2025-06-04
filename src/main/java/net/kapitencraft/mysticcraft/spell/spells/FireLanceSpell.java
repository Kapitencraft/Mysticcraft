package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.combat.spells.FireLance;
import net.kapitencraft.mysticcraft.misc.damage_source.SpellDamageSource;
import net.kapitencraft.mysticcraft.registry.ModMobEffects;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class FireLanceSpell implements Spell {

    //public static List<Component> getDescription() {
    //    return List.of(Component.literal("Fires a line of fire where you are looking and deals 40 Base Ability Damage per seconds"));
    //}

    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
        LivingEntity user = context.getCaster();
        Vec3 offset = SpellHelper.getCastOffset(new Vec2(0, user.yBodyRot), user.getUsedItemHand() == InteractionHand.OFF_HAND);
        ArrayList<Vec3> lineOfSight = MathHelper.lineOfSight(user, 10, 0.05);
        lineOfSight.stream()
                .map(vec3 -> FireLanceSpell.merge(vec3.add(offset), user))
                .flatMap(Collection::stream)
                .forEach(living -> {
                    if (living.getLastDamageSource() instanceof SpellDamageSource abilitySource && Objects.equals(abilitySource.getSpell(), this)) {
                        living.invulnerableTime = 0;
                    }
                    living.hurt(SpellDamageSource.create(ModDamageTypes.FIRE_LANCE, user, 0.2f, this), 4);
                    living.addEffect(new MobEffectInstance(ModMobEffects.BLAZING.get(), 40, 2));
                });

    }

    private static List<LivingEntity> merge(Vec3 source, LivingEntity user) {
        ParticleHelper.sendParticles(user.level(), ParticleTypes.SMALL_FLAME, false, source, 10, 0.1/8, 0.1/8, 0.1/8, 0);
        return MathHelper.getEntitiesAround(LivingEntity.class, user.level(), source, 0.1).stream()
                .filter(living -> living != user).toList();
    }

    @Override
    public double manaCost() {
        return 5;
    }

    @Override
    public Type getType() {
        return Type.CYCLE;
    }

    @Override
    public int getCooldownTime() {
        return 0;
    }

    @Override
    public boolean canApply(Item item) {
        return item instanceof FireLance;
    }
}
