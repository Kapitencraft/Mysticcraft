package net.kapitencraft.mysticcraft.item.combat.totems;

import net.kapitencraft.mysticcraft.entity.portal.TransferForcer;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.Vec3;

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
                    ServerLevel serverLevel = player.getLevel();
                    MinecraftServer server = serverLevel.getServer();
                    TransferForcer forcer = new TransferForcer(serverLevel);
                    player.changeDimension(server.overworld(), forcer);
                    setPos(player, respawnPoint.getCenter());
                    handled = true;
                }
            }
            if (!handled) {
                setPos(living, living.position().add(0, 300, 0));
            }
            return true;
        }
        return false;
    }

    private static void setPos(LivingEntity living, Vec3 targetLoc) {
        living.fallDistance = 0;
        living.setHealth(0.5f);
        living.teleportTo(targetLoc.x, targetLoc.y, targetLoc.z);
    }

    @Override
    public TabGroup getGroup() {
        return TabGroup.COMBAT;
    }
}
