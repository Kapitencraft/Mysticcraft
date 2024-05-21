package net.kapitencraft.mysticcraft.item.capability.containable;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class WalletCapability extends ContainableCapability<Item> {
    private static final Codec<ContainableCapability<Item>> CODEC = createCodec(WalletCapability::new);

    protected WalletCapability(List<ItemStack> content) {
        super(CODEC, content);
    }

    public WalletCapability() {
        super(CODEC);
    }

    @Override
    public Capability<IContainable<Item>> getCapability() {
        return null;
    }

    @Override
    public boolean checkCanInsert(Item item) {
        return item.builtInRegistryHolder().is(Tags.Items.GEMS_EMERALD);
    }
}
