package net.kapitencraft.mysticcraft.item.loot_table.container;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class EssenceContainer extends LootPoolEntryContainer {
    protected EssenceContainer(LootItemCondition[] p_79638_) {
        super(p_79638_);
    }

    @Override
    public LootPoolEntryType getType() {
        return null;
    }

    @Override
    public boolean expand(@NotNull LootContext context, @NotNull Consumer<LootPoolEntry> entryConsumer) {
        return false;
    }

    private static class EssenceLootPoolEntry implements LootPoolEntry {

        @Override
        public int getWeight(float p_79632_) {
            return 0;
        }

        @Override
        public void createItemStack(Consumer<ItemStack> p_79633_, LootContext p_79634_) {

        }
    }
}
