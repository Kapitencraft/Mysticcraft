package net.kapitencraft.mysticcraft.villagers;

import com.google.common.collect.ImmutableSet;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.VillagerRegistryHolder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> REGISTRY = DeferredRegister.create(ForgeRegistries.POI_TYPES, MysticcraftMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MysticcraftMod.MOD_ID);


    private static VillagerRegistryHolder register(String poiName, String professionName, Block block, SoundEvent event) {
        RegistryObject<PoiType> poiType = REGISTRY.register(poiName, ()-> new PoiType(ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates()), 1, 1));
        RegistryObject<VillagerProfession> profession = PROFESSION_REGISTRY.register(professionName, ()-> new VillagerProfession(professionName, x -> x.get() == poiType.get(), x -> x.get() == poiType.get(), ImmutableSet.of(), ImmutableSet.of(), event));
        return new VillagerRegistryHolder(poiType, profession);
    }

    //public static final VillagerRegistryHolder GEMSTONE_MAKER = register("gemstone_grinder", "gemstone_maker", ModBlocks.GEMSTONE_GRINDER.getBlock(), SoundEvents.VILLAGER_WORK_WEAPONSMITH);


}
