package net.kapitencraft.mysticcraft.rpg.perks;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.S2C.UpdatePerksPacket;
import net.kapitencraft.mysticcraft.rpg.perks.rewards.PerkReward;
import net.minecraft.FileUtil;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;

public class PlayerPerks {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer()).setPrettyPrinting().create();
   private static final TypeToken<Map<ResourceLocation, Integer>> DATA_TOKEN = new TypeToken<>() {};
   private final ServerPerksManager manager;
   private final DataFixer dataFixer;
   private final Path playerSavePath;
   private final Map<Perk, Integer> progress = new HashMap<>();
   private final Map<PerkTree, Integer> availableTokens = new HashMap<>();
   private final Set<PerkTree> trees = new HashSet<>();
   private final Set<Perk> visible = new HashSet<>();
   private final Set<Perk> progressChanged = new HashSet<>();
   private final Set<Perk> rootsToUpdate = new HashSet<>();
   private final Set<PerkTree> treesAdded = new HashSet<>();
   private final Set<PerkTree> treesChanged = new HashSet<>();
   private ServerPlayer player;
   private boolean isFirstPacket = true;

   public PlayerPerks(DataFixer pDataFixer, ServerPerksManager pManager, Path pPlayerSavePath, ServerPlayer pPlayer) {
      this.dataFixer = pDataFixer;
      this.playerSavePath = pPlayerSavePath;
      this.player = pPlayer;
      this.manager = pManager;
      this.unlockTree(MysticcraftMod.res("mage"));
      this.load();
      pManager.getRoots().forEach(this::markForVisibilityUpdate);
   }

   public void unlockTree(ResourceLocation treeId) {
      PerkTree tree = this.manager.getTree(treeId);
      if (tree == null) {
         LOGGER.warn("unknown tree: {}", treeId);
         return;
      }
      this.trees.add(tree);
      this.treesAdded.add(tree);
   }

   public void setPlayer(ServerPlayer pPlayer) {
      this.player = pPlayer;
   }

   public void reload() {
      this.trees.clear();
      this.treesChanged.clear();
      this.treesAdded.clear();
      this.progress.clear();
      this.visible.clear();
      this.rootsToUpdate.clear();
      this.progressChanged.clear();
      this.isFirstPacket = true;
      this.unlockTree(MysticcraftMod.res("mage"));
      this.load();
   }

   private void load() {
      if (Files.isRegularFile(this.playerSavePath)) {
         try (JsonReader jsonreader = new JsonReader(Files.newBufferedReader(this.playerSavePath, StandardCharsets.UTF_8))) {
            jsonreader.setLenient(false);
            Dynamic<JsonElement> dynamic = new Dynamic<>(JsonOps.INSTANCE, Streams.parse(jsonreader));
            int i = dynamic.get("DataVersion").asInt(1343);
            dynamic = dynamic.remove("DataVersion");
            dynamic = DataFixTypes.ADVANCEMENTS.updateToCurrentVersion(this.dataFixer, dynamic, i);
            JsonObject object = dynamic.getValue().getAsJsonObject();
            Map<ResourceLocation, Integer> progress = GSON.getAdapter(DATA_TOKEN).fromJsonTree(object.get("perks"));
            if (progress == null) {
               throw new JsonParseException("Found null for perks");
            }
            progress.entrySet().stream().sorted(Entry.comparingByValue()).forEach((p_265663_) -> {
               Perk perk = this.manager.getPerk(p_265663_.getKey());
               if (perk == null) {
                  LOGGER.warn("Ignored perk '{}' in progress file {} - it doesn't exist anymore?", p_265663_.getKey(), this.playerSavePath);
               } else {
                  this.startProgress(perk, p_265663_.getValue());
                  this.progressChanged.add(perk);
                  this.updatePerk(perk, p_265663_.getValue());
                  this.markForVisibilityUpdate(perk);
               }
            });

            Map<ResourceLocation, Integer> available = GSON.getAdapter(DATA_TOKEN).fromJsonTree(object.get("trees"));
            for (Entry<ResourceLocation, Integer> entry : available.entrySet()) {
               PerkTree tree = this.manager.getTree(entry.getKey());
               this.unlockTree(entry.getKey());
               this.availableTokens.put(tree, entry.getValue());
            }

         } catch (JsonParseException jsonparseexception) {
            LOGGER.error("Couldn't parse player perks in {}", this.playerSavePath, jsonparseexception);
         } catch (IOException ioexception) {
            LOGGER.error("Couldn't access player perks in {}", this.playerSavePath, ioexception);
         }
      }
   }

   public void save() {
      Map<ResourceLocation, Integer> progressData = new HashMap<>();

      for(Entry<Perk, Integer> entry : this.progress.entrySet()) {
         int progress = entry.getValue();
         if (progress != 0) {
            progressData.put(entry.getKey().getId(), progress);
         }
      }

      JsonObject object = new JsonObject();

      object.add("perks", GSON.toJsonTree(progressData));
      object.addProperty("DataVersion", SharedConstants.getCurrentVersion().getDataVersion().getVersion());

      Map<ResourceLocation, Integer> availableData = new HashMap<>();

      for (Entry<PerkTree, Integer> entry : this.availableTokens.entrySet()) {
         int available = entry.getValue();
         if (available != 0) {
            availableData.put(entry.getKey().id(), available);
         }
      }

      object.add("trees", GSON.toJsonTree(availableData));

      try {
         FileUtil.createDirectoriesSafe(this.playerSavePath.getParent());

         try (Writer writer = Files.newBufferedWriter(this.playerSavePath, StandardCharsets.UTF_8)) {
            GSON.toJson(object, writer);
         }
      } catch (IOException ioexception) {
         LOGGER.error("Couldn't save player advancements to {}", this.playerSavePath, ioexception);
      }
   }

   public boolean upgrade(ResourceLocation perkId, ResourceLocation treeId, int count) {
      if (this.player instanceof net.minecraftforge.common.util.FakePlayer) return false;
      Perk pPerk = this.manager.getPerk(perkId);
      PerkTree tree = this.manager.getTree(treeId);
      if (pPerk == null || tree == null || this.perkDone(pPerk)) return false;
      if (this.availableTokens.get(tree) >= pPerk.getCost()) {
         this.award(treeId, -pPerk.getCost());
         int progress = this.getOrStartProgress(pPerk);
         progress = Math.min(progress + count, pPerk.getMaxLevel());
         this.progressChanged.add(pPerk);
         this.progress.put(pPerk, progress);
         this.updatePerk(pPerk, progress);
         this.markForVisibilityUpdate(pPerk);
         return true;
      }
      return false;
   }

   public void award(ResourceLocation treeId, int amount) {
      PerkTree tree = this.manager.getTree(treeId);
      if (tree == null) {
         LOGGER.warn("unknown tree: {}", treeId);
         return;
      }
      this.availableTokens.compute(tree, (tree1, integer) -> integer == null ? amount : integer + amount);
      this.treesChanged.add(tree);
   }

   private void updatePerk(Perk pPerk, int level) {
      PerkReward reward = pPerk.getReward();
      reward.revoke(this.player);
      reward.grant(level, this.player);
   }

   private void markForVisibilityUpdate(Perk pPerk) {
      this.rootsToUpdate.add(pPerk.getRoot());
   }

   public void flushDirty(ServerPlayer pServerPlayer) {
      if (this.isFirstPacket || !this.rootsToUpdate.isEmpty() || !this.progressChanged.isEmpty() || !this.treesChanged.isEmpty() || !this.treesAdded.isEmpty()) {
         Map<ResourceLocation, Integer> progress = new HashMap<>();
         Set<Perk> set = new HashSet<>();
         Set<ResourceLocation> set1 = new HashSet<>();

         for(Perk advancement : this.rootsToUpdate) {
            this.updateTreeVisibility(advancement, set, set1);
         }

         this.rootsToUpdate.clear();

         Map<ResourceLocation, Integer> treeProgress = new HashMap<>();
         for (PerkTree tree : this.treesChanged) {
            this.updateTreeVisibility(tree.root(), set, set1);
            treeProgress.put(tree.id(), this.availableTokens.get(tree));
         }
         this.treesChanged.clear();


         for(Perk advancement1 : this.progressChanged) {
            if (this.visible.contains(advancement1)) {
               progress.put(advancement1.getId(), this.progress.get(advancement1));
            }
         }

         for (PerkTree tree : this.treesAdded) {
            this.updateTreeVisibility(tree.root(), set, set1);
         }

         this.progressChanged.clear();
         if (!progress.isEmpty() || !set.isEmpty() || !set1.isEmpty() || !treeProgress.isEmpty() || !this.treesAdded.isEmpty()) {
            ModMessages.sendToClientPlayer(new UpdatePerksPacket(isFirstPacket, set, List.copyOf(this.treesAdded), set1, progress, treeProgress), pServerPlayer);
         }
         this.treesAdded.clear();
      }

      this.isFirstPacket = false;
   }

   public int getOrStartProgress(Perk pPerk) {
      return this.progress.computeIfAbsent(pPerk, p -> 0);
   }

   private void startProgress(Perk pPerk, int amount) {
      this.progress.put(pPerk, amount);
   }

   private int getProgressOrZero(Perk perk) {
      return this.progress.getOrDefault(perk, 0);
   }

   private void updateTreeVisibility(Perk pPerk, Set<Perk> pVisiblePerks, Set<ResourceLocation> pInvisiblePerks) {
      PerkVisibilityEvaluator.evaluateVisibilityNew(pPerk, this::getProgressOrZero, (perk, pVisible) -> {
         if (pVisible) {
            if (this.visible.add(perk)) {
               pVisiblePerks.add(perk);
               if (this.progress.containsKey(perk)) {
                  this.progressChanged.add(perk);
               }
            }
         } else if (this.visible.remove(perk)) {
            pInvisiblePerks.add(perk.getId());
         }
      });
   }

   private boolean perkDone(Perk perk) {
      return this.progress.get(perk) != null && this.progress.get(perk) == perk.getMaxLevel();
   }
}
