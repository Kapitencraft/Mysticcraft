package net.kapitencraft.mysticcraft.entity.dragon;

import com.mojang.serialization.Dynamic;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Dragon extends Mob {
    private final NonNullList<ItemStack> armor = NonNullList.withSize(4, ItemStack.EMPTY);
    private final DragonBossEvent event;

    public Dragon(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.event = new DragonBossEvent(Component.translatable(Util.makeDescriptionId("entity", ForgeRegistries.ENTITY_TYPES.getKey(pEntityType))));
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void setHealth(float pHealth) {
        super.setHealth(pHealth);
        this.event.setProgress(pHealth / this.getMaxHealth());
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return armor;
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        return DragonBossAi.makeBrain(this, pDynamic);
    }

    @Override
    public @NotNull ItemStack getItemBySlot(EquipmentSlot pSlot) {
        if (pSlot.isArmor()) return armor.get(pSlot.getIndex());
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        if (pSlot.isArmor()) this.armor.set(pSlot.getIndex(), pStack);
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public boolean isFlying() {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer pServerPlayer) {
        this.event.addPlayer(pServerPlayer);
        super.startSeenByPlayer(pServerPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer pServerPlayer) {
        this.event.removePlayer(pServerPlayer);
        super.stopSeenByPlayer(pServerPlayer);
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("dragonBoss");
        Brain<Dragon> brain = this.getBrain();
        brain.tick((ServerLevel) this.level(), this);
        brain.setActiveActivityToFirstValid(DragonBossAi.ACTIVITIES);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, pLevel);
        return navigation;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull Brain<Dragon> getBrain() {
        return (Brain<Dragon>) super.getBrain();
    }

    @Contract("null->false")
    public boolean canTargetEntity(@Nullable Entity entity) {
        return entity instanceof LivingEntity living &&
                this.level() == entity.level() &&
                EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity) &&
                !this.isAlliedTo(entity) &&
                living.getType() != EntityType.ARMOR_STAND &&
                !living.isInvulnerable() &&
                !living.isDeadOrDying() &&
                this.level().getWorldBorder().isWithinBounds(living.getBoundingBox());
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }
}
