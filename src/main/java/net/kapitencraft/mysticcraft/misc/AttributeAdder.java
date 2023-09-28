package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.config.ClientModConfig;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.entity.SchnauzenPluesch;
import net.kapitencraft.mysticcraft.entity.WithermancerLordEntity;
import net.kapitencraft.mysticcraft.entity.skeleton_master.SkeletonMaster;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeAdder {
    @SubscribeEvent
    public static void AttributeAdding(EntityAttributeModificationEvent event) {
        addAll(event, ModAttributes.STRENGTH.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.CRIT_DAMAGE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.RANGED_DAMAGE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.HEALTH_REGEN.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.MAGIC_DEFENCE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.TRUE_DEFENCE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.BONUS_ATTACK_SPEED.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.CRIT_CHANCE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.COOLDOWN_REDUCTION.get(), LIVINGS);
        event.add(EntityType.PLAYER, ModAttributes.ABILITY_DAMAGE.get());
        event.add(EntityType.PLAYER, ModAttributes.MANA_COST.get());
        event.add(EntityType.PLAYER, ModAttributes.INTELLIGENCE.get());
        event.add(EntityType.PLAYER, ModAttributes.MAGIC_FIND.get());
        event.add(EntityType.PLAYER, ModAttributes.FEROCITY.get());
        event.add(EntityType.PLAYER, ModAttributes.MAX_MANA.get());
        event.add(EntityType.PLAYER, ModAttributes.MANA_REGEN.get());
        event.add(EntityType.PLAYER, ModAttributes.MANA.get());
        event.add(EntityType.PLAYER, ModAttributes.DODGE.get());
        event.add(EntityType.PLAYER, ModAttributes.LIVE_STEAL.get());
        event.add(EntityType.PLAYER, ModAttributes.DRAW_SPEED.get());
        event.add(EntityType.PLAYER, ModAttributes.ARROW_SPEED.get());
        event.add(EntityType.PLAYER, ModAttributes.ARMOR_SHREDDER.get());
        event.add(EntityType.PLAYER, ModAttributes.DOUBLE_JUMP.get());
        event.add(EntityType.PLAYER, ModAttributes.FISHING_SPEED.get());
    }

    private interface isAInstance {
        boolean is(EntityType<? extends LivingEntity> entityType);
    }

    private static final isAInstance ONLY_WITH_BRAIN = (entityType)-> (entityType.getCategory() != MobCategory.MISC) || entityType == EntityType.PLAYER;
    private static final isAInstance LIVINGS = entityType -> true;


    private static void addAll(EntityAttributeModificationEvent event, Attribute attribute, isAInstance generator) {
        List<String> addedList = new ArrayList<>();
        for (EntityType<? extends Entity> entityType : ForgeRegistries.ENTITY_TYPES.getValues()) {
            EntityType<? extends LivingEntity> livingType;
            try {
                livingType = (EntityType<? extends LivingEntity>) entityType;
                if (generator.is(livingType)) {
                    event.add(livingType, attribute);
                    addedList.add(livingType.getDescriptionId());
                }
            } catch (Exception ignored) {

            }
        }
        if (ClientModConfig.extraDebug) MysticcraftMod.sendRegisterDisplay("Attribute: [" + attribute.getDescriptionId() + "] to: " + addedList);
    }
    @SubscribeEvent
    public static void AttributeCreating(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeEntity.createAttributes().build());
        event.put(ModEntityTypes.WITHERMANCER_LORD.get(), WithermancerLordEntity.createAttributes().build());
        event.put(ModEntityTypes.SCHNAUZEN_PLUESCH.get(), SchnauzenPluesch.createAttributes().build());
        event.put(ModEntityTypes.SKELETON_MASTER.get(), SkeletonMaster.createAttributes().build());
    }
}
