package net.kapitencraft.mysticcraft.rpg.skill.xp.combat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;

public record SizeBasedXpProvider(int base, int scale) implements EntityXpProvider {
    public static final MapCodec<SizeBasedXpProvider> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("base").forGetter(SizeBasedXpProvider::base),
            Codec.INT.fieldOf("scale").forGetter(SizeBasedXpProvider::scale)
    ).apply(i, SizeBasedXpProvider::new));

    @Override
    public ProviderType type() {
        return ProviderType.SIZE_BASED;
    }

    @Override
    public int get(Entity entity) {
        if (entity instanceof Slime slime) {
            return base + slime.getSize() * scale;
        } else {
            EntityXpProvider.LOGGER.warn("attempted to get size based xp from non-slime entity {}", entity);
        }
        return 0;
    }
}
