package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.config.ServerModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class VeinMinerHolder {
    private static final List<VeinMinerHolder> ALL = new ArrayList<>();
    private static boolean ticking = false;
    private final List<BlockPos> iterator = new ArrayList<>();
    private final ServerPlayer serverPlayer;
    private final Block block;
    private final Consumer<BlockPos> extra;
    private final Predicate<BlockState> shouldMine;
    private final Predicate<BlockPos> shouldBreak;

    public VeinMinerHolder(ServerPlayer serverPlayer, Block block, Consumer<BlockPos> extra, Predicate<BlockState> shouldMine, Predicate<BlockPos> shouldBreak) {
        this.serverPlayer = serverPlayer;
        this.block = block;
        this.extra = extra;
        this.shouldMine = shouldMine;
        this.shouldBreak = shouldBreak;
    }

    public VeinMinerHolder start(BlockPos pos) {
        if (!ticking) {
            this.iterator.add(pos);
            ALL.add(this);
        }
        return this;
    }

    public boolean tick() {
        ItemStack mainHandItem = this.serverPlayer.getMainHandItem();
        BlockPos extraPos = iterator.get(0);
        iterator.remove(0);
        for (BlockPos blockPos : Values()) {
            if (!(blockPos.getX() != 0 && blockPos.getY() != 0 && blockPos.getZ() != 0)) {
                BlockPos pos1 = new BlockPos(blockPos.getX() + extraPos.getX(), blockPos.getY() + extraPos.getY(), blockPos.getZ() + extraPos.getZ());
                BlockState state = this.serverPlayer.level.getBlockState(pos1);
                if (block == state.getBlock() && shouldMine.test(state)) {
                    mainHandItem.hurtAndBreak(1, serverPlayer,
                            serverPlayer1 -> serverPlayer1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    if (mainHandItem.isEmpty()) {
                        return true;
                    }
                    extra.accept(pos1);
                    breakBlock(pos1, serverPlayer);
                    iterator.add(pos1);
                    if (shouldBreak.test(pos1)) break;
                }
            }
        }
        return shouldBreak.test(extraPos) || iterator.isEmpty();
    }

    private static void breakBlock(BlockPos pos, ServerPlayer player) {
        player.gameMode.destroyBlock(pos);
    }

    public static void tickAll() {
        for (int i = 0; i < ServerModConfig.iterationMaxBroken; i++) {
            ticking = true;
            ALL.removeIf(VeinMinerHolder::tick);
            ticking = false;
        }
    }

    public static BlockPos[] Values() {
        BlockPos[] returnValue = new BlockPos[26];
        int x = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (!(i == 0 && j == 0 && k == 0)) {
                        returnValue[x] = new BlockPos(i, j, k);
                        x++;
                    }
                }
            }
        }
        return returnValue;
    }
}
