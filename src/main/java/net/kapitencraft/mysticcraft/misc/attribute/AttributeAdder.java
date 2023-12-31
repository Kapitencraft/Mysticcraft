package net.kapitencraft.mysticcraft.misc.attribute;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.config.ClientModConfig;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.entity.WithermancerLordEntity;
import net.kapitencraft.mysticcraft.entity.skeleton_master.SkeletonMaster;
import net.kapitencraft.mysticcraft.entity.vampire.VampireBat;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeAdder {
    @SubscribeEvent
    public static void modifyAttributes(EntityAttributeModificationEvent event) {
        addAll(event, ModAttributes.STRENGTH.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.CRIT_DAMAGE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.RANGED_DAMAGE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.HEALTH_REGEN.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.MAGIC_DEFENCE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.TRUE_DEFENCE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.BONUS_ATTACK_SPEED.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.CRIT_CHANCE.get(), ONLY_WITH_BRAIN);
        addAll(event, ModAttributes.COOLDOWN_REDUCTION.get(), LIVINGS);
        addToPlayer(event,
                ModAttributes.MINING_FORTUNE,
                ModAttributes.PRISTINE,
                ModAttributes.MINING_SPEED,
                ModAttributes.ABILITY_DAMAGE,
                ModAttributes.MANA_COST,
                ModAttributes.INTELLIGENCE,
                ModAttributes.MAGIC_FIND,
                ModAttributes.FEROCITY,
                ModAttributes.MAX_MANA,
                ModAttributes.MANA_REGEN,
                ModAttributes.MANA,
                ModAttributes.DODGE,
                ModAttributes.LIVE_STEAL,
                ModAttributes.DRAW_SPEED,
                ModAttributes.ARROW_SPEED,
                ModAttributes.ARMOR_SHREDDER,
                ModAttributes.DOUBLE_JUMP,
                ModAttributes.FISHING_SPEED
        );
    }

    private interface isAInstance {
        boolean is(EntityType<? extends LivingEntity> entityType);
    }

    private static final isAInstance ONLY_WITH_BRAIN = (entityType)-> (entityType.getCategory() != MobCategory.MISC) || entityType == EntityType.PLAYER;
    private static final isAInstance LIVINGS = entityType -> true;


    @SafeVarargs
    private static void addToPlayer(EntityAttributeModificationEvent event, Supplier<Attribute>... attributes) {
        Arrays.stream(attributes).map(Supplier::get).forEach(attribute -> event.add(EntityType.PLAYER, attribute));
    }


    private static void addAll(EntityAttributeModificationEvent event, Attribute attribute, isAInstance generator) {
        List<String> addedList = new ArrayList<>();
        ForgeRegistries.ENTITY_TYPES.getValues().stream().map(AttributeAdder::toLiving).filter(Objects::nonNull).filter(generator::is)
                .forEach(entityType -> {
                    event.add(entityType, attribute);
                    addedList.add(entityType.getDescriptionId());
                });
        if (ClientModConfig.extraDebug) MysticcraftMod.sendRegisterDisplay("Attribute: [" + attribute.getDescriptionId() + "] to: " + addedList);
    }

    private static EntityType<? extends LivingEntity> toLiving(EntityType<?> in) {
        try {
            return (EntityType<? extends LivingEntity>) in;
        } catch (ClassCastException e) {
            return null;
        }
    }
    @SubscribeEvent
    public static void createAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeEntity.createAttributes().build());
        event.put(ModEntityTypes.WITHERMANCER_LORD.get(), WithermancerLordEntity.createAttributes().build());
        event.put(ModEntityTypes.SKELETON_MASTER.get(), SkeletonMaster.createAttributes().build());
        event.put(ModEntityTypes.VAMPIRE_BAT.get(), VampireBat.createAttributes().build());
    }
}
