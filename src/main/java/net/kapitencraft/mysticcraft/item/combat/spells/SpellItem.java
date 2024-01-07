package net.kapitencraft.mysticcraft.item.combat.spells;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.data.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class SpellItem extends SwordItem implements IModItem, ISpellItem {
    public static final TabGroup SPELL_GROUP = new TabGroup(TabRegister.TabTypes.SPELL, TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    public static final UUID ATTACK_SPEED_UUID = BASE_ATTACK_SPEED_UUID;
    public static final UUID ATTACK_DAMAGE_UUID = BASE_ATTACK_DAMAGE_UUID;


    public static final String SPELL_EXECUTION_DUR = "ExeSpellDur";
    public static final String SPELL_EXE = "ExeSpell";
    private final int intelligence;
    private final int ability_damage;

    @Override
    public TabGroup getGroup() {
        return SPELL_GROUP;
    }

    //Display settings
    public abstract List<Component> getItemDescription();
    public abstract List<Component> getPostDescription();


    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        if (this.getItemDescription() != null) {
            list.addAll(this.getItemDescription());
            list.add(Component.literal(""));
        }
        if (this.getPostDescription() != null) {
            list.addAll(this.getPostDescription());
        }
    }

    public SpellItem(Properties p_41383_, int damage, float speed, int intelligence, int ability_damage) {
        super(ModTiers.SPELL_TIER, damage, speed,  p_41383_.stacksTo(1));
        this.intelligence = intelligence;
        this.ability_damage = ability_damage;
    }

    //Creating The Attribute Modifiers for this to work
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            if (this.intelligence > 0) {
                builder.put(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscHelper.createCustomIndex(slot)], "Default Item Modifier", intelligence, AttributeModifier.Operation.ADDITION));
            }
            if (this.ability_damage > 0) {
                builder.put(ModAttributes.ABILITY_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscHelper.createCustomIndex(slot)], "Default Item Modifier", ability_damage, AttributeModifier.Operation.ADDITION));
            }
        }
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        return builder;
    }

    //The actual logic for the SpellItem

    public static final UUID MANA_COST_MOD = UUID.fromString("95c53029-8493-4e05-9e7b-a0c0530dae83");


    /* EXECUTING SPELL */
}