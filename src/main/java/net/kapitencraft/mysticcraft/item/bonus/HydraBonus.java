package net.kapitencraft.mysticcraft.item.bonus;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.core.io.serialization.RegistrySerializer;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.Nullable;

public class HydraBonus extends StackingBonus<HydraBonus> {
    public static final HydraBonus INSTANCE = new HydraBonus();

    private static final ResourceLocation MODIFIER_UUID = MysticcraftMod.res("hydra");

    public static final RegistrySerializer<HydraBonus> SERIALIZER = RegistrySerializer.unit(INSTANCE);

    public HydraBonus() {
        super(200, "hydra", s -> !s.isDirect());
    }

    @Override
    public RegistrySerializer<HydraBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @Nullable Multimap<Holder<Attribute>, AttributeModifier> getModifiers(LivingEntity living) {
        HashMultimap<Holder<Attribute>, AttributeModifier> multimap = HashMultimap.create();
        int stack = getStack(living);
        multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(MODIFIER_UUID, stack * .15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        multimap.put(ExtraAttributes.PROJECTILE_SPEED, new AttributeModifier(MODIFIER_UUID, stack * 3, AttributeModifier.Operation.ADD_VALUE));
        return multimap;
    }
}