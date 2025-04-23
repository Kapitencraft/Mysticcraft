package net.kapitencraft.mysticcraft.villagers;

import com.google.common.collect.ImmutableSet;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.VillagerRegistryHolder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPE_REGISTRY = MysticcraftMod.registry(ForgeRegistries.POI_TYPES);
    public static final DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = MysticcraftMod.registry(ForgeRegistries.VILLAGER_PROFESSIONS);

    private static VillagerRegistryHolder register(String poiName, String professionName, Supplier<PoiType> target, SoundEvent event) {
        RegistryObject<PoiType> poiType = POI_TYPE_REGISTRY.register(poiName, target);
        RegistryObject<VillagerProfession> profession = PROFESSION_REGISTRY.register(professionName, ()-> new VillagerProfession(professionName, x -> x.get() == poiType.get(), x -> x.get() == poiType.get(), ImmutableSet.of(), ImmutableSet.of(), event));
        return new VillagerRegistryHolder(poiType, profession);
    }

    private static Supplier<PoiType> createPoi(Supplier<Block> blockSupplier) {
        return ()-> new PoiType(ImmutableSet.copyOf(blockSupplier.get().getStateDefinition().getPossibleStates()), 2, 1);
    }

    public static final VillagerRegistryHolder GEMSTONE_MAKER = register("gemstone_grinder_poi", "gemstone_maker", createPoi(ModBlocks.GEMSTONE_GRINDER.block()), SoundEvents.VILLAGER_WORK_WEAPONSMITH);
}
