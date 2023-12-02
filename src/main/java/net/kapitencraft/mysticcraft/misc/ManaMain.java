package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class ManaMain {

    @SubscribeEvent
    public static void ManaChange(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        AttributeInstance max_mana_instance = player.getAttribute(ModAttributes.MAX_MANA.get());
        AttributeInstance mana_instance = player.getAttribute(ModAttributes.MANA.get());
        if (max_mana_instance == null || mana_instance == null) {
            return;
        }
        double max_mana = max_mana_instance.getValue();
        double mana = mana_instance.getBaseValue();
        double intel = player.getAttributeValue(ModAttributes.INTELLIGENCE.get());
        double mana_regen = player.getAttributeValue(ModAttributes.MANA_REGEN.get());
        double manaRegen = max_mana / 500 * (1 + mana_regen / 100);
        player.getPersistentData().putDouble("manaRegen", manaRegen);
        if (mana < max_mana) {
             mana += manaRegen;

        }
        if (mana > max_mana) {
            mana = max_mana;
        }
        max_mana = 100 + intel;


        mana_instance.setBaseValue(mana);
        max_mana_instance.setBaseValue(max_mana);

    }
}
