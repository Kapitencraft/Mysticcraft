package net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.kapitencraft.mysticcraft.network.packets.S2C.SwingPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public abstract class ModDaggerItem extends ModSwordItem {
    public ModDaggerItem(Tier p_43269_, Properties p_43272_) {
        super(p_43269_, p_43272_);
    }

    /**
     * code to attack the target with the offhand if mainhand is same item
     */
    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        super.hurtEnemy(pStack, pTarget, pAttacker);
        ItemStack offhand = pAttacker.getOffhandItem();
        if (offhand.getItem() == this && pAttacker instanceof Player player && pTarget.isAlive() && !player.isOffhandAttack()) {
            MiscHelper.schedule(10, ()-> {
                if (player.canAttack(pTarget)) {
                    MiscHelper.swapHands(pAttacker);
                    player.setOffhandAttack();
                    player.attack(pTarget);
                    PacketDistributor.sendToPlayersInDimension((ServerLevel) player.level(), new SwingPacket(InteractionHand.OFF_HAND, player.getId()));
                    player.setMainhandAttack();
                    MiscHelper.swapHands(pAttacker);
                }
            });
        }
        return true;
    }
}
