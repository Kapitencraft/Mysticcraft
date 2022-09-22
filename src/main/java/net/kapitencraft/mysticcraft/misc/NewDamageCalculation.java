package net.kapitencraft.mysticcraft.misc;


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
        if (event.getSource() instanceof EntityDamageSource source) {
            if (source.getEntity() instanceof Player attacker) {
                double Strength = attacker.getAttributeValue(ModAttributes.STRENGTH.get());
                event.setAmount((float) (event.getAmount() * (1 + Strength / 100)));
            } else if (source.getEntity() instanceof Projectile projectile) {
                if (projectile.getOwner() instanceof Player attacker) {
                    double Strength = attacker.getAttributeValue(ModAttributes.STRENGTH.get());
                    event.setAmount((float) (event.getAmount() * (1 + Strength / 100)));
                }
            }
        }
    }
}
