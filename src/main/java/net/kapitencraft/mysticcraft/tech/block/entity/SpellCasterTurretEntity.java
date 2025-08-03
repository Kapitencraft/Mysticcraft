package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.kapitencraft.mysticcraft.tech.gui.menu.SpellCasterTurretMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellCasterTurretEntity extends AbstractTurretBlockEntity implements MenuProvider {
    private static final double MAX_DISTANCE = 10;

    private Entity target;

    private final ItemHandler inventory = new ItemHandler();
    private int castDuration = 0;
    private Spell spell;

    public SpellCasterTurretEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SPELL_CASTER_TURRET.get(), pPos, pBlockState, MAX_DISTANCE);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SpellCasterTurretEntity entity) {
        entity.updateTarget();
        if (entity.target != null && entity.spell != null  && entity.castDuration++ >= entity.spell.castDuration()) {
            entity.castDuration = 0;
            SpellCastContext.Builder builder = new SpellCastContext.Builder();
            builder.addParam(SpellCastContextParams.TARGET, entity.target);
            try {
                entity.spell.cast(builder.build(pLevel));
            } catch (SpellExecutionFailedException ignored) {}
            entity.target = null;
        }

    }

    @SuppressWarnings({"DataFlowIssue", "unchecked"})
    @Override
    protected void selectTarget() {
        ItemStack stack = this.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            Spell spell = SpellScrollItem.getSpell(stack);
            List<Entity> entities = this.level.getEntitiesOfClass(Entity.class, checkArea, (SpellTarget<Entity>) spell.getTarget());
            if (!entities.isEmpty()) this.target = entities.get(0);
        }
    }

    @Override
    protected void unselectTarget() {
        this.castDuration = 0;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.put("inventory", this.inventory.serializeNBT());
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        this.inventory.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.spell_caster_turret");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new SpellCasterTurretMenu(pContainerId, pPlayerInventory, this);
    }

    private class ItemHandler extends ItemStackHandler {
        public ItemHandler() {
            super(1);
        }

        @Override
        protected void onContentsChanged(int slot) {
            ItemStack stack = this.getStackInSlot(0);
            if (!stack.isEmpty()) SpellCasterTurretEntity.this.spell = SpellScrollItem.getSpell(stack);
            SpellCasterTurretEntity.this.setChanged();
        }
    }
}
