package net.kapitencraft.mysticcraft.misc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.Queue;
import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.bestiary.BestiaryManager;
import net.kapitencraft.mysticcraft.content.ChainLightningHelper;
import net.kapitencraft.mysticcraft.enchantments.HealthMendingEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModBowEnchantment;
import net.kapitencraft.mysticcraft.enchantments.armor.BasaltWalkerEnchantment;
import net.kapitencraft.mysticcraft.enchantments.weapon.ranged.OverloadEnchantment;
import net.kapitencraft.mysticcraft.event.custom.AddGemstonesToItemEvent;
import net.kapitencraft.mysticcraft.helpers.*;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.kapitencraft.mysticcraft.inst.MysticcraftPlayerInstance;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.ITieredItem;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.capability.item_stat.ItemStatCapability;
import net.kapitencraft.mysticcraft.item.capability.reforging.ReforgeManager;
import net.kapitencraft.mysticcraft.item.combat.duel.DuelHandler;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ModBowItem;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.kapitencraft.mysticcraft.misc.content.EssenceHolder;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.kapitencraft.mysticcraft.tags.ModBlockTags;
import net.kapitencraft.mysticcraft.tags.ModItemTags;
import net.kapitencraft.mysticcraft.villagers.ModVillagers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Mod.EventBusSubscriber
public class MiscRegister {
    public static final String DOUBLE_JUMP_ID = "currentDoubleJump";

