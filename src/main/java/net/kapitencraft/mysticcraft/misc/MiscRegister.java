package net.kapitencraft.mysticcraft.misc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.tags.ExtraTags;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.bestiary.BestiaryManager;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.inst.MysticcraftPlayerInstance;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.ITieredItem;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.capability.item_stat.ItemStatCapability;
import net.kapitencraft.mysticcraft.item.capability.reforging.ReforgeManager;
import net.kapitencraft.mysticcraft.item.combat.duel.DuelHandler;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.kapitencraft.mysticcraft.misc.content.EssenceHolder;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.S2C.SyncEssenceDataPacket;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.kapitencraft.mysticcraft.villagers.ModVillagers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;


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
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(CapabilityHelper.ESSENCE).isPresent()) {
                event.addCapability(MysticcraftMod.res("essence"), new EssenceHolder());
            }
        }
    }

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        MysticcraftMod.sendRegisterDisplay("Reloadables");
        event.addListener(new BestiaryManager());
        event.addListener(new ReforgeManager());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void changeAttackTarget(LivingChangeTargetEvent event) {
        LivingEntity newTarget = event.getNewTarget();
        if (newTarget != null && newTarget.isInvisible()) event.setCanceled(true);
    }


    @SubscribeEvent
    public static void entityTick(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (InventoryHelper.hasSetInInventory(player, ITieredItem.ItemTier.INFERNAL) && player instanceof ServerPlayer serverPlayer) {
                MiscHelper.awardAchievement(serverPlayer, MysticcraftMod.res("infernal_armor"));
            }
        }
        if (living instanceof Mob mob) {
            if (mob.getTarget() != null && mob.getTarget().isInvisible()) {
                mob.setTarget(null);
            }
        }
    }

    @SubscribeEvent
    public static void joinLevelEvent(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            if (arrow.getOwner() instanceof LivingEntity living) {
                ItemStack bow = living.getMainHandItem();
                CompoundTag arrowTag = arrow.getPersistentData();
                if (bow.is(ExtraTags.Items.HITS_ENDERMAN)) {
                    arrowTag.putBoolean("HitsEnderMan", true);
                }
            }
        }
        if (event.getEntity() instanceof Player player) {
            new MysticcraftPlayerInstance(player);
            AttributeInstance instance = player.getAttribute(ExtraAttributes.MANA.get());
            if (instance == null) throw new IllegalStateException();
            else {
                double mana;
                if (player.getPersistentData().contains("Mana", 6)) {
                    mana = player.getPersistentData().getDouble("Mana");
                } else mana = 100;
                instance.setBaseValue(mana);
            }
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                ModMessages.sendToClientPlayer(new SyncEssenceDataPacket(serverPlayer.getCapability(CapabilityHelper.ESSENCE).orElseGet(EssenceHolder::new)), serverPlayer);
                //ModMessages.sendToClientPlayer(SyncGemstoneDataToPlayerPacket.fromPlayer(serverPlayer), serverPlayer);
                //ModMessages.sendToClientPlayer(SyncElytraDataToPlayerPacket.fromPlayer(serverPlayer), serverPlayer);
                serverPlayer.getStats().sendStats(serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void leaveLevelEvent(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getPersistentData().putDouble("Mana", player.getAttributeValue(ExtraAttributes.MANA.get()));
        }
    }

    @SubscribeEvent
    public static void healthRegenRegister(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        if (event.getAmount() > 0) MiscHelper.createDamageIndicator(living, event.getAmount(), "heal");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void blockBreakRegister(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer(); ItemStack mainHandItem = player.getMainHandItem(); BlockState state = event.getState();

        if (state.is(ModTags.Blocks.FARMABLE) || state.is(ModTags.Blocks.FORAGEABLE)) {
            mainHandItem.getCapability(CapabilityHelper.ITEM_STAT).ifPresent(iItemStatHandler -> iItemStatHandler.increase(ItemStatCapability.Type.FARMED, 1));
        } else if (state.is(ModTags.Blocks.MINEABLE)) {
            mainHandItem.getCapability(CapabilityHelper.ITEM_STAT).ifPresent(iItemStatHandler -> iItemStatHandler.increase(ItemStatCapability.Type.MINED, 1));
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