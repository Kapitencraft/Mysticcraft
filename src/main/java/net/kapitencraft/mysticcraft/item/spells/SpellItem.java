package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.gemstone_slot.Gemstone;
import net.kapitencraft.mysticcraft.item.gemstone_slot.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone_slot.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class SpellItem extends Item {
    @Override
    public void appendHoverText(@Nonnull ItemStack itemStack, @Nullable Level level, @Nonnull List<Component> list, @Nonnull TooltipFlag flag) {
        @Nullable GemstoneSlot[] gemstoneSlots = itemStack.getItem() instanceof IGemstoneApplicable applicable ? applicable.getGemstoneSlots() : null;
        @Nullable SpellSlot activeSpellSlot = this.getSpellSlots()[this.getActiveSpell()];
        Spell spell;
        if (activeSpellSlot == null) {
            spell = Spells.EMPTY_SPELL;
        } else {
            spell = activeSpellSlot.getSpell();
        }
        StringBuilder gemstoneText = new StringBuilder();
        if (gemstoneSlots != null) {
            for (@Nullable GemstoneSlot slot : gemstoneSlots) {
                if (slot != null) {
                    gemstoneText.append(slot.getDisplay());
                }
            }
        }
        if (!gemstoneText.toString().equals("")) {
            list.add(Component.literal(gemstoneText.toString()));
        }
        list.addAll(this.getItemDescription());
        list.add(Component.literal(""));
        spell.addDescription(list, this, itemStack);
        list.add(Component.literal(""));
            if (this.getPostDescription() != null) {
            list.addAll(this.getPostDescription());
        }
        if (flag.isAdvanced() && gemstoneSlots != null) {
            boolean flag1 = false;
            for (@Nullable GemstoneSlot slot : gemstoneSlots) {
                flag1 = slot != null && slot.getAppliedGemstone() != null;
                if (flag1) {
                    break;
                }
            }
            if (flag1) {
                HashMap<Attribute, Double> attributeModifier = new HashMap<>();
                ArrayList<Attribute> attributes = new ArrayList<>();
                double gemstoneModifier;
                Attribute attribute;
                Gemstone gemstone;
                for (@Nullable GemstoneSlot slot : gemstoneSlots) {
                    gemstone = slot.getAppliedGemstone();
                    attribute = gemstone.modifiedAttribute;
                    gemstoneModifier = gemstone.BASE_VALUE * gemstone.getRarity().modMul;
                    if (attributeModifier.containsKey(attribute)) {
                        attributeModifier.put(attribute, attributeModifier.get(attribute) + gemstoneModifier);
                    } else {
                        attributeModifier.put(attribute, gemstoneModifier);
                        attributes.add(attribute);
                    }
                }
                list.add(Component.literal("Gemstone Modifications:").withStyle(ChatFormatting.GREEN));
                for (Attribute ignored : attributes) {
                    list.add(Component.literal(ignored.toString() + ": " + attributeModifier.get(ignored)));
                }
            }
        }
    }

    @Override
    public @Nonnull Rarity getRarity(ItemStack stack) {
        if (!stack.isEnchanted()) {
            return MISCTools.getItemRarity(this);
        } else {
            final Rarity rarity = MISCTools.getItemRarity(this);
            if (rarity == Rarity.COMMON) {
                return Rarity.UNCOMMON;
            } else if (rarity == Rarity.UNCOMMON) {
                return Rarity.RARE;
            } else if (rarity == Rarity.RARE) {
                return Rarity.EPIC;
            } else if (rarity == Rarity.EPIC) {
                return FormattingCodes.LEGENDARY;
            } else if (rarity == FormattingCodes.LEGENDARY) {
                return FormattingCodes.MYTHIC;
            } else if (rarity == FormattingCodes.MYTHIC) {
                return FormattingCodes.DIVINE;
            } else {
                return Rarity.COMMON;
            }
        }
    }


    public SpellItem(Properties p_41383_) {
        super(p_41383_.stacksTo(1));
    }

    public abstract List<Component> getItemDescription();
    public abstract List<Component> getPostDescription();
    public abstract SpellSlot[] getSpellSlots();
    public abstract int getSpellSlotAmount();
    public abstract int getActiveSpell();

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @Nullable Level level, @NotNull LivingEntity user, int p_41415_) {
        Spell spell = this.getSpellSlots()[this.getActiveSpell()].getSpell();
        if (spell.TYPE == Spells.RELEASE) {
            spell.execute(user, stack);
        }

    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, @Nullable int count) {
        Spell spell = this.getSpellSlots()[this.getActiveSpell()].getSpell();
        if (spell.TYPE == Spells.CYCLE) {
            spell.execute(player, stack);
        }
    }

    public double calculateManaCost(ItemStack stack) {
        return this.getSpellSlots()[this.getActiveSpell()].getManaCost() * (1 - (this.getEnchantmentLevel(stack, ModEnchantments.ULTIMATE_WISE.get())) * 0.1);
    }
}
