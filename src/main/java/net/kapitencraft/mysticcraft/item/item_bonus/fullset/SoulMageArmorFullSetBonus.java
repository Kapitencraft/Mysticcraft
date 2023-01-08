package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SoulMageArmorFullSetBonus extends FullSetBonus {
    public SoulMageArmorFullSetBonus() {
        super("Mana Syphon");
    }

    @Override
    public void onEntityKilled(LivingEntity killed, LivingEntity user) {
        if (MISCTools.getSaveAttributeValue(ModAttributes.MANA.get(), user) != -1) {
            double mana = MISCTools.getSaveAttributeValue(ModAttributes.MANA.get(), user);
            Objects.requireNonNull(user.getAttribute(ModAttributes.MANA.get())).setBaseValue(mana + 10);
        }
    }

    @Nullable
    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        return null;
    }

    @Override
    public List<Component> getDisplay() {
        ArrayList<Component> list = new ArrayList<>();
        list.add(Component.literal("Regenerate 10 Mana on kill"));
        return list;
    }
}
