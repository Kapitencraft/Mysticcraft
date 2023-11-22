package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.data.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.data.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public abstract class NecronSword extends SpellItem implements IGemstoneApplicable {
    public static final TabGroup NECRON_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    protected static final int BASE_DAMAGE = 12;
    protected static final double BASE_STRENGHT = 150;
    protected static final int REFINED_BASE_DAMAGE = 13;
    protected static final double BASE_FEROCITY = 30;
    protected static final int BASE_INTEL = 50;
    private final double FEROCITY;
    private final double STRENGHT;

    @Override
    public GemstoneSlot[] getDefaultSlots() {
        GemstoneSlot.Type type = (this instanceof Valkyrie ? GemstoneSlot.Type.STRENGTH : this instanceof Hyperion ? GemstoneSlot.Type.INTELLIGENCE : this instanceof Scylla ? GemstoneSlot.Type.COMBAT : this instanceof Astraea ? GemstoneSlot.Type.DEFENCE : null);
        return type == null ? new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT).build() : new GemstoneSlot.Builder(GemstoneSlot.Type.COMBAT, type).build();
    }

    public NecronSword(int damage, int intelligence, double ferocity, double strenght) {
        super(new Properties().rarity(FormattingCodes.LEGENDARY), damage, -2.4f, intelligence, 0);
        this.FEROCITY = ferocity;
        this.STRENGHT = strenght;
    }

    @Override
    public int getSlotAmount() {
        return 1;
    }

    @Override
    public void generateSlots(SpellHelper stack) {
    }


    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        multimap.putAll(super.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.MAINHAND) {
            multimap.put(ModAttributes.FEROCITY.get(), AttributeHelper.createModifier("Necron Modifier", AttributeModifier.Operation.ADDITION, this.FEROCITY));
            multimap.put(ModAttributes.STRENGTH.get(), AttributeHelper.createModifier("Necron Modifier", AttributeModifier.Operation.ADDITION, this.STRENGHT));
            this.getAdditionalModifiers().accept(multimap);
        }
        return multimap;
    }

    protected abstract @NotNull Consumer<Multimap<Attribute, AttributeModifier>> getAdditionalModifiers();

    @Override
    public List<Component> getItemDescription() {
        return null;
    }

    @Override
    public List<Component> getPostDescription() {
        return null;
    }

    @Override
    public TabGroup getGroup() {
        return NECRON_GROUP;
    }
}
