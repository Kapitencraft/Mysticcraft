package net.kapitencraft.mysticcraft.item.spells;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.IModItem;
import net.kapitencraft.mysticcraft.item.ModTiers;
import net.kapitencraft.mysticcraft.item.RNGDropHelper;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MiscRegister;
import net.kapitencraft.mysticcraft.misc.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.misc.utils.MathUtils;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.kapitencraft.mysticcraft.misc.utils.TextUtils;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class SpellItem extends SwordItem implements IModItem {

    public static final String SPELL_EXECUTION_DUR = "ExeSpellDur";
    public static final String SPELL_EXE = "ExeSpell";
    private SpellSlot[] spellSlots;
    private final int intelligence;
    private final int ability_damage;


    public int getIndexForSlot(Spells spell) {
        for (int i = 0; i < this.spellSlots.length; i++) {
            if (this.spellSlots[i].getSpell() == spell) {
                return i;
            }
        }
        return -1;
    }

    //Display settings
    public abstract List<Component> getItemDescription();
    public abstract List<Component> getPostDescription();

    @Override
    public void appendHoverText(@Nonnull ItemStack itemStack, @Nullable Level level, @Nonnull List<Component> list, @Nonnull TooltipFlag flag) {
        @Nullable SpellSlot activeSpellSlot = this.getActiveSpellSlot();
        Spell spell;
        if (activeSpellSlot == null) {
            spell = Spells.EMPTY_SPELL;
        } else {
            spell = activeSpellSlot.getSpell();
        }
        if (itemStack.getItem() instanceof IGemstoneApplicable applicable) {
            applicable.appendDisplay(itemStack, list);
        }
        if (this.getItemDescription() != null) {
            list.addAll(this.getItemDescription());
            list.add(Component.literal(""));
        }
        if (this.spellSlots.length == 1) {
            spell.addDescription(list, this, itemStack);
        } else {
            if (Screen.hasShiftDown()) {
                list.add(Component.literal(""));
                addMultiSpellDisplay(list, itemStack);
            } else {
                list.add(Component.literal(""));
                list.add(Component.literal("press [SHIFT] to show all Spells"));
            }
        }
        list.add(Component.literal(""));
        if (this.getPostDescription() != null) {
            list.addAll(this.getPostDescription());
        }
    }

    private void addMultiSpellDisplay(List<Component> list, ItemStack stack) {
        for (SpellSlot spellSlot : this.spellSlots) {
            if (spellSlot != null) {
                list.add(Component.literal(""));
                spellSlot.getSpell().addDescription(list, this, stack);
            }
        }
    }

    public SpellItem(Properties p_41383_, int damage, float speed,  int spellSlots, int intelligence, int ability_damage) {
        super(ModTiers.SPELL_TIER, damage, speed,  p_41383_.stacksTo(1));
        this.spellSlots = new SpellSlot[spellSlots];
        this.intelligence = intelligence;
        this.ability_damage = ability_damage;
    }

    //Creating The Attribute Modifiers for this to work
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND) {
            if (this.intelligence > 0) {
                builder.put(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "Default Item Modifier", intelligence, AttributeModifier.Operation.ADDITION));
            }
            if (this.ability_damage > 0) {
                builder.put(ModAttributes.ABILITY_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "Default Item Modifier", ability_damage, AttributeModifier.Operation.ADDITION));
            }
        }
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        return builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.putAll(super.getAttributeModifiers(slot, stack));
        if (this instanceof IGemstoneApplicable applicable && slot == EquipmentSlot.MAINHAND) {
            return AttributeUtils.increaseAllByAmount(builder.build(), applicable.getAttributeModifiers(stack));
        }
        return builder.build();
    }

    //The actual logic for the SpellItem
    public boolean addSlot(SpellSlot slot) {
        if (this.getFirstEmptySpellSlot() > -1 && !this.containsSpell(slot.getSpell()) && slot.getSpell().canApply(this)) {
            this.setSlot(slot, this.getFirstEmptySpellSlot());
            return true;
        }
        return false;
    }

    private boolean containsSpell(Spell spell) {
        for (SpellSlot slot : this.spellSlots) {
            if (slot != null && slot.getSpell() == spell) {
                return true;
            }
        }
        return false;
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
            if (this.spellSlots[i] == null || this.spellSlots[i].getSpell() == Spells.EMPTY_SPELL) {
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
        if (this.spellSlots.length == 1) {
            SpellSlot activeSpellSlot = this.spellSlots[0];
            return Objects.requireNonNullElseGet(activeSpellSlot, () -> new SpellSlot(Spells.EMPTY_SPELL));
        }
        return null;
    }

    public Spell getActiveSpell() {
        if (this.getActiveSpellSlot() != null) {
            return this.getActiveSpellSlot().getSpell();
        }
        return null;
    }

    public static final UUID MANA_COST_MOD = UUID.fromString("95c53029-8493-4e05-9e7b-a0c0530dae83");
    private Spells.Type getType() {
        if (this.getActiveSpell() != null) {
            return this.getActiveSpell().getType();
        }
        return null;
    }

    @Override
    public @NotNull Rarity getRarity(ItemStack stack) {
        if (!stack.isEnchanted()) {
            return super.getRarity(stack);
        } else {
            final Rarity rarity = MiscUtils.getItemRarity(this);
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
        if (this.getType() != null) {
            Spells.Type type = this.getType();
            return type == Spells.Type.RELEASE ? 2 : Integer.MAX_VALUE;
        }
        return -1;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        CompoundTag tag = player.getPersistentData();
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.getSpellSlotAmount() > 1) {
            tag.putString(SPELL_EXE, tag.getString(SPELL_EXE) + "1");
            TextUtils.sendTitle(player, Component.literal(Spells.getPattern(tag.getString(SPELL_EXE))));
            if (this.getClosestSpell(tag.getString(SPELL_EXE)) != null) {
                TextUtils.sendSubTitle(player, Component.literal(FormattingCodes.ORANGE + "\u27A4 " + this.getClosestSpell(tag.getString(SPELL_EXE)).getName()));
            }
            tag.putByte(SPELL_EXECUTION_DUR, (byte) 20);
        } else {
            if (this.getActiveSpell().getType() == Spells.Type.RELEASE) {
                if (handleMana(player, this.getActiveSpell())) {
                    this.getActiveSpell().execute(player, itemstack);
                    player.displayClientMessage(Component.literal("Used " + this.getActiveSpell().getName() + ": " + FormattingCodes.RED + "-" + AttributeUtils.getAttributeValue(player.getAttribute(ModAttributes.MANA_COST.get()), this.getActiveSpell().getDefaultManaCost()) + " Mana"), true);
                }
            } else {
                player.startUsingItem(hand);
            }
        }
        return InteractionResultHolder.consume(itemstack);
    }

    public boolean executeSpell(String executionId, ItemStack stack, LivingEntity user) {
        if (Spells.contains(executionId) && executionId.length() == 7) {
            Spell spell = Spells.get(executionId);
            if (this.containsSpell(spell) && handleMana(user, spell)) {
                spell.execute(user, stack);
                if (user instanceof Player player) {
                    player.displayClientMessage(Component.literal("Used " + spell.getName() + ": " + FormattingCodes.RED + "-" + AttributeUtils.getAttributeValue(user.getAttribute(ModAttributes.MANA_COST.get()), spell.getDefaultManaCost()) + " Mana"), true);
                }
                return true;
            }
        }
        return false;
        }



    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null) {
            use(context.getLevel(), context.getPlayer(), context.getHand());
        }
        return super.useOn(context);
    }

    private boolean handleMana(LivingEntity user, Spell spell) {
        if (user.getAttribute(ModAttributes.INTELLIGENCE.get()) == null || user.level.isClientSide()) {
            return false;
        }
        double manaToUse = AttributeUtils.getAttributeValue(user.getAttribute(ModAttributes.MANA_COST.get()), spell.getDefaultManaCost());
        if (manaToUse >= 10000 && user instanceof Player player) {
            MiscUtils.awardAchievement(player, "mysticcraft:archmage");
        }
        double overflowMana = user.getPersistentData().getDouble(MiscRegister.OVERFLOW_MANA_ID);
        AttributeInstance manaInstance = user.getAttribute(ModAttributes.MANA.get());
        if (manaInstance != null) {
            double currentMana = manaInstance.getBaseValue() + overflowMana;
            if (currentMana == 0 || manaToUse == 0) {
                return false;
            }
            if (currentMana >= manaToUse) {
                if (overflowMana > 0) {
                    user.getPersistentData().putDouble(MiscRegister.OVERFLOW_MANA_ID, overflowMana > manaToUse ? overflowMana - manaToUse : 0);
                    manaToUse -= overflowMana;
                }
                if (manaToUse > 0) {
                    manaInstance.setBaseValue(user.getAttributeBaseValue(ModAttributes.MANA.get()) - manaToUse);
                }
                ItemStack spellShardRNG = new ItemStack(ModItems.SPELL_SHARD.get());
                RNGDropHelper.calculateAndDrop(spellShardRNG, 0.00002f, user, MathUtils.getPosition(user));
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player && this.getSpellSlotAmount() > 1) {
            CompoundTag tag = entity.getPersistentData();
            tag.putString(SPELL_EXE, tag.getString(SPELL_EXE) + "0");
            TextUtils.sendTitle(player, Component.literal(Spells.getPattern(tag.getString(SPELL_EXE))));
            if (this.getClosestSpell(tag.getString(SPELL_EXE)) != null) {
                TextUtils.sendSubTitle(player, Component.literal(FormattingCodes.ORANGE + "\u27A4 " + this.getClosestSpell(tag.getString(SPELL_EXE)).getName()));
            }
            tag.putByte(SPELL_EXECUTION_DUR, (byte) 20);
            return true;
        }
        return false;
    }

    private Spell getClosestSpell(String spell_exe) {
        return Spells.EMPTY_SPELL;
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity user, @NotNull ItemStack stack, int count) {
        Spell spell = this.getActiveSpell();
        if (spell.getType() == Spells.Type.CYCLE && this.handleMana(user, spell) && (Integer.MAX_VALUE - count & 2) == 0) {
            spell.execute(user, stack);
            double mana_cost = AttributeUtils.getAttributeValue(user.getAttribute(ModAttributes.MANA_COST.get()), spell.getDefaultManaCost());
            if (Integer.MAX_VALUE - count == 0 && user instanceof Player player) {
                player.sendSystemMessage(Component.literal("Started Using " + spell.getName() + ": " + FormattingCodes.RED + "-" + (mana_cost * 20) + " Mana" + "/s"));
            }
        }
    }
}