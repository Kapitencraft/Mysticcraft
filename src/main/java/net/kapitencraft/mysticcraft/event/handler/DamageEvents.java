package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.kap_lib.helpers.IOHelper;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.capability.item_stat.ItemStatCapability;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ManaSteelSwordItem;
import net.kapitencraft.mysticcraft.item.material.PrecursorRelicItem;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.kapitencraft.mysticcraft.misc.damage_source.ISpellSource;
import net.kapitencraft.mysticcraft.mob_effects.NumbnessEffect;
import net.kapitencraft.mysticcraft.registry.ModMobEffects;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Map;

@Mod.EventBusSubscriber
public class DamageEvents {
    private DamageEvents() {}//dummy constructor (do not call)
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
                attacker.heal(2f);
            }
        }
    }

    @SubscribeEvent
    public static void statUpgradeRegister(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        LivingEntity attacker = MiscHelper.getAttacker(source);
        if (attacker != null) {
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
        if (event.getSource().is(DamageTypeTags.IS_FIRE) && living.hasEffect(ModMobEffects.BLAZING.get())) {
            MathHelper.mul(event::getAmount, event::setAmount, 1 + 0.2f * living.getEffect(ModMobEffects.BLAZING.get()).getAmplifier());
        }
        if (living.hasEffect(ModMobEffects.VULNERABILITY.get())) {
            MathHelper.mul(event::getAmount, event::setAmount, 1 + 0.05f * living.getEffect(ModMobEffects.VULNERABILITY.get()).getAmplifier());
        }
        if (living.hasEffect(ModMobEffects.NUMBNESS.get())) {
            event.setCanceled(true);
            IOHelper.increaseFloatTagValue(tag, NumbnessEffect.NUMBNESS_ID, event.getAmount());
        }
        if (event.getSource().getDirectEntity() instanceof SmallFireball smallFireball) {
            if (smallFireball.getOwner() instanceof FrozenBlazeEntity) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void DamageBonusRegister(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Arrow arrow) {
            CompoundTag tag = arrow.getPersistentData();
            if (tag.getInt("OverloadEnchant") > 0 && arrow.isCritArrow()) {
                if (MathHelper.chance(0.1, arrow.getOwner())) event.setAmount((float) (event.getAmount() * 1 + (tag.getInt("OverloadEnchant") * 0.1)));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void damageAttributeRegister(LivingHurtEvent event) {
        @Nullable LivingEntity attacker = MiscHelper.getAttacker(event.getSource());
        if (attacker == null) { return; }
        if (event.getSource() instanceof ISpellSource) {
            double ability_damage = attacker.getAttributeValue(ExtraAttributes.MAGIC_DAMAGE.get());
            MathHelper.mul(event::getAmount, event::setAmount, (float) ((1 + (ability_damage / 100))));
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
                    tag.put("SlotContent", stack.save(new CompoundTag()));
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
