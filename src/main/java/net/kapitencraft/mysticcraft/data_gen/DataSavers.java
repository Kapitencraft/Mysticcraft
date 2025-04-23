package net.kapitencraft.mysticcraft.data_gen;

import com.google.gson.JsonArray;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataSavers {
    private static final String ITEM_SAVE_FILE = "Items";
    private static final String directory;


    static {
        try {
            directory = new File(".").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveItems() {
        List<String> allItems = ModItems.REGISTRY.getEntries().stream().map(RegistryObject::getId).map(ResourceLocation::toString).toList();
        JsonArray array = new JsonArray();
        allItems.forEach(array::add);
        try {
            FileWriter fileWriter = new FileWriter(directory + ".json");
            fileWriter.write(array.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ignored) {

        }
    }

    public static void loadItems() {

    }
}