    @SubscribeEvent
    public static void loadingLevel(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel && serverLevel.dimension() == Level.OVERWORLD) {
            MinecraftServer server = serverLevel.getServer();
            DuelHandler.setInstance(serverLevel.getDataStorage().computeIfAbsent(tag -> DuelHandler.load(tag, server), DuelHandler::new, "duels"));
        }
    }


    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(CapabilityHelper.ESSENCE).ifPresent(oldStore ->
                    event.getOriginal().getCapability(CapabilityHelper.ESSENCE).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));
        }
    }

    @SubscribeEvent
    public static void registerGemstones(AddGemstonesToItemEvent event) {
        event.registerArmor(ModItems.ENDER_KNIGHT_ARMOR, new GemstoneSlot.Builder(GemstoneSlot.Type.DEFENCE, GemstoneSlot.Type.OFFENCE, GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.STRENGTH));
        event.register(ModItems.VALKYRIE, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.STRENGTH));
        event.register(ModItems.HYPERION, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.INTELLIGENCE));
        event.register(ModItems.SCYLLA, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT));
        event.register(ModItems.ASTREA, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.DEFENCE));
        event.register(ModItems.NECRON_SWORD, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT));
        event.register(ModItems.LONGBOW, new GemstoneSlot.Builder(GemstoneSlot.Type.OFFENCE, GemstoneSlot.Type.DRAW_SPEED));
        event.register(ModItems.MANA_STEEL_SWORD, new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT));
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(CapabilityHelper.ESSENCE).isPresent()) {
                event.addCapability(MysticcraftMod.res("essence"), new EssenceHolder());
            }
        }
    }

    @SubscribeEvent
    public static void registerEffectAddModifications(MobEffectEvent.Added event) {
        if (event.getEffectInstance().getEffect().isBeneficial() && event.getEntity().hasEffect(ModMobEffects.STUN.get())) {
            event.getEntity().removeEffect(ModMobEffects.STUN.get());
        }
    }

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        MysticcraftMod.sendRegisterDisplay("Reloadables");
        event.addListener(new BestiaryManager());
        event.addListener(new ReforgeManager());
    }

    @SubscribeEvent
    public static void modArrowEnchantments(ArrowLooseEvent event) {
        event.setCharge((int) (event.getCharge() * event.getEntity().getAttributeValue(ModAttributes.DRAW_SPEED.get()) / 100));
        Player player = event.getEntity();
        ItemStack bow = event.getBow();
        if (bow.getItem() instanceof ModBowItem) return;
        ModBowItem.addAllExtraArrows(bow, player, 0);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void changeAttackTarget(LivingChangeTargetEvent event) {
        LivingEntity newTarget = event.getNewTarget();
        if (newTarget != null && newTarget.isInvisible()) event.setCanceled(true);
    }



    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void tickVeinMiner(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            VeinMinerHolder.tickAll();
        }
    }


    private static final Queue<UUID> helper = Queue.create();
    public static final String OVERFLOW_MANA_ID = "overflowMana";


    @SubscribeEvent
    public static void entityTick(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();
        BonusHelper.tickEnchantments(living);
        CompoundTag tag = living.getPersistentData();
        int i = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.BASALT_WALKER.get(), living);
        if (i > 0) {
            BasaltWalkerEnchantment.onEntityMoved(living, living.blockPosition(), i);
        }
        if (living instanceof Player player) {
            MobEffectInstance stunInstance = player.getEffect(ModMobEffects.STUN.get());
            if (stunInstance != null && player.level.isClientSide) {
                int duration = stunInstance.getDuration();
                TextHelper.setHotbarDisplay(player, Component.translatable("effect.stun.timer",  MiscHelper.stabiliseDouble(String.valueOf(duration / 20.), 2)).withStyle(ChatFormatting.RED));
            }
            if (tag.contains(SpellItem.SPELL_EXECUTION_DUR)) {
                if (tag.getByte(SpellItem.SPELL_EXECUTION_DUR) < 1 || tag.getString(SpellItem.SPELL_EXE).length() >= 7) {
                    ItemStack mainHand = living.getMainHandItem();
                    if (mainHand.getItem() instanceof SpellItem spellItem) {
                        spellItem.executeSpell(tag.getString(SpellItem.SPELL_EXE), mainHand, living);
                        tag.putString(SpellItem.SPELL_EXE, "");
                    }
                    TextHelper.clearTitle(player);
                } else {
                    tag.putByte(SpellItem.SPELL_EXECUTION_DUR, (byte) (tag.getByte(SpellItem.SPELL_EXECUTION_DUR) - 1));
                }
            }
            if (InventoryHelper.hasSetInInventory(player, ITieredItem.ItemTier.INFERNAL)) {
                MiscHelper.awardAchievement(player, "mysticcraft:infernal_armor");
            }
            if (!player.isOnGround()) {
                if (canJump(player) && tag.getInt(DOUBLE_JUMP_ID) < player.getAttributeValue(ModAttributes.DOUBLE_JUMP.get())) {
                    if (player.jumping && player.noJumpDelay <= 0) {
                        ParticleHelper.sendAlwaysVisibleParticles(ParticleTypes.CLOUD, player.level, player.getX(), player.getY(), player.getZ(), 0.25, 0.0, 0.25, 0,0,0, 15);
                        player.noJumpDelay = 10; player.fallDistance = 0;
                        Vec3 targetLoc = MathHelper.setLength(player.getLookAngle().multiply(1, 0, 1), 0.75).add(0, 1, 0);
                        player.setDeltaMovement(targetLoc.x, targetLoc.y > 0 ? targetLoc.y : -targetLoc.y, targetLoc.z);
                        IOHelper.increaseIntegerTagValue(player.getPersistentData(), DOUBLE_JUMP_ID, 1);
                    }
                }
            } else if (tag.getInt(DOUBLE_JUMP_ID) > 0) {
                tag.putInt(DOUBLE_JUMP_ID, 0);
            }
        }
        if (living instanceof Mob mob) {
            if (mob.getTarget() != null && mob.getTarget().isInvisible()) {
                mob.setTarget(null);
            }
        }
    }


    @SubscribeEvent
    public static void endermanEvent(EnderManAngerEvent event) {
        Player player = event.getPlayer();
        if (player.getItemBySlot(EquipmentSlot.HEAD).getEnchantmentLevel(ModEnchantments.ENDER_FRIEND.get()) > 0) {
            event.setCanceled(true);
        }
    }

    private static boolean canJump(Player player) {
        return !player.isOnGround() && !(player.isPassenger() || player.getAbilities().flying) && !(player.isInWater() || player.isInLava());
    }

    @SubscribeEvent
    public static void serverTick(TickEvent.LevelTickEvent event) {
        if (event.level instanceof ServerLevel serverLevel) {
            List<UUID> helperMembers = helper.getAll();
            helper.startQueuing();
            helperMembers.forEach(uuid -> {
                Arrow arrow = (Arrow) serverLevel.getEntity(uuid);
                if (arrow != null) {
                    CompoundTag arrowTag = arrow.getPersistentData();
                    ModBowEnchantment.loadFromTag(null, arrowTag, ModBowEnchantment.ExecuteType.TICK, 0, arrow);
                } else {
                    helper.remove(uuid);
                }
            });
            helper.stopQueuing();
        }
        ChainLightningHelper.tick();
    }

    @SubscribeEvent
    public static void joinLevelEvent(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            if (arrow.getOwner() instanceof LivingEntity living) {
                ItemStack bow = living.getMainHandItem();
                CompoundTag arrowTag = arrow.getPersistentData();
                if (bow.is(ModItemTags.ENDER_HITTABLE)) {
                    arrowTag.putBoolean("HitsEnderMan", true);
                }
                for (Enchantment enchantment : bow.getAllEnchantments().keySet()) {
                    if (enchantment instanceof ModBowEnchantment bowEnchantment) {
                        arrowTag.put(bowEnchantment.getTagName(), bowEnchantment.write(bow.getEnchantmentLevel(enchantment), bow, living, arrow));
                        if (bowEnchantment.shouldTick()) helper.add(arrow.getUUID());
                    }
                    if (enchantment instanceof OverloadEnchantment) {
                        arrowTag.putInt("OverloadEnchant", bow.getEnchantmentLevel(ModEnchantments.OVERLOAD.get()));
                    }
                }
            }
        }
        if (event.getEntity() instanceof Player player) {
            new MysticcraftPlayerInstance(player);
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                NetworkingHelper.syncAll(serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void healthRegenRegister(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        HealingHelper.HealReason reason = HealingHelper.getAndRemoveReason(living);
        if (reason == HealingHelper.HealReason.NATURAL && living.getAttribute(ModAttributes.HEALTH_REGEN.get()) != null) {
            double health_regen = living.getAttributeValue(ModAttributes.HEALTH_REGEN.get());
            event.setAmount(event.getAmount() * (1 + (float) health_regen / 100));
        }
        if (living.getAttribute(ModAttributes.VITALITY.get()) != null) {
            double vitality = living.getAttributeValue(ModAttributes.VITALITY.get());
            event.setAmount(event.getAmount() * (1 + (float) vitality / 100));
        }
        if (living instanceof Player player) event.setAmount(HealthMendingEnchantment.repairPlayerItems(player, event.getAmount()));
        if (event.getAmount() > 0) MiscHelper.createDamageIndicator(living, event.getAmount(), "heal");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void telekinesisXpRegister(LivingExperienceDropEvent event) {
        Player attacker = event.getAttackingPlayer();
        if (attacker != null) {
            ItemStack mainHand = attacker.getMainHandItem();
            if (mainHand.getEnchantmentLevel(ModEnchantments.TELEKINESIS.get()) > 0) {
                addXp(attacker, event.getDroppedExperience());
                event.setCanceled(true);
            }
        }
    }

    private static void addXp(Player player, int amount) {
        player.giveExperiencePoints(MiscHelper.repairPlayerItems(player, amount, Enchantments.MENDING));
    }

    @SubscribeEvent
    public void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        event.setCanceled(Requirement.doesntMeetReqsFromEvent(event));
    }



    @SuppressWarnings("all")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void blockBreakRegister(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer(); ItemStack mainHandItem = player.getMainHandItem(); BlockState state = event.getState();
        Block block = state.getBlock(); Level level = player.level; final BlockPos pos = event.getPos();

        ServerLevel serverLevel = level instanceof ServerLevel serverLevel1 ? serverLevel1 : null;
        if (serverLevel == null) return;
        ServerPlayer serverPlayer = (ServerPlayer) player;
        LootContext.Builder context = (new LootContext.Builder(serverLevel)).withRandom(level.random).withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ())).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)).withParameter(LootContextParams.TOOL, mainHandItem).withOptionalParameter(LootContextParams.THIS_ENTITY, serverPlayer);
        if (block instanceof CropBlock || block instanceof NetherWartBlock) {
            IntegerProperty ageProperty = block instanceof CropBlock cropBlock ? cropBlock.getAgeProperty() : BlockStateProperties.AGE_3;
            if (state.getValue(ageProperty) < MathHelper.getHighest(ageProperty.getPossibleValues())) {
                if (mainHandItem.getEnchantmentLevel(ModEnchantments.DELICATE.get()) > 0) {
                    event.setCanceled(true);
                    return;
                }
            } else {
            }

            if (mainHandItem.getEnchantmentLevel(ModEnchantments.REPLENISH.get()) > 0) {
                event.setCanceled(true);
                Block.dropResources(state, context);
                state.setValue(ageProperty, 0);
                level.setBlockAndUpdate(pos, state);
            }
        }
        if (state.is(ModBlockTags.FARMABLE) || state.is(ModBlockTags.FORAGEABLE)) {
            mainHandItem.getCapability(CapabilityHelper.ITEM_STAT).ifPresent(iItemStatHandler -> iItemStatHandler.increase(ItemStatCapability.Type.FARMED, 1));
        } else if (state.is(ModBlockTags.MINEABLE)) {
            mainHandItem.getCapability(CapabilityHelper.ITEM_STAT).ifPresent(iItemStatHandler -> iItemStatHandler.increase(ItemStatCapability.Type.MINED, 1));
        }
        if (mainHandItem.getEnchantmentLevel(ModEnchantments.LUMBERJACK.get()) > 0 && state.is(BlockTags.LOGS)) {
            MiscHelper.mineMultiple(pos, serverPlayer, block, pos1 -> {}, state1 -> true, pos1 -> false);
        }
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            MiscHelper.getEnchantmentLevelAndDo(mainHandItem, ModEnchantments.VEIN_MINER.get(), integer -> {
                Reference<Integer> brokenBlocks = Reference.of(-1);
                MiscHelper.mineMultiple(pos, serverPlayer, block,
                        pos1 -> MathHelper.up1(brokenBlocks),
                        state1 -> true, pos1 -> brokenBlocks.getIntValue() > integer);
            });
        }
        MiscHelper.getEnchantmentLevelAndDo(mainHandItem, ModEnchantments.EXPERIENCED.get(), integer -> {
            MathHelper.add(event::getExpToDrop, event::setExpToDrop, integer);
        });
        if (mainHandItem.getEnchantmentLevel(ModEnchantments.TELEKINESIS.get()) > 0) {
            addXp(player, event.getExpToDrop());
            event.setExpToDrop(0);
        }
    }



    @SubscribeEvent
    public static void registerVillagerProfession(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        if (event.getType() == ModVillagers.GEMSTONE_MAKER.getProfession().get()) {
            Multimap<Integer, VillagerTrades.ItemListing> multimap = HashMultimap.create();
            for (GemstoneType type : GemstoneType.TYPES_TO_USE) {
                ItemStack sell = GemstoneType.allItems().get(type, GemstoneType.Rarity.ROUGH);
                multimap.put(1,
                        new BasicItemListing(getEmeraldCost(4), sell, 8, 5, 1.2f));
            }
            int i = 2;
            for (GemstoneType.Rarity rarity : GemstoneType.RARITIES_TO_USE) {
                if (rarity != GemstoneType.Rarity.PERFECT) {
                    for (GemstoneType type : GemstoneType.TYPES_TO_USE) {
                        ItemStack defRarity = GemstoneType.allItems().get(type).get(rarity);
                        ItemStack newRarity = GemstoneType.allItems().get(type).get(rarity.next());
                        int c = Mth.nextInt(RandomSource.create(), 1, 5);
                        multimap.put(i,
                                new BasicItemListing(getEmeraldCost((int) Math.pow(i, 2)), defRarity.copyWithCount(c), newRarity.copyWithCount(c), 8 - i, i + 6, (float) Math.pow(i, -1.5))
                        );
                    }
                    i++;
                }
            }
            multimap.keySet().forEach(integer -> {
                Collection<VillagerTrades.ItemListing> values = multimap.get(integer);
                trades.put(integer, values.stream().toList());
            });
        }
    }

    @SubscribeEvent
    public static void disableToss(ItemTossEvent event) {
        Player player = event.getPlayer();
        if (SoulbindHelper.isSoulbound(event.getEntity().getItem())) {
            event.setCanceled(true);
            player.displayClientMessage(Component.translatable("soulbound.toss"), false);
        }
    }

    private static ItemStack getEmeraldCost(int emeralds) {
        if (emeralds <= 64) {
            return new ItemStack(Items.EMERALD).copyWithCount(emeralds);
        } else {
            int emeraldBlocks = emeralds / 9;
            return new ItemStack(Items.EMERALD_BLOCK).copyWithCount(emeraldBlocks);
        }
    }
}