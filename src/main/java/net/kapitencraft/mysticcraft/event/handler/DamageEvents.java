package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.client.particle.animation.*;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IToolEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModBowEnchantment;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.entity.item.UnCollectableItemEntity;
import net.kapitencraft.mysticcraft.helpers.*;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.item_stat.ItemStatCapability;
import net.kapitencraft.mysticcraft.item.combat.totems.ModTotemItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ManaSteelSwordItem;
import net.kapitencraft.mysticcraft.item.material.PrecursorRelicItem;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.kapitencraft.mysticcraft.misc.DamageCounter;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.kapitencraft.mysticcraft.misc.MiscRegister;
import net.kapitencraft.mysticcraft.misc.damage_source.FerociousDamageSource;
import net.kapitencraft.mysticcraft.misc.damage_source.IAbilitySource;
import net.kapitencraft.mysticcraft.mob_effects.NumbnessMobEffect;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.S2C.DisplayTotemActivationPacket;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber
public class DamageEvents {
    private DamageEvents() {}//dummy constructor
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void miscDamageEvents(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        LivingEntity attacker = MiscHelper.getAttacker(event.getSource());
        CompoundTag tag = attacked.getPersistentData();
        if (IOHelper.checkForIntAbove0(tag, WitherShieldSpell.DAMAGE_REDUCTION_TIME)) {
            MathHelper.mul(event::getAmount, event::setAmount, 0.9f);
        }
        if (attacker != null) {
            ItemStack mainHand = attacker.getMainHandItem();
            if (mainHand.getItem() instanceof ManaSteelSwordItem) {
                HealingHelper.setEffectReason(attacker);
                attacker.heal(2f);
            }
        }
    }

    @SubscribeEvent
    public static void damageIndicatorRegister(LivingDamageEvent event) {
        LivingEntity attacked = event.getEntity();
        DamageSource source = event.getSource();
        boolean dodge = false;
        double Dodge = AttributeHelper.getSaveAttributeValue(ModAttributes.DODGE.get(), attacked);
        if (Dodge > 0) {
            if (MathHelper.chance(Dodge / 100, attacked) && ((!source.isBypassArmor() && !source.isFall() && !source.isFire()) || source == DamageSource.STALAGMITE)) {
                dodge = true;
                event.setAmount(0);
            }
        }
        MiscHelper.createDamageIndicator(attacked, event.getAmount(), dodge ? "dodge" : source.getMsgId());
        DamageCounter.increaseDamage(event.getAmount());
    }

    @SubscribeEvent
    public static void itemBonusRegister(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        LivingEntity attacker = MiscHelper.getAttacker(source);
        MiscHelper.DamageType type = MiscHelper.getDamageType(source);
        LivingEntity killed = event.getEntity();
        if (attacker != null) {
            MiscHelper.getArmorEquipment(attacker)
                    .map(BonusHelper::getBonusesFromStack)
                    .collect(CollectorHelper.merge())
                    .forEach(bonus -> bonus.onEntityKilled(killed, attacker, type));
            attacker.getItemBySlot(EquipmentSlot.MAINHAND).getCapability(CapabilityHelper.ITEM_STAT).ifPresent(iItemStatHandler -> {
                iItemStatHandler.increase(ItemStatCapability.Type.KILLED, 1);
            });
        }
    }

