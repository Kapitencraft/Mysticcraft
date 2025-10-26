package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.kapitencraft.mysticcraft.tech.gui.menu.SpellCasterTurretMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellCasterTurretBlockEntity extends AbstractTurretBlockEntity implements MenuProvider {
    private static final double MAX_DISTANCE = 10;

    private Entity target;

    private final ItemHandler inventory = new ItemHandler();
    private int castDuration = 0;
    private SpellSlot spell;

    public SpellCasterTurretBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SPELL_CASTER_TURRET.get(), pPos, pBlockState, MAX_DISTANCE);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SpellCasterTurretBlockEntity entity) {
        entity.updateTarget();
        SpellSlot slot = entity.spell;
        if (entity.target != null && slot != null  && entity.castDuration++ >= slot.getSpell().value().castDuration()) {
            entity.castDuration = 0;
            SpellCastContext.Builder builder = new SpellCastContext.Builder();
            builder.addParam(SpellCastContextParams.TARGET, entity.target);
            try {
                slot.getSpell().value().cast(builder.build(pLevel, slot.getLevel()));
            } catch (SpellExecutionFailedException ignored) {}
            entity.target = null;
        }
    }

    @SuppressWarnings({"DataFlowIssue"})
    @Override
    protected void selectTarget() {
        ItemStack stack = this.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            SpellSlot spell = SpellScrollItem.getSpell(stack);
            List<Entity> entities = this.level.getEntitiesOfClass(Entity.class, checkArea, (SpellTarget<Entity>) spell.getSpell().value().getTarget());
            if (!entities.isEmpty()) this.target = entities.get(0);
        }
    }

    @Override
    protected void unselectTarget() {
        this.castDuration = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("inventory", this.inventory.serializeNBT(registries));
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.turret.spell_caster");
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
            if (!stack.isEmpty()) SpellCasterTurretBlockEntity.this.spell = SpellScrollItem.getSpell(stack);
            SpellCasterTurretBlockEntity.this.setChanged();
        }
    }
}
