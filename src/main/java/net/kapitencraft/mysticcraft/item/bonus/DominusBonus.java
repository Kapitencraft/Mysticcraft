package net.kapitencraft.mysticcraft.item.bonus;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.core.io.serialization.RegistrySerializer;
import net.kapitencraft.kap_lib.particle.animation.core.ParticleAnimation;
import net.kapitencraft.kap_lib.particle.animation.finalizers.EmptyFinalizer;
import net.kapitencraft.kap_lib.particle.animation.spawners.RingSpawner;
import net.kapitencraft.kap_lib.particle.animation.target.pos.PositionTarget;
import net.kapitencraft.kap_lib.particle.animation.terminators.BonusRemovedTerminator;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.particle.flame.FlamesForColors;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.Nullable;

public class DominusBonus extends StackingBonus<DominusBonus> {
    public static final DominusBonus INSTANCE = new DominusBonus();

    private static final ResourceLocation MODIFIER_ID = MysticcraftMod.res("dominus");
    public static final RegistrySerializer<DominusBonus> SERIALIZER = RegistrySerializer.unit(INSTANCE);

    public DominusBonus() {
        super(200, "dominus", DamageSource::isDirect);
    }

    @Override
    public void onApply(LivingEntity living) {
        if (living.level() instanceof ServerLevel) {
            ParticleAnimation.builder()
                    .spawnTime(ParticleAnimation.SpawnTime.absolute(2))
                    .spawn(RingSpawner.fullCircle(2)
                            .rotPerTick(5)
                            .radius(living.getBbWidth())
                            .axis(Direction.Axis.Y)
                            .setTarget(PositionTarget.entity(living))
                            .setParticle(FlamesForColors.RED)
                    ).finalizes(EmptyFinalizer.builder())
                    .terminatedWhen(new BonusRemovedTerminator.Instance(living.getId(), MysticcraftMod.res("dominus")))
                    .sendToAllPlayers();
        }
    }

    @Override
    public RegistrySerializer<DominusBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @Nullable Multimap<Holder<Attribute>, AttributeModifier> getModifiers(LivingEntity living) {
        HashMultimap<Holder<Attribute>, AttributeModifier> multimap = HashMultimap.create();
        int stack = getStack(living);
        multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(MODIFIER_ID, stack * .1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        multimap.put(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(MODIFIER_ID, stack * .2, AttributeModifier.Operation.ADD_VALUE));
        multimap.put(ExtraAttributes.FEROCITY, new AttributeModifier(MODIFIER_ID, stack * 2, AttributeModifier.Operation.ADD_VALUE));
        return multimap;
    }
}