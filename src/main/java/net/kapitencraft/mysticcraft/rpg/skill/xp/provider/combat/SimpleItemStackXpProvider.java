package net.kapitencraft.mysticcraft.rpg.skill.xp.provider.combat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.rpg.skill.xp.provider.item.ItemStackXpProvider;
import net.minecraft.world.item.ItemStack;

public record SimpleItemStackXpProvider(int amount) implements ItemStackXpProvider {
    public static final MapCodec<SimpleItemStackXpProvider> CODEC = Codec.INT.xmap(SimpleItemStackXpProvider::new, SimpleItemStackXpProvider::amount).fieldOf("amount");

    @Override
    public ProviderType type() {
        return ProviderType.SIMPLE;
    }

    @Override
    public int get(ItemStack stack) {
        return amount;
    }
}
