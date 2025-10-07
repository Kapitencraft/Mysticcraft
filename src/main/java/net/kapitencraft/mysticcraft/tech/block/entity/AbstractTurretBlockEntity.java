package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.tech.block.UpgradableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class AbstractTurretBlockEntity extends UpgradableBlockEntity {
    protected final AABB checkArea; //TODO un-finalize when upgrades are added
    private UUID owner;
    protected Entity target;
    protected final TargetSelector targetSelector = new TargetSelector();

    public AbstractTurretBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, double radius) {
        super(pType, pPos, pBlockState);
        this.checkArea = new AABB(pPos.getX() - radius, pPos.getY() - radius, pPos.getZ() - radius, pPos.getX() + radius, pPos.getY() + radius, pPos.getZ() + radius);
    }

    protected void updateTarget() {
        if (this.target != null) {
            if (!checkArea.intersects(this.target.getBoundingBox())) {
                this.unselectTarget();
                this.target = null; //set target to null so super-classes do not implement that behaviour by themselves
            }
        }
        if (this.target == null && this.level != null) {
            this.selectTarget();
        }
    }

    /**
     * called when no target could be found and the turret has no target
     */
    protected abstract void unselectTarget();

    @SuppressWarnings("DataFlowIssue")
    protected void selectTarget() {
        List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, checkArea, living -> !living.isRemoved() && !living.isDeadOrDying() && !living.fireImmune());
        int i = 0;
        while (entities.size() > i && !canTarget(entities.get(i))) i++;
        if (entities.size() > i) //only update the target when there's actually a entity to target
            this.target = entities.get(i);
    }

    @Override
    public boolean canUpgrade(ItemStack upgradeModule) {
        return false;
    }

    protected boolean canTarget(LivingEntity living) {
        return this.targetSelector.test(living);
    }

    @Override
    public void upgrade(ItemStack stack) {

    }

    @Override
    public int upgradeSlots() {
        return 0;
    }

    @Override
    public ItemStackHandler getUpgrades() {
        return null;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("Owner", this.owner.toString());
        CompoundTag tag = new CompoundTag();
        this.targetSelector.serialize(tag);
        pTag.put("Selector", tag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.owner = UUID.fromString(pTag.getString("Owner"));
        this.targetSelector.deserialize(pTag.getCompound("Selector"));
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
        this.targetSelector.playersToIgnore.clear(); //clear entries and reset
        this.targetSelector.playersToIgnore.add(uuid);
    }

    //TODO target selector

    public static class TargetSelector implements Predicate<LivingEntity> {
        private final List<UUID> playersToIgnore = new ArrayList<>();

        public void addPlayer(Player player) {
            this.playersToIgnore.add(player.getUUID());
        }

        public void deserialize(CompoundTag tag) {
            ListTag playersToIgnore = tag.getList("PlayersToIgnore", 11);

        }

        private void serialize(CompoundTag tag) {
            ListTag listTag = new ListTag();
            playersToIgnore.stream().map(NbtUtils::createUUID).forEach(listTag::add);
            tag.put("PlayersToIgnore", listTag);
        }

        /**
         * @return whether the given entity may be targeted by the turret
         */
        @Override
        public boolean test(LivingEntity living) {
            return !(living instanceof Player player) || !playersToIgnore.contains(player.getUUID());
        }
    }

    private enum TargetOrder implements StringRepresentable {
        MOST_HEALTH(Comparator.comparingDouble(LivingEntity::getHealth)),
        MOST_ARMOR(Comparator.comparingInt(LivingEntity::getArmorValue)),
        LEAST_HEALTH(Comparator.comparingDouble(LivingEntity::getHealth).reversed()),
        LEAST_ARMOR(Comparator.comparingInt(LivingEntity::getArmorValue).reversed()),
        FASTEST(Comparator.comparingDouble(l -> l.getAttributeValue(Attributes.MOVEMENT_SPEED))),
        SLOWEST(FASTEST.comparator.reversed());

        private final Comparator<LivingEntity> comparator;

        TargetOrder(Comparator<LivingEntity> comparator) {
            this.comparator = comparator;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}