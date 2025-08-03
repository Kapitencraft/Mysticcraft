package net.kapitencraft.mysticcraft.data_gen;

import com.google.gson.JsonObject;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.capability.reforging.ReforgeStat;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.item.bonus.SacredBonus;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ReforgeProvider implements DataProvider {
    private final List<Reforge> reforges = new ArrayList<>();
    private final PackOutput output;
    private final String modId;

    public ReforgeProvider(PackOutput output, String modId) {
        this.output = output;
        this.modId = modId;
    }


    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        Reforges.registerRarities();
        addReforges();
        CompletableFuture<?>[] futures = new CompletableFuture[reforges.size()];
        int i = 0;
        for (Reforge reforge : reforges) {
            ResourceLocation location = reforge.getId();
            Path path = output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(location.getNamespace()).resolve("reforges").resolve(reforge.type().getSerializedName()).resolve(location.getPath() + ".json");
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
        add("epic", Reforge.builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(ExtraAttributes.BONUS_ATTACK_SPEED, 1, 2, 4, 7, 10, 15, 25)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 10, 15, 20, 27, 35, 45, 59)
                .addStat(ExtraAttributes.STRENGTH, 15, 20, 25, 32, 40, 50, 71)
        );
        add("fair", new Reforge.Builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 2, 3, 4, 7, 10, 15, 23)
                .addStat(ExtraAttributes.INTELLIGENCE, 2, 3, 4, 7, 10, 15, 23)
                .addStat(ExtraAttributes.STRENGTH, 2, 3, 4, 7, 10, 17, 30)
        );
        add("fast", new Reforge.Builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(ExtraAttributes.BONUS_ATTACK_SPEED, 10, 20, 30, 40, 50, 60, 72)
        );
        add("gentle", new Reforge.Builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(ExtraAttributes.BONUS_ATTACK_SPEED, 8, 10, 15, 20, 25, 30, 37)
                .addStat(ExtraAttributes.STRENGTH, 3, 5, 7, 10, 15, 20, 27)
        );
        add("heroic", new Reforge.Builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(ExtraAttributes.BONUS_ATTACK_SPEED, 1, 2, 2, 3, 5, 7, 10)
                .addStat(ExtraAttributes.INTELLIGENCE, 40, 50, 65, 80, 100, 125, 154)
                .addStat(ExtraAttributes.STRENGTH, 15, 20, 25, 32, 40, 50, 63)
        );
        add("legendary", new Reforge.Builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(ExtraAttributes.BONUS_ATTACK_SPEED, 2, 3, 5, 7, 10, 15, 22)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 6.5, 13, 18, 26, 35, 45, 57)
                .addStat(ExtraAttributes.INTELLIGENCE, 5, 8, 12, 18, 25, 35, 48)
                .addStat(ExtraAttributes.STRENGTH, 3, 7, 12, 18, 25, 32, 40)
        );
        add("odd", new Reforge.Builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 7.2, 14, 19.5, 30.0, 40.2, 53, 66)
                .addStat(ExtraAttributes.INTELLIGENCE, -5, -10, -18, -32, -50, -72, -110)
        );
        add("sacred", new Reforge.Builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(Attributes.LUCK, .1, .3, .7, 1.4, 2.6, 3.7, 5.2)
                .addStat(ExtraAttributes.STRENGTH, 7, 16, 30, 57, 80, 115, 150)
                .withBonus(new SacredBonus())
        );
        add("sharp", new Reforge.Builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(Attributes.ATTACK_DAMAGE, 1, 1.5, 2.7, 5, 6.7, 9, 13)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 25, 37, 50, 70, 92, 110, 135)
        );
        add("spicy", new Reforge.Builder()
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(ExtraAttributes.BONUS_ATTACK_SPEED, 2, 3, 5, 7, 10, 15, 22)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 28, 42, 55, 70, 95, 118, 135)
                .addStat(ExtraAttributes.STRENGTH, 2, 3, 4, 7, 10, 12, 15)
        );
        add("awkward", new Reforge.Builder()
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 5, 10, 15, 22, 30, 35, 50)
                .addStat(ExtraAttributes.INTELLIGENCE, -5, -10, -18, -32, -50, -72, -100)
                .addStat(ExtraAttributes.CRIT_CHANCE, 10, 12, 15, 20, 25, 30, 37)
        );
        add("deadly", new Reforge.Builder()
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 5, 10, 18, 32, 50, 78, 110)
                .addStat(ExtraAttributes.CRIT_CHANCE, 10, 13, 16, 19, 22, 25, 30)
        );
        add("fine", new Reforge.Builder()
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(ExtraAttributes.STRENGTH, 3, 7, 12, 18, 25, 33, 50)
                .addStat(ExtraAttributes.CRIT_CHANCE, 5, 7, 9, 12, 15, 18, 25)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 2, 4, 7, 10, 15, 20, 26)
        );
        add("grand", new Reforge.Builder()
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(ExtraAttributes.STRENGTH, 25, 32, 40, 50, 60, 75, 100)
        );
        add("hasty", new Reforge.Builder()
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(ExtraAttributes.CRIT_CHANCE, 20, 25, 30, 40, 50, 60, 75)
                .addStat(ExtraAttributes.STRENGTH, 3, 5, 7, 10, 15, 20, 30)
        );
        add("neat", new Reforge.Builder()
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(ExtraAttributes.CRIT_CHANCE, 10, 12, 14, 17, 20, 25, 32)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 2, 4, 7, 10, 15, 20, 27)
                .addStat(ExtraAttributes.INTELLIGENCE, 3, 5, 6, 12, 20, 30, 45)
        );
        add("rapid", new Reforge.Builder()
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 35, 45, 55, 65, 75, 90, 110)
                .addStat(ExtraAttributes.STRENGTH, 2, 3, 4, 7, 10, 12, 15)
        );
        add("rich", new Reforge.Builder()
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(ExtraAttributes.CRIT_CHANCE, 10, 12, 14, 17, 20, 25, 32)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 2, 4, 7, 10, 15, 20, 27)
                .addStat(ExtraAttributes.INTELLIGENCE, 3, 5, 6, 12, 20, 30, 42)
        );
        add("unreal", new Reforge.Builder()
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(ExtraAttributes.CRIT_CHANCE, 8, 9, 10, 11, 13, 15, 17)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 5, 10, 18, 32, 50, 70, 95)
                .addStat(ExtraAttributes.STRENGTH, 3, 7, 12, 18, 25, 34, 50)
        );
        //region armor
        add("clean", new Reforge.Builder()
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.ARMOR, 1, 1.7, 3, 4.5, 6.2, 8, 10)
                .addStat(Attributes.MAX_HEALTH, .2, .5, 1.1, 1.9, 2.8, 4, 5.9)
                .addStat(ExtraAttributes.CRIT_CHANCE, 2, 4, 6, 8, 10, 12, 15)
        );
        add("fierce", new Reforge.Builder()
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(ExtraAttributes.STRENGTH, 2, 4, 6, 8, 10, 12, 14)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 6, 11, 15, 21, 27, 36, 47)
        );
        add("heavy", new Reforge.Builder()
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.ARMOR, 6, 8.1, 12, 17, 23.5, 31, 41)
                .addStat(Attributes.MOVEMENT_SPEED, -1, -1, -1, -1, -1, -1, -1)
                .addStat(ExtraAttributes.CRIT_DAMAGE, -1, -2, -2, -3, -5, -7, -10)
        );
        add("light", new Reforge.Builder()
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.ARMOR, .4, .8, 1.2, 1.6, 2, 2.4, 3.2)
                .addStat(Attributes.MAX_HEALTH, 1, 1.3, 2, 3, 4.2, 5.5, 7)
                .addStat(Attributes.MOVEMENT_SPEED, 1, 2, 3, 4, 5, 6, 7)
                .addStat(ExtraAttributes.BONUS_ATTACK_SPEED, 1, 2, 3, 4, 5, 6, 7)
                .addStat(ExtraAttributes.CRIT_CHANCE, 1, 1, 2, 2, 3, 3, 4)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 1, 2, 3, 4, 5, 6, 8)
        );
        add("mythic", new Reforge.Builder()
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(0.2, 0.4, 0.6, 0.8, 1., 1.2, 1.4))
                .addStat(Attributes.ARMOR, 2, 3, 6, 8, 10, 12, 16)
                .addStat(ExtraAttributes.STRENGTH, 2., 4., 6., 8., 10., 12., 15.)
                .addStat(ExtraAttributes.CRIT_CHANCE, 0.5, 0.8, 1.1, 1.5, 1.9, 2.4, 3.)
                .addStat(Attributes.MOVEMENT_SPEED, ReforgeStat.build(2., 2., 2., 2., 2., 2., 2.))
                .addStat(ExtraAttributes.INTELLIGENCE, 15., 22., 30., 40., 52., 67., 90.)
        );
        add("pure", new Reforge.Builder()
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(0.2, 0.3, 0.4, 0.6, 0.9, 1.1, 1.3))
                .addStat(Attributes.ARMOR, ReforgeStat.build(2., 2.5, 5., 6.8, 9., 10.8, 14.6))
                .addStat(ExtraAttributes.CRIT_CHANCE, 1., 1.6, 2.2, 3.0, 3.8, 4.8, 6.)
                .addStat(ExtraAttributes.CRIT_DAMAGE, 2., 3., 4., 6., 8., 10., 13.)
                .addStat(Attributes.MOVEMENT_SPEED, 1., 1., 1., 1., 1., 1., 1.)
                .addStat(ExtraAttributes.INTELLIGENCE, 2., 3., 5., 10., 17., 30., 45.)
                .addStat(ExtraAttributes.BONUS_ATTACK_SPEED, 1., 1., 1.5, 2.1, 3., 4.1, 5.5)
        );
        add("smart", Reforge.builder()
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(0.4, 0.6, 0.8, 1., 1.2, 1.4, 1.6))
                .addStat(Attributes.ARMOR, ReforgeStat.build(4., 6., 9., 12., 15., 20., 30.))
                .addStat(ExtraAttributes.INTELLIGENCE, 20., 40., 60., 80., 100., 130., 165.)
        );
        add("titanic", Reforge.builder()
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(1., 1.2, 1.5, 2., 2.8, 4.1, 6.))
                .addStat(Attributes.ARMOR, ReforgeStat.build(10., 15., 20., 25., 35., 50., 75.))
        );
        add("wise", Reforge.builder()
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(0.6, 0.8, 1., 1.2, 1.5, 2., 2.8))
                .addStat(Attributes.MOVEMENT_SPEED, ReforgeStat.build(1., 1., 1., 2., 2., 3., 5.))
                .addStat(ExtraAttributes.INTELLIGENCE, 30., 50., 80., 110., 145., 190., 250.)
        );
        //endregion
        //region equipment
        add("astute", Reforge.builder()
                .reforgeType(Reforge.Type.EQUIPMENT)
                .addStat(Attributes.MAX_HEALTH, .1, .1, .1, .3, .4, .5, .5)
                .addStat(Attributes.ARMOR, .5, 1, 1, 1.5, 2, 2.5, 2.5)
                .addStat(ExtraAttributes.INTELLIGENCE.get(), 3, 4, 5, 6, 8, 10, 10)
        );
        add("blended", Reforge.builder()
                .reforgeType(Reforge.Type.EQUIPMENT)
        );
    }

    private void add(String name, Reforge.Builder builder) {
        reforges.add(builder.build(new ResourceLocation(modId, name)));
    }
}
