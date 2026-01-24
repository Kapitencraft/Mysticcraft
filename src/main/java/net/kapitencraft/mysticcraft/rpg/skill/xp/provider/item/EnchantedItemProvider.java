package net.kapitencraft.mysticcraft.rpg.skill.xp.provider.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.kapitencraft.mysticcraft.rpg.skill.xp.SkillXpMaps;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantedItemProvider implements ItemStackXpProvider {
    public static final MapCodec<EnchantedItemProvider> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("base").forGetter(p -> p.baseXp)
    ).apply(i, EnchantedItemProvider::new));

    private final int baseXp;

    public EnchantedItemProvider(int baseXp) {
        this.baseXp = baseXp;
    }

    @Override
    public ProviderType type() {
        return ProviderType.ENCHANTED;
    }

    @Override
    public int get(ItemStack stack) {
        int xp = 0;
        for (Object2IntMap.Entry<Holder<Enchantment>> holder : stack.getEnchantments().entrySet()) {
            Integer data = holder.getKey().getData(SkillXpMaps.ENCHANTING);
            if (data != null) {
                xp += data * holder.getIntValue();
            }
        }
        return xp + baseXp;
    }
}
