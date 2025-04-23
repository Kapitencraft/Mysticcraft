package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.kap_lib.data_gen.abst.EnglishLanguageProvider;
import net.kapitencraft.kap_lib.helpers.TextHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.data.PackOutput;

public class ModLanguageProvider extends EnglishLanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, MysticcraftMod.MOD_ID);
    }

    @Override
    protected void addTranslations() {

        Reforges.all().keySet().forEach(string -> this.add("reforge." + string, TextHelper.makeGrammar(string)));
        ModItems.getEntries().forEach(this::addItem);

        addDeathMessage("chain_lightning", "%1$s got hit by chain lightning from %2$s");
        addDeathMessage("mana_overflow", "%1$s stood to close to %2$s as they lost control over their mana");
        addDeathMessage("mana_overflow_self", "%1$s couldn't handle their mana");
        addDeathMessage("ability", "%1$s got killed by %2$s's ability");
        addDeathMessage("magic_explosion", "%1$s got exploded into a million pieces by %2$s's magic explosion");
        addDeathMessage("numbness", ""); //TODO add msg
        addDeathMessage("fire_lance", "%1$s was burned to ash by %1$s's fire lance");
    }
}
