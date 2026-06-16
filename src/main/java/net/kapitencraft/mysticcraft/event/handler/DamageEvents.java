package net.kapitencraft.mysticcraft.event.handler;

import com.mojang.datafixers.util.Pair;
import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.core.helpers.IOHelper;
import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ManaSteelSwordItem;
import net.kapitencraft.mysticcraft.item.material.PrecursorRelicItem;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.registry.ModMobEffects;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.rpg.perks.Perk;
import net.kapitencraft.mysticcraft.rpg.perks.Perks;
import net.kapitencraft.mysticcraft.rpg.skill.PlayerSkills;
import net.kapitencraft.mysticcraft.rpg.skill.Skill;
import net.kapitencraft.mysticcraft.rpg.skill.xp.SkillXpMaps;
import net.kapitencraft.mysticcraft.rpg.skill.xp.provider.combat.EntityXpProvider;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.kapitencraft.mysticcraft.util.damage_source.ISpellSource;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import javax.annotation.Nullable;
import java.util.Map;

@EventBusSubscriber
public class DamageEvents {
    private DamageEvents() {}//dummy constructor (do not call)

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void miscDamageEvents(LivingDamageEvent.Pre event) {
        LivingEntity attacked = event.getEntity();
        LivingEntity attacker = MiscHelper.getAttacker(event.getSource());
        CompoundTag tag = attacked.getPersistentData();
        if (IOHelper.checkForIntAbove0(tag, WitherShieldSpell.DAMAGE_REDUCTION_TIME)) {
            event.setNewDamage(event.getNewDamage() * .9f);
        }
        if (attacker != null) {
            ItemStack mainHand = attacker.getMainHandItem();
            if (mainHand.getItem() instanceof ManaSteelSwordItem) {
                attacker.heal(2f);
            }
        }
    }

    @SuppressWarnings("all")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void hitEffectRegister(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        CompoundTag tag = living.getPersistentData();
        if (event.getSource().is(DamageTypeTags.IS_FIRE) && living.hasEffect(ModMobEffects.BLAZING)) {
            event.setNewDamage(event.getNewDamage() * (1 + 0.2f * living.getEffect(ModMobEffects.BLAZING).getAmplifier()));
        }
        if (living.hasEffect(ModMobEffects.VULNERABILITY)) {
            event.setNewDamage(event.getNewDamage() * (1 + 0.05f * living.getEffect(ModMobEffects.VULNERABILITY).getAmplifier()));
        }

        if (event.getSource().getDirectEntity() instanceof SmallFireball smallFireball) {
            if (smallFireball.getOwner() instanceof FrozenBlazeEntity) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity living = event.getEntity();
        if (living.hasEffect(ModMobEffects.NUMBNESS)) {
            event.setCanceled(true);
            living.setData(ModAttachmentTypes.NUMBNESS_DAMAGE, living.getData(ModAttachmentTypes.NUMBNESS_DAMAGE) + event.getAmount());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void damageAttributeRegister(LivingDamageEvent.Pre event) {
        @Nullable LivingEntity attacker = MiscHelper.getAttacker(event.getSource());
        if (attacker == null) { return; }
        if (event.getSource() instanceof ISpellSource) {
            double magicDamage = attacker.getAttributeValue(ExtraAttributes.MAGIC_DAMAGE);
            event.setNewDamage(event.getNewDamage() * (float) (1 + (magicDamage / 100)));
        }
        if (attacker instanceof Player player) {
            for (Pair<ResourceKey<Perk>, TagKey<Item>> perk : Perks.COMBAT_PERKS) {
                ItemStack mainHandItem = attacker.getMainHandItem();
                if (mainHandItem.is(perk.getSecond())) {
                    Level level = attacker.level();
                    Holder<Perk> orThrow = level.registryAccess().registryOrThrow(ModRegistries.Keys.PERKS).getHolderOrThrow(perk.getFirst());

                }
            }
        }
    }

    @SubscribeEvent
    public static void entityDeathEvents(LivingDeathEvent event) {
        LivingEntity toDie = event.getEntity();
        if (toDie instanceof ServerPlayer player) {
            if (!event.isCanceled()) {
                Map<Integer, ItemStack> soulbound = InventoryHelper.getContentByFilter(player, SoulbindHelper::isSoulbound);
                soulbound.forEach((integer, stack) -> {
                    ArmorStand armorStand = new ArmorStand(toDie.level(), toDie.getX(), toDie.getY(), toDie.getZ());
                    CompoundTag tag = armorStand.getPersistentData();
                    tag.putInt("SlotId", integer);
                    //TODO
                    //tag.put("SlotContent", stack.save(new CompoundTag()));
                });
            }
        }
        LivingEntity living = MiscHelper.getAttacker(event.getSource());
        if (living instanceof ServerPlayer player) {
            if (toDie instanceof WitherBoss boss) {
                PrecursorRelicItem.BossType type = PrecursorRelicItem.BossType.fromBoss(boss);
                player.awardStat(type.getStatLoc());
            }
            EntityType<?> type = toDie.getType();
            EntityXpProvider data = type.builtInRegistryHolder().getData(SkillXpMaps.COMBAT);
            if (data != null) {
                PlayerSkills.reward(player, Skill.COMBAT, data.get(toDie), true);
            } else {
                PlayerSkills.LOGGER.warn("unknown combat skill xp for {}", type);
            }
        }
    }
}
