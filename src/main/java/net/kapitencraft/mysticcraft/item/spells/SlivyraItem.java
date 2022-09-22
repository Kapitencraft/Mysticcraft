package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.item.gemstone_slot.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone_slot.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SlivyraItem extends Item implements ISpellItem {

    SpellSlot[] spellSlots = {new SpellSlot(Spells.CRYSTAL_WARP)};

    Component[] description = {Component.Serializer.fromJson("Some say, it might be the most"), Component.Serializer.fromJson("powerful Magic Weapon in the world")};

    public SlivyraItem() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.RARE));
    }

    @Override
    public List<Component> getItemDescription() {
        return List.of(this.description);
    }

    @Override
    public List<Component> getPostDescription() {
        return new ArrayList<Component>();
    }

    @Override
    public SpellSlot[] getSpellSlots() {
        return this.spellSlots;
    }

    @Override
    public int getActiveSpell() {
        return 0;
    }

    @Override
    public int getSpellSlotAmount() {
        return 1;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, @Nonnull List<Component> list, @Nullable TooltipFlag flag) {
        StringBuilder gemstoneText = new StringBuilder();
        if (itemStack.getItem() instanceof IGemstoneApplicable) {
            for (GemstoneSlot slot : ((IGemstoneApplicable) itemStack.getItem()).getGemstoneSlots()) {
                gemstoneText.append(slot.getDisplay());
            }
        }
        if (!gemstoneText.toString().equals("")) {
            list.add(Component.Serializer.fromJson(gemstoneText.toString()));
        }
        list.addAll(this.getItemDescription());
        list.add(Component.Serializer.fromJson(""));
        list.addAll(this.spellSlots[this.getActiveSpell()].getSpell().getDescription());
        list.add(Component.Serializer.fromJson(""));
        list.addAll(this.getPostDescription());
    }
}
