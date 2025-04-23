package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class AssassinBonus implements Bonus<AssassinBonus> {
    public static final DataPackSerializer<AssassinBonus> SERIALIZER = DataPackSerializer.unit(AssassinBonus::new);

    @Override
    public DataPackSerializer<AssassinBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public void additionalToNetwork(FriendlyByteBuf buf) {

    }

    @Override
    public float onEntityHurt(LivingEntity attacked, LivingEntity attacker, MiscHelper.DamageType type, float damage) {
        if (MathHelper.isBehind(attacker, attacked)) damage *= 2;
        return damage;
    }

    @Override
    public void addDisplay(List<Component> currentTooltip) {
        currentTooltip.add(Component.translatable("set_bonus.assassin"));
    }
}
