package net.kapitencraft.mysticcraft.client.rpg.perks;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import net.kapitencraft.mysticcraft.network.packets.S2C.UpdatePerksPacket;
import net.kapitencraft.mysticcraft.rpg.perks.Perk;
import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ClientPerks {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final Map<Perk, Integer> progress = Maps.newHashMap();
    private final Map<PerkTree, Integer> availableTokens = Maps.newHashMap();
    private final Map<ResourceLocation, PerkTree> trees = new HashMap<>();
    private final Map<ResourceLocation, Perk> perks = new HashMap<>();
    private final Set<Perk> leaves = Sets.newLinkedHashSet();
    private final Set<Perk> roots = Sets.newLinkedHashSet();
    private static final ClientPerks instance = new ClientPerks();
    private Listener listener;


    public static ClientPerks getInstance() {
        return instance;
    }


    public void update(UpdatePerksPacket perksPacket) {
        if (perksPacket.shouldReset()) {
            this.perks.clear();
            this.trees.clear();
            this.progress.clear();
            this.availableTokens.clear();
        }

        this.perks.remove(perksPacket.getRemoved());
        this.addPerks(perksPacket.getAdded());
        this.addTrees(perksPacket.getTreesAdded());

        for(Map.Entry<ResourceLocation, Integer> entry : perksPacket.getProgress().entrySet()) {
            Perk perk = this.perks.get(entry.getKey());
            if (perk != null) {
                Integer value = entry.getValue();
                this.progress.put(perk, value);
                if (this.listener != null) this.listener.onUpdateProgress(perk, value);
            } else {
                LOGGER.warn("Server informed client about progress for unknown perk {}", entry.getKey());
            }
        }

        for (Map.Entry<ResourceLocation, Integer> entry : perksPacket.getTreeProgress().entrySet()) {
            PerkTree tree = this.trees.get(entry.getKey());
            if (tree != null) {
                Integer value = entry.getValue();
                Integer previous = this.availableTokens.get(tree);
                int diff = value - (previous == null ? 0 : previous);
                if (diff > 0) Minecraft.getInstance().getToasts().addToast(new TokenAwardedToast(tree, diff));
                this.availableTokens.put(tree, value);
                if (this.listener != null) this.listener.onAvailableChange(tree, value);
            } else {
                LOGGER.warn("Server informed client about award for unknown perk-tree {}", entry.getKey());
            }
        }
    }

    private void addTrees(Map<ResourceLocation, PerkTree.Builder> treesAdded) {
        treesAdded.forEach((location, builder) -> {
            if (builder.canBuild(this.perks::get)) {
                PerkTree tree = builder.build(location);
                if (this.listener != null) this.listener.onTreeAdded(tree);
                this.trees.put(location, tree);
            } else LOGGER.warn("Couldn't load tree {}", location);
        });
    }

    private void addPerks(Map<ResourceLocation, Perk.Builder> pAdvancements) {
        Map<ResourceLocation, Perk.Builder> map = Maps.newHashMap(pAdvancements);

        while(!map.isEmpty()) {
            boolean flag = false;
            Iterator<Map.Entry<ResourceLocation, Perk.Builder>> iterator = map.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry<ResourceLocation, Perk.Builder> entry = iterator.next();
                ResourceLocation resourcelocation = entry.getKey();
                Perk.Builder advancement$builder = entry.getValue();
                if (advancement$builder.canBuild(this.perks::get)) {
                    Perk perk = advancement$builder.build(resourcelocation);
                    this.perks.put(resourcelocation, perk);
                    flag = true;
                    iterator.remove();
                    if (perk.getParent() == null) {
                        this.roots.add(perk);
                    } else {
                        this.leaves.add(perk);
                        if (this.listener != null) this.listener.onLeaveAdded(perk);
                    }
                }
            }

            if (!flag) {
                for(Map.Entry<ResourceLocation, Perk.Builder> entry1 : map.entrySet()) {
                    LOGGER.error("Couldn't load advancement {}: {}", entry1.getKey(), entry1.getValue());
                }
                break;
            }
        }

        LOGGER.info("Loaded {} advancements", this.perks.size());
    }

    public Set<Perk> getRoots() {
        return this.roots;
    }

    public void setListener(Listener listener) {
        this.listener = listener;

        this.trees.values().forEach(listener::onTreeAdded);
        this.leaves.forEach(listener::onLeaveAdded);
        this.progress.forEach(listener::onUpdateProgress);
        this.availableTokens.forEach(listener::onAvailableChange);
    }

    public interface Listener {
        void onTreeAdded(PerkTree root);

        void onUpdateProgress(Perk perk, int progress);

        void onLeaveAdded(Perk perk);

        void onAvailableChange(PerkTree tree, Integer value);
    }
}
