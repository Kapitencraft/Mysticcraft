package net.kapitencraft.mysticcraft.datagen;

import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.data.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.data.reforging.Reforges;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ReforgeProvider implements DataProvider {
    private final List<Reforge> reforges = new ArrayList<>();
    private final PackOutput output;

    public ReforgeProvider(PackOutput output) {
        this.output = output;
    }


    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        Reforges.registerRarities();
        addReforges();
        CompletableFuture<?>[] futures = new CompletableFuture[reforges.size()];
        int i = 0;
        for (Reforge reforge : reforges) {
            Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(MysticcraftMod.MOD_ID).resolve("reforges").resolve(reforge.getType().getName()).resolve(reforge.getRegistryName() + ".json");
            JsonObject object = reforge.serialize();
            futures[i++] = DataProvider.saveStable(cachedOutput, object, path);
        }
        return CompletableFuture.allOf(futures);
    }



    @Override
    public @NotNull String getName() {
        return "Reforge Provider";
    }

    public void addReforges() {
    }
}