    @SuppressWarnings("all")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void hitEffectRegister(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        CompoundTag tag = living.getPersistentData();
        if (event.getSource().isFire() && living.hasEffect(ModMobEffects.BLAZING.get())) {
            MathHelper.mul(event::getAmount, event::setAmount, 1 + 0.2f * living.getEffect(ModMobEffects.BLAZING.get()).getAmplifier());
        }
        if (living.hasEffect(ModMobEffects.VULNERABILITY.get())) {
            MathHelper.mul(event::getAmount, event::setAmount, 1 + 0.05f * living.getEffect(ModMobEffects.VULNERABILITY.get()).getAmplifier());
        }
        if (living.hasEffect(ModMobEffects.NUMBNESS.get())) {
            event.setCanceled(true);
            IOHelper.increaseFloatTagValue(tag, NumbnessMobEffect.NUMBNESS_ID, event.getAmount());
        }
        if (event.getSource().getDirectEntity() instanceof SmallFireball smallFireball) {
            if (smallFireball.getOwner() instanceof FrozenBlazeEntity) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5));
            }
        }
    }

    @SubscribeEvent
    public static void critDamageRegister(CriticalHitEvent event) {
        Player attacker = event.getEntity();
        event.setDamageModifier((float) (1 + AttributeHelper.getSaveAttributeValue(ModAttributes.CRIT_DAMAGE.get(), attacker) / 100));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void ferocityRegister(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        DamageSource source = event.getSource();
        LivingEntity attacker = MiscHelper.getAttacker(source);
        if (attacker == null || MiscHelper.getDamageType(source) != MiscHelper.DamageType.MELEE) {
            return;
        }
        if (attacker.getAttribute(ModAttributes.FEROCITY.get()) != null) {
            final double ferocity = source instanceof FerociousDamageSource damageSource ? damageSource.ferocity : attacker.getAttributeValue(ModAttributes.FEROCITY.get());
            if (MathHelper.chance(ferocity / 100, attacker)) {
                MiscHelper.delayed(40, () -> {
                    float ferocityDamage = (float) (source instanceof FerociousDamageSource ferociousDamageSource ? ferociousDamageSource.damage : source.getEntity() instanceof AbstractArrow arrow ?                             arrow.getBaseDamage() : attacker.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    attacked.level.playSound(attacked, new BlockPos(attacked.position()), SoundEvents.IRON_GOLEM_ATTACK, SoundSource.HOSTILE, 1f, 0.5f);
                    attacked.hurt(new FerociousDamageSource(attacker, (ferocity - 100), ferocityDamage), ferocityDamage);
                });
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void DamageBonusRegister(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        if (event.getSource().getEntity() instanceof Arrow arrow) {
            CompoundTag tag = arrow.getPersistentData();
            event.setAmount(ModBowEnchantment.loadFromTag(attacked, tag, ModBowEnchantment.ExecuteType.HIT, event.getAmount(), arrow));
            if (tag.getInt("OverloadEnchant") > 0 && arrow.isCritArrow()) {
                if (MathHelper.chance(0.1, arrow.getOwner())) event.setAmount((float) (event.getAmount() * 1 + (tag.getInt("OverloadEnchant") * 0.1)));
            }
            return;
        }

        DamageSource source = event.getSource();
        @Nullable LivingEntity attacker = MiscHelper.getAttacker(source);
        if (attacker == null) { return; }
        MiscHelper.DamageType type = MiscHelper.getDamageType(source);
        BonusHelper.useBonuses(attacked, (bonus, stack) -> event.setAmount(bonus.onTakeDamage(attacked, attacker, type, event.getAmount())));
        BonusHelper.useBonuses(attacker, (bonus, stack) -> event.setAmount(bonus.onEntityHurt(attacked, attacker, type, event.getAmount())));
        ItemStack stack = attacker.getMainHandItem();
        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        if (enchantments != null) {
            event.setAmount(ExtendedCalculationEnchantment.runWithPriority(stack, attacker, attacked, event.getAmount(), type, true, source));
            MiscHelper.getArmorEquipment(attacked).forEach(stack1 -> event.setAmount(ExtendedCalculationEnchantment.runWithPriority(stack1, attacker, attacked, event.getAmount(), type, false, source)));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void damageAttributeRegister(LivingHurtEvent event) {
        @Nullable LivingEntity attacker = MiscHelper.getAttacker(event.getSource());
        if (attacker == null) { return; }
        if (event.getSource() instanceof IAbilitySource abilitySource) {
            double intel = attacker.getAttributeValue(ModAttributes.INTELLIGENCE.get());
            double ability_damage = attacker.getAttributeValue(ModAttributes.ABILITY_DAMAGE.get());
            MathHelper.mul(event::getAmount, event::setAmount, (float) ((1 + (intel / 100) * abilitySource.getScaling()) * (1 + (ability_damage / 100))));
        } else if (AttributeHelper.getSaveAttributeValue(ModAttributes.STRENGTH.get(), attacker) != -1) {
            double Strength = AttributeHelper.getSaveAttributeValue(ModAttributes.STRENGTH.get(), attacker);
            MathHelper.mul(event::getAmount, event::setAmount, (float) (1 + Strength / 100));
        }
        double double_jump = AttributeHelper.getSaveAttributeValue(ModAttributes.DOUBLE_JUMP.get(), attacker);
        if (double_jump > 0 && attacker.getPersistentData().getInt(MiscRegister.DOUBLE_JUMP_ID) > 0 && event.getSource().getMsgId().equals("fall")) {
            event.setAmount((float) (event.getAmount() * (1 - double_jump * 0.02)));
        }
        LivingEntity attacked = event.getEntity();
        if (AttributeHelper.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), attacker) != -1) {
            double armorShredder = AttributeHelper.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), attacker);
            MiscHelper.getArmorEquipment(attacked)
                    .forEach(stack -> stack.hurt((int) (armorShredder / 3), attacked.level.getRandom(), attacker instanceof ServerPlayer serverPlayer ? serverPlayer : null));
        }
        double liveSteal = AttributeHelper.getSaveAttributeValue(ModAttributes.LIVE_STEAL.get(), attacker);
        if (liveSteal != -1) {
            HealingHelper.setEffectReason(attacker);
            ParticleHelper.sendParticles(new ParticleAnimationOptions(
                    new DustParticleOptions(MathHelper.color(130, 0, 0), 1.9f),
                    ParticleAnimationParameters.create().withParam(ParticleAnimParams.TARGET, attacker),
                    ParticleAnimationInfo.create(Map.of(50, ParticleAnimations.MOVE_TO)),
                    5
            ), false, attacked, 5, attacked.getBbWidth(), attacked.getBbHeight(), attacked.getBbWidth(), 0.1);

            attacker.heal(Math.min((float) liveSteal, event.getAmount()));
        }
        if (AttributeHelper.getSaveAttributeValue(ModAttributes.CRIT_CHANCE.get(), attacker) / 100 > Math.random() && attacker instanceof Player player) {
            CriticalHitEvent event1 = ForgeHooks.getCriticalHit(player, attacked, false, 1.5f);
            if (event1 != null) {
                MathHelper.mul(event::getAmount, event::setAmount, event1.getDamageModifier());
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        event.setCanceled(Requirement.doesntMeetReqsFromEvent(event));
    }

    @SubscribeEvent
    public static void shieldBlockEnchantments(ShieldBlockEvent event) {
        LivingEntity attacked = event.getEntity();
        @Nullable LivingEntity attacker = MiscHelper.getAttacker(event.getDamageSource());
        if (attacker == null) { return; }
        ItemStack stack = attacker.getUseItem();
        MiscHelper.DamageType type = MiscHelper.getDamageType(event.getDamageSource());
        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        if (enchantments != null && !enchantments.isEmpty()) {
            MapStream.of(enchantments)
                    .mapKeys(MiscHelper.instanceMapper(ExtendedCalculationEnchantment.class))
                    .filterKeys(Objects::nonNull)
                    .filterKeys(ench -> ench instanceof IToolEnchantment)
                    .forEach((enchantment, integer) -> enchantment.tryExecute(integer, stack, attacker, attacked, event.getBlockedDamage(), type, event.getDamageSource()));
        }
    }

    @SubscribeEvent
    public static void entityDeathEvents(LivingDeathEvent event) {
        LivingEntity toDie = event.getEntity();
        if (toDie instanceof ServerPlayer player) {
            List<ItemStack> totems = InventoryHelper.getByFilter(player, stack -> stack.getItem() instanceof ModTotemItem);
            if (!event.isCanceled()) for (ItemStack stack : totems) {
                ModTotemItem totemItem = (ModTotemItem) stack.getItem();
                if (totemItem.onUse(player, event.getSource())) {
                    event.setCanceled(true);
                    if (player.getHealth() <= 0) throw new IllegalStateException("Player wasn't revived!"); //ensure player being revived by the totem (e.g. health boost)
                    ModMessages.sendToAllConnectedPlayers(serverPlayer -> new DisplayTotemActivationPacket(stack.copy(), player.getId()), player.getLevel());
                    stack.shrink(1);
                    break;
                }
            }
            if (!event.isCanceled()) {
                List<ItemStack> soulbound = InventoryHelper.getByFilter(player, SoulbindHelper::isSoulbound);
                soulbound.forEach(stack -> {
                    UnCollectableItemEntity itemEntity = new UnCollectableItemEntity(toDie.level, toDie.getX(), toDie.getY(), toDie.getZ(), stack);
                    itemEntity.addPlayer(player);
                    itemEntity.move();
                });
            }
        }
        if (toDie instanceof WitherBoss boss) {
            PrecursorRelicItem.BossType type = PrecursorRelicItem.BossType.fromBoss(boss);
            LivingEntity living = MiscHelper.getAttacker(event.getSource());
            if (living instanceof Player player) player.awardStat(type.getStatLoc());
        }
    }
}
