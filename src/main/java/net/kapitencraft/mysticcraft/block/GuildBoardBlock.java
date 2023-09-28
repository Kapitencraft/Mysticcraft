package net.kapitencraft.mysticcraft.block;

import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildBlockProtection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GuildBoardBlock extends Block {
    private Guild guild;

    public GuildBoardBlock() {
        super(Properties.copy(Blocks.OBSIDIAN));
    }

    @Override
    public void destroy(@NotNull LevelAccessor accessor, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (guild != null) {
            GuildBlockProtection.remove(guild, pos, 1);
        }
    }
}
