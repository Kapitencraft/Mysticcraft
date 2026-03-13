package net.kapitencraft.mysticcraft.event.handler;

import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Either;
import net.kapitencraft.kap_lib.bonus.event.custom.RegisterBonusProvidersEvent;
import net.kapitencraft.kap_lib.requirement.event.custom.RegisterRequirementTypesEvent;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.elytra.ElytraAttachment;
import net.kapitencraft.mysticcraft.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.kapitencraft.mysticcraft.item.tools.HammerItem;
import net.kapitencraft.mysticcraft.network.packets.S2C.HammerAbortBreakPacket;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.ModMobEffects;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.requirement.type.PerkRequirementType;
import net.kapitencraft.mysticcraft.requirement.type.ReforgeRequirementType;
import net.kapitencraft.mysticcraft.rpg.skill.PlayerPlacedBlocks;
import net.kapitencraft.mysticcraft.rpg.skill.PlayerSkills;
import net.kapitencraft.mysticcraft.rpg.skill.Skill;
import net.kapitencraft.mysticcraft.rpg.skill.xp.SkillXpMaps;
import net.kapitencraft.mysticcraft.rpg.skill.xp.provider.item.ItemStackXpProvider;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.capability.PlayerSpells;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.brewing.PlayerBrewedPotionEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEnchantItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.HashMap;

@EventBusSubscriber
public class Events {

    @SubscribeEvent
    public static void onMobEffect(MobEffectEvent.Remove event) {
        LivingEntity entity = event.getEntity();
        if (event.getEffect() == ModMobEffects.NUMBNESS) {
            entity.hurt(entity.damageSources().source(ModDamageTypes.NUMBNESS), entity.getData(ModAttachmentTypes.NUMBNESS_DAMAGE));
        }
    }

    @SubscribeEvent
    public static void onRegisterBonusProviders(RegisterBonusProvidersEvent.ItemBound event) {
        event.register(MysticcraftMod.res("reforge"), Reforges::getReforgeBonus);
    }

    //region spell item

