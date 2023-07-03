package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.misc.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SoulMageArmorFullSetBonus extends FullSetBonus {
    public SoulMageArmorFullSetBonus() {
        super("Mana Syphon");
    }

    @Override
    public void onEntityKilled(LivingEntity killed, LivingEntity user, MiscUtils.DamageType type) {
        if (AttributeUtils.getSaveAttributeValue(ModAttributes.MANA.get(), user) != -1) {
            double mana = AttributeUtils.getSaveAttributeValue(ModAttributes.MANA.get(), user);
            Objects.requireNonNull(user.getAttribute(ModAttributes.MANA.get())).setBaseValue(mana + 10);
        }
    }

    @Override
    public List<Component> getDisplay() {
        ArrayList<Component> list = new ArrayList<>();
        list.add(Component.literal("Regenerate 10 Mana on kill"));
        return list;
    }
}
