package net.kapitencraft.mysticcraft.item.combat.totems;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Rarity;

public class VoidTotemItem extends ModTotemItem {
    public VoidTotemItem() {
        super(MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public boolean onUse(LivingEntity living, DamageSource source) {
        if (source == DamageSource.OUT_OF_WORLD) {
            boolean handled = false;
            if (living instanceof ServerPlayer player) {
                BlockPos respawnPoint = player.getRespawnPosition();
                if (respawnPoint != null) {
                    player.setPos(respawnPoint.getCenter());
                    handled = true;
                }
            }
            if (!handled) {
                living.setPos(MathHelper.getPosition(living).add(0, 300, 0));
            }
            return true;
        }
        return false;
    }

    @Override
    public TabGroup getGroup() {
        return TabGroup.COMBAT;
    }
}
