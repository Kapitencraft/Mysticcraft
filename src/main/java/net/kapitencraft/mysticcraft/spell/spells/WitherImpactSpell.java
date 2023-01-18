package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.misc.damage_source.AbilityDamageSource;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WitherImpactSpell extends Spell {
    private Component[] description = {Component.literal("Teleports you 10 blocks ahead"), Component.literal("and deals damage to all entities around")};

    public WitherImpactSpell() {
        super(300, "Crystal Warp", Spells.RELEASE, Rarity.RARE, "crystal_warp");
    }

    @Override
    public void execute(LivingEntity user, ItemStack stack) {
        user.heal(0.5F);
        MISCTools.saveTeleport(user, 10);
        MISCTools.sendParticles(user.level, ParticleTypes.ASH, false, user.getX(), user.getY(), user.getZ(), 1000, 1, 1, 1, 0.01);
        final Vec3 center = new Vec3((user.getX()), (user.getY()), (user.getZ()));
        List<LivingEntity> entFound = user.level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(5), e -> true).stream().sorted(Comparator.comparingDouble(entCnd -> entCnd.distanceToSqr(center))).collect(Collectors.toList());
        double damageInflicted = 0; double EnemyHealth; int damaged = 0;
        for (LivingEntity entityIterator : entFound) {
            if (!(entityIterator == user)) {
                EnemyHealth = entityIterator.getHealth();
                entityIterator.hurt(new AbilityDamageSource(user, 0.1f), 5);
                damageInflicted += (EnemyHealth - entityIterator.getHealth());
                damaged++;
            }
        }
        if (!user.level.isClientSide() && user instanceof Player player && damaged > 0) {
            String red = FormattingCodes.RED;
            player.sendSystemMessage(Component.literal("Your Wither Impact hit " + red + damaged + FormattingCodes.RESET + " Enemies for " + red + MysticcraftMod.MAIN_FORMAT.format(damageInflicted) + " Damage"));
        }

    }

    @Override
    public boolean canApply(Item stack) {
        return stack instanceof NecronSword;
    }

    @Override
    public List<Component> getDescription() {
        return List.of(this.description);
    }
}
