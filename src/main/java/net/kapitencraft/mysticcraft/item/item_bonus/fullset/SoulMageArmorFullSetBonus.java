package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.misc.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Nullable
    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        return null;
    }

    @Override
    public void onTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotID, boolean isSelected, int ticks) {

    }

    @Override
    public List<Component> getDisplay() {
        ArrayList<Component> list = new ArrayList<>();
        list.add(Component.literal("Regenerate 10 Mana on kill"));
        return list;
    }
}
