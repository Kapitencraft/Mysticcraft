package net.kapitencraft.mysticcraft.mixin.classes;


import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.QuiverItem;
import net.kapitencraft.mysticcraft.item.material.containable.ContainableHolder;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.kapitencraft.mysticcraft.mixin.duck.IAttacker;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Predicate;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IAttacker {

    @Shadow @Final private Inventory inventory;

    @Shadow public abstract void remove(RemovalReason p_150097_);

    @Shadow public abstract boolean tryToStartFallFlying();

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Override
    public boolean shouldShowName() {
        return !this.isInvisible();
    }

    public Player own() {
        return (Player) (Object) this;
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    public void aiStep(CallbackInfo info) {
        HealingHelper.setReason(own(), HealingHelper.HealReason.NATURAL);
    }

    /**
     * @reason quiver usage
     * @author Kapitencraft
     */
    @Overwrite
    public @NotNull ItemStack getProjectile(ItemStack stack) {
        if (!(stack.getItem() instanceof ProjectileWeaponItem)) {
            return ItemStack.EMPTY;
        } else {
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem)stack.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            if (!itemstack.isEmpty()) {
                return ForgeHooks.getProjectile(this, stack, itemstack);
            } else {
                predicate = ((ProjectileWeaponItem)stack.getItem()).getAllSupportedProjectiles();
                for(int i = 0; i < inventory.getContainerSize(); ++i) {
                    ItemStack stack1 = inventory.getItem(i);
                    if (stack1.getItem() instanceof QuiverItem quiverItem) {
                        List<ContainableHolder<ArrowItem>> quiverContents = quiverItem.getContents(stack1);
                        for (ContainableHolder<ArrowItem> holder : quiverContents) {
                            ArrowItem arrowItem = holder.getItem();
                            if (predicate.test(new ItemStack(arrowItem)) && holder.getAmount() > 0) {
                                int enchLevel = stack1.getEnchantmentLevel(ModEnchantments.INFINITE_QUIVER.get());
                                if (!(MathHelper.chance(0.01 * enchLevel, own()))) {
                                    holder.grow(-1);
                                }
                                quiverItem.saveContents(stack1, quiverContents);
                                return ForgeHooks.getProjectile(own(), stack, new ItemStack(arrowItem));
                            }
                        }
                    }
                    if (predicate.test(stack1)) {
                        return ForgeHooks.getProjectile(own(), stack, stack1);
                    }
                }

                return ForgeHooks.getProjectile(this, stack, own().getAbilities().instabuild ? new ItemStack(Items.ARROW) : ItemStack.EMPTY);
            }
        }
    }

    @Redirect(method = "sweepAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"))
    private int sendParticles(ServerLevel level, ParticleOptions options, double d, double d1, double d2, int i, double d3, double d4, double d5, double d6) {
        ItemStack mainHand = own().getMainHandItem();
        if (mainHand.getItem() instanceof ModSwordItem modSwordItem) {
            return level.sendParticles(modSwordItem.getSweepParticle(mainHand), d, d1, d2, i, d3, d4, d5, d6);
        }
        return level.sendParticles(ParticleTypes.SWEEP_ATTACK, d, d1, d2, i, d3, d4, d5, d6);
    }

    //IAttacker start

    private boolean offhandAttack;

    @Override
    public void setMainhandAttack() {
        offhandAttack = false;
    }

    @Override
    public void setOffhandAttack() {
        offhandAttack = true;
    }

    @Override
    public boolean isOffhandAttack() {
        return offhandAttack;
    }

    //IAttacker end
}