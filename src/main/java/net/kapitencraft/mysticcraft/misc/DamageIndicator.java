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
        ArmorStand dmginc = new ArmorStand(attacked.level, attacked.getX() + Math.random() - 0.5, attacked.getY() - 1, attacked.getZ() + Math.random() - 0.5);
        dmginc.setNoGravity(true);
        dmginc.setInvisible(true);
        dmginc.setInvulnerable(true);
        dmginc.setBoundingBox(new AABB(0,0,0,0,0,0));
        dmginc.setCustomNameVisible(true);
        dmginc.setCustomName(Component.Serializer.fromJson(String.valueOf(event.getAmount())));
        attacked.level.addFreshEntity(dmginc);
        new Object() {
            private int ticks = 0;
            private int waitticks;

            public void Start(int waitticks) {
                this.waitticks = waitticks;
                MinecraftForge.EVENT_BUS.register(this);
            }

            @SubscribeEvent
            public void tick(TickEvent.ServerTickEvent event) {
                if (event.phase == TickEvent.Phase.END) {
                    this.ticks++;
                    if (this.ticks >= this.waitticks) {
                        run();
                    }
                }
            }

            private void run() {
                dmginc.kill();
            }
        }.Start(20);

    }
}
