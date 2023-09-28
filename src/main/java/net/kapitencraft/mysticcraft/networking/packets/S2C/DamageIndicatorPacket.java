package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DamageIndicatorPacket {
    private final int entityID;
    private final float damage;
    private final int damageType;

    public DamageIndicatorPacket(FriendlyByteBuf buf) {
        this.entityID = buf.readInt();
        this.damage = buf.readFloat();
        this.damageType = buf.readInt();
    }

    public DamageIndicatorPacket(int id, float damage, int damageType) {
        this.entityID = id;
        this.damage = damage;
        this.damageType = damageType;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeFloat(this.damage);
        buf.writeInt(this.damageType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ignoredSupplier) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            Entity entity = level.getEntity(this.entityID);
            if (entity instanceof LivingEntity living) {
                level.addParticle(ModParticleTypes.DAMAGE_INDICATOR.get(), living.getX(), living.getY() + 1, living.getZ(), this.damage, this.damageType, Mth.nextDouble(MysticcraftMod.RANDOM_SOURCE, 0, 3));
                return true;
            }
        }
        return false;
    }
}