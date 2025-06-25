package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class NecronSword extends SpellItem {
    public static final TabGroup NECRON_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    public static final int BASE_DAMAGE = 12;
    public static final double BASE_STRENGHT = 150;
    public static final int REFINED_BASE_DAMAGE = 13;
    public static final double BASE_FEROCITY = 30;
    public static final int BASE_INTEL = 50;
    private final double FEROCITY;
    private final double STRENGHT;

    public NecronSword(int damage, int intelligence, double ferocity, double strenght) {
        super(new Properties().rarity(ExtraRarities.LEGENDARY), damage, -2.4f, intelligence, 0);
        this.FEROCITY = ferocity;
        this.STRENGHT = strenght;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        multimap.putAll(super.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.MAINHAND) {
            multimap.put(ExtraAttributes.FEROCITY.get(), AttributeHelper.createModifier("Necron Modifier", AttributeModifier.Operation.ADDITION, this.FEROCITY));
            multimap.put(ExtraAttributes.STRENGTH.get(), AttributeHelper.createModifier("Necron Modifier", AttributeModifier.Operation.ADDITION, this.STRENGHT));
            this.getAdditionalModifiers().accept(multimap);
        }
        return multimap;
    }

    protected @NotNull Consumer<Multimap<Attribute, AttributeModifier>> getAdditionalModifiers() {
        return multimap -> {};
    }

    @Override
    public List<Component> getItemDescription() {
        return null;
    }

    @Override
    public List<Component> getPostDescription() {
        return null;
    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.empty();
    }
}
