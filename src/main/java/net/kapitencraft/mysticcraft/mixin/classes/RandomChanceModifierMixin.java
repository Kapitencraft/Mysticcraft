package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootItemRandomChanceWithLootingCondition.class)
public abstract class RandomChanceModifierMixin implements LootItemCondition {

    @Override
    public boolean test(LootContext lootContext) {
        int i = lootContext.getLootingModifier();
        boolean flag = lootContext.getParamOrNull(LootContextParams.KILLER_ENTITY) != null;
        float mul = flag ? lootContext.getParam(LootContextParams.KILLER_ENTITY) instanceof LivingEntity living ? (float) (living.getAttributeValue(ModAttributes.MAGIC_FIND.get()) / 100) : 1 : 1;
        return lootContext.getRandom().nextFloat() < (this.getPercent() + (float)i * this.getLootingMultiplier()) * (1 + (flag ? mul : 1));
    }

    @Accessor
    abstract float getPercent();

    @Accessor
    abstract float getLootingMultiplier();
}