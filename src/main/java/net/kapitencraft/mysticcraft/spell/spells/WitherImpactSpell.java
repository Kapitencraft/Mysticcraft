package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.ModConstance;
import net.kapitencraft.mysticcraft.client.particle.CircleParticleOptions;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.damage_source.AbilityDamageSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Comparator;
import java.util.List;

public class WitherImpactSpell {
    private static final Component[] description = {Component.literal("Teleports you 10 blocks ahead"), Component.literal("and deals damage to all entities around")};
    public static void execute(LivingEntity user, ItemStack stack) {
        WitherShieldSpell.execute(user, stack);
        MiscHelper.saveTeleport(user, 10);
        ParticleHelper.sendParticles(user.level, new CircleParticleOptions(new Vector3f(143f / 255, 0, 1), 5, 0.6), false, user.getX(), user.getY(), user.getZ(), 1, 0, 0, 0, 0);
        final Vec3 center = new Vec3((user.getX()), (user.getY()), (user.getZ()));
        List<LivingEntity> entFound = user.level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(5), e -> true).stream().sorted(Comparator.comparingDouble(entCnd -> entCnd.distanceToSqr(center))).toList();
        double damageInflicted = 0; double EnemyHealth; int damaged = 0;
        for (LivingEntity entityIterator : entFound) {
            if (!(entityIterator == user)) {
                EnemyHealth = entityIterator.getHealth();
                entityIterator.hurt(new AbilityDamageSource(user, 0.4f, "wither_impact"), 5);
                damageInflicted += (EnemyHealth - entityIterator.getHealth());
                damaged++;
            }
        }
        if (!user.level.isClientSide() && user instanceof Player player && damaged > 0 && damageInflicted > 0) {
            String red = FormattingCodes.RED;
            player.sendSystemMessage(Component.literal("Your Wither Impact hit " + TextHelper.wrapInRed(String.valueOf(damaged)) + " Enemies for " + red + ModConstance.MAIN_FORMAT.format(damageInflicted) + " Damage"));
        }
    }

    public static List<Component> getDescription() {
        return List.of(description);
    }
}
