package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.bonus.Bonus;
import net.kapitencraft.kap_lib.core.helpers.MathHelper;
import net.kapitencraft.kap_lib.core.io.serialization.RegistrySerializer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class AssassinBonus implements Bonus<AssassinBonus> {
    public static final AssassinBonus INSTANCE = new AssassinBonus();

    public static final RegistrySerializer<AssassinBonus> SERIALIZER = RegistrySerializer.unit(INSTANCE);

    @Override
    public RegistrySerializer<AssassinBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public void onEntityHurt(LivingEntity attacked, LivingEntity attacker, DamageContainer container) {
        if (MathHelper.isBehind(attacker, attacked)) container.setNewDamage(container.getNewDamage() * 2);
    }
}
