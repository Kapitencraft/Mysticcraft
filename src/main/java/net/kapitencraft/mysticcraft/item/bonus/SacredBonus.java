package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.bonus.Bonus;
import net.kapitencraft.kap_lib.core.io.serialization.RegistrySerializer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class SacredBonus implements Bonus<SacredBonus> {
    private static final SacredBonus INSTANCE = new SacredBonus();
    public static final RegistrySerializer<SacredBonus> SERIALIZER = RegistrySerializer.unit(INSTANCE);

    @Override
    public RegistrySerializer<SacredBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public void onEntityHurt(LivingEntity attacked, LivingEntity attacker, DamageContainer container) {
        if (attacker.getType().is(EntityTypeTags.UNDEAD)) {
            container.setNewDamage(container.getNewDamage() * 1.1f);
        }
    }
}
