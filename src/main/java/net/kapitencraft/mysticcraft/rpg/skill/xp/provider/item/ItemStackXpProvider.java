package net.kapitencraft.mysticcraft.rpg.skill.xp.provider.item;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.rpg.skill.xp.provider.combat.SimpleItemStackXpProvider;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public interface ItemStackXpProvider {
    Logger LOGGER = LogUtils.getLogger();

    Codec<ItemStackXpProvider> CODEC = ProviderType.CODEC.dispatch(ItemStackXpProvider::type, ProviderType::getCodec);

    ProviderType type();

    int get(ItemStack stack);

    enum ProviderType implements StringRepresentable {
        SIMPLE(SimpleItemStackXpProvider.CODEC),
        ENCHANTED(EnchantedItemProvider.CODEC);

        public static final EnumCodec<ProviderType> CODEC = StringRepresentable.fromEnum(ProviderType::values);

        private final MapCodec<? extends ItemStackXpProvider> codec;

        ProviderType(MapCodec<? extends ItemStackXpProvider> codec) {
            this.codec = codec;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }

        public MapCodec<? extends ItemStackXpProvider> getCodec() {
            return codec;
        }
    }
}
