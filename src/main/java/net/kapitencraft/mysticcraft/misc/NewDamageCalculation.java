package net.kapitencraft.mysticcraft.misc;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class NewDamageCalculation {
    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        MysticcraftMod.LOGGER.info("Launching the new Damage Calculation");
        if (event.getSource() instanceof EntityDamageSource) {
            EntityDamageSource source = (EntityDamageSource) event.getSource();
            if (source.getEntity() instanceof Player) {
                Player attacker = (Player) source.getEntity();
                double Strenght = attacker.getAttributeValue(ModAttributes.STRENGHT.get());
                event.setAmount((float) (event.getAmount() * (1 + Strenght / 100)));
            } else if (source.getEntity() instanceof Projectile) {
                Projectile projectile = (Projectile) source.getEntity();
                if (projectile.getOwner() instanceof Player) {
                    Player attacker = (Player) projectile.getOwner();
                    double Strenght = attacker.getAttributeValue(ModAttributes.STRENGHT.get());
                    event.setAmount((float) (event.getAmount() * (1 + Strenght / 100)));
                }
            }
        }
    }
}
