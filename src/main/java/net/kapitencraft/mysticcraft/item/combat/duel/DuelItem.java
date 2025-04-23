package net.kapitencraft.mysticcraft.item.combat.duel;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class DuelItem extends Item implements IModItem {
    public DuelItem() {
        super(MiscHelper.rarity(Rarity.UNCOMMON));
    }

    @Override
    public TabGroup getGroup() {
        return TabGroup.COMBAT;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack p_41395_, @NotNull LivingEntity attacked, @NotNull LivingEntity attacker) {
        return addToTeams(attacker, attacked, false);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack p_41398_, @NotNull Player player, @NotNull LivingEntity living, @NotNull InteractionHand p_41401_) {
        return addToTeams(player, living, true) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    private boolean addToTeams(LivingEntity source, LivingEntity target, boolean sameTeam) {
        DuelHandler handler = DuelHandler.getInstance();
        if (source instanceof ServerPlayer serverPlayer && target instanceof ServerPlayer attackedPlayer) {
            Duel duel = handler.computeOrAdd(serverPlayer);
            if (sameTeam) {
                duel.addToTeam(attackedPlayer, serverPlayer);
                attackedPlayer.sendSystemMessage(Component.translatable("duel_item.request", serverPlayer.getName()).withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ""))));
            }
            else {
                duel.addToEnemyTeam(attackedPlayer, serverPlayer);
            }
            return true;
        }
        return false;

    }
}
