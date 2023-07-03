package net.kapitencraft.mysticcraft.item.reforging;

import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reforges {
    private static final Attribute STRENGHT = ModAttributes.STRENGTH.get();
    private static final Attribute CRIT_DAMAGE = ModAttributes.CRIT_DAMAGE.get();
    private static final Attribute CRIT_CHANCE = ModAttributes.CRIT_CHANCE.get();
    private static final Attribute B_A_S = ModAttributes.BONUS_ATTACK_SPEED.get();

    public static final String REFORGE_NAME_ID = "ReforgeName";
    private static final HashMap<String, Reforge> reforges = new HashMap<>();
    private static final List<Rarity> list = new ArrayList<>();
    public static void register() {
        registerReforge(new Reforge.Builder("epic")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(15., 20., 25., 32., 40., 50., 71.))
                .addStat(CRIT_DAMAGE, ReforgeStat.build(10., 15., 20., 27., 35., 45., 59.))
                .addStat(B_A_S, ReforgeStat.build(1., 2., 4., 7., 10., 15., 25.))
                .build()
        );
        registerReforge(new Reforge.Builder("fair")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(2., 3., 4., 7., 10., 13., 19.))
                .addStat(CRIT_DAMAGE, ReforgeStat.build(2., 3., 4., 7., 10., 12., 15.))
                .addStat(ModAttributes.INTELLIGENCE.get(), ReforgeStat.build(2., 3., 4., 7., 10., 12., 24.))
                .build()
        );
        registerReforge(new Reforge.Builder("fast")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(B_A_S, ReforgeStat.build(10., 20., 30., 40., 50., 60., 72.))
                .build()
        );
        registerReforge(new Reforge.Builder("gentle")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(3., 5., 7., 10., 15., 20.,  27.))
                .addStat(B_A_S, ReforgeStat.build(8., 10., 15., 20., 25., 30., 37.))
                .build()
        );
        registerReforge(new Reforge.Builder("heroic")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(15., 20., 25., 32., 40., 50., 63.))
                .addStat(ModAttributes.INTELLIGENCE.get(), ReforgeStat.build(40., 50., 65., 80., 100., 125., 154.))
                .addStat(B_A_S, ReforgeStat.build(1., 2., 2., 3., 5., 7., 10.))
                .build()
        );
        registerReforge(new Reforge.Builder("legendary")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(3., 7., 12., 18., 25., 32., 40.))
                .addStat(CRIT_DAMAGE, ReforgeStat.build(6.5, 13., 18., 26., 35., 45., 57.))
                .addStat(ModAttributes.INTELLIGENCE.get(), ReforgeStat.build(5., 8., 12., 18., 25., 35., 48.))
                .addStat(B_A_S, ReforgeStat.build(2., 3., 5., 7., 10., 15., 22.))
                .build()
        );
        registerReforge(new Reforge.Builder("odd")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(CRIT_DAMAGE, ReforgeStat.build(7.2, 14., 19.5, 30., 40.2, 53., 66.))
                .addStat(ModAttributes.INTELLIGENCE.get(), ReforgeStat.build(-5., -10., -18., -32., -50., -75., -110.))
                .build()
        );
        registerReforge(new Reforge.Builder("sharp")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(CRIT_DAMAGE, ReforgeStat.build(25., 37., 50., 70., 92., 110., 135.))
                .addStat(Attributes.ATTACK_DAMAGE, ReforgeStat.build(1., 1.5, 2.7, 5., 6.7, 9., 13.))
                .build()
        );
        registerReforge(new Reforge.Builder("spicy")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(2., 3., 4., 7., 10., 12., 15.))
                .addStat(CRIT_DAMAGE, ReforgeStat.build(28., 42., 55., 70., 95., 118., 135.))
                .addStat(B_A_S, ReforgeStat.build(2., 3., 5., 7., 10., 15., 22.))
                .build()
        );
        registerReforge(new Reforge.Builder("sacred")
                .reforgeType(Reforge.Type.MELEE_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(7., 16., 30., 57., 80., 115., 150.))
                .addStat(ModAttributes.MAGIC_FIND.get(), ReforgeStat.build(0.1, 0.3, 0.7, 1.4, 2.6, 3.7, 5.2))
                .build()
        );

        registerReforge(new Reforge.Builder("awkward")
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(CRIT_DAMAGE, ReforgeStat.build(5., 10., 15., 22., 30., 35., 50.))
                .addStat(ModAttributes.INTELLIGENCE.get(), ReforgeStat.build(-5., -10., -18., 32., -50., -72., -100))
                .addStat(CRIT_CHANCE, ReforgeStat.build(10., 12., 15., 20., 25., 30., 37.))
                .build()
        );
        registerReforge(new Reforge.Builder("deadly")
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(CRIT_DAMAGE, ReforgeStat.build(5., 10., 18., 32., 50., 78., 110.))
                .addStat(CRIT_CHANCE, ReforgeStat.build(10., 13., 16., 19., 22., 25., 30.))
                .build()
        );
        registerReforge(new Reforge.Builder("fine")
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(3., 7., 12., 18., 25., 33., 50.))
                .addStat(CRIT_CHANCE, ReforgeStat.build(5., 7., 9., 12., 15., 18., 25.))
                .addStat(CRIT_DAMAGE, ReforgeStat.build(2., 4., 7., 10., 15., 20., 25.))
                .build()
        );
        registerReforge(new Reforge.Builder("grand")
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(25., 32., 40., 50., 60., 75., 100.))
                .build()
        );
        registerReforge(new Reforge.Builder("hasty")
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(3., 5., 7., 10., 15., 20., 30.))
                .addStat(CRIT_CHANCE, ReforgeStat.build(20., 25., 30., 40., 50., 60., 75.))
                .build()
        );
        registerReforge(new Reforge.Builder("neat")
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(CRIT_CHANCE, ReforgeStat.build(10., 12., 14., 17., 20., 25., 32.))
                .addStat(CRIT_DAMAGE, ReforgeStat.build(2., 4., 7., 10., 15., 20., 27.))
                .addStat(ModAttributes.INTELLIGENCE.get(), ReforgeStat.build(3., 5., 6., 15., 20., 30., 45.))
                .build()
        );
        registerReforge(new Reforge.Builder("unreal")
                .reforgeType(Reforge.Type.RANGED_WEAPON)
                .addStat(STRENGHT, ReforgeStat.build(3, 7, 12, 18, 25, 34, 50))
                .addStat(CRIT_CHANCE, ReforgeStat.build(8, 9, 10, 11, 13, 15, 17))
                .addStat(CRIT_DAMAGE, ReforgeStat.build(5, 10, 18, 32, 50, 70, 95))
                .build()
        );

        registerReforge(new Reforge.Builder("light")
                .reforgeType(Reforge.Type.ARMOR)
                .addStat(Attributes.MAX_HEALTH, ReforgeStat.build(1., 2., 2., 3., 3.5, 4., 5.))
                .addStat(Attributes.ARMOR, ReforgeStat.build(1., 2., 3., 4., 5., 6., 8.))
                .addStat(CRIT_CHANCE, ReforgeStat.build(1., 1., 2., 2., 3., 3., 4.))
                .addStat(CRIT_DAMAGE, ReforgeStat.build(1., 2., 3., 4., 5., 6., 8.))
                .addStat(Attributes.MOVEMENT_SPEED, ReforgeStat.build(1., 2., 3., 4., 5., 6., 7.))
                .addStat(B_A_S, ReforgeStat.build(1., 2., 3., 4., 5., 6., 7.))
                .build()
        );
        ModEventFactory.onReforgeRegister(reforges);
    }

    public static void registerRarities() {
        list.addAll(List.of(Rarity.values()));
        ModEventFactory.onRarityRegister(list);
    }

    public static Reforge getByName(String name) {
        return reforges.get(name);
    }

    public static List<Rarity> getRegisteredRarities() {
        return list;
    }

    public static @Nullable Reforge getReforge(ItemStack stack) {
        String name = stack.getOrCreateTag().getString(REFORGE_NAME_ID);
        if (reforges.containsKey(name)) return reforges.get(name);
        return null;
    }

    public static Reforge applyRandom(boolean withStones, ItemStack stack) {
        List<Reforge> list = reforges.values().stream().filter(reforge -> reforge.getType().mayApply(stack)).toList();
        if (withStones) {
            return list.get(Mth.nextInt(RandomSource.create(), 0, list.size()-1));
        } else {
            List<Reforge> withoutStones = list.stream().filter(reforge -> !reforge.isOnlyFromStone()).toList();
            return withoutStones.get(Mth.nextInt(RandomSource.create(), 0, withoutStones.size()-1));
        }
    }

    private static void registerReforge(Reforge reforge) {
        reforges.put(reforge.getRegistryName(), reforge);
    }
}
