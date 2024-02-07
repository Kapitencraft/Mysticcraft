package net.kapitencraft.mysticcraft.api;

/**
 * quad consumer to consume 4 different types
 */
@FunctionalInterface
public interface QuadConsumer<A, B, C, D> {

    void accept(A a, B b, C c, D d);
}
