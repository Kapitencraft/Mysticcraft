package net.kapitencraft.mysticcraft.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.tags.ExtraTags;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.bestiary.BestiaryManager;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.reforging.ReforgeManager;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.kapitencraft.mysticcraft.network.packets.S2C.SyncManaDistributionNetworksPacket;
import net.kapitencraft.mysticcraft.registry.ModAttributes;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.kapitencraft.mysticcraft.villagers.ModVillagers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;


@EventBusSubscriber
public class MiscRegister {


    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
    }

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        MysticcraftMod.sendRegisterDisplay("Reloadables");
        event.addListener(new BestiaryManager());
        event.addListener(new ReforgeManager());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void changeAttackTarget(LivingChangeTargetEvent event) {
        LivingEntity newTarget = event.getNewAboutToBeSetTarget();
        if (newTarget != null && newTarget.isInvisible()) event.setCanceled(true);
    }


    @SuppressWarnings("removal")
    @SubscribeEvent
    public static void entityTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (InventoryHelper.hasSetInInventory(player, ITieredItem.ItemTier.INFERNAL) && player instanceof ServerPlayer serverPlayer) {
                MiscHelper.awardAchievement(serverPlayer, MysticcraftMod.res("infernal_armor"));
            }
        }
        if (entity instanceof Mob mob) {
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
    }

    @SubscribeEvent
    public static void onBlockEntityPlace(BlockEvent.EntityPlaceEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();
        for (Direction direction : Direction.values()) {
            BlockPos neighbourPos = pos.relative(direction);
            BlockState neighbourState = level.getBlockState(neighbourPos);
            if (neighbourState.is(ModBlocks.MANA_PORT.get())) {
                neighbourState.getBlock().onNeighborChange(neighbourState, level, neighbourPos, pos);
            }
        }
    }

    @SubscribeEvent
    public static void registerVillagerProfession(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        if (event.getType() == ModVillagers.GEMSTONE_MAKER.profession().value()) {
            Multimap<Integer, VillagerTrades.ItemListing> multimap = HashMultimap.create();
            for (GemstoneType type : GemstoneType.WITHOUT_EMPTY) {
                ItemStack sell = GemstoneType.allItems().get(type, GemstoneType.Rarity.ROUGH);
                multimap.put(1,
                        new BasicItemListing(getEmeraldCost(4), sell, 8, 5, 1.2f));
            }
            int i = 2;
            for (GemstoneType.Rarity rarity : GemstoneType.Rarity.WITHOUT_EMPTY) {
                if (rarity != GemstoneType.Rarity.PERFECT) {
                    for (GemstoneType type : GemstoneType.WITHOUT_EMPTY) {
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

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
    }

    @SubscribeEvent
    public static void onOnDatapackSync(OnDatapackSyncEvent event) {
        Consumer<ServerPlayer> syncData = player -> {
            PacketDistributor.sendToPlayer(player, new SyncManaDistributionNetworksPacket(DistributionNetworkManager.get(player.level())));
            player.getStats().sendStats(player);
        };
        ServerPlayer player = event.getPlayer();

        if (player != null) {
            syncData.accept(player);
        } else {
            event.getRelevantPlayers().forEach(syncData);
        }
    }

    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.CAST_DURATION);
        ModAttributes.XP_BOOSTS.values().forEach(h -> event.add(EntityType.PLAYER, h));
    }

}