package net.kapitencraft.mysticcraft.api;

@FunctionalInterface
public interface TriConsumer<A, B, C> {

    void accept(A a, B b, C c);
}
