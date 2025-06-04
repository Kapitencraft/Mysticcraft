package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.data_gen.abst.RequirementProvider;
import net.kapitencraft.kap_lib.requirements.conditions.CustomStatReqCondition;
import net.kapitencraft.kap_lib.requirements.conditions.StatReqCondition;
import net.kapitencraft.kap_lib.requirements.type.RequirementType;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.registry.ModStatTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemRequirementsProvider extends RequirementProvider<Item> {
    protected ModItemRequirementsProvider(PackOutput output) {
        super(output, MysticcraftMod.MOD_ID, RequirementType.ITEM);
    }

    @Override
    protected void register() {
        this.add(Items.ELYTRA, new StatReqCondition(Stats.ENTITY_KILLED.get(EntityType.ENDER_DRAGON), 5));
        this.add(ModItems.HYPERION.get(), new CustomStatReqCondition(ModStatTypes.STORMS_KILLED, 10, "stat_req.storm"));
        this.add(ModItems.VALKYRIE.get(), new CustomStatReqCondition(ModStatTypes.NECRONS_KILLED, 10, "stat_req.necron"));
        this.add(ModItems.SCYLLA.get(), new CustomStatReqCondition(ModStatTypes.MAXORS_KILLED, 10, "stat_req.maxor"));
        this.add(ModItems.ASTREA.get(), new CustomStatReqCondition(ModStatTypes.GOLDORS_KILLED, 10, "stat_req.goldor"));
    }
}
