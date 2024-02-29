package net.kapitencraft.mysticcraft.misc.cooldown;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class SpellCooldown extends Cooldown {
    public SpellCooldown(String path, int defaultTime) {
        super(new CompoundPath(path, null, entity -> entity instanceof LivingEntity living && living.getAttribute(ModAttributes.MANA.get()) != null), defaultTime, entity ->  {
            CompoundTag tag = entity.getPersistentData().getCompound("SpellCooldowns");
            tag.putInt(path, -1);
            entity.getPersistentData().put("SpellCooldowns", tag);
        });
    }
}