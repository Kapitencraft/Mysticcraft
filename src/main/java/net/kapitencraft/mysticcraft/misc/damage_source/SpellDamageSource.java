package net.kapitencraft.mysticcraft.misc.damage_source;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpellDamageSource extends DamageSource implements ISpellSource {
    public static SpellDamageSource createIndirect(@NotNull Entity direct, Entity causing, float intScaling, Spell spell) {
        return new SpellDamageSource(
                MiscHelper.lookupDamageTypeHolder(direct.level(), ModDamageTypes.ABILITY),
                direct, causing, intScaling, spell
        );
    }

    public static SpellDamageSource createDirect(@NotNull Entity causer, float intScaling, Spell spell) {
        return create(ModDamageTypes.ABILITY, causer, intScaling, spell);
    }

    public static SpellDamageSource createExplosion(@NotNull Entity causer, float intScaling, Spell spell) {
        return create(ModDamageTypes.MAGIC_EXPLOSION, causer, intScaling, spell);
    }

    public static SpellDamageSource create(ResourceKey<DamageType> typeKey, @NotNull Entity causer, float intScaling, Spell spell) {
        return new SpellDamageSource(
                MiscHelper.lookupDamageTypeHolder(causer.level(), typeKey),
                causer, intScaling, spell
        );
    }

    private final float intScaling;
    private final Spell spell;

    public SpellDamageSource(Holder<DamageType> pType, Entity pDirectEntity, float intScaling, Spell spell) {
        super(pType, pDirectEntity);
        this.intScaling = intScaling;
        this.spell = spell;
    }

    public SpellDamageSource(Holder<DamageType> pType, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity, float intScaling, Spell spell) {
        super(pType, pDirectEntity, pCausingEntity);
        this.intScaling = intScaling;
        this.spell = spell;
    }

    public float getScaling() {
        return intScaling;
    }

    public Spell getSpell() {
        return spell;
    }
}
