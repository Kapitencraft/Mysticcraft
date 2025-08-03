package net.kapitencraft.mysticcraft.entity.dragon;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;

public class DragonBossAi {
    public static final List<Activity> ACTIVITIES = List.of(
            Activity.FIGHT, Activity.REST
    );

    private static final List<SensorType<? extends Sensor<? super Dragon>>> SENSORS = List.of(
            SensorType.HURT_BY
    );

    private static final List<MemoryModuleType<?>> MEMORIES = List.of(
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.ATTACK_TARGET
    );

    public static Brain<?> makeBrain(Dragon dragon, Dynamic<?> pDynamic) {
        Brain.Provider<Dragon> provider = Brain.provider(MEMORIES, SENSORS);
        Brain<Dragon> brain = provider.makeBrain(pDynamic);
        initCoreActivity(brain);
        initFightActivity(dragon, brain);
        return brain;
    }

    private static void initCoreActivity(Brain<Dragon> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink()
        ));
    }

    private static void initFightActivity(Dragon dragon, Brain<Dragon> pBrain) {
        pBrain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10,
                ImmutableList.of(StopAttackingIfTargetInvalid.create(
                        (p_219540_) -> !dragon.canTargetEntity(p_219540_),
                        DragonBossAi::onTargetInvalid,
                        false
                ), SetEntityLookTarget.create(
                        (target) -> isTarget(pBrain, target),
                        (float)dragon.getAttributeValue(Attributes.FOLLOW_RANGE)), SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.2F), MeleeAttack.create(18)), MemoryModuleType.ATTACK_TARGET);
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
