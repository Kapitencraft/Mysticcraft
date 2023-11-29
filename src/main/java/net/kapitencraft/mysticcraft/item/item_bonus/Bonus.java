package net.kapitencraft.mysticcraft.item.item_bonus;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface Bonus {

    default void onEntityKilled(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {}
    default @Nullable Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {return null;}

    default float onEntityHurt(LivingEntity hurt, LivingEntity user, MiscHelper.DamageType type, float damage) {
        return damage;
    }

    default float onTakeDamage(LivingEntity hurt, LivingEntity source, MiscHelper.DamageType type, float damage) {
        return damage;
    }
    default void onTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity) {}
    Consumer<List<Component>> getDisplay();

    String getSuperName();
    String getName();

    default List<Component> makeDisplay() {
        List<Component> display = new ArrayList<>();
        display.add(Component.literal((this instanceof Spell ? "Spell: " : getSuperName() + " Bonus: ") + getName()).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        getDisplay().accept(display);
        return display;
    }
}