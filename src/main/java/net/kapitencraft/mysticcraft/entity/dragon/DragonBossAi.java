package net.kapitencraft.mysticcraft.entity.dragon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.kapitencraft.mysticcraft.registry.ModMemoryModuleTypes;
import net.kapitencraft.mysticcraft.registry.ModSensorTypes;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DragonBossAi {

    public static final List<Activity> ACTIVITIES = List.of(
            Activity.FIGHT, Activity.REST, Activity.IDLE
    );

    private static final List<SensorType<? extends Sensor<? super Dragon>>> SENSORS = List.of(
            ModSensorTypes.DRAGON_TEMPTATIONS.get(),
            ModSensorTypes.DRAGON_ATTACKABLES.get(),
            SensorType.NEAREST_LIVING_ENTITIES
    );

    private static final List<MemoryModuleType<?>> MEMORIES = List.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            ModMemoryModuleTypes.DRAGON_FIRE_BREATH_COOLDOWN.get()
    );

    public static Brain<?> makeBrain(Dragon dragon, Dynamic<?> pDynamic) {
        Brain.Provider<Dragon> provider = Brain.provider(MEMORIES, SENSORS);
        Brain<Dragon> brain = provider.makeBrain(pDynamic);
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(dragon, brain);
        brain.setCoreActivities(Set.of(Activity.CORE));
        return brain;
    }

    private static void initCoreActivity(Brain<Dragon> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink()
        ));
    }

    private static void initIdleActivity(Brain<Dragon> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(2, new RunOne<>(ImmutableList.of(
                        Pair.of(new FollowTemptation(LivingEntity::getSpeed), 1)
                ))),
                Pair.of(3, StartAttacking.create(DragonBossAi::findNearestValidAttackTarget)),
                Pair.of(4, new GateBehavior<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of(
                        Pair.of(RandomStroll.swim(0.5F), 2),
                        Pair.of(RandomStroll.stroll(0.15F, false), 2),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::isInWaterOrBubble), 5),
                        Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 5)
                )))
        ));
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Dragon p_149299_) {
        return BehaviorUtils.isBreeding(p_149299_) ? Optional.empty() : p_149299_.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static void initFightActivity(Dragon dragon, Brain<Dragon> pBrain) {
        pBrain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10,
                ImmutableList.of(
                        StopAttackingIfTargetInvalid.create(
                                (p_219540_) -> !dragon.canTargetEntity(p_219540_),
                                DragonBossAi::onTargetInvalid,
                                false
                        ),
                        SetEntityLookTarget.create(
                                (target) -> isTarget(pBrain, target),
                                (float)dragon.getAttributeValue(Attributes.FOLLOW_RANGE)
                        ),
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.2F),
                        MeleeAttack.create(18),
                        new BreathFire()
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static void onTargetInvalid(Dragon dragon, LivingEntity living) {
    }

    private static boolean isTarget(Brain<Dragon> brain, LivingEntity pEntity) {
        return brain
                .getMemory(MemoryModuleType.ATTACK_TARGET)
                .filter((p_219509_) -> p_219509_ == pEntity)
                .isPresent();
    }
}
