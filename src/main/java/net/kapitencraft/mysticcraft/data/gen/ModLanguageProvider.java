package net.kapitencraft.mysticcraft.data.gen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforges;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, MysticcraftMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        Reforges.all().keySet().forEach(string -> this.add("reforge." + string, filter(string)));
        ModItems.getEntries().forEach(this::registerItem);
    }

    private void registerItem(RegistryObject<Item> object) {
        this.add(object.get(), filter(object.getId().getPath()));
    }

    private String filter(String toName) {
        String val1 = toName.replace("_", " ");
        char[] chars = val1.toCharArray();
        return fromStrings(makeCapital(fromChars(chars)));
    }

    private String[] fromChars(char[] chars) {
        String[] strings = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            strings[i] = String.valueOf(chars[i]);
        }
        return strings;
    }

    private String fromStrings(String[] strings) {
        return String.join("", strings);
    }

    private String[] makeCapital(String[] input) {
        boolean nextCapital = false;
        for (int i = 0; i < input.length; i++) {
            if (Objects.equals(input[i], " ")) {
                nextCapital = true;
            } else if (nextCapital || i == 0){
                input[i] = input[i].toUpperCase();
                nextCapital = false;
            }
        }
        if (nextCapital) {
            String[] toReturn = new String[input.length-1];
            System.arraycopy(input, 0, toReturn, 0, toReturn.length);
            return toReturn;
        }
        return input;
    }
}
