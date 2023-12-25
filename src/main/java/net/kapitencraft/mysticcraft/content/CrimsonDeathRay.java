package net.kapitencraft.mysticcraft.content;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrimsonDeathRay {
    private final ClientLevel level;
    private final LivingEntity target;
    private int activeTime = 0;
    private float baseRot = 1;

    public CrimsonDeathRay(LivingEntity target) {
        this.level = (ClientLevel) target.level;
        this.target = target;
    }
    
    public boolean tick() {
        for (int i = 0; i < 3; i++) {
            float rot = baseRot + 120 * i;
            Vec3 rotVec = MathHelper.calculateViewVector(0, rot);

        }
        baseRot += 0.5 * baseRot * (1200 - baseRot);
        return activeTime++ > 200;
    }
}
