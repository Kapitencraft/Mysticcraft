package net.kapitencraft.mysticcraft.worldgen.gemstone;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.event.custom.RegisterGemstoneTypePlacementsEvent;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.Predicate;

public class GemstoneDecorator {
    private final HashMap<GemstoneType, Predicate<Holder<Biome>>> provider = new HashMap<>();

    public GemstoneDecorator() {
        ModEventFactory.fireModEvent(new RegisterGemstoneTypePlacementsEvent(provider));
    }

    @Nullable
    public GemstoneType makeType(Holder<Biome> biome) {
        GemstoneType[] array = MapStream.of(provider).filterValues(gemstoneValidator -> gemstoneValidator.test(biome), null)
                .toMap().keySet().toArray(GemstoneType[]::new);
        if (array.length == 0) return null;
        return array[Mth.nextInt(MysticcraftMod.RANDOM_SOURCE, 0, array.length - 1)];
    }

    @FunctionalInterface
    public interface GemstoneValidator {
        boolean valid(Holder<Biome> biome, ResourceKey<Level> dimension);
    }
}
