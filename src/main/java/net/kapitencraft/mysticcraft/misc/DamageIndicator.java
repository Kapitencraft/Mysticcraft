package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DamageIndicator {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void EntityDamaged(LivingDamageEvent event) {
        LivingEntity attacked = event.getEntity();
        MISCTools.createDamageIndicator(attacked, (double)event.getAmount(), "damage");
    }
}
