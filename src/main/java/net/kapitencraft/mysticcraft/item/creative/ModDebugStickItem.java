package net.kapitencraft.mysticcraft.item.creative;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ModDebugStickItem extends Item implements IModItem {
    public ModDebugStickItem() {
        super(new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack p_41453_) {
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return super.useOn(context);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        multimap.putAll(super.getAttributeModifiers(slot, stack));
        multimap.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "Test Mod", 100, AttributeModifier.Operation.ADDITION));
        multimap.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "Test Mod", 100, AttributeModifier.Operation.MULTIPLY_BASE));
        multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "Test Mod", 1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return multimap;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack p_41454_) {
        return 72;
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
