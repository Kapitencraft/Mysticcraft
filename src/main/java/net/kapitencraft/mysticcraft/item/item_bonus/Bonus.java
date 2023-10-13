package net.kapitencraft.mysticcraft.item.item_bonus;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
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

public abstract class Bonus {
    protected final String name;
    protected final String superName;

    protected Bonus(String name, String superName) {
        this.name = name;
        this.superName = superName;
    }

    public void onEntityKilled(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {}
    public @Nullable Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {return null;}

    public float onEntityHurt(LivingEntity hurt, LivingEntity user, MiscHelper.DamageType type, float damage) {
        return damage;
    }
    public void onTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity) {}
    public abstract Consumer<List<Component>> getDisplay();

    public List<Component> makeDisplay() {
        List<Component> display = new ArrayList<>();
        display.add(Component.literal(superName + " Bonus: " + name).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        getDisplay().accept(display);
        return display;
    }
}