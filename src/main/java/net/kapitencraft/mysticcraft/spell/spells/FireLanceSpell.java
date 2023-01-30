package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.spells.FireLance;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.misc.damage_source.AbilityDamageSource;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireLanceSpell extends Spell {
    public FireLanceSpell() {
        super(5, "Fire Lance", "1011100", Spell.CYCLE, Rarity.UNCOMMON);
    }

    @Override
    public void execute(LivingEntity user, ItemStack stack) {
        MysticcraftMod.sendInfo("execute");
        ArrayList<Vec3> lineOfSight = MISCTools.LineOfSight(user, 10, 0.05);
        for (Vec3 vec3 : lineOfSight) {
            List<LivingEntity> entities = user.level.getEntitiesOfClass(LivingEntity.class, new AABB(vec3.x - 0.1, vec3.y - 0.1, vec3.z - 0.1, vec3.x + 0.1, vec3.y + 0.1, vec3.z + 0.1));
            MISCTools.sendParticles(user.level, ParticleTypes.SMALL_FLAME, false, vec3, 10, 0.1/8, 0.1/8, 0.1/8, 0);
            for (LivingEntity living : entities) {
                if (living != user) {
                    if (living.getLastDamageSource() instanceof AbilityDamageSource abilitySource && Objects.equals(abilitySource.getSpellType(), this.REGISTRY_NAME)) {
                        living.invulnerableTime = 0;
                    }
                    living.hurt(new AbilityDamageSource(user, 0.2f, this.REGISTRY_NAME).setIsFire(), 2);
                }
            }
        }
    }

    @Override
    public boolean canApply(Item stack) {
        return stack instanceof FireLance;
    }

    @Override
    public List<Component> getDescription() {
        return List.of(Component.literal("Fires a line of fire where you are looking and deals 2 Damage per seconds"));
    }
}
