package net.kapitencraft.mysticcraft.item.data.spell;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.data.ItemData;
import net.kapitencraft.mysticcraft.item.misc.RNGDropHelper;
import net.kapitencraft.mysticcraft.misc.MiscRegister;
import net.kapitencraft.mysticcraft.spell.Element;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class SpellHelper implements ItemData<SpellSlot[], SpellHelper> {

    private final SpellSlot[] spellSlots;
    private final Item item;

    public SpellHelper(int spellSlotAmount, Item item) {
        this.spellSlots = new SpellSlot[spellSlotAmount];
        for (int i = 0; i < spellSlotAmount; i++) {
            this.spellSlots[i] = new SpellSlot(Spells.EMPTY_SPELL);
        }
        this.item = item;
    }

    public boolean addSlot(SpellSlot slot) {
        if (this.getFirstEmptySpellSlot() > -1 && !this.containsSpell(slot.getSpell()) && slot.getSpell().canApply(item)) {
            this.setSlot(this.getFirstEmptySpellSlot(), slot);
            return true;
        }
        return false;
    }

    private void addMultiSpellDisplay(List<Component> list, ItemStack stack, Player player, ISpellItem spellItem) {
        for (SpellSlot spellSlot : this.spellSlots) {
            if (spellSlot != null) {
                list.add(Component.literal(""));
                spellSlot.getSpell().addDescription(list, spellItem, stack, player);
            }
        }
    }

    public void appendDisplay(List<Component> list, ItemStack stack, Player player, ISpellItem spellItem) {
        @Nullable SpellSlot activeSpellSlot = this.getActiveSpellSlot();
        Spell spell;
        if (activeSpellSlot == null) {
            spell = Spells.EMPTY_SPELL;
        } else {
            spell = activeSpellSlot.getSpell();
        }
        if (this.spellSlots.length == 1) {
            spell.addDescription(list, spellItem, stack, player);
        } else {
            if (Screen.hasShiftDown()) {
                list.add(Component.literal(""));
                addMultiSpellDisplay(list, stack, player, spellItem);
            } else {
                list.add(Component.literal(""));
                list.add(Component.literal("press [SHIFT] to show all Spells"));
            }
        }
    }

    public int getIndexForSlot(Spells spell) {
        for (int i = 0; i < this.spellSlots.length; i++) {
            if (this.spellSlots[i].getSpell() == spell) {
                return i;
            }
        }
        return -1;
    }

    private boolean containsSpell(Spell spell) {
        for (SpellSlot slot : this.spellSlots) {
            if (slot != null && slot.getSpell() == spell) {
                return true;
            }
        }
        return false;
    }

    public boolean removeSlot(int slotIndex) {
        if (this.spellSlots.length == 1) {
            this.spellSlots[0] = null;
            return true;
        }
        try {
            this.spellSlots[slotIndex] = null;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            MysticcraftMod.sendWarn("unable to change slot: " + e.getMessage());
        }

        return false;
    }

    public boolean handleMana(LivingEntity user, Spell spell) {
        if (user.getAttribute(ModAttributes.INTELLIGENCE.get()) == null) {
            return false;
        }
        double manaToUse = AttributeHelper.getAttributeValue(user.getAttribute(ModAttributes.MANA_COST.get()), spell.getDefaultManaCost());
        if (manaToUse >= 10000 && user instanceof Player player) {
            MiscHelper.awardAchievement(player, "mysticcraft:archmage");
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
                    manaInstance.setBaseValue(manaInstance.getBaseValue() - manaToUse);
                    sendUseDisplay(user, spell);
                }
                List<Element> elements = spell.elements();
                ItemStack spellShardRNG = new ItemStack(ModItems.ELEMENTAL_SHARDS.get(MathHelper.pickRandom(elements)).get());
                RNGDropHelper.calculateAndDrop(spellShardRNG, 0.00002f, user, MathHelper.getPosition(user));
                return true;
            }
        }
        return false;
    }

    public boolean executeSpell(String executionId, ItemStack stack, LivingEntity user) {
        if (Spells.contains(executionId) && executionId.length() == 7) {
            Spell spell = Spells.get(executionId);
            if (this.containsSpell(spell) && handleMana(user, spell)) {
                spell.execute(user, stack);
                return true;
            }
        }
        return false;
    }


    private static void sendUseDisplay(LivingEntity user, Spell spell) {
        if (user instanceof Player player) {
            double mana_cost = AttributeHelper.getAttributeValue(player.getAttribute(ModAttributes.MANA_COST.get()), spell.getDefaultManaCost());
            if (spell.getType() == Spell.Type.RELEASE) TextHelper.setHotbarDisplay(player, Component.literal("Used " + spell.getName() + ": " + TextHelper.wrapInRed("-" + mana_cost + " Mana")).withStyle(ChatFormatting.AQUA));
            else TextHelper.setHotbarDisplay(player, Component.literal("Using " + spell.getName() + ": " + TextHelper.wrapInRed("-" + (mana_cost * 20) + " Mana" + "/s")).withStyle(ChatFormatting.AQUA));
        }
    }

    boolean hasSpell(Spell spell) {
        for (SpellSlot slot : this.spellSlots) {
            if (slot.getSpell() == spell) return true;
        }
        return false;
    }


    public void setSlot(int index, SpellSlot slot) {
        this.spellSlots[index] = slot;
    }

    public int getFirstEmptySpellSlot() {
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

    public Spell.Type getType() {
        return getActiveSpell().getType();
    }


    @Override
    public SpellSlot[] loadData(ItemStack stack, Consumer<SpellHelper> stackConsumer) {
        CompoundTag tag = stack.getTagElement(getTagId());
        if (tag != null) {
            ListTag listTag = tag.getList("slots", Tag.TAG_COMPOUND);
            int i = 0;
            for (Tag tag1 : listTag) {
                if (tag1 instanceof CompoundTag cTag) {
                    SpellSlot slot = SpellSlot.fromNbt(cTag);
                    setSlot(i, slot);
                    i++;
                }
            }
        } else {
            stackConsumer.accept(this);
            save(stack);
        }
        return this.spellSlots;
    }

    void save(ItemStack stack) {
        this.saveData(stack, this.spellSlots);
    }

    @Override
    public void getDisplay(ItemStack stack, List<Component> list) {

    }

    @Override
    public String getTagId() {
        return "SpellData";
    }

    @Override
    public void saveData(ItemStack stack, SpellSlot[] slots) {
        CompoundTag tag = new CompoundTag();
        ListTag slotsTag = new ListTag();
        for (int i = 0; i < slots.length; i++) {
            SpellSlot slot = slots[i];
            if (slot == null) slot = new SpellSlot(Spells.EMPTY_SPELL);
            slotsTag.add(i, slot.toNbt());
        }
        tag.putShort("Size", (short) slots.length);
        tag.put("slots", slotsTag);
        stack.addTagElement(getTagId(), tag);
    }
}
