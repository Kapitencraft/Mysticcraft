package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaAOE;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

import java.util.List;

public class WitherImpactSpell {
    private static final Component[] description = {Component.literal("Teleports you 10 blocks ahead"), Component.literal("and deals damage to all entities around")};
    public static boolean execute(LivingEntity user, ItemStack stack) {
        WitherShieldSpell.execute(user, stack);
        MiscHelper.saveTeleport(user, 10);
        ManaAOE.execute(user, "wither_impact", 0.4f, 5, 5);
        ParticleHelper.sendParticles(user.level, new CircleParticleOptions(new Vector3f(143f / 255, 0, 1), 5, 0.6), false, user.getX(), user.getY(), user.getZ(), 1, 0, 0, 0, 0);
        return true;
    }

    public static List<Component> getDescription() {
        return List.of(description);
    }
}
