package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CustomEnchantRegister {

    @SubscribeEvent
    public static void DamageRegistries(LivingDamageEvent event) {
        if(event.getSource() instanceof EntityDamageSource source && source.getEntity() instanceof LivingEntity attacker) {
            ItemStack mainHandItem = attacker.getMainHandItem();
            int giantKillerLvl = mainHandItem.getEnchantmentLevel(ModEnchantments.GIANT_KILLER.get());
            LivingEntity attacked = event.getEntity();
           double MoreHpPercent = attacked.getHealth() - attacker.getHealth() / attacked.getMaxHealth() - attacker.getMaxHealth();
           event.setAmount((float) (event.getAmount() * (1 + MoreHpPercent * giantKillerLvl * 0.01)));


        }
    }
}
