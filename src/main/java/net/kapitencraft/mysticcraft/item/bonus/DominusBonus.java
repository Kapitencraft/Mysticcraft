package net.kapitencraft.mysticcraft.item.bonus;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.client.particle.animation.core.ParticleAnimation;
import net.kapitencraft.kap_lib.client.particle.animation.finalizers.EmptyFinalizer;
import net.kapitencraft.kap_lib.client.particle.animation.spawners.RingSpawner;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.client.particle.flame.FlamesForColors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DominusBonus extends StackingBonus<DominusBonus> {
    private static final UUID MODIFIER_ID = UUID.randomUUID();
    public static final DataPackSerializer<DominusBonus> SERIALIZER = DataPackSerializer.unit(DominusBonus::new);

    public DominusBonus() {
        super(MiscHelper.DamageType.MELEE, 200, "dominus");
    }

    @Override
    public void onApply(LivingEntity living) {
        if (living.level() instanceof ServerLevel sL) {
            ParticleAnimation.builder()
                    .spawn(RingSpawner.fullCircle(2)
                            .setParticle(FlamesForColors.RED)
                    ).finalizes(EmptyFinalizer.builder())
                    //.terminatedWhen()
                    .sendToAllPlayers(sL);
        }
    }

    @Override
    public DataPackSerializer<DominusBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @Nullable Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        int stack = getStack(living);
        multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(MODIFIER_ID, "DominusBonus", stack * .1, AttributeModifier.Operation.MULTIPLY_BASE));
        multimap.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(MODIFIER_ID, "DominusBonus", stack * .2, AttributeModifier.Operation.ADDITION));
        multimap.put(ExtraAttributes.FEROCITY.get(), new AttributeModifier(MODIFIER_ID, "DominusBonus", stack * 2, AttributeModifier.Operation.ADDITION));
        return multimap;
    }
}