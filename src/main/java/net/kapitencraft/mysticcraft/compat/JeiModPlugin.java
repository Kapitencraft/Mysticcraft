package net.kapitencraft.mysticcraft.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.ItemGemstoneData;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@JeiPlugin
public class JeiModPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return MysticcraftMod.res("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        ISubtypeInterpreter<ItemStack> gemstoneInterpreter = new ISubtypeInterpreter<>() {
            @Override
            public @NotNull Object getSubtypeData(ItemStack ingredient, UidContext context) {
                ItemGemstoneData gemstoneData = ingredient.getOrDefault(ModDataComponentTypes.ITEM_GEMSTONE_DATA, new ItemGemstoneData(GemstoneType.RUBY, GemstoneType.Rarity.ROUGH));
                return gemstoneData.type().getId() + gemstoneData.rarity().getId();
            }

            @Override
            public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
                ItemGemstoneData gemstoneData = ingredient.getOrDefault(ModDataComponentTypes.ITEM_GEMSTONE_DATA, new ItemGemstoneData(GemstoneType.RUBY, GemstoneType.Rarity.ROUGH));
                return gemstoneData.type().getId() + gemstoneData.rarity().getId();
            }
        };
        registration.registerSubtypeInterpreter(ModItems.GEMSTONE.get(), gemstoneInterpreter);
        registration.registerSubtypeInterpreter(ModBlocks.GEMSTONE_BLOCK.getItem(), gemstoneInterpreter);
        registration.registerSubtypeInterpreter(ModBlocks.GEMSTONE_CRYSTAL.getItem(), gemstoneInterpreter);
        registration.registerSubtypeInterpreter(ModBlocks.GEMSTONE_SEED.getItem(), gemstoneInterpreter);

        registration.registerSubtypeInterpreter(ModItems.SPELL_SCROLL.get(), new ISubtypeInterpreter<>() {
            @Override
            public @NotNull Object getSubtypeData(@NotNull ItemStack ingredient, @NotNull UidContext context) {
                SpellSlot spellSlot = SpellScrollItem.getSpell(ingredient);
                if (spellSlot == null) return "null";
                return ingredient.getDescriptionId() + Objects.requireNonNull(spellSlot.getSpell().getKey(), "unknown spell in item " + ingredient).location() + "$" + spellSlot.getLevel();
            }

            @Override
            public String getLegacyStringSubtypeInfo(ItemStack ingredient, @NotNull UidContext context) {
                SpellSlot spellSlot = SpellScrollItem.getSpell(ingredient);
                if (spellSlot == null) return "null";
                return ingredient.getDescriptionId() + Objects.requireNonNull(spellSlot.getSpell().getKey(), "unknown spell in item " + ingredient).location() + "$" + spellSlot.getLevel();
            }
        });
    }
}
