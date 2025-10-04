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
    public static SpellDamageSource createIndirect(@NotNull Entity direct, Entity causing, Spell spell) {
        return new SpellDamageSource(
                MiscHelper.lookupDamageTypeHolder(direct.level(), ModDamageTypes.ABILITY),
                direct, causing, spell
        );
    }

    public static SpellDamageSource createDirect(@NotNull Entity causer, Spell spell) {
        return create(ModDamageTypes.ABILITY, causer, spell);
    }

    public static SpellDamageSource createExplosion(@NotNull Entity causer, Spell spell) {
        return create(ModDamageTypes.MAGIC_EXPLOSION, causer, spell);
    }

    public static SpellDamageSource create(ResourceKey<DamageType> typeKey, @NotNull Entity causer, Spell spell) {
        return new SpellDamageSource(
                MiscHelper.lookupDamageTypeHolder(causer.level(), typeKey),
                causer, spell
        );
    }

    private final Spell spell;

    public SpellDamageSource(Holder<DamageType> pType, Entity pDirectEntity, Spell spell) {
        super(pType, pDirectEntity);
        this.spell = spell;
    }

    public SpellDamageSource(Holder<DamageType> pType, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity, Spell spell) {
        super(pType, pDirectEntity, pCausingEntity);
        this.spell = spell;
    }

    public Spell getSpell() {
        return spell;
    }
}
