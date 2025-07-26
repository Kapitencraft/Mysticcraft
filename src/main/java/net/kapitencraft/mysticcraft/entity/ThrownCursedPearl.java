package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownCursedPearl extends ThrownEnderpearl {
    public ThrownCursedPearl(EntityType<? extends ThrownEnderpearl> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownCursedPearl(Level level, LivingEntity pShooter) {
        this(ModEntityTypes.CURSED_PEARL.get(), level);
        this.setPos(pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ());
        this.setOwner(pShooter);
    }

    @Override
    protected void onHit(HitResult pResult) {
        if (this.getOwner() instanceof ServerPlayer player) {
            Inventory inventory = player.getInventory();
            Vec3 pos = player.position();
            inventory.armor.forEach(stack -> Containers.dropItemStack(this.level(), pos.x, pos.y, pos.z, stack));
        }

        super.onHit(pResult);
    }
}
