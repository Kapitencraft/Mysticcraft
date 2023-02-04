package net.kapitencraft.mysticcraft.misc;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.IArmorEnchantment;
import net.kapitencraft.mysticcraft.enchantments.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.init.*;
import net.kapitencraft.mysticcraft.item.RNGDropHelper;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.weapon.melee.sword.ManaSteelSwordItem;
import net.kapitencraft.mysticcraft.item.weapon.ranged.bow.TallinBow;
import net.kapitencraft.mysticcraft.misc.damage_source.FerociousDamageSource;
import net.kapitencraft.mysticcraft.misc.damage_source.IAbilitySource;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.*;

@Mod.EventBusSubscriber
public class MiscRegister {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void DamageAttributeRegister(LivingDamageEvent event) {
        @Nullable LivingEntity attacker = MISCTools.getAttacker(event.getSource());
        if (attacker == null) { return; }
        if (event.getSource() instanceof IAbilitySource abilitySource) {
            double intel = attacker.getAttributeValue(ModAttributes.INTELLIGENCE.get());
            double ability_damage = attacker.getAttributeValue(ModAttributes.ABILITY_DAMAGE.get());
            event.setAmount((float) (event.getAmount() * (1 + (intel / 100) * abilitySource.getScaling()) * (1 + (ability_damage / 100))));
        } else if (MISCTools.getSaveAttributeValue(ModAttributes.STRENGTH.get(), attacker) != -1) {
            double Strength = MISCTools.getSaveAttributeValue(ModAttributes.STRENGTH.get(), attacker);
            event.setAmount((float) (event.getAmount() * (1 + Strength / 100)));
        }
        LivingEntity attacked = event.getEntity();
        if (MISCTools.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), attacker) != -1) {
            double armorShredder = (int) MISCTools.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), attacker);
            for (int i = 0; i < 4; i++) {
                ItemStack stack = attacked.getItemBySlot(MISCTools.ARMOR_EQUIPMENT[i]);
                stack.hurt((int) armorShredder / 3, attacked.level.getRandom(), attacker instanceof ServerPlayer serverPlayer ? serverPlayer : null);
            }
        }
        if (MISCTools.getSaveAttributeValue(ModAttributes.LIVE_STEAL.get(), attacker) != -1) {
            double live_steel = (int) MISCTools.getSaveAttributeValue(ModAttributes.LIVE_STEAL.get(), attacker);
            attacker.heal(Math.min((float) live_steel, event.getAmount()));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void DamageEnchantRegister(LivingDamageEvent event) {
        LivingEntity attacked = event.getEntity();
        if (event.getSource().getEntity() instanceof Arrow arrow) {
            CompoundTag tag = arrow.getPersistentData();
            if (tag.contains("SnipeEnchant", 3) && tag.getInt("SnipeEnchant") > 0) {
                Vec3 launchLoc = new Vec3(tag.getDouble("LaunchX"), tag.getDouble("LaunchY"), tag.getDouble("LaunchZ"));
                Vec3 hitLoc = arrow.position();
                double distance = launchLoc.distanceTo(hitLoc);
                event.setAmount((float) (event.getAmount() * (1 + (float) Math.floor(distance / 10) * 0.05 * tag.getInt("SnipeEnchant"))));
            }
            return;
        }
        @Nullable LivingEntity attacker = MISCTools.getAttacker(event.getSource());
        if (attacker == null) { return; }
        ItemStack stack = attacker.getMainHandItem();
        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        if (enchantments != null) {
            for (Enchantment enchantment : enchantments.keySet()) {
                if (enchantment instanceof ExtendedCalculationEnchantment modEnchantment && enchantment instanceof IWeaponEnchantment) {
                    event.setAmount((float) modEnchantment.execute(enchantments.get(enchantment), stack, attacker, attacked, event.getAmount()));
                }
            }
        }
        for (EquipmentSlot slot : MISCTools.ARMOR_EQUIPMENT) {
            stack = attacked.getItemBySlot(slot);
            enchantments = stack.getAllEnchantments();
            if (enchantments != null) {
                for (Enchantment enchantment : enchantments.keySet()) {
                    if (enchantment instanceof ExtendedCalculationEnchantment modEnchantment && enchantment instanceof IArmorEnchantment) {
                        event.setAmount((float) modEnchantment.execute(enchantments.get(enchantment), stack, attacker, attacked, event.getAmount()));
                    }
                }
            }
        }
        for (EquipmentSlot slot : MISCTools.ARMOR_EQUIPMENT) {
            stack = attacker.getItemBySlot(slot);
            enchantments = stack.getAllEnchantments();
            if (enchantments != null) {
                for (Enchantment enchantment : enchantments.keySet()) {
                    if (enchantment instanceof ExtendedCalculationEnchantment modEnchantment && enchantment instanceof IArmorEnchantment) {
                        event.setAmount((float) modEnchantment.execute(enchantments.get(enchantment), stack, attacker, attacked, event.getAmount()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void ItemBonusRegister(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        LivingEntity attacker = MISCTools.getAttacker(source);
        if (attacker != null) {
            for (EquipmentSlot slot : MISCTools.ARMOR_EQUIPMENT) {
                LivingEntity killed = event.getEntity();
                if (attacker.getItemBySlot(slot).getItem() instanceof IArmorBonusItem bonusItem) {
                    if (bonusItem.getPieceBonusForSlot(slot) != null) {
                        bonusItem.getPieceBonusForSlot(slot).onEntityKilled(killed, attacker);
                    }
                    if (bonusItem.getFullSetBonus() != null) {
                        bonusItem.getFullSetBonus().onEntityKilled(killed, attacker);
                    }
                    if (bonusItem.getExtraBonus(slot) != null) {
                        bonusItem.getExtraBonus(slot).onEntityKilled(killed, attacker);
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
    public static void HitEffectRegister(LivingDamageEvent event) {
        LivingEntity living = event.getEntity();
        if (living.hasEffect(ModMobEffects.VULNERABILITY.get())) {
            event.setAmount((float) (event.getAmount() * (1 + Objects.requireNonNull(living.getEffect(ModMobEffects.VULNERABILITY.get())).getAmplifier() * 0.05)));
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
    public static void FerocityRegister(LivingDamageEvent event) {
        LivingEntity attacked = event.getEntity();
        LivingEntity attacker = MISCTools.getAttacker(event.getSource());
        if (attacker == null) {
            return;
        }
        if (attacker.getAttribute(ModAttributes.FEROCITY.get()) != null) {
            final double ferocity = event.getSource() instanceof FerociousDamageSource damageSource ? damageSource.ferocity : attacker.getAttributeValue(ModAttributes.FEROCITY.get());
            if (ferocity >= (Math.random() * 100)) {
                new Object() {
                    private int ticks = 0;
                    private float waitTicks;

                    public void start(int waitTicks) {
                        this.waitTicks = waitTicks;
                        MinecraftForge.EVENT_BUS.register(this);
                    }
                    @SubscribeEvent
                    public void tick(TickEvent.ServerTickEvent event) {
                        if (event.phase == TickEvent.Phase.END) {
                            this.ticks += 1;
                            if (this.ticks >= this.waitTicks)
                                run();
                        }
                    }
                    private void run() {
                        MinecraftForge.EVENT_BUS.unregister(this);
                        float ferocity_damage = (float) (event.getSource() instanceof FerociousDamageSource ferociousDamageSource ? ferociousDamageSource.damage : event.getSource().getEntity() instanceof AbstractArrow arrow ? arrow.getBaseDamage() : event.getSource().getEntity() instanceof LivingEntity living ? living.getAttributeValue(Attributes.ATTACK_DAMAGE) : 0.0f);
                        attacked.hurt(new FerociousDamageSource(attacker, (ferocity - 100), ferocity_damage), ferocity_damage);
                    }
                }.start(40);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void MiscDamageEvents(LivingDamageEvent event) {
        LivingEntity attacker = MISCTools.getAttacker(event.getSource());
        if (attacker == null) {
            return;
        }
        ItemStack mainHand = attacker.getMainHandItem();
        if (mainHand.getItem() instanceof ManaSteelSwordItem) {
            attacker.heal(2f);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void EntityDamaged(LivingDamageEvent event)   {
        LivingEntity attacked = event.getEntity();
        boolean dodge = false;
        double Dodge = MISCTools.getSaveAttributeValue(ModAttributes.DODGE.get(), attacked);
        DamageSource source = event.getSource();
        if (Dodge > 0) {
            if (Math.random() > Dodge / 100 && ((!source.isBypassArmor() && !source.isFall() && !source.isFire()) || source == DamageSource.STALAGMITE)) {
                dodge = true;
                event.setAmount(0);
            }
        }
        MISCTools.createDamageIndicator(attacked, event.getAmount(), dodge ? "dodge" : source.msgId);
    }

    @SubscribeEvent
    public static void EntityTick(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();
        CompoundTag tag = living.getPersistentData();
        if (living instanceof ArmorStand) {
            if (tag.getBoolean("isDamageIndicator")) {
                tag.putInt("time", tag.getInt("time") + 1);
                if (tag.getInt("time") >= 35) {
                    living.kill();
                }
            }
        }

        if (MISCTools.getSaveAttributeValue(ModAttributes.MANA.get(), living) != -1 && living.level.getBlockState(new BlockPos(living.getX(), living.getY(), living.getZ())).getBlock() == ModBlocks.MANA_FLUID_BLOCK.get()) {
            double overflowMana = tag.getDouble("overflowMana") + tag.getDouble("manaRegen");
            tag.putDouble("overflowMana", overflowMana);
            if (overflowMana >= living.getAttributeValue(ModAttributes.MAX_MANA.get())) {
                living.hurt(new DamageSource("overflowMana").bypassInvul().bypassMagic().bypassEnchantments().bypassArmor(), Float.MAX_VALUE);
                List<LivingEntity> livings = living.level.getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(5));
                for (LivingEntity living1 : livings) {
                    living1.hurt(new EntityDamageSource("overflowMana", living).bypassArmor().bypassMagic().bypassEnchantments().bypassInvul(), Float.MAX_VALUE);
                }
            }
        }
        if (living instanceof Player player && tag.contains(SpellItem.SPELL_EXECUTION_DUR)) {
            if (tag.getByte(SpellItem.SPELL_EXECUTION_DUR) < 1 || tag.getString(SpellItem.SPELL_EXE).length() >= 7) {
                ItemStack mainHand = living.getMainHandItem();
                if (mainHand.getItem() instanceof SpellItem spellItem) {
                    spellItem.executeSpell(tag.getString(SpellItem.SPELL_EXE), mainHand, living);
                    tag.putString(SpellItem.SPELL_EXE, "");
                }
                MISCTools.clearTitle(player);
            } else {
                tag.putByte(SpellItem.SPELL_EXECUTION_DUR, (byte) (tag.getByte(SpellItem.SPELL_EXECUTION_DUR) - 1));
            }
        }
        //ParticleHelper.tickHelper(living);
    }

    @SubscribeEvent
    public static void DescriptionRegister(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> toolTip = event.getToolTip();
        Player player = event.getEntity();
        final Component SEARCHED = Component.literal(FormattingCodes.GRAY + "Mana-Cost: " + FormattingCodes.DARK_RED);
        if (player != null) {
            if (toolTip.contains(SEARCHED) && stack.getItem() instanceof SpellItem spellItem) {
                if (spellItem.getSpellSlotAmount() > 1) {
                    for (int i = 0; i < toolTip.size(); i++) {
                        Component component = toolTip.get(i);
                        if (i < toolTip.size() - 1 && component.contains(SEARCHED)) {
                            MutableComponent mutable = (MutableComponent) component;
                            MysticcraftMod.sendInfo("a");
                            Component pattern = toolTip.get(i + 1);
                            String patternString = pattern.getString();
                            Spell spell = Spells.get(Spell.getStringForPattern(patternString, true));
                            if (spell != null) {
                                AttributeInstance instance = player.getAttribute(ModAttributes.MANA_COST.get());
                                mutable.append(FormattingCodes.DARK_RED + MISCTools.getAttributeValue(instance, spell.MANA_COST));
                            }
                        }
                    }
                } else {
                    Spell spell = spellItem.getActiveSpell();
                    Component component = toolTip.get(toolTip.indexOf(SEARCHED));
                    if (component instanceof MutableComponent mutable) {
                        AttributeInstance instance = player.getAttribute(ModAttributes.MANA_COST.get());
                        if (instance != null) {
                            mutable.append(FormattingCodes.DARK_RED + MISCTools.getAttributeValue(instance, spell.MANA_COST));
                        }
                    }
                }
            }
        }
        Rarity rarity = stack.getItem().getRarity(stack);
        boolean flag = rarity != MISCTools.getItemRarity(stack.getItem());
        if (stack.getItem() instanceof IGemstoneApplicable gemstoneApplicable) {
            gemstoneApplicable.getModInfo(stack, toolTip);
            toolTip.add(Component.literal(""));
        }
        if (!(stack.getItem() instanceof IGuiHelper)) {
            String RarityMod = FormattingCodes.OBFUSCATED + "A" + FormattingCodes.RESET;
            toolTip.add(Component.literal(""));
            toolTip.add(Component.literal((flag ? RarityMod + " " : "") + rarity + " " + MISCTools.getNameModifier(stack) + (flag ? " " + RarityMod : "")).withStyle(rarity.getStyleModifier()).withStyle(ChatFormatting.BOLD));
        }
    }

    @SubscribeEvent
    public static void healthRegenRegister(LivingHealEvent event) {
        if (event.getEntity().getAttribute(ModAttributes.HEALTH_REGEN.get()) != null) {
            double health_regen = event.getEntity().getAttributeValue(ModAttributes.HEALTH_REGEN.get());
            event.setAmount(event.getAmount() * (1 + (float) health_regen / 100));
        }
        MISCTools.createDamageIndicator(event.getEntity(), event.getAmount(), "heal");
    }

    @SubscribeEvent
    public static void ArrowRegisterEvent(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            if (arrow.getOwner() instanceof LivingEntity living) {
                ItemStack mainHand = living.getMainHandItem();
                CompoundTag arrowTag = arrow.getPersistentData();
                if (mainHand.getItem() instanceof TallinBow) {
                    arrowTag.putBoolean("CanHitEnderman", true);
                }
                arrowTag.putInt("SnipeEnchant", mainHand.getEnchantmentLevel(ModEnchantments.SNIPE.get()));
                if (arrowTag.getInt("SnipeEnchant") > 0) {
                    arrowTag.putDouble("LaunchX", arrow.getX());
                    arrowTag.putDouble("LaunchY", arrow.getY());
                    arrowTag.putDouble("LaunchZ", arrow.getZ());
                }
                arrowTag.putInt("AimEnchant", mainHand.getEnchantmentLevel(ModEnchantments.AIM.get()));
            }
        }
    }

    @SubscribeEvent
    public static void gemstoneAttributeModifications(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (item instanceof IGemstoneApplicable applicable) {
            HashMap<Attribute, AttributeModifier> modifierHashMap = applicable.getAttributeModifiers(stack);
            if (item instanceof ArmorItem armorItem) {
                if (event.getSlotType() == armorItem.getSlot() && applicable.getAttributesModified(stack) != null) {
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
        LivingEntity attacker = MISCTools.getAttacker(event.getSource());
        LivingEntity attacked = event.getEntity();
        Vec3 pos = new Vec3(attacked.getX(), attacked.getY(), attacked.getZ());
        assert attacker != null;
        if (event.getEntity() instanceof Blaze && !(event.getEntity() instanceof FrozenBlazeEntity)) {
            ItemStack toDrop = new ItemStack(ModItems.HEART_OF_THE_NETHER.get());
            RNGDropHelper.calculateAndDrop(toDrop, 0.000001f, attacker, pos);
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
        if (hasReplenish) {
            MISCTools.shrinkDrops(drops, Items.WHEAT_SEEDS, 1);
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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void manaRegister(RenderGuiOverlayEvent.Pre event) {
        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();
        int posX = w / 2;
        int posY = h / 2;
            Player entity = Minecraft.getInstance().player;
        if (entity != null) {
            String builder = FormattingCodes.BLUE + MISCTools.round(entity.getAttributeValue(ModAttributes.MANA.get()), 1) + " [+" + MISCTools.round(entity.getPersistentData().getDouble("overflowMana"), 1) + " Overflow] " + FormattingCodes.RESET + " / " + FormattingCodes.DARK_BLUE + entity.getAttributeValue(ModAttributes.MAX_MANA.get()) + " (+" + MISCTools.round(entity.getPersistentData().getDouble("manaRegen") * 20, 2) + "/s)";
            Minecraft.getInstance().font.draw(event.getPoseStack(), builder, posX - 203, posY - 116, -1);
        }
    }
}