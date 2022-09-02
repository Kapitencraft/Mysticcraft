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
    public static final RegistryObject<SwordItem> SLIVYRA = REGISTRY.register("slivyra", () -> new SlivyraItem(ModTiers.SLIVYRA_SWORD, 5, 0, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> WIZARD_HAT = REGISTRY.register("wizard_hat", () -> new WizardHatItem(ModArmorMaterials.WIZARD_HAT, EquipmentSlot.HEAD, new Item.Properties().rarity(Rarity.RARE).tab(CreativeModeTab.TAB_COMBAT)));
}
