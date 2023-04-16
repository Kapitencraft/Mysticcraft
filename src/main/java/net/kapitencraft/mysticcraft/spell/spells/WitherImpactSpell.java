package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.ModConstance;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.damage_source.AbilityDamageSource;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.kapitencraft.mysticcraft.misc.utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WitherImpactSpell {
    private static final Component[] description = {Component.literal("Teleports you 10 blocks ahead"), Component.literal("and deals damage to all entities around")};
    public static final String DAMAGE_REDUCTION_TIME = "WI-DamageReductionTime";
    public static final String ABSORPTION_AMOUNT_ID = "WI-AbsorptionAmount";

    public static void execute(LivingEntity user, ItemStack stack) {
        CompoundTag tag = user.getPersistentData();
        tag.putInt(DAMAGE_REDUCTION_TIME, 100);
        float absorption = (float) (user.getAttributeValue(ModAttributes.CRIT_DAMAGE.get()) * 0.3);
        if (tag.getFloat(ABSORPTION_AMOUNT_ID) <= 0 || !tag.contains(ABSORPTION_AMOUNT_ID)) {
            user.setAbsorptionAmount(user.getAbsorptionAmount() + absorption);
        }
        tag.putFloat(ABSORPTION_AMOUNT_ID, absorption);
        MiscUtils.saveTeleport(user, 10);
        ParticleUtils.sendParticles(user.level, ParticleTypes.ASH, false, user.getX(), user.getY(), user.getZ(), 1000, 1, 1, 1, 0.01);
        final Vec3 center = new Vec3((user.getX()), (user.getY()), (user.getZ()));
        List<LivingEntity> entFound = user.level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(5), e -> true).stream().sorted(Comparator.comparingDouble(entCnd -> entCnd.distanceToSqr(center))).collect(Collectors.toList());
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
            player.sendSystemMessage(Component.literal("Your Wither Impact hit " + red + damaged + FormattingCodes.RESET + " Enemies for " + red + ModConstance.MAIN_FORMAT.format(damageInflicted) + " Damage"));
        }

    }

    public static List<Component> getDescription() {
        return List.of(description);
    }
}
