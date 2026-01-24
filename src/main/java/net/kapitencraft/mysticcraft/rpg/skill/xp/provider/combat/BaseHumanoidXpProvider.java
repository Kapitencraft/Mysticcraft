package net.kapitencraft.mysticcraft.rpg.skill.xp.provider.combat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BaseHumanoidXpProvider implements EntityXpProvider {
    public static final MapCodec<BaseHumanoidXpProvider> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("base_xp").forGetter(p -> p.baseXp),
            Codec.FLOAT.fieldOf("base_hp").forGetter(p -> p.baseHp),
            Codec.FLOAT.fieldOf("hp_scale").forGetter(p -> p.hpXpScale)
    ).apply(i, BaseHumanoidXpProvider::new));
    private final int baseXp;
    private final float baseHp;
    private final float hpXpScale;

    public BaseHumanoidXpProvider(int baseXp, float baseHp, float hpXpScale) {
        this.baseXp = baseXp;
        this.baseHp = baseHp;
        this.hpXpScale = hpXpScale;
    }
    //TODO

    @Override
    public ProviderType type() {
        return ProviderType.HUMANOID;
    }

    @Override
    public int get(Entity entity) {
        int xp = baseXp;
        if (entity instanceof LivingEntity living) {
            xp += (int) ((living.getMaxHealth() - baseHp) * hpXpScale);
        }
        return xp;
    }
}
