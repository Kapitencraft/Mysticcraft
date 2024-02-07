package net.kapitencraft.mysticcraft.api;

/**
 * {@link java.util.function.Consumer} for 3 params
 */
@FunctionalInterface
public interface TriConsumer<A, B, C> {

    void accept(A a, B b, C c);
}
