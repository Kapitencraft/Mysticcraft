package net.kapitencraft.mysticcraft.item.capability.containable;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class QuiverCapability extends ContainableCapability<ArrowItem> {
    private static final Codec<ContainableCapability<ArrowItem>> CODEC = createCodec(QuiverCapability::new);
    protected QuiverCapability(List<ItemStack> content) {
        super(CODEC, content);
    }

    public QuiverCapability() {
        super(CODEC);
    }

    @Override
    public Capability<QuiverCapability> getCapability() {
        return CapabilityHelper.QUIVER;
    }

    @Override
    public boolean checkCanInsert(Item item) {
        return item.builtInRegistryHolder().is(ItemTags.ARROWS);
    }
}
