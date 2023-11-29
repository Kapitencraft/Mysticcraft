package net.kapitencraft.mysticcraft.mixin.classes;

import com.mojang.datafixers.util.Pair;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.ModBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {

    Climate.Parameter FULL = Climate.Parameter.span(-1, 1);

    @Inject(method = "addUndergroundBiomes", at = @At("HEAD"))
    public void add(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo info) {
        MysticcraftMod.sendRegisterDisplay("Underground Biomes");
        callAddBottomBiome(consumer, FULL, FULL, FULL, FULL, FULL, 0, ModBiomes.GEMSTONE_GORGE);
    }

    @Invoker
    abstract void callAddBottomBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter depth, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome);
}
