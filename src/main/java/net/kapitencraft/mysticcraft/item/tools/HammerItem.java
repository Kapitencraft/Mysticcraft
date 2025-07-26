package net.kapitencraft.mysticcraft.item.tools;

import com.google.common.collect.Sets;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HammerItem extends PickaxeItem {
    public static final TabGroup HAMMER_GROUP = new TabGroup(TabRegister.TabTypes.MOD_MATERIALS);
    public HammerItem(Properties properties, Tier tier, int pAttackDamageModifier) {
        super(tier, pAttackDamageModifier, -3.2f, properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.getDamageValue() < this.getMaxDamage(stack);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack old = itemStack.copy();
        old.setDamageValue(old.getDamageValue()+1);
        return old;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return DEFAULT_HAMMER_ACTIONS.contains(toolAction);
    }

    private static final Set<ToolAction> DEFAULT_HAMMER_ACTIONS = of(ToolActions.PICKAXE_DIG, ToolActions.SWORD_SWEEP);

    private static Set<ToolAction> of(ToolAction... actions) {
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }


    //TODO fix hardcoding
    public static void gatherBlocks(Level level, BlockPos origin, Direction clickedFace, Consumer<BlockPos> posSink) {
        BlockState state = level.getBlockState(origin);
        switch (clickedFace.getAxis()) {
            case X -> {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (!(y == 0 && z == 0)) {
                            BlockPos pos = origin.offset(0, y, z);
                            if (state.is(level.getBlockState(pos).getBlock())) posSink.accept(pos);
                        }
                    }
                }
            }
            case Y -> {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        if (!(x == 0 && z == 0)) {
                            BlockPos pos = origin.offset(x, 0, z);
                            if (state.is(level.getBlockState(pos).getBlock())) posSink.accept(pos);
                        }
                    }
                }
            }
            case Z -> {
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (!(y == 0 && x == 0)) {
                            BlockPos pos = origin.offset(x, y, 0);
                            if (state.is(level.getBlockState(pos).getBlock())) posSink.accept(pos);
                        }
                    }
                }
            }
        }

    }

    public static int getPositionId(BlockPos pos) {
        return ((((pos.getY() & 0x3FF) << 10) | (pos.getX() & 0x7FF)) << 11) | (pos.getZ() & 0x7FF);
    }

    @SuppressWarnings("DataFlowIssue")
    public static void abort(BlockPos pos, Direction direction) {
        gatherBlocks(Minecraft.getInstance().level, pos, direction, p -> Minecraft.getInstance().level.destroyBlockProgress(getPositionId(p), p, -1));
    }
}
