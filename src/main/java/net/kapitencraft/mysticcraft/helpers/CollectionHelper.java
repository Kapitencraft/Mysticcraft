package net.kapitencraft.mysticcraft.helpers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionHelper {

    public static <T, K> T getFirstKey(Map<T, K> collection) {
        for (Map.Entry<T, K> entry : collection.entrySet()) {
            return entry.getKey();
        }
        return null;
    }

    public static <T> ArrayList<T> colToList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    public static <T> ArrayList<T> toList(T ts) {
        ArrayList<T> target = new ArrayList<>();
        Collections.addAll(target, ts);
        return target;
    }

    public static <T> List<T> merge(List<List<T>> toMerge) {
        List<T> list = new ArrayList<>();
        toMerge.forEach(list::addAll);
        return list;
    }

    public static <T, K> K getFirstValue(Map<T, K> map) {
        for (Map.Entry<T, K> entry : map.entrySet()) {
            return entry.getValue();
        }
        return null;
    }

    public static <T, K> void removeIf(Map<T, K> map, Predicate<T> predicate) {
        for (T t : map.keySet()) {
            if (predicate.test(t)) {
                map.remove(t);
            }
        }
    }

    public static <T, V> void forEach(HashMap<T, HashMap<T, V>> map, BiConsumer<? super T, ? super V> consumer) {
        for (HashMap<T, V> map1 : map.values()) {
            map1.forEach(consumer);
        }
    }

    public static  <V> ArrayList<V> invertList(ArrayList<V> list) {
        ArrayList<V> out = new ArrayList<>();
        for (int i = list.size(); i > 0; i--) {

            out.add(list.get((i - 1)));
        }
        return out;
    }

    public static List<LivingEntity> sortLowestDistance(Entity source, List<LivingEntity> list) {
        if (list.isEmpty()) {
            return List.of();
        }
        return list.stream().sorted(Comparator.comparingDouble(living -> living.distanceToSqr(MathHelper.getPosition(source)))).collect(Collectors.toList());
    }

    public static <T> boolean arrayContains(T[] array, T t) {
        return List.of(array).contains(t);
    }

    public static <T> T[] listToArray(List<T> list) {
        return (T[]) list.toArray();
    }

    public static <T> List<T> copy(T[] source) {
        return Arrays.asList(source);
    }

    public static <T> T[] remove(T[] values, T... toRemove) {
        return (T[]) Arrays.stream(values).filter(t -> !arrayContains(toRemove, t)).toArray();
    }

    public static <T> T[] add(T[] values, T... toAdd) {
        List<T> source = new ArrayList<>(Arrays.stream(values).toList());
        source.addAll(Arrays.stream(toAdd).toList());
        return listToArray(source);
    }
}
