package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.capability.mana.IManaStorage;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.gui.menu.PrismaticGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PrismaticGeneratorBlockEntity extends BlockEntity implements IManaStorage, MenuProvider {
    private static final int MAX_MANA = 10000;

    public PrismaticGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PRISMATIC_GENERATOR.get(), pPos, pBlockState);
    }

    private int mana;
    private int burnTime;
    private int rate;

    private final ItemStackHandler items = new ItemHandler();

    //region persistence

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("mana", this.mana);
        pTag.putInt("burnTime", this.burnTime);
        pTag.putInt("rate", this.rate);
        pTag.put("inventory", this.items.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.mana = pTag.getInt("mana");
        this.burnTime = pTag.getInt("burnTime");
        this.rate = pTag.getInt("rate");
        this.items.deserializeNBT(pTag.getCompound("inventory"));
    }

    //endregion

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, PrismaticGeneratorBlockEntity pBlockEntity) {
        if (pBlockEntity.mana > 0) {
            int mana = pBlockEntity.mana;
            for (Direction direction : Direction.values()) {
                BlockEntity entity = pLevel.getBlockEntity(pPos.relative(direction));
                if (entity instanceof IManaStorage manaStorage) {
                    if (manaStorage.canReceive())
                        pBlockEntity.mana -= manaStorage.receiveMana(pBlockEntity.mana, false);
                }
            }
            if (pBlockEntity.mana != mana) pBlockEntity.setChanged();
        }
        if (pBlockEntity.mana < MAX_MANA) {
            if (pBlockEntity.burnTime > 0) {
                pBlockEntity.burnTime--;
                pBlockEntity.mana = Math.min(MAX_MANA, pBlockEntity.mana + pBlockEntity.rate);
                pBlockEntity.setChanged();
            } else {
                ItemStack stack = pBlockEntity.items.extractItem(0, 1, false);
                if (!stack.isEmpty()) {
                    GemstoneType.Rarity rarity = IGemstoneItem.getGemRarity(stack);
                    GemstoneType type = IGemstoneItem.getGemstone(stack);
                    if (type == GemstoneType.SAPPHIRE) {
                        pBlockEntity.rate = 15 * (rarity.getLevel() + 1);
                    } else {
                        pBlockEntity.rate = (int) type.getBaseBlockStrenght() * (rarity.getLevel() + 1);
                    }
                    pBlockEntity.burnTime = 200;
                    pBlockEntity.setChanged();
                }
            }
        }
    }

    @Override
    public int receiveMana(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate) {
        int pull = Math.min(maxExtract, mana);
        if (!simulate) {
            mana -= pull;
        }
        return pull;
    }

    @Override
    public int getManaStored() {
        return mana;
    }

    @Override
    public int getMaxManaStored() {
        return MAX_MANA;
    }

    @Override
    public boolean canExtract() {
        return mana > 0;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
    }

    public IItemHandler getItems() {
        return items;
    }

    public boolean isLit() {
        return this.burnTime > 0;
    }

    public int getLitProgress() {
        return 13 * this.burnTime / 200;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.prismatic_generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PrismaticGeneratorMenu(pContainerId, pPlayerInventory, this);
    }

    private static class ItemHandler extends ItemStackHandler {
        public ItemHandler() {
            super(1);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return IGemstoneItem.getGemstone(stack) != GemstoneType.EMPTY;
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("mana", this.mana);
        tag.putInt("burnTime", this.burnTime);
        tag.putInt("rate", this.rate);
        return tag;
    }
}
