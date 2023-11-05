package net.kapitencraft.mysticcraft.item.storage.loot_table.modifiers;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;

public abstract class ModLootModifier extends LootModifier {

    protected ModLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }
}
