package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.kapitencraft.mysticcraft.item.DropRarity;
import net.kapitencraft.mysticcraft.item.IRNGDrop;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow  ;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.level.gameevent.GameEvent;
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

@Mod.EventBusSubscriber
public class MiscRegister {

    @SubscribeEvent
    public static void DamageEnchantRegister(LivingDamageEvent event) {
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
        float amount = event.getAmount();
        if (attacker.getAttribute(ModAttributes.STRENGTH.get()) != null) {
            double StrengthMul = 1 + (attacker.getAttributeValue(ModAttributes.STRENGTH.get()));
            event.setAmount(amount * (float) StrengthMul);
        }
    }

    @SubscribeEvent
    public static void HitEffectRegister(LivingDamageEvent event) {
        LivingEntity living = event.getEntity();
        if (living.hasEffect(ModMobEffects.VULNERABILITY.get())) {
            event.setAmount((float) (event.getAmount() * (1 + living.getEffect(ModMobEffects.VULNERABILITY.get()).getAmplifier() * 0.05)));
        }
    }

    @SubscribeEvent
    public static void HitEnchantmentRegister(LivingDamageEvent event) {
        if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof LivingEntity living) {
            ItemStack stack = living.getMainHandItem();
            int nec_touch_lvl = stack.getEnchantmentLevel(ModEnchantments.NECROTIC_TOUCH.get());
            if (nec_touch_lvl > 0) {
                living.heal(event.getAmount() > nec_touch_lvl ? nec_touch_lvl : event.getAmount());
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
                if (persistentData.getInt("time") >= 20) {
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
            new ArrowTickSchedule(arrow);
        }
    }

    @SubscribeEvent
    public static void gemstoneAttributeModifications(ItemAttributeModifierEvent event) {
        Item item = event.getItemStack().getItem();
        if (item instanceof IGemstoneApplicable applicable) {
            if (item instanceof ArmorItem armorItem) {
                if (event.getSlotType() == armorItem.getSlot()) {
                    for (Attribute attribute : applicable.getAttributesModified()) {
                        event.addModifier(attribute, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(armorItem.getSlot())], "Gemstone Modification", applicable.getAttributeModifiers().get(attribute), AttributeModifier.Operation.ADDITION));
                    }
                }
            } else if (item instanceof TieredItem && event.getSlotType() == EquipmentSlot.MAINHAND) {
                for (Attribute attribute : applicable.getAttributesModified()) {
                    event.addModifier(attribute, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(EquipmentSlot.MAINHAND)], "Gemstone Modification", applicable.getAttributeModifiers().get(attribute), AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    @SubscribeEvent
    public static void rareDropMessage(LivingDropsEvent event) {
        Collection<ItemEntity> entities = event.getDrops();
        for (ItemEntity entity : entities) {
            Item item = entity.getItem().getItem();
            DropRarity dropRarity = item instanceof IRNGDrop drop ? drop.getRarity() : (item instanceof BlockItem block && block.getBlock() instanceof WitherSkullBlock skull) ? DropRarity.LEGENDARY : DropRarity.COMMON;
            if (dropRarity.getID() > 1) {
                if (event.getSource().getEntity() instanceof Player attacker) {
                    attacker.sendSystemMessage(Component.literal(dropRarity.getColor().UNICODE + dropRarity.getDisplayName() + "DROP!" + FormattingCodes.RESET + "(").append(item.getName(entity.getItem())).append(") ("+ FormattingCodes.BLUE.UNICODE + "+" + attacker.getAttributeValue(ModAttributes.MAGIC_FIND.get()) + "% Magic Find)"));
                }
            }
        }

    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void manaRegister(RenderGuiOverlayEvent.Pre event) {
        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();
        int posX = w / 2;
        int posY = h / 2;
        Player entity = Minecraft.getInstance().player;
        if (entity != null) {
            String builder = FormattingCodes.BLUE.UNICODE + MISCTools.round(entity.getAttributeValue(ModAttributes.MANA.get()), 1) + FormattingCodes.RESET + " / " + FormattingCodes.DARK_BLUE.UNICODE + entity.getAttributeValue(ModAttributes.MAX_MANA.get());
            Minecraft.getInstance().font.draw(event.getPoseStack(), builder , posX + -203, posY + 93, -1);
        }
    }
}
