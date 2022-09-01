package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ManaMain {

    @SubscribeEvent
    public static void ManaChange(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        double mana = player.getAttributeValue(ModAttributes.MANA.get());
        double max_mana = player.getAttributeValue(ModAttributes.MAX_MANA.get());
        double intel = player.getAttributeValue(ModAttributes.INTELLIGENCE.get());
        double mana_regen = player.getAttributeValue(ModAttributes.MANA_REGEN.get());
        if (mana < max_mana) {
            mana += max_mana / 500 * mana_regen;

        }

        max_mana = 100 + intel;


        AttributeInstance mana_instance = player.getAttribute(ModAttributes.MANA.get());
        AttributeInstance max_mana_instance = player.getAttribute(ModAttributes.MAX_MANA.get());
        mana_instance.setBaseValue(mana);
        max_mana_instance.setBaseValue(max_mana);

    }
}
