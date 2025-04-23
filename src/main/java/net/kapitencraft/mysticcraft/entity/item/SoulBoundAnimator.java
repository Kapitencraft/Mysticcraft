package net.kapitencraft.mysticcraft.entity.item;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SoulBoundAnimator extends ArmorStand {
    private UUID owner;
    private int slotId;
    private EquipmentSlot storageSlot;
    private int tickAmount = 0;
    private boolean teleported;

    public SoulBoundAnimator(EntityType<? extends ArmorStand> p_31553_, Level p_31554_) {
        super(p_31553_, p_31554_);
        this.setInvisible(true);
    }

    public SoulBoundAnimator(Player player, ItemStack stack, int slotId) {
        super(ModEntityTypes.SOUL_BOUND_ANIMATOR.get(), player.level());
        EquipmentSlot slot = LivingEntity.getEquipmentSlotForItem(stack);
        this.setItemSlot(slot, stack);
        this.setPos(player.position());
        this.storageSlot = slot;
        this.slotId = slotId;
        this.owner = player.getUUID();
    }


    @Override
    public void tick() {
        super.tick();
        if (this.getOwner() != null) {
            tickAmount++;
            this.setRot(this.getXRot(), this.getYRot() + tickAmount);
            Vec2 targetPlayerRot = MathHelper.createTargetRotation(this, this.getOwner());
            Vec3 relative = MathHelper.calculateViewVector(targetPlayerRot.x, targetPlayerRot.y);
            if (tickAmount >= 100 && !teleported) {
                this.setPos(this.getOwner().position().add(relative.scale(-100)));
                teleported = true;
            } else {
                this.setPos(this.position().add(relative.scale(2)));
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SlotId", slotId);
        tag.putString("Slot", storageSlot.getName());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("Marker", true);
        super.readAdditionalSaveData(tag);
        this.slotId = tag.getInt("SlotId");
        this.storageSlot = EquipmentSlot.byName(tag.getString("Slot"));
    }

    @Override
    public void playerTouch(@NotNull Player player) {
        if (player == getOwner()) {
            Inventory inventory = player.getInventory();
            ItemStack carried = this.getItemBySlot(storageSlot);
            if (!inventory.getItem(slotId).isEmpty()) {
                player.drop(inventory.getItem(slotId), true);
            }
            inventory.setItem(slotId, carried);
            this.discard();
        }
    }

    private Player getOwner() {
        return this.level() instanceof ServerLevel server ? server.getPlayerByUUID(this.owner) : null;
    }
}