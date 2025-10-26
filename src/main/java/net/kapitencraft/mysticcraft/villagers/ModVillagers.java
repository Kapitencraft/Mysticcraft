package net.kapitencraft.mysticcraft.villagers;

import com.google.common.collect.ImmutableSet;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.VillagerRegistryHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPE_REGISTRY = MysticcraftMod.registry(Registries.POINT_OF_INTEREST_TYPE);
    public static final DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = MysticcraftMod.registry(Registries.VILLAGER_PROFESSION);

    private static VillagerRegistryHolder register(String poiName, String professionName, Supplier<PoiType> target, SoundEvent event) {
        Holder<PoiType> poiType = POI_TYPE_REGISTRY.register(poiName, target);
        Holder<VillagerProfession> profession = PROFESSION_REGISTRY.register(professionName, ()-> new VillagerProfession(professionName, x -> x.value() == poiType.value(), x -> x.value() == poiType.value(), ImmutableSet.of(), ImmutableSet.of(), event));
        return new VillagerRegistryHolder(poiType, profession);
    }

    private static Supplier<PoiType> createPoi(Supplier<? extends Block> blockSupplier) {
        return ()-> new PoiType(ImmutableSet.copyOf(blockSupplier.get().getStateDefinition().getPossibleStates()), 2, 1);
    }

    public static final VillagerRegistryHolder GEMSTONE_MAKER = register("gemstone_grinder_poi", "gemstone_maker", createPoi(ModBlocks.ARTIFICER_TABLE), SoundEvents.VILLAGER_WORK_WEAPONSMITH);
}
