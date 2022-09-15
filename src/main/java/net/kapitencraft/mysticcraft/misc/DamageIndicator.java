package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DamageIndicator {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void EntityDamaged(LivingDamageEvent event) {
        MysticcraftMod.LOGGER.info("Launching Damage Indication");
        LivingEntity attacked = event.getEntity();
        ArmorStand dmgInc = new ArmorStand(attacked.level, attacked.getX() + Math.random() - 0.5, attacked.getY() - 1, attacked.getZ() + Math.random() - 0.5);
        dmgInc.setNoGravity(true);
        dmgInc.setInvisible(true);
        dmgInc.setInvulnerable(true);
        dmgInc.setBoundingBox(new AABB(0,0,0,0,0,0));
        dmgInc.setCustomNameVisible(true);
        dmgInc.setCustomName(Component.Serializer.fromJson(String.valueOf(event.getAmount())));
        attacked.level.addFreshEntity(dmgInc);
        new Object() {
            private int ticks = 0;
            private int waitTicks;

            public void Start(int waitTicks) {
                this.waitTicks = waitTicks;
                MinecraftForge.EVENT_BUS.register(this);
            }

            @SubscribeEvent
            public void tick(TickEvent.ServerTickEvent event) {
                if (event.phase == TickEvent.Phase.END) {
                    this.ticks++;
                    if (this.ticks >= this.waitTicks) {
                        run();
                    }
                }
            }

            private void run() {
                dmgInc.kill();
            }
        }.Start(20);

    }
}
