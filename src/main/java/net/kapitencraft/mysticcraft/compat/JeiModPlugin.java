package net.kapitencraft.mysticcraft.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

@JeiPlugin
public class JeiModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return MysticcraftMod.res("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        IIngredientSubtypeInterpreter<ItemStack> gemstoneInterpreter = (ingredient, context) -> IGemstoneItem.getGemId(ingredient) + IGemstoneItem.getGemRarity(ingredient).getId();
        registration.registerSubtypeInterpreter(ModItems.GEMSTONE.get(), gemstoneInterpreter);
        registration.registerSubtypeInterpreter(ModBlocks.GEMSTONE_BLOCK.getItem(), gemstoneInterpreter);
        registration.registerSubtypeInterpreter(ModBlocks.GEMSTONE_CRYSTAL.getItem(), gemstoneInterpreter);
        registration.registerSubtypeInterpreter(ModBlocks.GEMSTONE_SEED.getItem(), gemstoneInterpreter);

        registration.registerSubtypeInterpreter(ModItems.SPELL_SCROLL.get(), (ingredient, context) -> Objects.requireNonNull(ModRegistries.SPELLS.getKey(SpellScrollItem.getSpell(ingredient)), "unknown spell in item " + ingredient).toString());
    }
}
