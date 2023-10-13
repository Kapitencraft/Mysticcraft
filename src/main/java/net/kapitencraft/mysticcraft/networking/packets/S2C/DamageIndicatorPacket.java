package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class DamageIndicatorPacket extends MakeParticlePacket {
    private final int entityID;
    private final float damage;
    private final int damageType;

    public DamageIndicatorPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readFloat(), buf.readInt());
    }

    public DamageIndicatorPacket(int id, float damage, int damageType) {
        super(ModParticleTypes.DAMAGE_INDICATOR.get(), 0, 0, 0, 0, 0, 0);
        this.entityID = id;
        this.damage = damage;
        this.damageType = damageType;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeFloat(this.damage);
        buf.writeInt(this.damageType);
    }

    private static Vec3 getUpdateForPos(Vec3 cam, LivingEntity living) {
        Vec3 livingPos = MathHelper.getPosition(living);
        Vec3 delta = cam.subtract(livingPos);
        return MathHelper.setLength(delta, 0.5);
    }

    @Override
    void changeValues(ClientLevel toUse) {
        Entity en = toUse.getEntity(this.entityID);
        if (en instanceof LivingEntity living) {
            Vec3 change = getUpdateForPos(Minecraft.getInstance().getCameraEntity().getEyePosition(), living);
            change = new Vec3(change.x, 1, change.z);
            pos = change.add(MathHelper.getPosition(living));
            delta = new Vec3(this.damage, this.damageType, Mth.nextDouble(MysticcraftMod.RANDOM_SOURCE, 0, 3));
            return;
        }
        cancelled = true;
    }
}