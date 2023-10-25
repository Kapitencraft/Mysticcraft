package net.kapitencraft.mysticcraft.mixin.classes;


import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.QuiverItem;
import net.kapitencraft.mysticcraft.item.tools.ContainableHolder;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Predicate;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

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
                for(int i = 0; i < own().getInventory().getContainerSize(); ++i) {
                    ItemStack stack1 = own().getInventory().getItem(i);
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

}