package net.kapitencraft.mysticcraft.capability.gemstone;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.item.modifier_display.EquipmentDisplayExtension;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.artificer_table.ArtificerTableMenu;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public record GemstoneHandler(GemstoneSlot[] slots) implements EquipmentDisplayExtension, TooltipProvider {
    public static final Codec<GemstoneHandler> CODEC = GemstoneSlot.CODEC.listOf().xmap(l -> l.toArray(GemstoneSlot[]::new), List::of).xmap(GemstoneHandler::new, GemstoneHandler::slots);
    public static final StreamCodec<RegistryFriendlyByteBuf, GemstoneHandler> STREAM_CODEC = GemstoneSlot.STREAM_CODEC.apply(ByteBufCodecs.list()).map(l -> l.toArray(GemstoneSlot[]::new), List::of).map(GemstoneHandler::new, GemstoneHandler::slots);

    public boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex) {
        GemstoneSlot slot = slots[slotIndex];
        if (slot.isValidGemstone(gemstoneType)) {
            slots[slotIndex] = slot.setApplied(gemstoneType, rarity);
            return true;
        }
        return false;
    }

    public boolean putGemstoneFromStack(ItemStack gem, int slotIndex) {
        if (gem.getItem() != ModItems.GEMSTONE.get()) return false;
        GemstoneType type = IGemstoneItem.getGemstone(gem);
        GemstoneType.Rarity rarity = IGemstoneItem.getGemRarity(gem);
        return this.putGemstone(type, rarity, slotIndex);
    }

    public int getSlotAmount() {
        return slots.length;
    }

    @Override
    public Style getStyle() {
        return Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE);
    }

    @Override
    public Type getType() {
        return Type.DEFAULT;
    }

    @Override
    public ResourceLocation getModifiersLocation() {
            return MysticcraftMod.res("embedded_gemstones");
    }

    public static GemstoneHandler get(ItemStack stack) {
        return stack.get(ModDataComponentTypes.EMBEDDED_GEMSTONES);
    }

    public static GemstoneHandler create(GemstoneSlot.Type... types) {
        return new Builder(types).build();
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        MutableComponent component = null;
        try {
            for (@Nullable GemstoneSlot slot : this.slots()) {
                if (slot != null && slot != GemstoneSlot.BLOCKED) {
                    if (component == null) {
                        component = slot.getDisplay();
                    } else {
                        component.append(slot.getDisplay());
                    }
                }
            }
        } catch (NullPointerException e) {
            MysticcraftMod.LOGGER.warn(Markers.GEMSTONE, "unable to read Gemstone slots: {}", e.getMessage());
            e.printStackTrace(System.err);
        }
        if (component != null) {
            tooltipAdder.accept(component);
        }
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers() {
        HashMap<Holder<Attribute>, Double> attributeModifier = new HashMap<>();
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        double gemstoneModifier;
        @Nullable Holder<Attribute> attribute;
        @Nullable GemstoneType gemstoneType;
        try {
            for (@Nullable GemstoneSlot slot : this.slots()) {
                if (slot != null) {
                    gemstoneType = slot.gemstoneType();
                    if (gemstoneType != null) {
                        attribute = gemstoneType.modifiedAttribute;
                        gemstoneModifier = gemstoneType.baseValue * slot.rarity().modMul; // * (1 + stack.getEnchantmentLevel(ModEnchantments.EFFICIENT_JEWELLING.get()) * 0.08); TODO
                        if (attributeModifier.containsKey(attribute))
                            attributeModifier.put(attribute, attributeModifier.get(attribute) + gemstoneModifier);
                        else
                            attributeModifier.put(attribute, gemstoneModifier);
                    }
                }
            }
        } catch (NullPointerException e) {
            MysticcraftMod.LOGGER.warn(Markers.GEMSTONE, "unable to read Gemstone slots: {}", e.getMessage());
        }
        for (Holder<Attribute> attribute1 : attributeModifier.keySet()) {
            modifiers.put(attribute1, new AttributeModifier(MysticcraftMod.res("gemstone_modifications"), attributeModifier.get(attribute1), attribute1 == Attributes.MOVEMENT_SPEED ? AttributeModifier.Operation.ADD_MULTIPLIED_BASE : AttributeModifier.Operation.ADD_VALUE));
        }
        return modifiers;
    }

    public static class Builder {
        private final GemstoneSlot.Type[] types;

        public Builder(GemstoneSlot.Type... types) {
            this.types = types;
        }

        public GemstoneHandler build() {
            if (types.length > ArtificerTableMenu.MAX_GEMSTONE_SLOTS) throw new IllegalStateException("detected Gemstone builder exceeding size limit (found: " + types.length + ", max: " + ArtificerTableMenu.MAX_GEMSTONE_SLOTS + ")");
            GemstoneSlot[] slots = new GemstoneSlot[types.length];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = new GemstoneSlot(types[i], GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY);
            }
            return new GemstoneHandler(slots);
        }
    }
}
