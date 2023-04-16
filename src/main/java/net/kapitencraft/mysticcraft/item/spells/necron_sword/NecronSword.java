package net.kapitencraft.mysticcraft.item.spells.necron_sword;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class NecronSword extends SpellItem implements IGemstoneApplicable {
    protected static final int BASE_DAMAGE = 12;
    protected static final double BASE_STRENGHT = 150;
    protected static final int REFINED_BASE_DAMAGE = 13;
    protected static final double BASE_FEROCITY = 30;
    protected static final int BASE_INTEL = 50;
    private final double FEROCITY;
    private final double STRENGHT;

    @Override
    public GemstoneSlot[] getDefaultSlots() {
        GemstoneSlot extraSlot = (this instanceof Valkyrie ? GemstoneSlot.STRENGHT : this instanceof Hyperion ? GemstoneSlot.INTELLIGENCE : this instanceof Scylla ? GemstoneSlot.COMBAT : this instanceof Astraea ? GemstoneSlot.DEFENCE : null);
        return extraSlot == null ? null : new GemstoneSlot[] {GemstoneSlot.COMBAT, extraSlot};
    }

    @Override
    public GemstoneHelper getHelper() {
        return new GemstoneHelper(this.getDefaultSlots());
    }

    public NecronSword(int damage, int intelligence, double ferocity, double strenght) {
        super(new Properties().rarity(FormattingCodes.LEGENDARY), damage, -2.4f, 1, intelligence, 0);
        this.FEROCITY = ferocity;
        this.STRENGHT = strenght;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(ModAttributes.FEROCITY.get(), MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, this.FEROCITY, EquipmentSlot.MAINHAND));
            builder.put(ModAttributes.STRENGTH.get(), MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, this.STRENGHT, EquipmentSlot.MAINHAND));
            if (this.getAdditionalModifiers() != null) {
                builder.putAll(this.getAdditionalModifiers());
            }
        }
        return super.getDefaultAttributeModifiers(slot);
    }

    protected abstract Multimap<Attribute, AttributeModifier> getAdditionalModifiers();

    @Override
    public List<Component> getItemDescription() {
        return null;
    }

    @Override
    public List<Component> getPostDescription() {
        return null;
    }
}
