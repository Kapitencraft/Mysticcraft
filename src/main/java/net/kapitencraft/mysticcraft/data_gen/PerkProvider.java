package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.rpg.perks.Perk;
import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.kapitencraft.mysticcraft.rpg.perks.rewards.StatPerkReward;
import net.minecraft.advancements.FrameType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PerkProvider implements DataProvider {
    private final CompletableFuture<HolderLookup.Provider> registries;
    private final PackOutput.PathProvider perkProvider, treeProvider;

    public PerkProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this.registries = registries;
        perkProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "perks");
        treeProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "perk_trees");
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput pOutput) {
        return registries.thenCompose(provider -> {
            Set<ResourceLocation> perkLocations = new HashSet<>();
            Set<ResourceLocation> treeLocations = new HashSet<>();
            List<CompletableFuture<?>> list = new ArrayList<>();

            Consumer<PerkTree> treeConsumer = perkTree -> {
                if (!treeLocations.add(perkTree.id())) {
                    throw new IllegalStateException("Duplicate perk tree " + perkTree.id());
                } else {
                    Path path = this.treeProvider.json(perkTree.id());
                    PerkTree.Builder builder = perkTree.deconstruct();
                    list.add(DataProvider.saveStable(pOutput, builder.serializeToJson(), path));
                }
            };

            Consumer<Perk> perkConsumer = (perk) -> {
                if (!perkLocations.add(perk.getId())) {
                    throw new IllegalStateException("Duplicate perk " + perk.getId());
                } else {
                    if (perk.getMaxLevel() < 1) throw new IllegalStateException("perk without valid max level detected: " + perk.getId());
                    Path path = this.perkProvider.json(perk.getId());
                    Perk.Builder builder = perk.deconstruct();
                    if (!builder.valid()) throw new IllegalStateException("perk without rewards detected: " + perk.getId());
                    list.add(DataProvider.saveStable(pOutput, builder.serializeToJson(), path));
                }
            };

            this.generate(provider, perkConsumer, treeConsumer);

            return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
        });
    }

    protected void generate(HolderLookup.Provider provider, Consumer<Perk> sink, Consumer<PerkTree> treeConsumer) {

        Perk root = Perk.builder()
                .maxLevel(10)
                .display(ModItems.BUCKET_OF_MANA.get(), FrameType.TASK)
                .rewards(StatPerkReward.builder().add(ExtraAttributes.MAX_MANA.get(), 5, AttributeModifier.Operation.ADDITION))
                .save(sink, MysticcraftMod.res("mana_pool"));
        Perk manaPool2 = Perk.builder()
                .maxLevel(20)
                .cost(4)
                .childrenVisibleMin(10)
                .display(ModItems.BUCKET_OF_MANA.get(), FrameType.TASK)
                .rewards(StatPerkReward.builder().add(ExtraAttributes.MAX_MANA.get(), 10, AttributeModifier.Operation.ADDITION))
                .parent(root)
                .save(sink, MysticcraftMod.res("mana_pool_2"));
        Perk manaRegen = Perk.builder()
                .maxLevel(5)
                .cost(2)
                .display(IGemstoneItem.createData(GemstoneType.Rarity.PERFECT, GemstoneType.SAPPHIRE, ModItems.GEMSTONE), FrameType.TASK)
                .rewards(StatPerkReward.builder().add(ExtraAttributes.MANA_REGEN.get(), 1, AttributeModifier.Operation.ADDITION))
                .parent(manaPool2)
                .save(sink, MysticcraftMod.res("mana_regen"));
        Perk abilityDamage = Perk.builder()
                .maxLevel(5)
                .cost(3)
                .display(IGemstoneItem.createData(GemstoneType.Rarity.PERFECT, GemstoneType.ALMANDINE, ModItems.GEMSTONE), FrameType.TASK)
                .rewards(StatPerkReward.builder().add(ExtraAttributes.ABILITY_DAMAGE.get(), 2, AttributeModifier.Operation.ADDITION))
                .parent(manaPool2)
                .save(sink, MysticcraftMod.res("ability_damage"));

        PerkTree tree = PerkTree.builder(root)
                .icon(new ItemStack(ModItems.AOTE.get()))
                .background(new ResourceLocation("textures/block/amethyst_block.png"))
                .save(treeConsumer, MysticcraftMod.res("mage"));
    }

    @Override
    public @NotNull String getName() {
        return "Perks";
    }
}
