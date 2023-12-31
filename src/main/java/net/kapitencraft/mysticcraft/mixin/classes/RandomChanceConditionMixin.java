package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LootItemRandomChanceWithLootingCondition.class)
public abstract class RandomChanceConditionMixin implements LootItemCondition {

    @Shadow @Final
    float percent;

    @Shadow @Final
    float lootingMultiplier;

    @Override
    public boolean test(LootContext lootContext) {
        int i = lootContext.getLootingModifier();
        Entity entity = lootContext.getParamOrNull(LootContextParams.KILLER_ENTITY);
        float mul = entity instanceof LivingEntity living ? (float) (living.getAttributeValue(ModAttributes.MAGIC_FIND.get()) / 100) : 1;
        return lootContext.getRandom().nextFloat() < (this.percent + (float)i * this.lootingMultiplier) * (1 + mul);
    }
}