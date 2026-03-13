package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.core.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.core.helpers.MathHelper;
import net.kapitencraft.kap_lib.mana.ManaAttributes;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Spell {
    private final double manaCost;
    private final int castDuration;
    private final Type type;
    private final SpellTarget<?> target;
    private final @Nullable Holder<Cooldown> cooldown;
    private final Holder.Reference<Spell> holder = ModRegistries.SPELLS.createIntrusiveHolder(this);

    protected Spell(double manaCost, int castDuration, Type type, SpellTarget<?> target, @Nullable Holder<Cooldown> cooldown) {
        this.manaCost = manaCost;
        this.castDuration = castDuration;
        this.type = type;
        this.target = target;
        this.cooldown = cooldown;
    }

    public abstract void cast(SpellCastContext context) throws SpellExecutionFailedException;

    public double manaCost() {
        return manaCost;
    }

    public int castDuration() {
        return castDuration;
    }

    @NotNull
    public Type getType() {
        return type;
    }

    public @NotNull SpellTarget<?> getTarget() {
        return target;
    }

    public @Nullable Cooldown getCooldown() {
        return cooldown == null ? null : cooldown.value();
    }

    public Holder.Reference<Spell> getHolder() {
        return holder;
    }

    public boolean is(TagKey<Spell> key) {
        return holder.is(key);
    }

    public double getManaCostForUser(LivingEntity user) {
        AttributeInstance instance = user.getAttribute(ManaAttributes.MANA_COST);
        return MathHelper.defRound(AttributeHelper.getAttributeValue(instance, this.manaCost()));
    }

    public enum Type {
        RELEASE,
        HOLD
    }

    public abstract boolean canApply(Item item);
}