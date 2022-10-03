package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ArrowTickSchedule {
    Arrow arrow;
    UUID TargetUUID;
    public ArrowTickSchedule(Arrow arrow) {
        this.arrow = arrow;
        MinecraftForge.EVENT_BUS.register(this);
        CompoundTag tag = arrow.getPersistentData();
        ItemStack stack = arrow.getOwner() instanceof LivingEntity living ? living.getMainHandItem() : null;
        if (stack != null) {
            tag.putInt("SnipeEnchant", stack.getEnchantmentLevel(ModEnchantments.SNIPE.get()));
            if (tag.getInt("SnipeEnchant") > 0) {
                tag.putDouble("LaunchX", this.arrow.getX());
                tag.putDouble("LaunchY", this.arrow.getY());
                tag.putDouble("LaunchZ", this.arrow.getZ());
            }
            tag.putInt("AimEnchant", stack.getEnchantmentLevel(ModEnchantments.AIM.get()));
        }
    }

    //@SubscribeEvent
    public void tick(TickEvent.LevelTickEvent ignoredEvent) {
        CompoundTag tag = this.arrow.getPersistentData();
        if (tag.contains("isUnregistered") && tag.getBoolean("isUnregistered")) {
            return;
        }
        if (tag.contains("ticks")) {
            tag.putInt("ticks", tag.getInt("ticks") + 1);
        }
        if (tag.contains("isUsed") && tag.getBoolean("isUsed") || this.arrow.isInWall()) {
            MinecraftForge.EVENT_BUS.unregister(this);
            tag.putBoolean("isUnregistered", true);
            return;
        }
        List<LivingEntity> list = this.arrow.level.getEntitiesOfClass(LivingEntity.class, this.arrow.getBoundingBox().inflate(tag.getInt("AimEnchant") * 2));
        @Nullable LivingEntity[] livingEntities = MISCTools.sortLowestDistance(this.arrow, list);
        if (livingEntities != null) {
            @Nullable LivingEntity target = null;
            LivingEntity living;
            boolean flag = tag.contains("AimEnchant") && tag.getInt("AimEnchant") > 0;
            boolean flag2;
            for (int i = 0; i < livingEntities.length; i++) {
                living = livingEntities[i];
                flag2 = living.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(ModEnchantments.FIRM_STAND.get()) > 0;
                if (!(living.is(this.arrow.getOwner()))) {
                    if (flag2 && !flag) {
                        this.arrow.setDeltaMovement(this.arrow.position().subtract(living.position()));
                        break;
                    } else if (flag && !flag2) {
                        this.arrow.setDeltaMovement(living.position().subtract(this.arrow.position()));
                        break;
                    }
                }
            }
        }
    }
}
