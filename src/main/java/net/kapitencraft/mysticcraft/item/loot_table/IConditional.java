package net.kapitencraft.mysticcraft.item.loot_table;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public interface IConditional {

    LootItemCondition[] getConditions();
}
