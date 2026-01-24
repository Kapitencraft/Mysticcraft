package net.kapitencraft.mysticcraft.rpg.skill.xp.provider.combat;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public interface EntityXpProvider {
    Logger LOGGER = LogUtils.getLogger();

    Codec<EntityXpProvider> CODEC = ProviderType.CODEC.dispatch(EntityXpProvider::type, ProviderType::getCodec);

    ProviderType type();

    int get(Entity entity);

    enum ProviderType implements StringRepresentable {
        SIMPLE(SimpleEntityXpProvider.CODEC),
        SIZE_BASED(SizeBasedXpProvider.CODEC),
        HUMANOID(BaseHumanoidXpProvider.CODEC);

        public static final EnumCodec<ProviderType> CODEC = StringRepresentable.fromEnum(ProviderType::values);

        private final MapCodec<? extends EntityXpProvider> codec;

        ProviderType(MapCodec<? extends EntityXpProvider> codec) {
            this.codec = codec;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }

        public MapCodec<? extends EntityXpProvider> getCodec() {
            return codec;
        }
    }
}
