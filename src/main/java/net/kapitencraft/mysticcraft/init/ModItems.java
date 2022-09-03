package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MysticcraftMod.MOD_ID);

    public static final RegistryObject<Item> LONGBOW = REGISTRY.register("longbow", () -> new LongBowItem());
    public static final RegistryObject<Item> SLIVYRA = REGISTRY.register("slivyra", () -> new SlivyraItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> WIZARD_HAT = REGISTRY.register("wizard_hat", () -> new WizardHatItem(ModArmorMaterials.WIZARD_HAT, EquipmentSlot.HEAD, new Item.Properties().rarity(Rarity.RARE).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> STAFF_OF_THE_WILD = REGISTRY.register("staff_of_the_wild", () -> new StaffOfTheWildItem(new Item.Properties().rarity(Rarity.RARE).tab(CreativeModeTab.TAB_COMBAT)));
}
