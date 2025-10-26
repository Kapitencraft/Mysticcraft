package net.kapitencraft.mysticcraft.block.gemstone;

import net.kapitencraft.mysticcraft.gui.artificer_table.ArtificerTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ArtificerTableBlock extends Block {
    private static final Component MENU_TITLE = Component.translatable("container.artificer_table");

    public ArtificerTableBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE));
    }

    /* BLOCK ENTITY */

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) ->
                new ArtificerTableMenu(pContainerId, pPlayer, ContainerLevelAccess.create(pLevel, pPos)),
                MENU_TITLE
        );
    }
}