package net.kapitencraft.mysticcraft.enchantments.armor;

import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;

public class BasaltWalkerEnchantment extends Enchantment {
    public BasaltWalkerEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static void onEntityMoved(LivingEntity living, BlockPos pos, int enchLevel) {
        if (living.isOnGround()) {
            Level level = living.level;
            BlockState blockstate = ModBlocks.FRAGILE_BASALT.get().defaultBlockState();
            float f = (float)Math.min(16, 2 + enchLevel);
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

            for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-f, -1.0D, -f), pos.offset(f, -1.0D, (double)f))) {
                if (blockpos.closerToCenterThan(living.position(), f)) {
                    mutableBlockPos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState state = level.getBlockState(mutableBlockPos);
                    if (state.isAir()) {
                        BlockState blockState = level.getBlockState(blockpos);
                        boolean isFull = blockState.getBlock() == Blocks.LAVA && blockState.getValue(LiquidBlock.LEVEL) == 0;
                        if (blockState.getMaterial() == Material.LAVA && isFull && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(living, net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, blockpos), net.minecraft.core.Direction.UP)) {
                            level.setBlockAndUpdate(blockpos, blockstate);
                            level.scheduleTick(blockpos, ModBlocks.FRAGILE_BASALT.get(), Mth.nextInt(living.getRandom(), 60, 120));
                        }
                    }
                }
            }

        }
    }

}
