package net.kapitencraft.mysticcraft.misc.content.mana;

import net.kapitencraft.kap_lib.util.DamageCounter;
import net.kapitencraft.mysticcraft.misc.damage_source.SpellDamageSource;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.spells.SpellProjectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class ManaAOE {

    public static void execute(LivingEntity user, Spell spell, float intScaling, float damage, double range) {
        final Vec3 center = new Vec3((user.getX()), (user.getY()), (user.getZ()));
        List<LivingEntity> entFound = user.level().getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(range), e -> true).stream().sorted(Comparator.comparingDouble(entCnd -> entCnd.distanceToSqr(center))).toList();
        DamageCounter.activate();
        for (LivingEntity entityIterator : entFound) {
            if (!(entityIterator == user)) {
                entityIterator.hurt(SpellDamageSource.createDirect(user, intScaling, spell), damage);
            }
        }
        DamageCounter.DamageHolder holder = DamageCounter.getDamage(true);
        if (!user.level().isClientSide() && user instanceof Player player && holder.hasDamage()) {
            SpellProjectile.sendDamageMessage(player, spell, holder.hit(), (float) holder.damage());
        }
    }
}
