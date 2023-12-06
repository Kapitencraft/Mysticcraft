package net.kapitencraft.mysticcraft.api;

public interface QuadConsumer<A, B, C, D> {

    void accept(A a, B b, C c, D d);
}
