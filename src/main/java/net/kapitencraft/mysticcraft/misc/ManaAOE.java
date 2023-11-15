package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.misc.damage_source.AbilityDamageSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class ManaAOE {

    public static void execute(LivingEntity user, String name, float intScaling, float damage, double range) {
        final Vec3 center = new Vec3((user.getX()), (user.getY()), (user.getZ()));
        List<LivingEntity> entFound = user.level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(range), e -> true).stream().sorted(Comparator.comparingDouble(entCnd -> entCnd.distanceToSqr(center))).toList();
        DamageCounter.activate();
        for (LivingEntity entityIterator : entFound) {
            if (!(entityIterator == user)) {
                entityIterator.hurt(new AbilityDamageSource(user, intScaling, name), damage);
            }
        }
        DamageCounter.DamageHolder holder = DamageCounter.getDamage(true);
        if (!user.level.isClientSide() && user instanceof Player player && holder.hasDamage()) {
            String red = FormattingCodes.RED;
            player.sendSystemMessage(Component.literal("Your " + TextHelper.makeGrammar(name) + " hit " + TextHelper.wrapInRed(holder.hit()) + " Enemies for " + red + MathHelper.defRound(holder.damage()) + " Damage"));
        }
    }
}
