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
import org.jetbrains.annotations.NotNull;

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
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
        return DEFAULT_HAMMER_ACTIONS.contains(toolAction);
    }

    private static final Set<ToolAction> DEFAULT_HAMMER_ACTIONS = of(ToolActions.PICKAXE_DIG, ToolActions.SWORD_SWEEP);

    private static Set<ToolAction> of(ToolAction... actions) {
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }

    public static void gatherBlocks(Level level, BlockPos origin, Direction clickedFace, Consumer<BlockPos> posSink) {
        BlockState state = level.getBlockState(origin);
        Direction.Axis[] axes = new Direction.Axis[2];
        int i = 0;
        for (Direction.Axis value : Direction.Axis.values()) {
            if (value != clickedFace.getAxis()) axes[i++] = value;
        }
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (!(a == 0 && b == 0)) {
                    BlockPos pos = origin.relative(axes[0], a).relative(axes[1], b);
                    if (state.is(level.getBlockState(pos).getBlock())) posSink.accept(pos);
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
