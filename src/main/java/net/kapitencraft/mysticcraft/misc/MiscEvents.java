package net.kapitencraft.mysticcraft.misc;


import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MiscEvents {

    @SubscribeEvent
    public static void AbilityUsing(LivingEntityUseItemEvent.Tick event) {
        Item usedItem = event.getItem().getItem();
        if (usedItem instanceof SpellItem) {
            SpellItem spellItem = (SpellItem) usedItem;
            SpellSlot spellSlot = spellItem.getSpellSlots()[spellItem.getActivespell()];
            if (!(spellSlot.getSpell().TYPE == Spells.CYCLE)) {
                return;
            } else {
                if (spellSlot.getManaCost() <= event.getEntity().getAttributeValue(ModAttributes.MANA.get())) {
                    AttributeInstance mana = event.getEntity().getAttribute(ModAttributes.MANA.get());
                    mana.setBaseValue(mana.getBaseValue() - spellSlot.getManaCost());
                    spellItem.execute(event.getEntity());
                } else {
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public static void AbilityUse(LivingEntityUseItemEvent.Stop event) {
        Item usedItem = event.getItem().getItem();
        if (usedItem instanceof SpellItem) {
            SpellItem spellItem = (SpellItem) usedItem;
            SpellSlot spellSlot = spellItem.getSpellSlots()[spellItem.getActivespell()];
            if (!(spellSlot.getSpell().TYPE == Spells.RELEASE)) {
                return;
            } else {
                if (spellSlot.getManaCost() <= event.getEntity().getAttributeValue(ModAttributes.MANA.get())) {
                    AttributeInstance mana = event.getEntity().getAttribute(ModAttributes.MANA.get());
                    mana.setBaseValue(mana.getBaseValue() - spellSlot.getManaCost());
                    spellItem.execute(event.getEntity());
                }
            }
        }
    }
}
