package net.kapitencraft.mysticcraft.data_gen;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.util.concurrent.CompletableFuture;

public class RPGClassProvider implements DataProvider {

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        return null;
    }

    @Override
    public String getName() {
        return "RPG Class Provider";
    }
}
