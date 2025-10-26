package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.gui.menu.ObeliskTurretMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class ObeliskTurretBlockEntity extends AbstractTurretBlockEntity implements MenuProvider {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    private int activeTicks;
    private int damage = 1;

    public ObeliskTurretBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.OBELISK_TURRET.get(), pPos, pBlockState, 6);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ObeliskTurretBlockEntity entity) {
        entity.updateTarget();
        if (entity.target != null) {
            if (!pLevel.isClientSide) {
                Vec3 center = new Vec3(pPos.getX() + .5, pPos.getY() + 14f / 16, pPos.getZ() + .5);
                List<Vec3> positions = MathHelper.makeLine(entity.target.getEyePosition(), center, .2f);
                for (Vec3 position : positions) {
                    ParticleHelper.sendParticles(pLevel, new DustParticleOptions(new Vector3f(1, 0, 0), .5f), false, position, 10, .05, .05, .05, 0);
                }
            }

            if ((entity.activeTicks & 7) == 0 && !entity.target.hurt(pLevel.damageSources().source(ModDamageTypes.SCORCH), entity.damage)) {
                entity.unselectTarget();
                entity.target = null;
                pLevel.setBlock(pPos, pState.setValue(POWERED, false), 18);
            } else if (!pState.getValue(POWERED)) pLevel.setBlock(pPos, pState.setValue(POWERED, true), 18);
            if ((entity.activeTicks++ & 31) == 0) entity.damage <<= 1;
        }
    }

    @Override
    protected void unselectTarget() {
        damage = 1;
        activeTicks = 0;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.turret.obelisk");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ObeliskTurretMenu(pContainerId, pPlayerInventory, this);
    }
}
