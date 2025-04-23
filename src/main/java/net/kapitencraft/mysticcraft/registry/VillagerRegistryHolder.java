package net.kapitencraft.mysticcraft.registry;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.RegistryObject;

public class VillagerRegistryHolder {
    private final RegistryObject<PoiType> POI;
    private final RegistryObject<VillagerProfession> PROFESSION;


    public VillagerRegistryHolder(RegistryObject<PoiType> poi, RegistryObject<VillagerProfession> profession) {
        POI = poi;
        PROFESSION = profession;
    }

    public RegistryObject<VillagerProfession> getProfession() {
        return PROFESSION;
    }

    public RegistryObject<PoiType> getPoi() {
        return POI;
    }
}
