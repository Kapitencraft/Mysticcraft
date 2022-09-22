package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MiscRegister {

    @SubscribeEvent
    public static void DamageEnchantRegister(LivingDamageEvent event) {
        LivingEntity attacker = MISCTools.getAttacker(event);
        if (attacker == null) {
            return;
        }
        float amount = event.getAmount();
        if (attacker.getAttribute(ModAttributes.STRENGHT.get()) != null) {
            double StrenghtMul = 1 + (attacker.getAttributeValue(ModAttributes.STRENGHT.get()));
            event.setAmount(amount * (float) StrenghtMul);
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
            MysticcraftMod.LOGGER.info(String.valueOf(ferocity >= (Math.random() * 100)));
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
                        MysticcraftMod.LOGGER.info("hitting");
                        attacked.hurt(new FerociousDamageSource("ferocity", attacker, (ferocity - 100)), (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE));
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
        double health_regen = event.getEntity().getAttributeValue(ModAttributes.HEALTH_REGEN.get());
        event.setAmount(event.getAmount() * (float) health_regen / 100);
        MISCTools.createDamageIndicator(event.getEntity(), event.getAmount(), "heal");

    }
}
