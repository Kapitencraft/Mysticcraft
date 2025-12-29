package net.kapitencraft.mysticcraft.rpg.skill.xp.combat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;

public record SimpleEntityXpProvider(int amount) implements EntityXpProvider {
    public static final MapCodec<SimpleEntityXpProvider> CODEC = Codec.INT.xmap(SimpleEntityXpProvider::new, SimpleEntityXpProvider::amount).fieldOf("amount");

    @Override
    public ProviderType type() {
        return ProviderType.SIMPLE;
    }

    @Override
    public int get(Entity entity) {
        return amount;
    }
}
