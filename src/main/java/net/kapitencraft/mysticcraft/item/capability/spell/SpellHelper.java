package net.kapitencraft.mysticcraft.item.capability.spell;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.cooldown.Cooldown;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.capability.ItemData;
import net.kapitencraft.mysticcraft.item.misc.RNGHelper;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaMain;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.kapitencraft.mysticcraft.spell.Element;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

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
            MysticcraftMod.LOGGER.warn("unable to change slot: {}", e.getMessage());
        }

        return false;
    }

    public boolean handleManaAndExecute(LivingEntity user, Spell spell, ItemStack stack) {
        if (user.getAttribute(ModAttributes.INTELLIGENCE.get()) == null || user instanceof Player player && Requirement.doesntMeetRequirements(player, stack.getItem())) {
            return false;
        }
        double manaToUse = AttributeHelper.getAttributeValue(user.getAttribute(ModAttributes.MANA_COST.get()), spell.getDefaultManaCost());
        if (manaToUse >= 10000 && user instanceof Player player) {
            MiscHelper.awardAchievement(player, "mysticcraft:archmage");
        }
        AttributeInstance manaInstance = user.getAttribute(ModAttributes.MANA.get());
        Cooldown cooldown = spell.getCooldown();
        boolean hasCooldown = cooldown != null;
        if (!hasCooldown || !cooldown.isActive(user) && manaInstance != null && ManaMain.hasMana(user, manaToUse)) {
            try {
                spell.execute(user, stack);
            } catch (SpellExecutionFailedException e) {
                if (user instanceof Player player) {
                    player.displayClientMessage(Component.translatable(e.getMsg()), true);
                    return false;
                }
            }

            if (ManaMain.consumeMana(user, manaToUse)) {
                if (cooldown != null) cooldown.applyCooldown(user, true);
                sendUseDisplay(user, spell);
                List<Element> elements = spell.elements();
                if (!elements.isEmpty()) {//spawn spell shards
                    ItemStack spellShardRNG = new ItemStack(ModItems.ELEMENTAL_SHARDS.get(MathHelper.pickRandom(elements)).get());
                    RNGHelper.calculateAndDrop(spellShardRNG, 0.00002f, user, user.position());
                }
                return true;
            }
        }
        return false;
    }

    public boolean executeSpell(String executionId, ItemStack stack, LivingEntity user) {
        if (Spells.contains(executionId) && executionId.length() == 7) {
            Spell spell = Spells.get(executionId);
            return this.containsSpell(spell) && handleManaAndExecute(user, spell, stack);
        }
        return false;
    }


    private static void sendUseDisplay(LivingEntity user, Spell spell) {
        if (user instanceof Player player) {
            double mana_cost = AttributeHelper.getAttributeValue(player.getAttribute(ModAttributes.MANA_COST.get()), spell.getDefaultManaCost());
            MutableComponent visible;
            String wrappedManaUsage = TextHelper.wrapInRed("-" + mana_cost + " Mana");
            if (spell.getType() == Spell.Type.RELEASE) visible = Component.translatable("spell.cast", spell.getName(), wrappedManaUsage);
            else visible = Component.translatable("spell.use", spell.getName(), wrappedManaUsage);
            TextHelper.setHotbarDisplay(player, visible.withStyle(ChatFormatting.AQUA));
        }
    }

    private static final double CAST_OFFSET_SCALE = 0.31;

    public static Vec3 getCastOffset(Vec2 rotationVec, boolean left) {
        Vec3 lookVec = MathHelper.calculateViewVector(rotationVec.x, rotationVec.y).scale(0.60);
        Vec3 sideOffset = MathHelper.calculateViewVector(rotationVec.x, rotationVec.y + 90).scale(left ? -CAST_OFFSET_SCALE : CAST_OFFSET_SCALE);
        return lookVec.add(sideOffset).add(0, -0.275, 0);
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
            ListTag listTag = tag.getList("Slots", Tag.TAG_COMPOUND);
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
        tag.put("Slots", slotsTag);
        stack.addTagElement(getTagId(), tag);
    }
}