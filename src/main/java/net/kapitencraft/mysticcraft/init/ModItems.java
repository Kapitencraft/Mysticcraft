package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.LongBowItem;
import net.kapitencraft.mysticcraft.item.WizardHatItem;
import net.kapitencraft.mysticcraft.item.spells.SlivyraItem;
import net.kapitencraft.mysticcraft.item.spells.StaffOfTheWildItem;
import net.kapitencraft.mysticcraft.item.spells.TheStaffOfDestruction;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MysticcraftMod.MOD_ID);
    public static final RegistryObject<Item> LONGBOW = REGISTRY.register("longbow", LongBowItem::new);
    public static final RegistryObject<Item> WIZARD_HAT = REGISTRY.register("wizard_hat", WizardHatItem::new);
    public static final RegistryObject<Item> THE_STAFF_DESTRUCTION = REGISTRY.register("staff_of_destruction", TheStaffOfDestruction::new);
    public static final RegistryObject<Item> SLIVYRA = REGISTRY.register("slivyra", SlivyraItem::new);

    public static final RegistryObject<Item> STAFF_OF_THE_WILD = REGISTRY.register("staff_of_the_wild", StaffOfTheWildItem::new);
}