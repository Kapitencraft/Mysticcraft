package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class LongSwordItem extends ModSwordItem {

    public static final UUID ATTACK_RANGE_ID = UUID.fromString("97b8ca01-78bf-4bba-9132-4c3cf38ff406");

    public LongSwordItem(Tier p_43269_, int attackDamage, Properties p_43272_) {
        super(p_43269_, attackDamage, -3.3f, p_43272_);
    }

    public abstract double getReachMod();

    @Override
    public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.MAINHAND;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(ATTACK_RANGE_ID, "Long Sword Range Mod", this.getReachMod(), AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }
}
