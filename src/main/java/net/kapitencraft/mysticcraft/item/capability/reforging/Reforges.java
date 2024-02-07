package net.kapitencraft.mysticcraft.item.capability.reforging;

import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reforges {
    public static final String REFORGE_NAME_ID = "ReforgeName";
    private static final HashMap<String, Reforge> reforges = new HashMap<>();
    private static final List<Rarity> list = new ArrayList<>();
    public static void register() {
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

    public static int getReforgesSize() {
        return reforges.size();
    }


    public static HashMap<String, Reforge> all() {
        return reforges;
    }

    public static @NotNull Reforge makeRandom(boolean withStones, ItemStack stack) {
        List<Reforge> list = reforges.values().stream().filter(reforge -> reforge.getType().mayApply(stack)).toList();
        if (withStones) {
            return MathHelper.pickRandom(list);
        } else {
            List<Reforge> withoutStones = list.stream().filter(reforge -> !reforge.isOnlyFromStone()).toList();
            return MathHelper.pickRandom(withoutStones);
        }
    }

    public static Reforge applyRandom(boolean withStones, ItemStack stack) {
        Reforge reforge = makeRandom(withStones, stack);
        reforge.saveToStack(stack);
        return reforge;
    }

    public static void registerReforge(Reforge reforge) {
        reforges.put(reforge.getRegistryName(), reforge);
    }
}
