package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class SpellTarget<T> implements Predicate<T> {
    public static final SpellTarget<LivingEntity> SELF = Type.SELF.always();

    private final Type<T> type;
    private final Predicate<T> targetSelector;

    SpellTarget(Type<T> type, Predicate<T> targetSelector) {
        this.type = type;
        this.targetSelector = targetSelector;
    }

    public Type<T> getType() {
        return type;
    }

    public boolean test(T t) {
        return targetSelector.test(t);
    }

    public static class Type<T> {
        /**
         * the spell shall target the caster. only {@link SpellCastContextParams#CASTER} is available to use
         */
        public static final Type<LivingEntity> SELF = new Type<>();
        /**
         * the spell shall target a block.
         * <br> {@link SpellCastContextParams#TARGET_BLOCK} is available to use
         */
        public static final Type<BlockState> BLOCK = new Type<>();
        /**
         * the spell shall target an entity.
         * <br> {@link SpellCastContextParams#TARGET} is available to use
         */
        public static final Type<Entity> ENTITY = new Type<>();

        public SpellTarget<T> always() {
            return new SpellTarget<>(this, t -> true);
        }

        public SpellTarget<T> create(Predicate<T> predicate) {
            return new SpellTarget<>(this, predicate);
        }
    }
}
