package net.kapitencraft.mysticcraft.misc;

import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.BasaltWalkerEnchantment;
import net.kapitencraft.mysticcraft.enchantments.VenomousEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IToolEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModBowEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.init.*;
import net.kapitencraft.mysticcraft.item.RNGDropHelper;
import net.kapitencraft.mysticcraft.item.armor.ModArmorItem;
import net.kapitencraft.mysticcraft.item.armor.ModArmorMaterials;
import net.kapitencraft.mysticcraft.item.armor.SoulMageArmorItem;
import net.kapitencraft.mysticcraft.item.armor.TieredArmorItem;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.weapon.melee.sword.ManaSteelSwordItem;
import net.kapitencraft.mysticcraft.item.weapon.ranged.bow.ModdedBows;
import net.kapitencraft.mysticcraft.item.weapon.ranged.bow.ShortBowItem;
import net.kapitencraft.mysticcraft.misc.damage_source.FerociousDamageSource;
import net.kapitencraft.mysticcraft.misc.damage_source.IAbilitySource;
import net.kapitencraft.mysticcraft.misc.guilds.Guild;
import net.kapitencraft.mysticcraft.misc.guilds.GuildHandler;
import net.kapitencraft.mysticcraft.misc.particle_help.ParticleHelper;
import net.kapitencraft.mysticcraft.misc.utils.*;
import net.kapitencraft.mysticcraft.mixin.LivingEntityAccessor;
import net.kapitencraft.mysticcraft.mob_effects.NumbnessMobEffect;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
    public static void DamageAttributeRegister(LivingHurtEvent event) {
        @Nullable LivingEntity attacker = MiscUtils.getAttacker(event.getSource());
        if (attacker == null) { return; }
        if (event.getSource() instanceof IAbilitySource abilitySource) {
            double intel = attacker.getAttributeValue(ModAttributes.INTELLIGENCE.get());
            double ability_damage = attacker.getAttributeValue(ModAttributes.ABILITY_DAMAGE.get());
            event.setAmount((float) (event.getAmount() * (1 + (intel / 100) * abilitySource.getScaling()) * (1 + (ability_damage / 100))));
        } else if (AttributeUtils.getSaveAttributeValue(ModAttributes.STRENGTH.get(), attacker) != -1) {
            double Strength = AttributeUtils.getSaveAttributeValue(ModAttributes.STRENGTH.get(), attacker);
            event.setAmount((float) (event.getAmount() * (1 + Strength / 100)));
        }
        double double_jump = AttributeUtils.getSaveAttributeValue(ModAttributes.DOUBLE_JUMP.get(), attacker);
        if (double_jump > 0 && attacker.getPersistentData().getInt(doubleJumpId) > 0 && event.getSource().getMsgId().equals("fall")) {
            event.setAmount((float) (event.getAmount() * (1 - double_jump * 0.02)));
        }
        LivingEntity attacked = event.getEntity();
        if (AttributeUtils.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), attacker) != -1) {
            double armorShredder = (int) AttributeUtils.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), attacker);
            for (int i = 0; i < 4; i++) {
                ItemStack stack = attacked.getItemBySlot(MiscUtils.ARMOR_EQUIPMENT[i]);
                stack.hurt((int) armorShredder / 3, attacked.level.getRandom(), attacker instanceof ServerPlayer serverPlayer ? serverPlayer : null);
            }
        }
        if (AttributeUtils.getSaveAttributeValue(ModAttributes.LIVE_STEAL.get(), attacker) != -1) {
            double live_steel = (int) AttributeUtils.getSaveAttributeValue(ModAttributes.LIVE_STEAL.get(), attacker);
            attacker.heal(Math.min((float) live_steel, event.getAmount()));
        }
    }

    @SubscribeEvent
    public static void modArrowEnchantments(ArrowLooseEvent event) {
        Player player = event.getEntity();
        ItemStack bow = event.getBow();
        if (bow.getItem() instanceof ModdedBows) return;
        event.setCharge((int) (event.getCharge() * (1 + player.getAttributeValue(ModAttributes.DRAW_SPEED.get()) * 0.01)));
        if (bow.getEnchantmentLevel(ModEnchantments.LEGOLAS_EMULATION.get()) > 0) {
            int legolasLevel = bow.getEnchantmentLevel(ModEnchantments.LEGOLAS_EMULATION.get());
            float movementSpeedValue = BowItem.getPowerForTime(event.getCharge());
            for (int i = 0; i < legolasLevel; i++) {
                if (Math.random() <= legolasLevel * 0.1) {
                    float yChange = (float) (Math.random() *  (5 - legolasLevel) - (5 - legolasLevel) / 2);
                    float xChange = (float) (Math.random() * (5 - legolasLevel) - (5 - legolasLevel) / 2);
                    createArrow(bow, player, movementSpeedValue, xChange, yChange);
                }
            }
        }
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
    public static void DamageEnchantRegister(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        if (event.getSource().getEntity() instanceof Arrow arrow) {
            CompoundTag tag = arrow.getPersistentData();
            event.setAmount(ModBowEnchantment.loadFromTag(attacked, tag, ModBowEnchantment.ExecuteType.HIT, event.getAmount(), arrow));
            return;
        }
        DamageSource source = event.getSource();
        @Nullable LivingEntity attacker = MiscUtils.getAttacker(source);
        if (attacker == null) { return; }
        ItemStack stack = attacker.getMainHandItem();
        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        MiscUtils.DamageType type = MiscUtils.getDamageType(event.getSource());
        if (enchantments != null) {
            event.setAmount(ExtendedCalculationEnchantment.runWithPriority(stack, attacker, attacked, event.getAmount(), type, true, source));
        }
        for (EquipmentSlot slot : MiscUtils.ARMOR_EQUIPMENT) {
            stack = attacked.getItemBySlot(slot);
            assert enchantments != null;
            event.setAmount(ExtendedCalculationEnchantment.runWithPriority(stack, attacker, attacked, event.getAmount(), type, false, source));
        }
    }

    @SubscribeEvent
    public static void shieldBlockEnchantments(ShieldBlockEvent event) {
        LivingEntity attacked = event.getEntity();
        @Nullable LivingEntity attacker = MiscUtils.getAttacker(event.getDamageSource());
        if (attacker == null) { return; }
        ItemStack stack = attacker.getUseItem();
        MiscUtils.DamageType type = MiscUtils.getDamageType(event.getDamageSource());
        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        if (enchantments != null && !enchantments.isEmpty()) {
            MysticcraftMod.sendInfo("ea");
            for (Enchantment enchantment : enchantments.keySet()) {
                if (enchantment instanceof ExtendedCalculationEnchantment modEnchantment && enchantment instanceof IToolEnchantment) {
                    modEnchantment.tryExecute(enchantments.get(enchantment), stack, attacker, attacked, event.getBlockedDamage(), type, event.getDamageSource());
                }
            }
        }
    }

    @SubscribeEvent
    public static void ItemBonusRegister(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        LivingEntity attacker = MiscUtils.getAttacker(source);
        if (attacker != null) {
            for (EquipmentSlot slot : MiscUtils.ARMOR_EQUIPMENT) {
                MiscUtils.DamageType type = MiscUtils.getDamageType(source);
                LivingEntity killed = event.getEntity();
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
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void StatModEnchantmentRegister(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        EquipmentSlot slot = event.getSlotType();
        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        for (Enchantment enchantment : enchantments.keySet()) {
            if (enchantment instanceof StatBoostEnchantment statBoostEnchantment && statBoostEnchantment.hasModifiersForThatSlot(slot)) {
                Multimap<Attribute, AttributeModifier> modifiableMap = event.getModifiers();
                Multimap<Attribute, AttributeModifier> modifiersMultimap = statBoostEnchantment.getModifiers(enchantments.get(enchantment), stack, slot);
                if (modifiersMultimap != null) {
                    for (Attribute attribute : modifiersMultimap.keys()) {
                        if (modifiableMap.keys().contains(attribute)) {
                            for (AttributeModifier modifierToAdd : modifiersMultimap.get(attribute)) {
                                if (getOperations(modifiableMap.get(attribute)).contains(modifierToAdd.getOperation())) {
                                    List<AttributeModifier> originalModifiers = new ArrayList<>(modifiableMap.get(attribute));
                                    AttributeModifier originalModifier = originalModifiers.get(getOperations(modifiableMap.get(attribute)).indexOf(modifierToAdd.getOperation()));
                                    double amount = modifierToAdd.getAmount();
                                    event.removeModifier(attribute, modifierToAdd);
                                    event.addModifier(attribute, new AttributeModifier(modifierToAdd.getId(), modifierToAdd.getName(), amount + originalModifier.getAmount(), modifierToAdd.getOperation()));
                                } else {
                                    event.addModifier(attribute, modifierToAdd);
                                }
                            }
                        } else {
                            for (AttributeModifier modifier : modifiersMultimap.get(attribute)) {
                                event.addModifier(attribute, modifier);
                            }
                        }
                    }
                }
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
    }

    @SubscribeEvent
    public static void CritDamageRegister(CriticalHitEvent event) {
        Player attacker = event.getEntity();
        event.setDamageModifier((float) (1 + attacker.getAttributeValue(ModAttributes.CRIT_DAMAGE.get()) / 100));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void FerocityRegister(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        LivingEntity attacker = MiscUtils.getAttacker(event.getSource());
        if (attacker == null) {
            return;
        }
        if (attacker.getAttribute(ModAttributes.FEROCITY.get()) != null) {
            final double ferocity = event.getSource() instanceof FerociousDamageSource damageSource ? damageSource.ferocity : attacker.getAttributeValue(ModAttributes.FEROCITY.get());
            if (ferocity >= (Math.random() * 100)) {
                MiscUtils.delayed(40, () -> {
                    float ferocity_damage = (float) (event.getSource() instanceof FerociousDamageSource ferociousDamageSource ? ferociousDamageSource.damage : event.getSource().getEntity() instanceof AbstractArrow arrow ? arrow.getBaseDamage() : event.getSource().getEntity() instanceof LivingEntity living ? living.getAttributeValue(Attributes.ATTACK_DAMAGE) : 0.0f);
                    attacked.hurt(new FerociousDamageSource(attacker, (ferocity - 100), ferocity_damage), ferocity_damage);
                });
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void miscDamageEvents(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        boolean dodge = false;
        double Dodge = AttributeUtils.getSaveAttributeValue(ModAttributes.DODGE.get(), attacked);
        LivingEntity attacker = MiscUtils.getAttacker(event.getSource());
        DamageSource source = event.getSource();
        CompoundTag tag = attacked.getPersistentData();
        if (TagUtils.checkForIntAbove0(tag, WitherShieldSpell.DAMAGE_REDUCTION_TIME)) {
            event.setAmount(event.getAmount() * 0.9f);
        }
        if (attacker != null) {
            ItemStack mainHand = attacker.getMainHandItem();
            if (mainHand.getItem() instanceof ManaSteelSwordItem) {
                attacker.heal(2f);
            }
        }
        if (Dodge > 0) {
            if (Math.random() > Dodge / 100 && ((!source.isBypassArmor() && !source.isFall() && !source.isFire()) || source == DamageSource.STALAGMITE)) {
                dodge = true;
                event.setAmount(0);
            }
        }
        MiscUtils.createDamageIndicator(attacked, event.getAmount(), dodge ? "dodge" : source.msgId);
    }

    private static final ArrowQueueHelper helper = new ArrowQueueHelper();
    public static final String OVERFLOW_MANA_ID = "overflowMana";

    @SubscribeEvent
    public static void entityTick(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();
        CompoundTag tag = living.getPersistentData();
        if (living instanceof ArmorStand) {
            if (tag.getBoolean("isDamageIndicator")) {
                TagUtils.increaseIntegerTagValue(tag, MiscUtils.TIMER_ID, 1);
                if (tag.getInt(MiscUtils.TIMER_ID) >= 35) {
                    living.kill();
                }
            }
        }
        int i = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.BASALT_WALKER.get(), living);
        if (i > 0) {
            BasaltWalkerEnchantment.onEntityMoved(living, living.blockPosition(), i);
        }
        final String timer = VenomousEnchantment.TIMER_ID;
        if (tag.contains(timer)) {
            if (tag.getInt(timer) <= 0) {
                AttributeInstance speed = living.getAttribute(Attributes.MOVEMENT_SPEED);
                assert speed != null;
                speed.removeModifier(VenomousEnchantment.ID);
            } else {
                TagUtils.increaseIntegerTagValue(tag, timer, -1);
            }
        }
        if (AttributeUtils.getSaveAttributeValue(ModAttributes.MANA.get(), living) != -1 && living.level.getBlockState(new BlockPos(living.getX(), living.getY(), living.getZ())).getBlock() == ModBlocks.MANA_FLUID_BLOCK.get()) {
            double overflowMana = tag.getDouble(OVERFLOW_MANA_ID) + tag.getDouble("manaRegen");
            tag.putDouble(OVERFLOW_MANA_ID, overflowMana);
            if (overflowMana >= living.getAttributeValue(ModAttributes.MAX_MANA.get())) {
                living.hurt(new DamageSource(OVERFLOW_MANA_ID).bypassInvul().bypassMagic().bypassEnchantments().bypassArmor(), Float.MAX_VALUE);
                List<LivingEntity> livings = living.level.getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(5));
                for (LivingEntity living1 : livings) {
                    living1.hurt(new EntityDamageSource(OVERFLOW_MANA_ID, living).bypassArmor().bypassMagic().bypassEnchantments().bypassInvul(), (float) (Float.MAX_VALUE * (0.01 * Math.max(1 - living1.distanceTo(living), 0))));
                }
            }
        }
        if (living instanceof Player player) {
            if (tag.contains(SpellItem.SPELL_EXECUTION_DUR)) {
                if (tag.getByte(SpellItem.SPELL_EXECUTION_DUR) < 1 || tag.getString(SpellItem.SPELL_EXE).length() >= 7) {
                    ItemStack mainHand = living.getMainHandItem();
                    if (mainHand.getItem() instanceof SpellItem spellItem) {
                        spellItem.executeSpell(tag.getString(SpellItem.SPELL_EXE), mainHand, living);
                        tag.putString(SpellItem.SPELL_EXE, "");
                    }
                    TextUtils.clearTitle(player);
                } else {
                    tag.putByte(SpellItem.SPELL_EXECUTION_DUR, (byte) (tag.getByte(SpellItem.SPELL_EXECUTION_DUR) - 1));
                }
            }
            if (InventoryUtils.hasSetInInventory(player, TieredArmorItem.ArmorTier.INFERNAL)) {
                MiscUtils.awardAchievement(player, "mysticcraft:infernal_armor");
            }
            if (!player.isOnGround()) {
                if (canJump(player) && tag.getInt(doubleJumpId) < player.getAttributeValue(ModAttributes.DOUBLE_JUMP.get())) {
                    if (((LivingEntityAccessor) player).getJumping() && ((LivingEntityAccessor) player).getNoJumpDelay() <= 0) {
                        ParticleUtils.sendAlwaysVisibleParticles(ParticleTypes.CLOUD, player.level, player.getX(), player.getY(), player.getZ(), 0.25, 0.0, 0.25, 0,0,0, 15);
                        ((LivingEntityAccessor) player).setNoJumpDelay(10); player.fallDistance = 0;
                        Vec3 targetLoc = MathUtils.setLength(player.getLookAngle().multiply(1, 0, 1), 0.75).add(0, 1, 0);
                        player.setDeltaMovement(targetLoc.x, targetLoc.y > 0 ? targetLoc.y : -targetLoc.y, targetLoc.z);
                        TagUtils.increaseIntegerTagValue(player.getPersistentData(), doubleJumpId, 1);
                    }
                }
            } else if (tag.getInt(doubleJumpId) > 0) {
                tag.putInt(doubleJumpId, 0);
            }
            if (tag.contains("InvuTime", 3)) {
                if (TagUtils.increaseIntegerTagValue(tag, "InvuTime", -1) <= 0) {
                    player.setInvulnerable(false);
                }
            }
        }
        if (!ModArmorItem.isFullSetActive(living, ModArmorMaterials.SOUL_MAGE) && tag.getString("lastFullSet").equals(ModArmorMaterials.SOUL_MAGE.getName())) {
            ParticleHelper.clearAllHelpers(SoulMageArmorItem.helperString, living);
            tag.putString("lastFullSet", "");
        }
        if (TagUtils.checkForIntAbove0(tag, WitherShieldSpell.DAMAGE_REDUCTION_TIME) && tag.getFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID) > 0) {
            TagUtils.increaseIntegerTagValue(tag, WitherShieldSpell.DAMAGE_REDUCTION_TIME, -1);
            float absorption = tag.getFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
            if (tag.getInt(WitherShieldSpell.DAMAGE_REDUCTION_TIME) <= 0) {
                living.heal(absorption / 2);
                living.setAbsorptionAmount(living.getAbsorptionAmount() - absorption);
                tag.putFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID, 0);
            }
        }
    }

    private static boolean canJump(Player player) {
        return !player.isOnGround() && !(player.isPassenger() || player.getAbilities().flying) && !(player.isInWater() || player.isInLava());
    }

    @SubscribeEvent
    public static void serverTick(TickEvent.LevelTickEvent event) {
        if (event.level instanceof ServerLevel serverLevel) {
            for (Entity entity : serverLevel.getEntities().getAll()) {
                ParticleHelper.tickHelper(entity);
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
                }
            }
        }
    }
    @SubscribeEvent
    public static void descriptionRegister(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> toolTip = event.getToolTip();
        Player player = event.getEntity();
        Item item = stack.getItem();
        final Component SEARCHED = Component.literal(FormattingCodes.GRAY + "Mana-Cost: " + FormattingCodes.DARK_RED);
        final Component APS_SEARCHED = Component.literal("Shot Cooldown: ").withStyle(ChatFormatting.GREEN);
        if (player != null) {
            if (toolTip.contains(SEARCHED) && item instanceof SpellItem spellItem) {
                if (spellItem.getSpellSlotAmount() > 1) {
                    for (int i = 0; i < toolTip.size() - 1; i++) {
                        Component component = toolTip.get(i);
                        if (component.contains(SEARCHED)) {
                            MutableComponent mutable = (MutableComponent) component;
                            Component pattern = toolTip.get(i + 1);
                            String patternString = pattern.getString();
                            Spells spell = Spells.get(Spells.getStringForPattern(patternString, true));
                            if (spell != null) {
                                AttributeInstance instance = player.getAttribute(ModAttributes.MANA_COST.get());
                                mutable.append(FormattingCodes.DARK_RED + AttributeUtils.getAttributeValue(instance, spell.MANA_COST));
                            }
                        }
                    }
                } else {
                    Spell spell = spellItem.getActiveSpell();
                    Component component = toolTip.get(toolTip.indexOf(SEARCHED));
                    if (component instanceof MutableComponent mutable) {
                        AttributeInstance instance = player.getAttribute(ModAttributes.MANA_COST.get());
                        if (instance != null) {
                            mutable.append(FormattingCodes.DARK_RED + AttributeUtils.getAttributeValue(instance, spell.getDefaultManaCost()));
                        }
                    }
                }
            }
            if (toolTip.contains(APS_SEARCHED) && item instanceof ShortBowItem shortBowItem) {
                Component component = toolTip.get(toolTip.indexOf(APS_SEARCHED));
                if (component instanceof MutableComponent mutableComponent) {
                    mutableComponent.append(shortBowItem.createCooldown(player) + "s");
                }
            }
            if (item instanceof BannerItem && false) {
                Guild guild = GuildHandler.getInstance().getGuildForBanner(stack);
                if (guild != null) {
                    toolTip.remove(0);
                    toolTip.add(0, Component.translatable("guild.banner.name", guild.getName()));
                }
            }
        }
        if (item instanceof IGemstoneApplicable gemstoneApplicable) {
            gemstoneApplicable.addModInfo(stack, toolTip);
            toolTip.add(Component.literal(""));
        }
        if (!(item instanceof IGuiHelper)) {
            Rarity rarity = item.getRarity(stack);
            boolean flag = rarity != MiscUtils.getItemRarity(stack.getItem());
            String RarityMod = FormattingCodes.OBFUSCATED + "A" + FormattingCodes.RESET;
            toolTip.add(Component.literal(""));
            toolTip.add(Component.literal((flag ? RarityMod + " " : "") + rarity + " " + TextUtils.getNameModifier(stack) + (flag ? " " + RarityMod : "")).withStyle(rarity.getStyleModifier()).withStyle(ChatFormatting.BOLD));
        }
    }

    @SubscribeEvent
    public static void healthRegenRegister(LivingHealEvent event) {
        if (event.getEntity().getAttribute(ModAttributes.HEALTH_REGEN.get()) != null) {
            double health_regen = event.getEntity().getAttributeValue(ModAttributes.HEALTH_REGEN.get());
            event.setAmount(event.getAmount() * (1 + (float) health_regen / 100));
        }
        if (event.getAmount() > 0) MiscUtils.createDamageIndicator(event.getEntity(), event.getAmount(), "heal");
    }


    @SubscribeEvent
    public static void gemstoneAttributeModifications(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (item instanceof IGemstoneApplicable applicable) {
            HashMap<Attribute, AttributeModifier> modifierHashMap = applicable.getAttributeModifiers(stack);
            if (item instanceof ArmorItem armorItem) {
                if (event.getSlotType() == armorItem.getSlot() && modifierHashMap != null) {
                    for (Attribute attribute : modifierHashMap.keySet()) {
                        event.addModifier(attribute, modifierHashMap.get(attribute));
                    }
                }
            } else if (item instanceof TieredItem && event.getSlotType() == EquipmentSlot.MAINHAND) {
                if (applicable.getAttributesModified(stack) != null) {
                    for (Attribute attribute : modifierHashMap.keySet()) {
                        event.addModifier(attribute, modifierHashMap.get(attribute));
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void telekinesis2Register(LivingDropsEvent event) {
        Collection<ItemEntity> entities = event.getDrops();
        if (event.getSource().getEntity() instanceof Player attacker) {
            for (ItemEntity entity : entities) {
                ItemStack stack = entity.getItem();
                //Telekinesis Register
                if (attacker.getMainHandItem().getEnchantmentLevel(ModEnchantments.TELEKINESIS.get()) > 0) {
                    attacker.getInventory().add(stack);
                    entity.kill();
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
        LivingEntity attacker = MiscUtils.getAttacker(event.getSource());
        LivingEntity attacked = event.getEntity();
        Vec3 pos = new Vec3(attacked.getX(), attacked.getY(), attacked.getZ());
        assert attacker != null;
        if (event.getEntity() instanceof Blaze && !(event.getEntity() instanceof FrozenBlazeEntity)) {
            ItemStack toDrop = new ItemStack(ModItems.HEART_OF_THE_NETHER.get());
            RNGDropHelper.calculateAndDrop(toDrop, 0.000001f, attacker, pos);
        }
        if (event.getEntity() instanceof WitherBoss) {
            List<Item> possibleDrops = List.of(ModItems.VALKYRIE.get(), ModItems.ASTREA.get(), ModItems.HYPERION.get(), ModItems.SCYLLA.get());
            RNGDropHelper.calculateAndDrop(new ItemStack(possibleDrops.get((int) (Math.random() * possibleDrops.size()))), 0.001f, attacker, pos);
        }
    }

    @SubscribeEvent
    public static void telekinesisRegister(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();
        BlockState state = event.getState();
        Block block = state.getBlock();
        Level level = player.level;
        BlockPos pos = event.getPos();
        ServerLevel serverLevel = level instanceof ServerLevel serverLevel1 ? serverLevel1 : null;
        assert serverLevel != null;
        LootContext.Builder context = (new LootContext.Builder(serverLevel)).withRandom(serverLevel.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)).withParameter(LootContextParams.TOOL, mainHandItem).withOptionalParameter(LootContextParams.THIS_ENTITY, player);
        boolean hasTelekinesis = mainHandItem.getEnchantmentLevel(ModEnchantments.TELEKINESIS.get()) > 0;
        boolean hasReplenish = mainHandItem.getEnchantmentLevel(ModEnchantments.REPLENISH.get()) > 0;
        List<ItemStack> drops = state.getDrops(context);
        if (block == Blocks.CRIMSON_NYLIUM) {
            drops.add(RNGDropHelper.dontDrop(ModItems.CRIMSONITE_CLUSTER.get(), 5, player, 0.00005f));
        }
        if (hasReplenish) {
            MiscUtils.shrinkDrops(drops, Items.WHEAT_SEEDS, 1);
        }
        if (hasTelekinesis) {
            for (ItemStack stack : drops) {
                player.getInventory().add(stack);
            }
            event.setCanceled(true);
            serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
        if (hasReplenish && block instanceof CropBlock cropBlock) {
            state.setValue(cropBlock.getAgeProperty(), 0);
            if (!hasTelekinesis) {
                for (ItemStack stack : drops) {
                    level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
                }
            }
            BlockState newState = cropBlock.getStateForAge(0);
            event.setCanceled(true);
            level.setBlock(pos, newState, 3);
        }
        if (mainHandItem.getEnchantmentLevel(ModEnchantments.LUMBERJACK.get()) > 0 && state.is(BlockTags.LOGS)) {
            BlockPos extraPos = pos;
            List<BlockPos> iterator = new ArrayList<>(1);
            iterator.add(extraPos);
            while (iterator.size() > 0 && iterator.get(0) != null) {
                extraPos = iterator.get(0);
                iterator.remove(0);
                for (BlockPos blockPos : Values()) {
                    if (!(blockPos.getX() != 0 && blockPos.getY() != 0 && blockPos.getZ() != 0)) {
                        BlockPos pos1 = new BlockPos(blockPos.getX() + extraPos.getX(), blockPos.getY() + extraPos.getY(), blockPos.getZ() + extraPos.getZ());
                        if (block == player.level.getBlockState(pos1).getBlock()) {
                            breakBlock(pos1, player.level, context);
                            iterator.add(pos1);
                        }
                    }
                }
            }
        }
        if (mainHandItem.getEnchantmentLevel(ModEnchantments.VEIN_MINER.get()) > 0 && state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            int brokenBlocks = -1;
            final int maximalBrokenBlocks = mainHandItem.getEnchantmentLevel(ModEnchantments.VEIN_MINER.get());
            List<BlockPos> iterator = new ArrayList<>(1);
            iterator.add(pos);
            while (iterator.size() > 0 && iterator.get(0) != null) {
                pos = iterator.get(0);
                iterator.remove(0);
                for (BlockPos blockPos : Values()) {
                    if (!(blockPos.getX() != 0 && blockPos.getY() != 0 && blockPos.getZ() != 0)) {
                        BlockPos pos1 = new BlockPos(blockPos.getX() + pos.getX(), blockPos.getY() + pos.getY(), blockPos.getZ() + pos.getZ());
                        if (brokenBlocks >= maximalBrokenBlocks) break;
                        if (block == player.level.getBlockState(pos1).getBlock()) {
                            breakBlock(pos1, player.level, context);
                            brokenBlocks++;
                            iterator.add(pos1);
                        }
                    }
                }
                if (brokenBlocks >= maximalBrokenBlocks) break;
            }
        }
    }

    public static BlockPos[] Values() {
        BlockPos[] returnValue = new BlockPos[27];
        int x = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    returnValue[x] = new BlockPos(i, j, k);
                    x++;
                }
            }
        }
        return returnValue;
    }

    private static void breakBlock(BlockPos pos, Level level, LootContext.Builder builder) {
        BlockState state = level.getBlockState(pos);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        for (ItemStack stack : state.getDrops(builder)) {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
        }
    }

    @SubscribeEvent
    public static void registerVillagerProfession(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        //if (event.getType() == ModVillagers.GEMSTONE_MAKER.getProfession().get()) {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void manaRegister(RenderGuiOverlayEvent.Pre event) {
        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();
        int posX = w / 2;
        int posY = h / 2;
            Player entity = Minecraft.getInstance().player;
        if (entity != null) {
            String builder = FormattingCodes.BLUE + MathUtils.round(entity.getAttributeValue(ModAttributes.MANA.get()), 1) + " [+" + MathUtils.round(entity.getPersistentData().getDouble(MiscRegister.OVERFLOW_MANA_ID), 1) + " Overflow] " + FormattingCodes.RESET + " / " + FormattingCodes.DARK_BLUE + entity.getAttributeValue(ModAttributes.MAX_MANA.get()) + " (+" + MathUtils.round(entity.getPersistentData().getDouble("manaRegen") * 20, 2) + "/s)";
            Minecraft.getInstance().font.draw(event.getPoseStack(), builder, posX - 203, posY - 116, -1);
        }
    }

    @SubscribeEvent
    public static void entityDeathEvents(LivingDeathEvent event) {
        LivingEntity toDie = event.getEntity();
        Level level = toDie.level;
        if (toDie instanceof Player player) {
            if (InventoryUtils.hasPlayerStackInInventory(player, ModItems.MOD_DEBUG_STICK.get())) {
                event.setCanceled(true);
                toDie.setHealth(2);
                toDie.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 2));
                Minecraft.getInstance().gameRenderer.displayItemActivation(player.getMainHandItem());
                if (level instanceof ServerLevel serverLevel) {
                    MiscUtils.awardAchievement(player, "minecraft:adventure/totem_of_undying");
                    toDie.setInvulnerable(true);
                    toDie.getPersistentData().putInt("InvuTime", 20);
                    serverLevel.explode(null, toDie.getX(), toDie.getY(), toDie.getZ(), 10, Level.ExplosionInteraction.BLOCK);
                }
            }
        }
    }
}