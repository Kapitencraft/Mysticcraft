package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.kap_lib.event.custom.RegisterBonusProvidersEvent;
import net.kapitencraft.kap_lib.event.custom.RegisterRequirementTypesEvent;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.tools.HammerItem;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.S2C.HammerAbortBreakPacket;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.requirement.type.ReforgeRequirementType;
import net.kapitencraft.mysticcraft.rpg.classes.RPGClassManager;
import net.kapitencraft.mysticcraft.rpg.perks.ServerPerksManager;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.capability.PlayerSpells;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events {

    @SubscribeEvent
    public static void onRegisterBonusProviders(RegisterBonusProvidersEvent event) {
        event.register(MysticcraftMod.res("reforge"), Reforges::getReforgeBonus);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ServerPerksManager.clearCache();
        RPGClassManager.clearCache();
    }

    //region spell item

    @SubscribeEvent
    public static void onLivingEntityUseItemStart(PlayerInteractEvent.RightClickItem event) {
        InteractionHand hand = event.getHand();
        LivingEntity entity = event.getEntity();
        ItemStack stack = entity.getItemInHand(hand);
        if (stack.is(ModTags.Items.CATALYST)) {
            SpellSlot slot = SpellHelper.getActiveSlot(stack);
            Spell spell = slot.getSpell();
            if (spell != Spells.EMPTY.get()) {
                Level level = event.getLevel();
                if (stack.hasTag()) stack.getTag().remove("target");
                if (SpellHelper.canExecuteSpell(entity, spell, stack)) {
                    if (spell.castDuration() == 0) SpellHelper.handleManaAndExecute(entity, spell, slot.getLevel(), stack);
                    else entity.startUsingItem(hand);
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (event.getFrom().is(ModTags.Items.CATALYST) || event.getTo().is(ModTags.Items.CATALYST)) {
                PlayerSpells.get(player).updateSlot();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void onLivingEntityUseItemTick(LivingEntityUseItemEvent.Tick event) {
        ItemStack stack = event.getItem();
        LivingEntity living = event.getEntity();
        if (!stack.is(ModTags.Items.CATALYST)) return;
        Level level = living.level();
        int duration = stack.getUseDuration() - event.getDuration();
        SpellSlot slot = SpellHelper.getActiveSlot(stack);
        Spell spell = slot.getSpell();
        if (duration >= spell.castDuration()) {
            if (!SpellHelper.handleManaAndExecute(living, spell, slot.getLevel(), stack) || spell.getType() == Spell.Type.RELEASE) living.stopUsingItem();
        } else {
            SpellTarget<?> target = spell.getTarget();
            SpellTarget.Type<?> type = target.getType();
            if (type == SpellTarget.Type.SELF) {
                if (!((SpellTarget<LivingEntity>) target).test(living)) {
                    living.stopUsingItem();
                }
            } else if (type == SpellTarget.Type.BLOCK) {
                BlockHitResult result = level.clip(new ClipContext(
                        living.getEyePosition(),
                        living.getLookAngle().scale(100).add(living.getEyePosition()),
                        ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, living
                ));
                if (result.getType() != HitResult.Type.MISS && ((SpellTarget<BlockState>) target).test(level.getBlockState(result.getBlockPos()))) {
                    CompoundTag tag = stack.getTag();
                    if (tag != null && tag.contains("target", Tag.TAG_LONG)) {
                        if (!BlockPos.of(tag.getLong("target")).equals(result.getBlockPos())) {
                            if (living instanceof Player player && level.isClientSide)
                                player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                            living.stopUsingItem();
                        }
                    } else stack.getOrCreateTag().putLong("target", result.getBlockPos().asLong());
                } else {
                    if (living instanceof Player player && level.isClientSide)
                        player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                    living.stopUsingItem();
                }
            } else if (type == SpellTarget.Type.ENTITY) {
                Vec3 start = living.getEyePosition();
                Vec3 end = living.getLookAngle().scale(100).add(living.getEyePosition());
                EntityHitResult result = ProjectileUtil.getEntityHitResult(level, living,
                        start,
                        end,
                        new AABB(start, end),
                        entity -> entity != living,
                        0
                );
                if (result != null && ((SpellTarget<Entity>) target).test(result.getEntity())) {
                    CompoundTag tag = stack.getTag();
                    if (tag != null && tag.contains("target", Tag.TAG_LONG)) {
                        if (tag.getInt("target") != result.getEntity().getId()) {
                            if (living instanceof Player player && level.isClientSide) player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                            living.stopUsingItem();
                        }
                    } else stack.getOrCreateTag().putLong("target", result.getEntity().getId());
                } else {
                    if (living instanceof Player player && level.isClientSide)
                        player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                    living.stopUsingItem();
                }
            }
        }
    }

    //endregion

    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent
    public static void onPlayerInteractLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pos = event.getPos();
        Direction face = event.getFace();
        Entity entity = event.getEntity();
        Level level = entity.level();
        if (event.getItemStack().is(ModTags.Items.HAMMER)) {
            switch (event.getAction()) {
                case CLIENT_HOLD -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    int destroyStage = minecraft.gameMode.getDestroyStage();
                    HammerItem.gatherBlocks(level, pos, face, p -> level.destroyBlockProgress(HammerItem.getPositionId(p), p, destroyStage), 1);
                }
                case STOP -> {
                    if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                        HammerItem.gatherBlocks(level, pos, face, serverPlayer.gameMode::destroyBlock, 1);
                    }
                }
                case ABORT -> ModMessages.sendToAllConnectedPlayers(p -> new HammerAbortBreakPacket(pos, face), (ServerLevel) event.getEntity().level());
            }
        }
    }

    @SubscribeEvent
    public void onRegisterRequirementTypes(RegisterRequirementTypesEvent event) {
        event.add(ReforgeRequirementType.INSTANCE);
    }
}