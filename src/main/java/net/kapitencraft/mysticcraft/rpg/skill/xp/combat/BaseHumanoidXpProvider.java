package net.kapitencraft.mysticcraft.rpg.skill.xp.combat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BaseHumanoidXpProvider implements EntityXpProvider {
    //TODO

    @Override
    public ProviderType type() {
        return ProviderType.HUMANOID;
    }

    @Override
    public int get(Entity entity) {
        if (entity instanceof LivingEntity living) {
        }
        return 0;
    }
}
