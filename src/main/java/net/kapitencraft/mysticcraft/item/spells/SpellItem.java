package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModCreativeModeTabs;
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
import net.minecraft.world.entity.LivingEntity;
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



    public SpellItem(Properties p_41383_, int spellSlots) {
        super(ModTiers.SPELL_TIER, p_41383_.stacksTo(1).tab(ModCreativeModeTabs.SPELL_AND_GEMSTONE));
        this.spellSlots = new SpellSlot[spellSlots];
    }

    protected void addSlot(SpellSlot slot, int index) {
        if (this.spellSlots != null) {
            MysticcraftMod.sendInfo("Successfully added Spell: " + slot.getSpell().getName() + "in Index: " + index);
        } else {
            MysticcraftMod.sendInfo("You have to run the constructor first to use this Method");
        }
    }

    public abstract List<Component> getItemDescription();
    public abstract List<Component> getPostDescription();
    public int getSpellSlotAmount() {
        return this.spellSlots.length;
    }
    public int getActiveSpellIndex() {
        return 0;
    }

    public SpellSlot getActiveSpellSlot() {
        if (this.spellSlots[this.getActiveSpellIndex()] != null) {
            return this.spellSlots[this.getActiveSpellIndex()];
        }
        return new SpellSlot(Spells.EMPTY_SPELL);
    }

    public Spell getActiveSpell() {
        if (this.getActiveSpellSlot() != null) {
            return this.getActiveSpellSlot().getSpell();
        }
        return Spells.EMPTY_SPELL;
    }

    public static final UUID MANA_COST_MOD = UUID.fromString("95c53029-8493-4e05-9e7b-a0c0530dae83");
    public static final UUID ULTIMATE_WISE_MOD = UUID.fromString("88572caa-0070-4e33-a82b-dadb48658c80");

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
    public int getUseDuration(@NotNull ItemStack stack) {
        if (this.spellSlots != null && this.getType() != null) {
            MysticcraftMod.sendInfo("?");
        }
        Spells.SpellType type = this.getType();
        return type == Spells.RELEASE ? 1 : Integer.MAX_VALUE;
    }


    /* EXECUTING SPELL */

    private boolean handleMana(LivingEntity user) {
        if (user.getAttributes().hasAttribute(ModAttributes.MANA.get()) && user.getAttribute(ModAttributes.MANA.get()).getBaseValue() >= this.getManaCost()) {
            user.getAttribute(ModAttributes.MANA.get()).setBaseValue(user.getAttributeBaseValue(ModAttributes.MANA.get()) - this.getManaCost());
            return true;
        }
        return false;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity user, int timeLeft) {
        MysticcraftMod.sendInfo("Launching Spell");
        Spell spell = this.getActiveSpell();
        if (spell.TYPE == Spells.RELEASE && this.handleMana(user)) {
            spell.execute(user, stack);
            if (user instanceof Player player) {
                player.sendSystemMessage(Component.literal("Used " + spell.getName() + ": " + FormattingCodes.RED.UNICODE + "-" + this.getManaCost()));
            }
        }
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity user, @NotNull ItemStack stack, int count) {
        MysticcraftMod.sendInfo("Launching Spell");
        Spell spell = this.getActiveSpell();
        if (spell.TYPE == Spells.CYCLE && this.handleMana(user)) {
            spell.execute(user, stack);
            if (count == 1 && user instanceof Player player) {
                player.sendSystemMessage(Component.literal("Started Using " + spell.getName() + ": " + FormattingCodes.RED.UNICODE + "-" + (this.getManaCost() * 20) + "/s"));
            }
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
}
