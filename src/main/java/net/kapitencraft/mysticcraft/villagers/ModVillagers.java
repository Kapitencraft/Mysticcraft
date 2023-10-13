package net.kapitencraft.mysticcraft.villagers;

import com.google.common.collect.ImmutableSet;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.VillagerRegistryHolder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPE_REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.POI_TYPES);
    public static final DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.VILLAGER_PROFESSIONS);

    public static void registerPOIs() {
        try {
            Method method = ObfuscationReflectionHelper.findMethod(PoiTypes.class, "registerBlockStates", PoiType.class);
            method.invoke(null, GEMSTONE_MAKER.getPoi().get());
            method.invoke(null, GUILD_MASTER.getPoi().get());
        } catch (InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    private static VillagerRegistryHolder register(String poiName, String professionName, Supplier<PoiType> block, SoundEvent event) {
        RegistryObject<PoiType> poiType = POI_TYPE_REGISTRY.register(poiName, block);
        RegistryObject<VillagerProfession> profession = PROFESSION_REGISTRY.register(professionName, ()-> new VillagerProfession(professionName, x -> x.get() == poiType.get(), x -> x.get() == poiType.get(), ImmutableSet.of(), ImmutableSet.of(), event));
        return new VillagerRegistryHolder(poiType, profession);
    }

    private static Supplier<PoiType> createPoi(Supplier<Block> blockSupplier) {
        return ()-> new PoiType(ImmutableSet.copyOf(blockSupplier.get().getStateDefinition().getPossibleStates()), 1, 1);
    }

    public static final VillagerRegistryHolder GEMSTONE_MAKER = register("gemstone_grinder", "gemstone_maker", createPoi(ModBlocks.GEMSTONE_GRINDER.block()), SoundEvents.VILLAGER_WORK_WEAPONSMITH);
    public static final VillagerRegistryHolder GUILD_MASTER = register("guild_banner", "guild_master", createPoi(ModBlocks.GUILD_BOARD.block()), SoundEvents.VILLAGER_WORK_LIBRARIAN);
}
