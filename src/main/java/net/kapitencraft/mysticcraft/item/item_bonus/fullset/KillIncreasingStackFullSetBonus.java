package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.helpers.IOHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class KillIncreasingStackFullSetBonus extends StackingFullSetBonus {
    private final Predicate<MiscHelper.DamageType> damageTypes;
    private final Supplier<Attribute> modifier;
    private final float modifierScale;

    protected KillIncreasingStackFullSetBonus(String name, Predicate<MiscHelper.DamageType> damageTypes, Supplier<Attribute> modifier, float modifierScale, int cooldownTime) {
        super(name, cooldownTime);
        this.damageTypes = damageTypes;
        this.modifier = modifier;
        this.modifierScale = modifierScale;
    }

    @Override
    public void onEntityKilled(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {
        if (!damageTypes.test(type)) return;
        CompoundTag data = user.getPersistentData();
        if (data.getInt(getName()) < 10) {
            IOHelper.increaseIntegerTagValue(data, getName(), 1);
        } else {
            killWhen10(killed, user, type);
        }
        data.putInt(cooldownId, cooldownTime);
    }

    protected void killWhen10(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {
    }

    private double getScaledStack(LivingEntity user) {
        return getStackCount(user) * modifierScale;
    }

    @Nullable
    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        multimap.put(modifier.get(), AttributeHelper.addLiquidModifier(null, "Dominus Modifier", AttributeModifier.Operation.ADDITION, this::getScaledStack, living));
        return super.getModifiers(living);
    }
}
