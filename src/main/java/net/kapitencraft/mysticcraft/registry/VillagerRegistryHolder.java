package net.kapitencraft.mysticcraft.registry;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;

public record VillagerRegistryHolder(Holder<PoiType> poi, Holder<VillagerProfession> profession) {
}
