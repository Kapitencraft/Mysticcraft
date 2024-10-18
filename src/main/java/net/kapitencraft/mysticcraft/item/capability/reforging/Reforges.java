package net.kapitencraft.mysticcraft.item.capability.reforging;

import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Reforges {
    public static final String REFORGE_NAME_ID = "ReforgeName";
    private static final HashMap<String, Reforge> reforges = new HashMap<>();
    private static final List<Rarity> list = new ArrayList<>();
    public static void registerRarities() {
        list.addAll(List.of(Rarity.values()));
        ModEventFactory.onRarityRegister(list);
    }

    public static Reforge byName(String name) {
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

    public static Reforge makeRandom(boolean withStones, ItemStack stack) {
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

    /**
     * only used withing {@link ReforgeManager#apply(Map, ResourceManager, ProfilerFiller)} <br>
     * do <i>not</i> call directly
     */
    public static void registerReforge(Reforge reforge) {
        reforges.put(reforge.getRegistryName(), reforge);
    }

    public static void bootstrap() {
    }

    public static boolean canBeReforged(ItemStack pStack) {
        return Stream.of(Reforge.Type.values()).anyMatch(type -> type.mayApply(pStack));
    }
}