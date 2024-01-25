package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.misc.ManaMain;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ElytraItem.class)
public abstract class ElytraItemMixin extends Item {

    public ElytraItemMixin(Properties p_41383_) {
        super(p_41383_);
    }

    /**
     * @author Kapitencraft
     * @reason elytra data :pog:
     */
    @Override
    public boolean elytraFlightTick(ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
        if (entity instanceof Player player && Requirement.doesntMeetRequirements(player, stack.getItem())) return false;
        if (!entity.level.isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                if (nextFlightTick % 20 == 0) {
                    if (!CapabilityHelper.exeCapability(stack, CapabilityHelper.ELYTRA, data -> {
                        int unb = data.getLevelForData(ElytraData.UNBREAKING);
                        if (unb == 0)
                            stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
                    })) stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
                }
                entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_GLIDE);
            }
            CapabilityHelper.exeCapability(stack, CapabilityHelper.ELYTRA, data -> {
                int manaBoost = data.getLevelForData(ElytraData.MANA_BOOST);
                if (manaBoost > 0) {
                    if (ManaMain.consumeMana(entity, 1.5)) {
                        //TODO fix not working
                        entity.setDeltaMovement(MiscHelper.getFireworkSpeedBoost(entity));
                    }
                }
            });
        }
        return true;
    }
}
