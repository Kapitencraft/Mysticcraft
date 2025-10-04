package net.kapitencraft.mysticcraft.entity.skeleton_master;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.helpers.ParticleHelper;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.function.Predicate;

public class ArrowKeeper {
    ControlledArrow[][] arrowMatrix = new ControlledArrow[10][5];
    private int rotation;
    private int targetRotation;

    public ArrowKeeper(int rotKey) {
        this.rotation = rotKey;
        this.targetRotation = rotation;
    }

    void update(SkeletonMaster master) {
        updateRotation();
        updatePosition(master);
    }

    private void updateRotation() {
        if (rotation != targetRotation) {
            if (rotation < targetRotation) {
                rotation++;
            } else {
                rotation--;
            }
        }
    }

    void nextTargetRotation() {
        if ((targetRotation+=90) >= 360) {
            if (targetRotation % 90 != 0) {
                throw new IllegalStateException("target rot should always be multiplicative of 90");
            }
            targetRotation-= 360;
        }
    }

    void shoot() {
        for (ControlledArrow[] arrows : arrowMatrix) {
            for (int i = 0; i < arrows.length; i++) {
                ControlledArrow arrow = arrows[i];
                if (arrow != null) {
                    arrow.hasBeenFired = true;
                    arrow.level().addFreshEntity(arrow);
                    arrows[i] = null;
                    return;
                }
            }
        }
    }

    private void updatePosition(SkeletonMaster master) {
        MiscHelper.repeat(arrowMatrix.length, integer -> MiscHelper.repeat(arrowMatrix[integer].length, integer1 -> {
                    double offset1 = integer1 * 0.25;
                    double offset2 = integer * 0.25;
                    Vec3 pos = MathHelper.calculateViewVector(master.getXRot(), master.getYRot() + rotation);
                    pos.scale(1 + offset1);
                    pos.add(master.getX(), master.getY() + offset2, master.getZ());
                    ControlledArrow arrow = arrowMatrix[integer][integer1];
                    if (arrow != null) {
                        arrow.setPos(pos);
                    }
                    ParticleHelper.sendParticles(master.level(), DustParticleOptions.REDSTONE, true, pos, 2, 0, 0, 0, 0);
                })
        );
    }

    void charge() {

    }

    boolean empty() {
        return !forAll(false, Objects::nonNull);
    }

    private boolean forAll(boolean defaultValue, Predicate<ControlledArrow> predicate) {
        for (ControlledArrow[] arrows : arrowMatrix) {
            for (ControlledArrow arrow : arrows) {
                defaultValue |= predicate.test(arrow);
            }
        }
        return defaultValue;
    }
}
