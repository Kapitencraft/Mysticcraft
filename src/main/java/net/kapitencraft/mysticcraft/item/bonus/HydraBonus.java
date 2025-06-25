package net.kapitencraft.mysticcraft.item.bonus;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HydraBonus extends StackingBonus<HydraBonus> {
    private static final UUID MODIFIER_UUID = UUID.randomUUID();

    public static final DataPackSerializer<HydraBonus> SERIALIZER = DataPackSerializer.unit(HydraBonus::new);

    public HydraBonus() {
        super(MiscHelper.DamageType.RANGED, 200, "hydra");
    }

    @Override
    public DataPackSerializer<HydraBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @Nullable Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        int stack = getStack(living);
        multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(MODIFIER_UUID, "HydraBonus", stack * .15, AttributeModifier.Operation.MULTIPLY_BASE));
        multimap.put(ExtraAttributes.PROJECTILE_SPEED.get(), new AttributeModifier(MODIFIER_UUID, "HydraBonus", stack * 3, AttributeModifier.Operation.ADDITION));
        return multimap;
    }
}