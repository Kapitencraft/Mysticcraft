package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class SoulMageArmorFullSetBonus extends FullSetBonus {
    public SoulMageArmorFullSetBonus() {
        super("Mana Syphon");
    }

    @Override
    public void onEntityKilled(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {
        if (AttributeHelper.getSaveAttributeValue(ModAttributes.MANA.get(), user) != -1) {
            double mana = AttributeHelper.getSaveAttributeValue(ModAttributes.MANA.get(), user);
            Objects.requireNonNull(user.getAttribute(ModAttributes.MANA.get())).setBaseValue(mana + 10);
        }
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("Regenerate 10 Mana on kill"));
    }
}