    @SubscribeEvent
    public static void onLivingEntityUseItemStart(PlayerInteractEvent.RightClickItem event) {
        InteractionHand hand = event.getHand();
        Player player = event.getEntity();
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(ModTags.Items.CATALYST)) {
            SpellSlot slot = SpellHelper.getActiveSpellSlot(player);
            Holder<Spell> spell = slot.getSpell();
            if (spell != Spells.EMPTY) {
                Level level = event.getLevel();
                stack.remove(ModDataComponentTypes.SPELL_TARGET);
                if (SpellHelper.canExecuteSpell(player, spell.value(), stack)) {
                    if (spell.value().castDuration() == 0) SpellHelper.handleManaAndExecute(player, spell, slot.getLevel(), stack);
                    else player.startUsingItem(hand);
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (event.getFrom().is(ModTags.Items.CATALYST) || event.getTo().is(ModTags.Items.CATALYST)) {
                PlayerSpells.updateSlot(player, event.getTo());
            }
        }
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void onLivingEntityUseItemTick(LivingEntityUseItemEvent.Tick event) {
        ItemStack stack = event.getItem();
        if (!stack.is(ModTags.Items.CATALYST) || !(event.getEntity() instanceof Player player)) return;
        Level level = player.level(); //TODO entity spells
        int duration = stack.getUseDuration(player) - event.getDuration();
        SpellSlot slot = SpellHelper.getActiveSpellSlot(player);
        Holder<Spell> spellHolder = slot.getSpell();
        Spell spell = spellHolder.value();
        if (duration >= spell.castDuration()) {
            if (!SpellHelper.handleManaAndExecute(player, spellHolder, slot.getLevel(), stack) || spell.getType() == Spell.Type.RELEASE) player.stopUsingItem();
        } else {
            SpellTarget<?> target = spell.getTarget();
            SpellTarget.Type<?> type = target.getType();
            if (type == SpellTarget.Type.SELF) {
                if (!((SpellTarget<LivingEntity>) target).test(player)) {
                    player.stopUsingItem();
                }
            } else {
                Either<BlockPos, Integer> either = stack.get(ModDataComponentTypes.SPELL_TARGET);
                if (type == SpellTarget.Type.BLOCK) {
                    BlockHitResult result = level.clip(new ClipContext(
                            player.getEyePosition(),
                            player.getLookAngle().scale(100).add(player.getEyePosition()),
                            ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player
                    ));
                    if (result.getType() != HitResult.Type.MISS && ((SpellTarget<BlockState>) target).test(level.getBlockState(result.getBlockPos()))) {
                        if (either != null && either.left().isPresent()) {
                            if (!either.left().get().equals(result.getBlockPos())) {
                                if (level.isClientSide)
                                    player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                                player.stopUsingItem();
                            }
                        } else stack.set(ModDataComponentTypes.SPELL_TARGET, Either.left(result.getBlockPos()));
                    } else {
                        if (level.isClientSide)
                            player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                        player.stopUsingItem();
                    }
                } else if (type == SpellTarget.Type.ENTITY) {
                    Vec3 start = player.getEyePosition();
                    Vec3 end = player.getLookAngle().scale(100).add(player.getEyePosition());
                    EntityHitResult result = ProjectileUtil.getEntityHitResult(level, player,
                            start,
                            end,
                            new AABB(start, end),
                            entity -> entity != player,
                            0
                    );
                    if (result != null && ((SpellTarget<Entity>) target).test(result.getEntity())) {
                        if (either != null && either.right().isPresent()) {
                            if (either.right().get() != result.getEntity().getId()) {
                                if (level.isClientSide) player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                                player.stopUsingItem();
                            }
                        } else
                            stack.set(ModDataComponentTypes.SPELL_TARGET, Either.right(result.getEntity().getId()));
                    } else {
                        if (level.isClientSide)
                            player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                        player.stopUsingItem();
                    }
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
                case ABORT -> PacketDistributor.sendToPlayersInDimension((ServerLevel) event.getEntity().level(), new HammerAbortBreakPacket(pos, face));
            }
        }
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        Reforge reforge = Reforge.getFromStack(stack);
        EquipmentSlot slot = stack.getEquipmentSlot();
        if (stack.getItem() instanceof ArmorItem armorItem)
            slot = armorItem.getEquipmentSlot();
        else if (stack.is(Items.ELYTRA))
            slot = EquipmentSlot.CHEST;
        else if (slot == null)
            slot = EquipmentSlot.MAINHAND;
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(slot);
        if (reforge != null) {
            HashMap<Holder<Attribute>, Double> map = reforge.applyModifiers(stack.getRarity());
            //TODO add reqs
            map.forEach((attribute, value) -> event.addModifier(attribute, new AttributeModifier(Reforge.MODIFIER_ID, value, AttributeModifier.Operation.ADD_VALUE), group));
        }
        GemstoneHandler handler = stack.get(ModDataComponentTypes.EMBEDDED_GEMSTONES);
        if (handler != null) {
            Multimap<Holder<Attribute>, AttributeModifier> modifiers = handler.getAttributeModifiers(stack);
            modifiers.forEach((attributeHolder, modifier) -> event.addModifier(attributeHolder, modifier, group));
        }
        ElytraAttachment attachment = stack.get(ModDataComponentTypes.ELYTRA);
        if (attachment != null && attachment.data() == ElytraData.GRAVITY_BOOST) {
            event.addModifier(Attributes.GRAVITY, new AttributeModifier(MysticcraftMod.res("elytra"), attachment.level() * -.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.CHEST);
        }
    }

    @SubscribeEvent
    public static void onRegisterRequirementTypes(RegisterRequirementTypesEvent event) {
        event.add(ReforgeRequirementType.INSTANCE);
        event.add(PerkRequirementType.INSTANCE);
    }

    @SubscribeEvent
    public static void onRegisterDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(SkillXpMaps.FISHING);
        event.register(SkillXpMaps.COMBAT);
        event.register(SkillXpMaps.FARMING);
        event.register(SkillXpMaps.ENCHANTING);
        event.register(SkillXpMaps.MINING);
        event.register(SkillXpMaps.FORAGING);
        event.register(SkillXpMaps.ALCHEMY);
    }

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        Player player = event.getEntity();
        int xpToGet = 0;
        for (ItemStack drop : event.getDrops()) {
            ItemStackXpProvider xp = drop.getItemHolder().getData(SkillXpMaps.FISHING);
            if (xp == null) {
                PlayerSkills.LOGGER.warn("unable to retrieve fishing xp for item {}", drop);
                continue;
            }
            xpToGet += xp.get(drop);
        }
        if (xpToGet > 0 && player instanceof ServerPlayer sp) {
            PlayerSkills.reward(sp, Skill.FISHING, xpToGet, true);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        Block block = state.getBlock();
        if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
            Holder<Block> holder = state.getBlockHolder();
            if (block instanceof CropBlock || block instanceof NetherWartBlock) {
                if (block instanceof CropBlock cropBlock ? cropBlock.isMaxAge(state) : state.getValue(BlockStateProperties.AGE_3) == 3) {
                    Integer xp = holder.getData(SkillXpMaps.FARMING);
                    if (xp != null) {
                        PlayerSkills.reward(serverPlayer, Skill.FARMING, xp, true);
                    } else {
                        PlayerSkills.LOGGER.warn("unable to retrieve farming xp for crop {}", state);
                    }
                }
            } else {
                if (!PlayerPlacedBlocks.get(serverPlayer.level()).hasBlock(event.getPos())) {
                    Integer miningXp = holder.getData(SkillXpMaps.MINING);
                    if (miningXp != null) {
                        PlayerSkills.reward(serverPlayer, Skill.MINING, miningXp, true);
                    } else {
                        Integer foragingXp = holder.getData(SkillXpMaps.FORAGING);
                        if (foragingXp != null) {
                            PlayerSkills.reward(serverPlayer, Skill.FORAGING, foragingXp, true);
                        } else {
                            PlayerSkills.LOGGER.warn("unable to retrieve mining / foraging xp for block {}", state);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerEnchantItem(PlayerEnchantItemEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) {
            int xpToGain = 0;
            for (EnchantmentInstance enchantment : event.getEnchantments()) {
                Integer xp = enchantment.enchantment.getData(SkillXpMaps.ENCHANTING);
                if (xp != null) xpToGain += xp * enchantment.level;
                else
                    PlayerSkills.LOGGER.warn("unable to get xp for enchantment: {}", enchantment.enchantment);
            }
            if (xpToGain > 0) {
                PlayerSkills.reward((ServerPlayer) player, Skill.ENCHANTING, xpToGain, true);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockEntityPlace(BlockEvent.EntityPlaceEvent event) {
        if (!event.getLevel().isClientSide()) {
            PlayerPlacedBlocks placedBlocks = PlayerPlacedBlocks.get(event.getLevel());
            if (event.getEntity() instanceof ServerPlayer) {
                placedBlocks.addBlock(event.getPos());
            } else {
                placedBlocks.removeBlock(event.getPos());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerBrewedPotion(PlayerBrewedPotionEvent event) {
        PotionContents contents = event.getStack().get(DataComponents.POTION_CONTENTS);
        if (contents != null && contents.potion().isPresent() && event.getEntity() instanceof ServerPlayer player) {
            Integer alchemyXp = contents.potion().get().getData(SkillXpMaps.ALCHEMY);
            if (alchemyXp != null) {
                PlayerSkills.reward(player, Skill.ALCHEMY, alchemyXp, true);
            } else {
                PlayerSkills.LOGGER.warn("unable to get alchemy xp for potion {}", contents);
            }
        }
    }
}