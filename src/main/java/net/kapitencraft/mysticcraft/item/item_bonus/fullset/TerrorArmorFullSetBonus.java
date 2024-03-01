package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.function.Consumer;

public class TerrorArmorFullSetBonus extends KillIncreasingStackFullSetBonus {
    public TerrorArmorFullSetBonus() {
        super("Hydra", damageType -> MiscHelper.DamageType.RANGED == damageType, ModAttributes.ARROW_SPEED, 1f, 120);
    }

    @Override
    protected void killWhen10(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {
        user.getPersistentData().putBoolean("GetTwoArrows", true);
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return components -> {};
    }
}
