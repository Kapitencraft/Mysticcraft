package net.kapitencraft.mysticcraft.api;

import java.util.ArrayList;
import java.util.List;

public class Queue<T> {
    private final List<T> active = new ArrayList<>();
    private final List<T> queue = new ArrayList<>();
    private final List<T> removeQueue = new ArrayList<>();
    private boolean queuing = false;

    private Queue() {
    }

    public static <T> Queue<T> create() {
        return new Queue<>();
    }

    public void add(T obj) {
        if (queuing) queue.add(obj);
        else active.add(obj);
    }

    public void startQueuing() {
        queuing = true;
    }

    public void stopQueuing() {
        active.addAll(queue);
        active.removeAll(removeQueue);
        queue.clear(); removeQueue.clear();
        queuing = false;
    }

    public void remove(T obj) {
        if (queuing) removeQueue.add(obj);
        else active.remove(obj);
    }

    public List<T> getAll() {
        if (queuing) throw new IllegalStateException("can not get elements while queuing!");
        return active;
    }
}
