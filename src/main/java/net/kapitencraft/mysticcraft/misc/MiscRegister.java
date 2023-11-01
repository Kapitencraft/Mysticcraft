package net.kapitencraft.mysticcraft.misc;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.bestiary.BestiaryManager;
import net.kapitencraft.mysticcraft.enchantments.HealthMendingEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IToolEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModBowEnchantment;
import net.kapitencraft.mysticcraft.enchantments.armor.BasaltWalkerEnchantment;
import net.kapitencraft.mysticcraft.enchantments.weapon.ranged.OverloadEnchantment;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.guild.GuildUpgrade;
import net.kapitencraft.mysticcraft.guild.GuildUpgrades;
import net.kapitencraft.mysticcraft.helpers.*;
import net.kapitencraft.mysticcraft.init.*;
import net.kapitencraft.mysticcraft.item.combat.armor.*;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.combat.totems.ModTotemItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ManaSteelSwordItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ModBowItem;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.misc.RNGDropHelper;
import net.kapitencraft.mysticcraft.item.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.reforging.ReforgeManager;
import net.kapitencraft.mysticcraft.misc.cooldown.Cooldowns;
import net.kapitencraft.mysticcraft.misc.damage_source.FerociousDamageSource;
import net.kapitencraft.mysticcraft.misc.damage_source.IAbilitySource;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Reference;
import net.kapitencraft.mysticcraft.mixin.classes.LivingEntityAccessor;
import net.kapitencraft.mysticcraft.mob_effects.NumbnessMobEffect;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.kapitencraft.mysticcraft.villagers.ModVillagers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.*;

