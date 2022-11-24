package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.kapitencraft.mysticcraft.item.DropRarity;
import net.kapitencraft.mysticcraft.item.IRNGDrop;
import net.kapitencraft.mysticcraft.item.bow.TallinBow;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber
public class MiscRegister {
    @SubscribeEvent(priority = EventPriority.LOWEST)
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
        @Nullable LivingEntity attacker = MISCTools.getAttacker(event);
        if (attacker == null) { return; }
        ItemStack stack = attacker.getMainHandItem();
        float amount = event.getAmount();
        if (attacker.getAttribute(ModAttributes.STRENGTH.get()) != null) {
            double StrengthMul = 1 + (attacker.getAttributeValue(ModAttributes.STRENGTH.get()));
            event.setAmount(amount * (float) StrengthMul);
        }
        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        for (Enchantment enchantment : enchantments.keySet()) {
            if (enchantment instanceof ExtendedCalculationEnchantment modEnchantment) {
                event.setAmount((float) modEnchantment.execute(enchantments.get(enchantment), stack, attacker, attacked, event.getAmount()));
            }
        }
    }

    @SubscribeEvent
    public static void StatModEnchantmentRegister(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        EquipmentSlot slot = event.getSlotType();
        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        for (Enchantment enchantment : enchantments.keySet()) {
            if (enchantment instanceof StatBoostEnchantment statBoostEnchantment) {
                event. MISCTools.increaseByAmount() statBoostEnchantment.getModifiers(enchantments.get(statBoostEnchantment), stack, slot);
            }
        }
    }

    @SubscribeEvent
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
    public static void HitEnchantmentRegister(LivingDamageEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity instanceof LivingEntity attacker) {
            LivingEntity attacked = event.getEntity();
            ItemStack stack = attacker.getMainHandItem();
            int nec_touch_lvl = stack.getEnchantmentLevel(ModEnchantments.NECROTIC_TOUCH.get());
            int poison_blade = stack.getEnchantmentLevel(ModEnchantments.POISONOUS_BLADE.get());
            int venomous = stack.getEnchantmentLevel(ModEnchantments.VENOMOUS.get());
            if (nec_touch_lvl > 0) {
                attacker.heal(event.getAmount() > nec_touch_lvl ? nec_touch_lvl : event.getAmount());
            }
            if (poison_blade > 0) {
                if (!MISCTools.increaseEffectDuration(attacked, MobEffects.POISON, poison_blade * 5)) {
                    attacked.addEffect(new MobEffectInstance(MobEffects.POISON, 20, 1));
                }
            }
            if (venomous > 0) {
            }
        }
    }

    @SubscribeEvent
    public static void FerocityRegister(LivingDamageEvent event) {
        LivingEntity attacked = event.getEntity();
        LivingEntity attacker = MISCTools.getAttacker(event);
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
                        attacked.hurt(new FerociousDamageSource("ferocity", attacker, (ferocity - 100), ferocity_damage), ferocity_damage);
                    }
                }.start(40);
            }
        }
    }

    @SubscribeEvent
    public static void damageIndicatorExecuting(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof ArmorStand armorStand) {
            CompoundTag persistentData = armorStand.getPersistentData();
            if (persistentData.contains("isDamageIndicator") && persistentData.getBoolean("isDamageIndicator")) {
                persistentData.putInt("time", (persistentData.getInt("time") + 1));
                @Nullable Entity target = armorStand.level instanceof ServerLevel serverLevel && persistentData.contains("targetUUID") ? serverLevel.getEntity(persistentData.getUUID("targetUUID")) : null;
                if (persistentData.getInt("time") >= 20 || (target != null && !target.isAlive())) {
                    armorStand.kill();
                }
            }
        }
    }

    @SubscribeEvent
    public static void healthRegenRegister(LivingHealEvent event) {
        if (event.getEntity().getAttribute(ModAttributes.HEALTH_REGEN.get()) != null) {
            double health_regen = event.getEntity().getAttributeValue(ModAttributes.HEALTH_REGEN.get());
            event.setAmount(event.getAmount() * (float) health_regen / 100);
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
        Item item = event.getItemStack().getItem();
        if (item instanceof IGemstoneApplicable applicable) {
            if (item instanceof ArmorItem armorItem) {
                if (event.getSlotType() == armorItem.getSlot() && applicable.getAttributesModified() != null) {
                    for (Attribute attribute : applicable.getAttributesModified()) {
                        event.addModifier(attribute, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(armorItem.getSlot())], "Gemstone Modification", applicable.getAttributeModifiers().get(attribute), AttributeModifier.Operation.ADDITION));
                    }
                }
            } else if (item instanceof TieredItem && event.getSlotType() == EquipmentSlot.MAINHAND) {
                if (applicable.getAttributesModified() != null) {
                    for (Attribute attribute : applicable.getAttributesModified()) {
                        event.addModifier(attribute, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(EquipmentSlot.MAINHAND)], "Gemstone Modification", applicable.getAttributeModifiers().get(attribute), AttributeModifier.Operation.ADDITION));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void rareDropMessage(LivingDropsEvent event) {
        Collection<ItemEntity> entities = event.getDrops();
        for (ItemEntity entity : entities) {
            Item item = entity.getItem().getItem();
            DropRarity dropRarity = item instanceof IRNGDrop drop ? drop.getRarity() : (item instanceof BlockItem block && block.getBlock() instanceof WitherSkullBlock) ? DropRarity.LEGENDARY : DropRarity.COMMON;
            if (dropRarity.getID() > 1) {
                if (event.getSource().getEntity() instanceof Player attacker) {
                    attacker.sendSystemMessage(Component.literal(dropRarity.getColor().UNICODE + dropRarity.getDisplayName() + "DROP!" + FormattingCodes.RESET + "(").append(item.getName(entity.getItem())).append(") ("+ FormattingCodes.BLUE.UNICODE + "+" + attacker.getAttributeValue(ModAttributes.MAGIC_FIND.get()) + "% Magic Find)"));
                }
            }
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
            String builder = FormattingCodes.BLUE.UNICODE + MISCTools.round(entity.getAttributeValue(ModAttributes.MANA.get()), 1) + FormattingCodes.RESET + " / " + FormattingCodes.DARK_BLUE.UNICODE + entity.getAttributeValue(ModAttributes.MAX_MANA.get());
            Minecraft.getInstance().font.draw(event.getPoseStack(), builder , posX - 203, posY + 93, -1);
        }
    }
}
