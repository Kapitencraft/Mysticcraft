package net.kapitencraft.mysticcraft.data_gen;

import com.google.gson.JsonObject;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.capability.reforging.ReforgeStat;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforges;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
            Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(MysticcraftMod.MOD_ID).resolve("reforges").resolve(reforge.getType().getSerializedName()).resolve(reforge.getRegistryName() + ".json");
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
        reforges.add(new Reforge.Builder("mythic")
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(0.2, 0.4, 0.6, 0.8, 1., 1.2, 1.4))
                .addStat(Attributes.ARMOR, ReforgeStat.build(2., 3., 6., 8., 10., 12., 16.))
                .addStat(ExtraAttributes.STRENGTH, ReforgeStat.build(2., 4., 6., 8., 10., 12., 15.))
                .addStat(ExtraAttributes.CRIT_CHANCE, ReforgeStat.build(0.5, 0.8, 1.1, 1.5, 1.9, 2.4, 3.))
                .addStat(Attributes.MOVEMENT_SPEED, ReforgeStat.build(2., 2., 2., 2., 2., 2., 2.))
                .addStat(ExtraAttributes.INTELLIGENCE, ReforgeStat.build(15., 22., 30., 40., 52., 67., 90.))
                .build()
        );
        reforges.add(new Reforge.Builder("pure")
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(0.2, 0.3, 0.4, 0.6, 0.9, 1.1, 1.3))
                .addStat(Attributes.ARMOR, ReforgeStat.build(2., 2.5, 5., 6.8, 9., 10.8, 14.6))
                .addStat(ExtraAttributes.CRIT_CHANCE, ReforgeStat.build(1., 1.6, 2.2, 3.0, 3.8, 4.8, 6.))
                .addStat(ExtraAttributes.CRIT_DAMAGE, ReforgeStat.build(2., 3., 4., 6., 8., 10., 13.))
                .addStat(Attributes.MOVEMENT_SPEED, ReforgeStat.build(1., 1., 1., 1., 1., 1., 1.))
                .addStat(ExtraAttributes.INTELLIGENCE, ReforgeStat.build(2., 3., 5., 10., 17., 30., 45.))
                .addStat(ExtraAttributes.BONUS_ATTACK_SPEED, ReforgeStat.build(1., 1., 1.5, 2.1, 3., 4.1, 5.5))
                .build()
        );
        reforges.add(new Reforge.Builder("titanic")
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(1., 1.2, 1.5, 2., 2.8, 4.1, 6.))
                .addStat(Attributes.ARMOR, ReforgeStat.build(10., 15., 20., 25., 35., 50., 75.))
                .build()
        );
        reforges.add(Reforge.builder("smart")
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(0.4, 0.6, 0.8, 1., 1.2, 1.4, 1.6))
                .addStat(Attributes.ARMOR, ReforgeStat.build(4., 6., 9., 12., 15., 20., 30.))
                .addStat(ExtraAttributes.INTELLIGENCE, ReforgeStat.build(20., 40., 60., 80., 100., 130., 165.))
                .build()
        );
        reforges.add(Reforge.builder("wise")
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(0.6, 0.8, 1., 1.2, 1.5, 2., 2.8))
                .addStat(Attributes.MOVEMENT_SPEED, ReforgeStat.build(1., 1., 1., 2., 2., 3., 5.))
                .addStat(ExtraAttributes.INTELLIGENCE, ReforgeStat.build(30., 50., 80., 110., 145., 190., 250.))
                .build()
        );
    }
}
