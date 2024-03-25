package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaMain;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ElytraItem.class)
public abstract class ElytraItemMixin extends Item {

    @Shadow public abstract boolean isValidRepairItem(ItemStack p_41134_, ItemStack p_41135_);

    public ElytraItemMixin(Properties p_41383_) {
        super(p_41383_);
    }

    /**
     * @author Kapitencraft
     * @reason elytra data :pog:
     */
    @Override
    public boolean elytraFlightTick(ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
        if (entity instanceof Player player && Requirement.doesntMeetRequirements(player, stack.getItem())) return false;
        if (!entity.level.isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                if (nextFlightTick % 20 == 0) {
                    if (!CapabilityHelper.exeCapability(stack, CapabilityHelper.ELYTRA, data -> {
                        int unb = data.getLevelForData(ElytraData.UNBREAKING);
                        if (unb == 0)
                            stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
                    })) stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
                }
                entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_GLIDE);
            }

        }
        CapabilityHelper.exeCapability(stack, CapabilityHelper.ELYTRA, data -> {
            int manaBoost = data.getLevelForData(ElytraData.MANA_BOOST);
            if (manaBoost > 0) {
                if (ManaMain.consumeMana(entity, 1.5)) {
                    entity.setDeltaMovement(MiscHelper.getFireworkSpeedBoost(entity, manaBoost));
                    if (entity.level.isClientSide())
                        sendManaBoostParticles(entity, entity.getRandom(), entity.getDeltaMovement());
                }
            }
        });
        return true;
    }

    @SuppressWarnings("all")
    private static void sendManaBoostParticles(Entity target, RandomSource random, Vec3 delta) {
        Level level = target.getLevel();
        if (!level.isClientSide()) return;
        ClientLevel clientLevel = (ClientLevel) level;
        Vec3 loc = MathHelper.getHandHoldingItemAngle(HumanoidArm.LEFT, target);
        addParticle(clientLevel, loc, random, delta);
        loc = MathHelper.getHandHoldingItemAngle(HumanoidArm.RIGHT, target);
        addParticle(clientLevel, loc, random, delta);
    }

    @SuppressWarnings("all")
    private static void addParticle(ClientLevel level, Vec3 loc, RandomSource random, Vec3 delta) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        SpriteSet spriteSet = engine.spriteSets.get(BuiltInRegistries.PARTICLE_TYPE.getKey(ParticleTypes.FIREWORK.getType()));
        FireworkParticles.SparkParticle particle = new FireworkParticles.SparkParticle(level, loc.x, loc.y, loc.z, random.nextGaussian() * 0.05D, -delta.y * 0.5D, random.nextGaussian() * 0.05D, engine, spriteSet);
        particle.setColor(0, 0, 1);
        particle.setFadeColor(MathHelper.RGBtoInt(new Vector3f(0.5f, 0, 0.5f)));
        engine.add(particle);
    }
}
