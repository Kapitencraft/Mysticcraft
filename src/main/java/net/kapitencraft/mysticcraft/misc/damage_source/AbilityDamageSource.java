package net.kapitencraft.mysticcraft.misc.damage_source;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbilityDamageSource extends DamageSource implements IAbilitySource {
    public static AbilityDamageSource createIndirect(@NotNull Entity direct, Entity causing, float intScaling, String spellName) {
        return new AbilityDamageSource(
                MiscHelper.lookupDamageTypeHolder(direct.level(), ModDamageTypes.ABILITY),
                direct, causing, intScaling, spellName
        );
    }

    public static AbilityDamageSource createDirect(@NotNull Entity causer, float intScaling, String spellName) {
        return create(ModDamageTypes.ABILITY, causer, intScaling, spellName);
    }

    public static AbilityDamageSource createExplosion(@NotNull Entity causer, float intScaling, String spellName) {
        return create(ModDamageTypes.MAGIC_EXPLOSION, causer, intScaling, spellName);
    }

    public static AbilityDamageSource create(ResourceKey<DamageType> typeKey, @NotNull Entity causer, float intScaling, String spellName) {
        return new AbilityDamageSource(
                MiscHelper.lookupDamageTypeHolder(causer.level(), typeKey),
                causer, intScaling, spellName
        );
    }

    private final float intScaling;
    private final String spellName;

    public AbilityDamageSource(Holder<DamageType> pType, Entity pDirectEntity, float intScaling, String spellName) {
        super(pType, pDirectEntity);
        this.intScaling = intScaling;
        this.spellName = spellName;
    }

    public AbilityDamageSource(Holder<DamageType> pType, @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity, float intScaling, String spellName) {
        super(pType, pDirectEntity, pCausingEntity);
        this.intScaling = intScaling;
        this.spellName = spellName;
    }

    public float getScaling() {
        return intScaling;
    }

    public String getSpellType() {
        return spellName;
    }
}
