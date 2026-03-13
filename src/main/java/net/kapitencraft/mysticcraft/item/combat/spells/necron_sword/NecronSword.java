package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.attribute.BaseAttributeLocations;
import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.core.util.ExtraRarities;
import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.kap_lib.mana.ManaAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class NecronSword extends SpellItem {
    public static final TabGroup NECRON_GROUP = TabGroup.create(ModCreativeModTabs.WEAPONS_AND_TOOLS);
    public static final int BASE_DAMAGE = 12;
    public static final int BASE_STRENGHT = 150;
    public static final int REFINED_BASE_DAMAGE = 13;
    public static final int BASE_FEROCITY = 30;
    public static final int BASE_INTEL = 50;

    protected NecronSword(Properties properties) {
        super(properties.rarity(ExtraRarities.LEGENDARY));
    }

    public NecronSword() {
        this(new Properties()
                .attributes(createNecronAttributes(NecronSword.BASE_DAMAGE, NecronSword.BASE_INTEL, NecronSword.BASE_FEROCITY, NecronSword.BASE_STRENGHT).build())
                .component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.COMBAT))
        );
    }

    protected static ItemAttributeModifiers.Builder createNecronAttributes(int damage, int maxMana, int ferocity, int strength) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, damage + ModTiers.SPELL_TIER.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                BASE_ATTACK_SPEED_ID, -2.4, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        ManaAttributes.MAX_MANA,
                        new AttributeModifier(
                                MysticcraftMod.res("tool_max_mana_modifier"), maxMana, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        ExtraAttributes.FEROCITY,
                        new AttributeModifier(
                                BaseAttributeLocations.FEROCITY, ferocity, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        ExtraAttributes.STRENGTH,
                        new AttributeModifier(
                                BaseAttributeLocations.STRENGTH, strength, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                );
    }


    //@Override
    //public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
    //    HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
    //    multimap.putAll(super.getDefaultAttributeModifiers(slot));
    //    if (slot == EquipmentSlot.MAINHAND) {
    //        multimap.put(ExtraAttributes.FEROCITY.get(), AttributeHelper.createModifier("Necron Modifier", AttributeModifier.Operation.ADDITION, this.FEROCITY));
    //        multimap.put(ExtraAttributes.STRENGTH.get(), AttributeHelper.createModifier("Necron Modifier", AttributeModifier.Operation.ADDITION, this.STRENGHT));
    //        this.getAdditionalModifiers().accept(multimap);
    //    }
    //    return multimap;
    //}

    protected @NotNull Consumer<Multimap<Attribute, AttributeModifier>> getAdditionalModifiers() {
        return multimap -> {};
    }
}
