package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class SpellProjectile extends AbstractArrow {
    private final List<UUID> attacked = new ArrayList<>();
    protected final String name;
    protected float damageInflicted = 0;

    protected SpellProjectile(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_, String name) {
        super(p_36721_, p_36722_);
        this.name = name;
    }

    protected SpellProjectile(EntityType<? extends AbstractArrow> type, LivingEntity living, Level level, String name) {
        super(type, living, level);
        this.setOwner(living);
        this.name = name;
    }


    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_36755_) {
        this.sendDamageMessage();
        this.discard();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult p_36757_) {
        this.sendDamageMessage();
        this.discard();
    }

    protected void addHitEntity(LivingEntity living) {
        if (this.level instanceof ServerLevel serverLevel) {
            for (UUID uuid : this.attacked) {
                if (living == serverLevel.getEntity(uuid)) {
                    return;
                }
            }
            this.attacked.add(living.getUUID());
        }
    }

    protected void sendDamageMessage() {
        if (!this.level.isClientSide() && this.getOwner() instanceof Player player && attacked.size() > 0 && damageInflicted > 0) {
            String red = FormattingCodes.RED;
            player.sendSystemMessage(Component.literal("Your " + this.name + " hit " + red + attacked.size() + FormattingCodes.RESET + " Enemies for " + red + MathHelper.round(this.damageInflicted, 3) + " Damage"));
        }
    }
    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

}
