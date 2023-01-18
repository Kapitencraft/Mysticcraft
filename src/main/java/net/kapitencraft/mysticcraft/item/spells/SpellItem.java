package net.kapitencraft.mysticcraft.item.spells;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.IModItem;
import net.kapitencraft.mysticcraft.item.ModTiers;
import net.kapitencraft.mysticcraft.item.RNGDropHelper;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class SpellItem extends SwordItem implements IModItem {
    private SpellSlot[] spellSlots;
    private final int intelligence;
    private final int ability_damage;


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
            GemstoneSlot[] gemstoneSlots = applicable.getGemstoneSlots();
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
            if (this.getManaCost() > 0) {
                builder.put(ModAttributes.MANA_COST.get(), new AttributeModifier(SpellItem.MANA_COST_MOD, "Attribute Modifier for Mana Cost", this.getManaCost(), AttributeModifier.Operation.ADDITION));
            }
        }
        builder.putAll(super.getAttributeModifiers(slot, stack));
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
    private Spells.SpellType getType() {
        return this.getActiveSpell().TYPE;
    }
    public double getManaCost() {
        if (this.getActiveSpell() != null) {
            return this.getActiveSpellSlot().getManaCost();
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

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null) {
            use(context.getLevel(), context.getPlayer(), context.getHand());
        }
        return super.useOn(context);
    }

    private boolean handleMana(LivingEntity user) {
        double manaToUse = user.getAttributeValue(ModAttributes.MANA_COST.get());
        double overflowMana = user.getPersistentData().getDouble("overflowMana");
        double currentMana = user.getAttribute(ModAttributes.MANA.get()).getBaseValue() + overflowMana;
        if (currentMana == 0) { return false; }
        if (currentMana >= manaToUse) {
            if (overflowMana > 0) {
                user.getPersistentData().putDouble("overflowMana", overflowMana > manaToUse ? overflowMana - manaToUse : 0);
                manaToUse -=overflowMana;
            }
            if (manaToUse > 0) {
                user.getAttribute(ModAttributes.MANA.get()).setBaseValue(user.getAttributeBaseValue(ModAttributes.MANA.get()) - manaToUse);
            }
            ItemStack spellShardRNG = new ItemStack(ModItems.SPELL_SHARD.get());
            RNGDropHelper.calculateAndDrop(spellShardRNG, 0.00002f, user, new Vec3(user.getX(), user.getY(), user.getZ()));
            return true;
        }
        return false;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity user) {
        if (!level.isClientSide()) {
            Spell spell = this.getActiveSpell();
            if (spell.TYPE == Spells.RELEASE && this.handleMana(user)) {
                spell.execute(user, stack);
            }
            if (user instanceof Player player) {
                player.displayClientMessage(Component.literal("Used " + spell.getName() + ": " + FormattingCodes.RED + "-" + user.getAttributeValue(ModAttributes.MANA_COST.get()) + " Mana"), true);
            }
        }
        return stack;
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity user, @NotNull ItemStack stack, int count) {
        if (!level.isClientSide()) {
            Spell spell = this.getActiveSpell();
            if (spell.TYPE == Spells.CYCLE && this.handleMana(user)) {
                spell.execute(user, stack);
                if (count == 1 && user instanceof Player player) {
                    player.sendSystemMessage(Component.literal("Started Using " + spell.getName() + ": " + FormattingCodes.RED + "-" + (this.getManaCost() * 20) + " Mana" + "/s"));
                }
            }
        }
    }
}