@Mod.EventBusSubscriber
public class MiscRegister {
    static final String doubleJumpId = "currentDoubleJump";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void damageAttributeRegister(LivingHurtEvent event) {
        @Nullable LivingEntity attacker = MiscHelper.getAttacker(event.getSource());
        if (attacker == null) { return; }
        if (event.getSource() instanceof IAbilitySource abilitySource) {
            double intel = attacker.getAttributeValue(ModAttributes.INTELLIGENCE.get());
            double ability_damage = attacker.getAttributeValue(ModAttributes.ABILITY_DAMAGE.get());
            event.setAmount((float) (event.getAmount() * (1 + (intel / 100) * abilitySource.getScaling()) * (1 + (ability_damage / 100))));
        } else if (AttributeHelper.getSaveAttributeValue(ModAttributes.STRENGTH.get(), attacker) != -1) {
            double Strength = AttributeHelper.getSaveAttributeValue(ModAttributes.STRENGTH.get(), attacker);
            event.setAmount((float) (event.getAmount() * (1 + Strength / 100)));
        }
        double double_jump = AttributeHelper.getSaveAttributeValue(ModAttributes.DOUBLE_JUMP.get(), attacker);
        if (double_jump > 0 && attacker.getPersistentData().getInt(doubleJumpId) > 0 && event.getSource().getMsgId().equals("fall")) {
            event.setAmount((float) (event.getAmount() * (1 - double_jump * 0.02)));
        }
        LivingEntity attacked = event.getEntity();
        if (AttributeHelper.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), attacker) != -1) {
            double armorShredder = (int) AttributeHelper.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), attacker);
            for (int i = 0; i < 4; i++) {
                ItemStack stack = attacked.getItemBySlot(MiscHelper.ARMOR_EQUIPMENT[i]);
                stack.hurt((int) armorShredder / 3, attacked.level.getRandom(), attacker instanceof ServerPlayer serverPlayer ? serverPlayer : null);
            }
        }
        if (AttributeHelper.getSaveAttributeValue(ModAttributes.LIVE_STEAL.get(), attacker) != -1) {
            double live_steel = (int) AttributeHelper.getSaveAttributeValue(ModAttributes.LIVE_STEAL.get(), attacker);
            HealingHelper.setEffectReason(attacked);
            attacker.heal(Math.min((float) live_steel, event.getAmount()));
        }
        if (AttributeHelper.getSaveAttributeValue(ModAttributes.CRIT_CHANCE.get(), attacker) / 100 > Math.random() && attacker instanceof Player player) {
            CriticalHitEvent event1 = ForgeHooks.getCriticalHit(player, attacked, false, 1.5f);
            if (event1 != null) {
                MathHelper.mul(event::getAmount, event::setAmount, event1.getDamageModifier());
            }
        }
    }

    @SubscribeEvent
    public static void registerEffectAddModifications(MobEffectEvent.Added event) {
        if (event.getEffectInstance().getEffect().isBeneficial() && event.getEntity().hasEffect(ModMobEffects.STUN.get())) {
            event.getEntity().removeEffect(ModMobEffects.STUN.get());
        }
    }

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        MysticcraftMod.sendRegisterDisplay("Reloadables");
        event.addListener(new BestiaryManager());
        event.addListener(new ReforgeManager());
    }

    @SubscribeEvent
    public static void modArrowEnchantments(ArrowLooseEvent event) {
        Player player = event.getEntity();
        ItemStack bow = event.getBow();
        if (bow.getItem() instanceof ModBowItem) return;
        ModBowItem.createLegolasExtraArrows(bow, player, 0);
    }

    @SubscribeEvent
    public static void registerDrawSpeed(ArrowLooseEvent event) {
        event.setCharge((int) (event.getCharge() * event.getEntity().getAttributeValue(ModAttributes.DRAW_SPEED.get()) / 100));
    }

    private static void createArrow(ItemStack itemStack, Player player, float speed, float xChange, float yChange) {
        ItemStack projectile = player.getProjectile(itemStack);
        ArrowItem arrowitem = (ArrowItem)(projectile.getItem() instanceof ArrowItem ? projectile.getItem() : Items.ARROW);
        AbstractArrow abstractarrow = arrowitem.createArrow(player.level, itemStack, player);
        abstractarrow.shootFromRotation(player, player.getXRot() + xChange, player.getYRot() + yChange, 0.0F, speed * 3.0F, 1.0F);
        if (speed == 1.0F) {
            abstractarrow.setCritArrow(true);
        }

        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemStack);
        if (j > 0) {
            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
        }

        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemStack);
        if (k > 0) {
            abstractarrow.setKnockback(k);
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemStack) > 0) {
            abstractarrow.setSecondsOnFire(100);
        }
        itemStack.hurtAndBreak(1, player, (p_253596_) -> p_253596_.broadcastBreakEvent(player.getUsedItemHand()));
        if ((player.getAbilities().instabuild || (projectile.getItem() instanceof ArrowItem && ((ArrowItem)projectile.getItem()).isInfinite(projectile, itemStack, player))) || player.getAbilities().instabuild && (itemStack.is(Items.SPECTRAL_ARROW) || itemStack.is(Items.TIPPED_ARROW))) {
            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        player.level.addFreshEntity(abstractarrow);
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
        }
        for (EquipmentSlot slot : MiscHelper.ARMOR_EQUIPMENT) {
            stack = attacked.getItemBySlot(slot);
            assert enchantments != null;
            event.setAmount(ExtendedCalculationEnchantment.runWithPriority(stack, attacker, attacked, event.getAmount(), type, false, source));
        }
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
            for (Enchantment enchantment : enchantments.keySet()) {
                if (enchantment instanceof ExtendedCalculationEnchantment modEnchantment && enchantment instanceof IToolEnchantment) {
                    modEnchantment.tryExecute(enchantments.get(enchantment), stack, attacker, attacked, event.getBlockedDamage(), type, event.getDamageSource());
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void changeAttackTarget(LivingChangeTargetEvent event) {
        LivingEntity newTarget = event.getNewTarget();
        if (newTarget != null && newTarget.isInvisible()) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void ItemBonusRegister(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        LivingEntity attacker = MiscHelper.getAttacker(source);
        MiscHelper.DamageType type = MiscHelper.getDamageType(source);
        LivingEntity killed = event.getEntity();
        if (attacker != null) {
            for (EquipmentSlot slot : MiscHelper.ARMOR_EQUIPMENT) {
                if (attacker.getItemBySlot(slot).getItem() instanceof IArmorBonusItem bonusItem) {
                    if (bonusItem.getPieceBonusForSlot(slot) != null) {
                        bonusItem.getPieceBonusForSlot(slot).onEntityKilled(killed, attacker, type);
                    }
                    if (bonusItem.getFullSetBonus() != null) {
                        bonusItem.getFullSetBonus().onEntityKilled(killed, attacker, type);
                    }
                    if (bonusItem.getExtraBonus(slot) != null) {
                        bonusItem.getExtraBonus(slot).onEntityKilled(killed, attacker, type);
                    }
                }
            }
            Reforge reforge = Reforge.getFromStack(attacker.getMainHandItem());
            if (reforge != null && reforge.getBonus() != null) {
                reforge.getBonus().onEntityKilled(killed, attacker, type);
            }
        }
    }

    private static List<AttributeModifier.Operation> getOperations(Collection<AttributeModifier> attributeModifiers) {
        ArrayList<AttributeModifier.Operation> operations = new ArrayList<>();
        for (AttributeModifier modifier : attributeModifiers) {
            operations.add(modifier.getOperation());
        }
        return operations;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void HitEffectRegister(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        CompoundTag tag = living.getPersistentData();
        if (living.hasEffect(ModMobEffects.VULNERABILITY.get())) {
            event.setAmount((float) (event.getAmount() * (1 + Objects.requireNonNull(living.getEffect(ModMobEffects.VULNERABILITY.get())).getAmplifier() * 0.05)));
        }
        if (living.hasEffect(ModMobEffects.NUMBNESS.get())) {
            event.setCanceled(true);
            tag.putFloat(NumbnessMobEffect.NUMBNESS_ID, tag.getFloat(NumbnessMobEffect.NUMBNESS_ID) + event.getAmount());
        }
        if (event.getSource().getDirectEntity() instanceof SmallFireball smallFireball) {
            if (smallFireball.getOwner() instanceof FrozenBlazeEntity) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5));
            }
        }
        if (tag.getBoolean(WarpedArmorItem.SHIELD_ID)) {
            AttributeInstance instance = living.getAttribute(ModAttributes.MANA.get());
            if (instance != null) {
                double manaValue = instance.getBaseValue();
                if (event.getAmount() < manaValue) {
                    manaValue -= event.getAmount();
                    event.setAmount(0);
                } else {
                    event.setAmount((float) (event.getAmount() - manaValue));
                    manaValue = 0;
                }
                instance.setBaseValue(manaValue);
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
        if (attacker == null) {
            return;
        }
        if (attacker.getAttribute(ModAttributes.FEROCITY.get()) != null) {
            final double ferocity = source instanceof FerociousDamageSource damageSource ? damageSource.ferocity : attacker.getAttributeValue(ModAttributes.FEROCITY.get());
            if (MathHelper.chance(ferocity / 100, attacker)) {
                MiscHelper.delayed(40, () -> {
                    float ferocity_damage = (float) (source instanceof FerociousDamageSource ferociousDamageSource ? ferociousDamageSource.damage : source.getEntity() instanceof AbstractArrow arrow ?                             arrow.getBaseDamage() : attacker.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    attacked.level.playSound(attacked, new BlockPos(MathHelper.getPosition(attacked)), SoundEvents.IRON_GOLEM_ATTACK, SoundSource.HOSTILE, 1f, 0f);
                    attacked.hurt(new FerociousDamageSource(attacker, (ferocity - 100), ferocity_damage), ferocity_damage);
                });
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void miscDamageEvents(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        LivingEntity attacker = MiscHelper.getAttacker(event.getSource());
        CompoundTag tag = attacked.getPersistentData();
        if (TagHelper.checkForIntAbove0(tag, WitherShieldSpell.DAMAGE_REDUCTION_TIME)) {
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

    private static final ArrowQueueHelper helper = new ArrowQueueHelper();
    public static final String OVERFLOW_MANA_ID = "overflowMana";


    @SubscribeEvent
    public static void entityTick(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();
        Cooldowns.tickCooldowns(living);
        BonusHelper.tickEnchantments(living);
        CompoundTag tag = living.getPersistentData();
        int i = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.BASALT_WALKER.get(), living);
        if (i > 0) {
            BasaltWalkerEnchantment.onEntityMoved(living, living.blockPosition(), i);
        }
        BonusHelper.useBonuses(living, (bonus, stack) -> bonus.onTick(stack, living.getLevel(), living));
        if (AttributeHelper.getSaveAttributeValue(ModAttributes.MANA.get(), living) != -1 && living.level.getBlockState(new BlockPos(living.getX(), living.getY(), living.getZ())).getBlock() == ModBlocks.MANA_FLUID_BLOCK.get()) {
            double overflowMana = tag.getDouble(OVERFLOW_MANA_ID) + tag.getDouble("manaRegen");
            tag.putDouble(OVERFLOW_MANA_ID, overflowMana);
            if (overflowMana >= AttributeHelper.getSaveAttributeValue(ModAttributes.MAX_MANA.get(), living)) {
                living.hurt(new DamageSource(OVERFLOW_MANA_ID).bypassInvul().bypassMagic().bypassEnchantments().bypassArmor(), Float.MAX_VALUE);
                List<LivingEntity> livings = MathHelper.getLivingAround(living, 5);
                for (LivingEntity living1 : livings) {
                    living1.hurt(new EntityDamageSource(OVERFLOW_MANA_ID, living).bypassArmor().bypassMagic().bypassEnchantments().bypassInvul(), (float) (Float.MAX_VALUE * (0.01 * Math.max(5 - living1.distanceTo(living), 0))));
                }
            }
        }
        if (living instanceof Player player) {
            MobEffectInstance stunInstance = player.getEffect(ModMobEffects.STUN.get());
            if (stunInstance != null && player.level.isClientSide) {
                int duration = stunInstance.getDuration();
                TextHelper.setHotbarDisplay(player, Component.translatable("effect.stun.timer",  MiscHelper.stabiliseDouble(String.valueOf(duration / 20.), 2)).withStyle(ChatFormatting.RED));
            }
            if (tag.contains(SpellItem.SPELL_EXECUTION_DUR)) {
                if (tag.getByte(SpellItem.SPELL_EXECUTION_DUR) < 1 || tag.getString(SpellItem.SPELL_EXE).length() >= 7) {
                    ItemStack mainHand = living.getMainHandItem();
                    if (mainHand.getItem() instanceof SpellItem spellItem) {
                        spellItem.executeSpell(tag.getString(SpellItem.SPELL_EXE), mainHand, living);
                        tag.putString(SpellItem.SPELL_EXE, "");
                    }
                    TextHelper.clearTitle(player);
                } else {
                    tag.putByte(SpellItem.SPELL_EXECUTION_DUR, (byte) (tag.getByte(SpellItem.SPELL_EXECUTION_DUR) - 1));
                }
            }
            if (InventoryHelper.hasSetInInventory(player, TieredArmorItem.ArmorTier.INFERNAL)) {
                MiscHelper.awardAchievement(player, "mysticcraft:infernal_armor");
            }
            if (!player.isOnGround()) {
                if (canJump(player) && tag.getInt(doubleJumpId) < player.getAttributeValue(ModAttributes.DOUBLE_JUMP.get())) {
                    if (((LivingEntityAccessor) player).getJumping() && ((LivingEntityAccessor) player).getNoJumpDelay() <= 0) {
                        ParticleHelper.sendAlwaysVisibleParticles(ParticleTypes.CLOUD, player.level, player.getX(), player.getY(), player.getZ(), 0.25, 0.0, 0.25, 0,0,0, 15);
                        ((LivingEntityAccessor) player).setNoJumpDelay(10); player.fallDistance = 0;
                        Vec3 targetLoc = MathHelper.setLength(player.getLookAngle().multiply(1, 0, 1), 0.75).add(0, 1, 0);
                        player.setDeltaMovement(targetLoc.x, targetLoc.y > 0 ? targetLoc.y : -targetLoc.y, targetLoc.z);
                        TagHelper.increaseIntegerTagValue(player.getPersistentData(), doubleJumpId, 1);
                    }
                }
            } else if (tag.getInt(doubleJumpId) > 0) {
                tag.putInt(doubleJumpId, 0);
            }
        }
        if (tag.contains("lastFullSet", 8)) {
            if (ModArmorItem.hadFullSet(ModArmorMaterials.SOUL_MAGE, living)) {
                net.kapitencraft.mysticcraft.misc.particle_help.ParticleHelper.clearAllHelpers(SoulMageArmorItem.helperString, living);
                tag.putString("lastFullSet", "");
            } else if (ModArmorItem.hadFullSet(ModArmorMaterials.SHADOW_ASSASSIN, living)) {
                living.setInvisible(false);
                living.getPersistentData().putBoolean("Invisible", false);
            }
        }
        if (living instanceof Mob mob) {
            if (mob.getTarget() != null && mob.getTarget().isInvisible()) {
                mob.setTarget(null);
            }
        }
    }


    @SubscribeEvent
    public static void endermanEvent(EnderManAngerEvent event) {
        Player player = event.getPlayer();
        if (player.getItemBySlot(EquipmentSlot.HEAD).getEnchantmentLevel(ModEnchantments.ENDER_FRIEND.get()) > 0) {
            event.setCanceled(true);
        }
    }

    private static boolean canJump(Player player) {
        return !player.isOnGround() && !(player.isPassenger() || player.getAbilities().flying) && !(player.isInWater() || player.isInLava());
    }

    @SubscribeEvent
    public static void serverTick(TickEvent.LevelTickEvent event) {
        if (event.level instanceof ServerLevel serverLevel) {
            for (Entity entity : serverLevel.getEntities().getAll()) {
                net.kapitencraft.mysticcraft.misc.particle_help.ParticleHelper.tickHelper(entity);
            }
            helper.update((uuid)-> {
                Arrow arrow = (Arrow) serverLevel.getEntity(uuid);
                if (arrow != null) {
                    CompoundTag arrowTag = arrow.getPersistentData();
                    ModBowEnchantment.loadFromTag(null, arrowTag, ModBowEnchantment.ExecuteType.TICK, 0, arrow);
                } else {
                    helper.remove(uuid);
                }
            });
        }
    }

    @SubscribeEvent
    public static void arrowRegisterEvent(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            if (arrow.getOwner() instanceof LivingEntity living) {
                ItemStack bow = living.getMainHandItem();
                CompoundTag arrowTag = arrow.getPersistentData();
                for (Enchantment enchantment : bow.getAllEnchantments().keySet()) {
                    if (enchantment instanceof ModBowEnchantment bowEnchantment) {
                        arrowTag.put(bowEnchantment.getTagName(), bowEnchantment.write(bow.getEnchantmentLevel(enchantment), bow, living, arrow));
                        if (bowEnchantment.shouldTick()) helper.add(arrow.getUUID());
                    }
                    if (enchantment instanceof OverloadEnchantment) {
                        arrowTag.putInt("OverloadEnchant", bow.getEnchantmentLevel(ModEnchantments.OVERLOAD.get()));
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void healthRegenRegister(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        HealingHelper.HealReason reason = HealingHelper.getAndRemoveReason(living);
        if (reason == HealingHelper.HealReason.NATURAL && living.getAttribute(ModAttributes.HEALTH_REGEN.get()) != null) {
            double health_regen = living.getAttributeValue(ModAttributes.HEALTH_REGEN.get());
            event.setAmount(event.getAmount() * (1 + (float) health_regen / 100));
        }
        if (living.getAttribute(ModAttributes.VITALITY.get()) != null) {
            double vitality = living.getAttributeValue(ModAttributes.VITALITY.get());
            event.setAmount(event.getAmount() * (1 + (float) vitality / 100));
        }
        if (living instanceof Player player) event.setAmount(HealthMendingEnchantment.repairPlayerItems(player, event.getAmount()));
        if (event.getAmount() > 0) MiscHelper.createDamageIndicator(living, event.getAmount(), "heal");
    }

    //@SubscribeEvent(priority = EventPriority.LOWEST)
    public static void telekinesis2Register(LivingDropsEvent event) {
        Collection<ItemEntity> entities = event.getDrops();
        if (event.getSource().getEntity() instanceof Player attacker) {
            for (ItemEntity entity : entities) {
                ItemStack stack = entity.getItem();
                //Telekinesis Register
                if (attacker.getMainHandItem().getEnchantmentLevel(ModEnchantments.TELEKINESIS.get()) > 0) {
                    attacker.getInventory().add(stack);
                    entity.kill();
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void telekinesisXpRegister(LivingExperienceDropEvent event) {
        Player attacker = event.getAttackingPlayer();
        if (attacker != null) {
            ItemStack mainHand = attacker.getMainHandItem();
            if (mainHand.getEnchantmentLevel(ModEnchantments.TELEKINESIS.get()) > 0) {
                attacker.giveExperiencePoints(event.getDroppedExperience());
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void rareDropsRegister(LivingDropsEvent event) {
        LivingEntity attacker = MiscHelper.getAttacker(event.getSource());
        LivingEntity attacked = event.getEntity();
        Collection<ItemEntity> drops = event.getDrops();
        Vec3 pos = new Vec3(attacked.getX(), attacked.getY(), attacked.getZ());
        if (attacker == null) return;
        ItemStack weapon = attacker.getMainHandItem();
        if (weapon.getEnchantmentLevel(ModEnchantments.SCAVENGER.get()) > 0) {
            float chance = weapon.getEnchantmentLevel(ModEnchantments.SCAVENGER.get()) * 0.2f;
            RNGDropHelper.addToDrops(new ItemStack(Items.EMERALD), chance, attacker, pos, drops);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void telekinesisRegister(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer(); ItemStack mainHandItem = player.getMainHandItem(); BlockState state = event.getState();
        Block block = state.getBlock(); Level level = player.level; final BlockPos pos = event.getPos();

        ServerLevel serverLevel = level instanceof ServerLevel serverLevel1 ? serverLevel1 : null;
        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (serverLevel == null) return;
        LootContext.Builder context = (new LootContext.Builder(serverLevel)).withRandom(level.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)).withParameter(LootContextParams.TOOL, mainHandItem).withOptionalParameter(LootContextParams.THIS_ENTITY, serverPlayer);
        if (block instanceof CropBlock || block instanceof NetherWartBlock) {
            IntegerProperty ageProperty = block instanceof CropBlock cropBlock ? cropBlock.getAgeProperty() : BlockStateProperties.AGE_3;
            if (mainHandItem.getEnchantmentLevel(ModEnchantments.DELICATE.get()) > 0) {
                if (state.getValue(ageProperty) < MathHelper.getHighest(ageProperty.getPossibleValues())) {
                    event.setCanceled(true);
                    return;
                }
            }
            if (mainHandItem.getEnchantmentLevel(ModEnchantments.REPLENISH.get()) > 0) {
                BlockState newState = block.defaultBlockState();
                newState.setValue(ageProperty, 0);
                event.setCanceled(true);
                Block.dropResources(state, context);
                level.setBlock(pos, newState, 3);
            }
        }
        if (mainHandItem.getEnchantmentLevel(ModEnchantments.LUMBERJACK.get()) > 0 && state.is(BlockTags.LOGS)) {
            MiscHelper.mineMultiple(pos, serverPlayer, block, pos1 -> {}, state1 -> true, pos1 -> false);
        }
        if (mainHandItem.getEnchantmentLevel(ModEnchantments.VEIN_MINER.get()) > 0 && state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            Reference<Integer> brokenBlocks = Reference.of(-1);
            final int maximalBrokenBlocks = mainHandItem.getEnchantmentLevel(ModEnchantments.VEIN_MINER.get());
            MiscHelper.mineMultiple(pos, serverPlayer, block, pos1 -> MathHelper.add(brokenBlocks::getValue, brokenBlocks::setValue, 1), state1 -> true, pos1 -> brokenBlocks.getValue() > maximalBrokenBlocks);
        }
        if (mainHandItem.getEnchantmentLevel(ModEnchantments.TELEKINESIS.get()) > 0) {
            player.giveExperiencePoints(event.getExpToDrop());
            event.setExpToDrop(0);
        }
    }



    @SubscribeEvent
    public static void registerVillagerProfession(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        if (event.getType() == ModVillagers.GUILD_MASTER.getProfession().get()) {
            for (GuildUpgrade upgrade : GuildUpgrades.values()) {
                trades.put(upgrade.getRarity().getProfessionLevel(),
                        List.of(new BasicItemListing(getEmeraldCost(upgrade.defaultCost()),
                                upgrade.mainCostItem(),
                                new ItemStack(ModItems.GUILD_UPGRADES.get(upgrade).get()),
                                10, 2, 1.6f)));
            }
        }
    }

    private static ItemStack getEmeraldCost(int emeralds) {
        if (emeralds <= 64) {
            return new ItemStack(Items.EMERALD).copyWithCount(emeralds);
        } else {
            int emeraldBlocks = emeralds / 9;
            return new ItemStack(Items.EMERALD_BLOCK).copyWithCount(emeraldBlocks);
        }
    }



    @SubscribeEvent
    public static void entityDeathEvents(LivingDeathEvent event) {
        LivingEntity toDie = event.getEntity();
        if (toDie instanceof Player player) {
            List<ItemStack> totems = InventoryHelper.getByFilter(player, stack -> stack.getItem() instanceof ModTotemItem);
            for (ItemStack stack : totems) {
                ModTotemItem totemItem = (ModTotemItem) stack.getItem();
                if (totemItem.onUse(player, event.getSource())) {
                    event.setCanceled(true);
                    Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
                    stack.shrink(1);
                    break;
                }
            }
        }
    }
}