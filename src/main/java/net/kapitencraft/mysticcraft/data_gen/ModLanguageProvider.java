package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.data_gen.abst.EnglishLanguageProvider;
import net.kapitencraft.kap_lib.helpers.TextHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;

import java.util.Comparator;

public class ModLanguageProvider extends EnglishLanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, MysticcraftMod.MOD_ID);
    }

    @Override
    protected void addTranslations() {

        Reforges.all().keySet().forEach(id -> this.add(Util.makeDescriptionId("reforge", id), TextHelper.makeGrammar(id.getPath())));
        ModItems.getEntries().stream().sorted(Comparator.comparing(o -> o.getKey().location())).forEach(this::addItem);

        addDeathMessage("mana_overflow", "%1$s stood to close to %2$s as they lost control over their mana");
        addDeathMessage("mana_overflow_self", "%1$s couldn't handle their mana");
        addDeathMessage("magic_explosion", "%1$s got exploded into a million pieces by %2$s's magic explosion");
        addDeathMessage("numbness", "%1$s didn't survive the backlash");
    }
}