package net.kapitencraft.mysticcraft.item.combat.totems;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.ExtendedItem;
import net.kapitencraft.kap_lib.item.combat.totem.AbstractTotemItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VoidTotemItem extends AbstractTotemItem implements ExtendedItem {
    public VoidTotemItem() {
        super(MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public boolean onUse(LivingEntity living, DamageSource source) {
        if (source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            if (living instanceof ServerPlayer player) {
                player.setHealth(player.getMaxHealth());
                player.serverLevel().getServer().getPlayerList().respawn(player, true, Entity.RemovalReason.CHANGED_DIMENSION);
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag, @Nullable Player player) {
        list.add(Component.translatable("void_totem.translation"));
    }
}
