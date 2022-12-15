package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.entity.WithermancerLordEntity;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeAdder {
    @SubscribeEvent
    public static void AttributeAdding(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.CRIT_DAMAGE.get());
        event.add(EntityType.PLAYER, ModAttributes.ABILITY_DAMAGE.get());
        event.add(EntityType.PLAYER, ModAttributes.MANA_COST.get());
        event.add(EntityType.PLAYER, ModAttributes.INTELLIGENCE.get());
        event.add(EntityType.PLAYER, ModAttributes.STRENGTH.get());
        event.add(EntityType.PLAYER, ModAttributes.MAGIC_FIND.get());
        event.add(EntityType.PLAYER, ModAttributes.FEROCITY.get());
        event.add(EntityType.PLAYER, ModAttributes.MAX_MANA.get());
        event.add(EntityType.PLAYER, ModAttributes.MANA_REGEN.get());
        event.add(EntityType.PLAYER, ModAttributes.CRIT_DAMAGE.get());
        event.add(EntityType.PLAYER, ModAttributes.MANA.get());
        event.add(EntityType.PLAYER, ModAttributes.HEALTH_REGEN.get());
        event.add(EntityType.PLAYER, ModAttributes.DODGE.get());

    }

    @SubscribeEvent
    public static void AttributeCreating(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeEntity.createAttributes().build());
        event.put(ModEntityTypes.WITHERMANCER_LORD.get(), WithermancerLordEntity.createAttributes().build());
    }
}
