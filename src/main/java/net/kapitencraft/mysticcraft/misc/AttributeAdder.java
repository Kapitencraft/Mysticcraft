package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeAdder {
    @SubscribeEvent
    public static void AttributeAdding(EntityAttributeModificationEvent event) {
        MysticcraftMod.LOGGER.info("Send");
        event.add(EntityType.PLAYER, ModAttributes.CRIT_DAMAGE.get());
        event.add(EntityType.PLAYER, ModAttributes.FEROCITY.get());
        event.add(EntityType.PLAYER, ModAttributes.INTELLIGENCE.get());
        event.add(EntityType.PLAYER, ModAttributes.MAGIC_FIND.get());
        event.add(EntityType.PLAYER, ModAttributes.MANA.get());
        event.add(EntityType.PLAYER, ModAttributes.MAX_MANA.get());
        event.add(EntityType.PLAYER, ModAttributes.STRENGHT.get());
        event.add(EntityType.PLAYER, ModAttributes.MANA_COST.get());
        event.add(EntityType.PLAYER, ModAttributes.MANA_REGEN.get());
        event.add(EntityType.PLAYER, ModAttributes.ABILLITY_DAMAGE.get());

    }
}
