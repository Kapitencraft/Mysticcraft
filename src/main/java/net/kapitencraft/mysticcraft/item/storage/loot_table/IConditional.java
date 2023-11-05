package net.kapitencraft.mysticcraft.item.storage.loot_table;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public interface IConditional {

    LootItemCondition[] getConditions();
}
