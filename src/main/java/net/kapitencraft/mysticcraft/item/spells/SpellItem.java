package net.kapitencraft.mysticcraft.item.spells;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.IModItem;
import net.kapitencraft.mysticcraft.item.ModTiers;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class SpellItem extends TieredItem implements IModItem {
    private SpellSlot[] spellSlots;
    private final int intelligence;
    private final int ability_damage;


    //Display settings
    public abstract List<Component> getItemDescription();
    public abstract List<Component> getPostDescription();

    @Override
    public void appendHoverText(@Nonnull ItemStack itemStack, @Nullable Level level, @Nonnull List<Component> list, @Nonnull TooltipFlag flag) {
        @Nullable GemstoneSlot[] gemstoneSlots = itemStack.getItem() instanceof IGemstoneApplicable applicable ? applicable.getGemstoneSlots() : null;
        @Nullable SpellSlot activeSpellSlot = this.getActiveSpellSlot();
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
            if (!gemstoneText.toString().equals("")) {
                list.add(Component.literal(gemstoneText.toString()));
            }
        }
        if (this.getItemDescription() != null) {
            list.addAll(this.getItemDescription());
            list.add(Component.literal(""));
        }
        spell.addDescription(list, this, itemStack);
        list.add(Component.literal(""));
        if (this.getPostDescription() != null) {
            list.addAll(this.getPostDescription());
        }
    }



    public SpellItem(Properties p_41383_, int spellSlots, int intelligence, int ability_damage) {
        super(ModTiers.SPELL_TIER, p_41383_.stacksTo(1));
        this.spellSlots = new SpellSlot[spellSlots];
        this.intelligence = intelligence;
        this.ability_damage = ability_damage;
    }

    //Creating The Attribute Modifiers for this to work
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND) {
            if (this.intelligence > 0) {
                builder.put(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(slot)], "Default Item Modifier", intelligence, AttributeModifier.Operation.ADDITION));
            }
            if (this.ability_damage > 0) {
                builder.put(ModAttributes.ABILITY_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(slot)], "Default Item Modifier", ability_damage, AttributeModifier.Operation.ADDITION));
            }
        }
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        return builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND) {
            if (this.getManaCost(stack) > 0)
            builder.put(ModAttributes.MANA_COST.get(), new AttributeModifier(UUID.randomUUID(), "Attribute Modifier for Mana Cost", this.getManaCost(stack), AttributeModifier.Operation.ADDITION));
        }
        builder.putAll(super.getAttributeModifiers(slot, stack));
        return builder.build();
    }


    //The actual logic for the SpellItem
    protected void addSlot(SpellSlot slot) {
        if (this.getFirstEmptySpellSlot() > -1) {
            this.setSlot(slot, this.getFirstEmptySpellSlot());
        }
    }

    private void setSlot(SpellSlot slot, int index) {
        if (this.spellSlots != null) {
            MysticcraftMod.sendInfo("Successfully added Spell: " + slot.getSpell().getName() + " in Index: " + index);
            this.spellSlots[index] = slot;
        } else {
            MysticcraftMod.sendInfo("You have to run the constructor first to use this Method");
        }

    }

    private int getFirstEmptySpellSlot() {
        for (int i = 0; i < this.getSpellSlotAmount(); i++) {
            if (this.spellSlots[i] == null) {
                return i;
            }
        }
        return -1;
    }
    public int getSpellSlotAmount() {
        return this.spellSlots.length;
    }
    public int getActiveSpellIndex() {
        return 0;
    }

    public SpellSlot getActiveSpellSlot() {
        SpellSlot activeSpellSlot = this.spellSlots[this.getActiveSpellIndex()];
        if (activeSpellSlot != null) {
            return this.spellSlots[this.getActiveSpellIndex()];
        }
        return new SpellSlot(Spells.EMPTY_SPELL);
    }

    public Spell getActiveSpell() {
        return this.getActiveSpellSlot().getSpell();
    }

    public static final UUID MANA_COST_MOD = UUID.fromString("95c53029-8493-4e05-9e7b-a0c0530dae83");
    public static final UUID ULTIMATE_WISE_MOD = UUID.fromString("88572caa-0070-4e33-a82b-dadb48658c80");

    private Spells.SpellType getType() {
        return this.getActiveSpell().TYPE;
    }
    public double getManaCost(ItemStack stack) {
        if (this.getActiveSpell() != null) {
            double manaCost = this.getActiveSpellSlot().getManaCost();
            if (stack.getEnchantmentLevel(ModEnchantments.ULTIMATE_WISE.get()) > 0) {
                manaCost *= 1 - (0.1 * stack.getEnchantmentLevel(ModEnchantments.ULTIMATE_WISE.get()));
            }
            return manaCost;
        } else {
            return 0;
        }
    }

    @Override
    public @NotNull Rarity getRarity(ItemStack stack) {
        if (!stack.isEnchanted()) {
            return super.getRarity(stack);
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


    /* EXECUTING SPELL */

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        Spells.SpellType type = this.getType();
        return type == Spells.RELEASE ? 2 : Integer.MAX_VALUE;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    private boolean handleMana(LivingEntity user, ItemStack stack) {
        if (user.getAttributeBaseValue(ModAttributes.MANA.get()) == 0) {
            return false;
        }
        if (user.getAttributes().hasAttribute(ModAttributes.MANA.get()) && user.getAttribute(ModAttributes.MANA.get()).getBaseValue() >= this.getManaCost(stack)) {
            user.getAttribute(ModAttributes.MANA.get()).setBaseValue(user.getAttributeBaseValue(ModAttributes.MANA.get()) - this.getManaCost(stack));
            return true;
        }
        return false;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity user) {
        if (!level.isClientSide()) {
            Spell spell = this.getActiveSpell();
            if (spell.TYPE == Spells.RELEASE && this.handleMana(user, stack)) {
                spell.execute(user, stack);
                if (user instanceof Player player) {
                    player.sendSystemMessage(Component.literal("Used " + spell.getName() + ": " + FormattingCodes.RED.UNICODE + "-" + this.getManaCost(stack) + " Mana"));
                }
            }
        }
        return stack;
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity user, @NotNull ItemStack stack, int count) {
        if (!level.isClientSide()) {
            Spell spell = this.getActiveSpell();
            if (spell.TYPE == Spells.CYCLE && this.handleMana(user, stack)) {
                spell.execute(user, stack);
                if (count == 1 && user instanceof Player player) {
                    player.sendSystemMessage(Component.literal("Started Using " + spell.getName() + ": " + FormattingCodes.RED.UNICODE + "-" + (this.getManaCost(stack) * 20) + " Mana" + "/s"));
                }
            }
        }
    }
}