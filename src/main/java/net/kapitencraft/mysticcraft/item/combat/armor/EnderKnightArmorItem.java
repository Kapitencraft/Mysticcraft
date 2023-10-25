package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class EnderKnightArmorItem extends ModArmorItem implements IGemstoneApplicable {
    public static final TabGroup ENDER_KNIGHT_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);

    @Override
    public TabGroup getGroup() {
        return ENDER_KNIGHT_GROUP;
    }

    public EnderKnightArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.ENDER_KNIGHT, p_40387_, new Properties().rarity(FormattingCodes.LEGENDARY).fireResistant());
    }

    @Override
    boolean withCustomModel() {
        return false;
    }

    public static EnderKnightArmorItem create(EquipmentSlot slot) {
        return new EnderKnightArmorItem(slot);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifierMultimap = super.getAttributeModifiers(slot, stack);
        if (Objects.equals(this.dimension, MiscHelper.getDimensionRegistries().get(Level.END))) {
            modifierMultimap = AttributeHelper.increaseByPercent(modifierMultimap, 1, new AttributeModifier.Operation[]{AttributeModifier.Operation.ADDITION, AttributeModifier.Operation.MULTIPLY_BASE, AttributeModifier.Operation.MULTIPLY_TOTAL}, null);
        }
        return modifierMultimap;
    }

    public HashMultimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> preReturn = HashMultimap.create();
        if (slot == this.slot) {
            preReturn.put(ModAttributes.CRIT_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscHelper.createCustomIndex(slot)], "Modded Attributes", 27, AttributeModifier.Operation.ADDITION));
            preReturn.put(ModAttributes.STRENGTH.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscHelper.createCustomIndex(slot)], "Modded Attributes", 58, AttributeModifier.Operation.ADDITION));
            preReturn.put(Attributes.MAX_HEALTH, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscHelper.createCustomIndex(slot)], "Modded Modifier", 4, AttributeModifier.Operation.ADDITION));
        }
        return preReturn;
    }


    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        super.appendHoverTextWithPlayer(p_41421_, p_41422_, list, flag, player);
        list.add(Component.literal(""));
        list.add(Component.literal("This Armor get`s double stats in the End"));
        list.add(Component.literal(""));
    }

    @Override
    public GemstoneSlot[] getDefaultSlots() {
        return new GemstoneSlot.Builder(GemstoneSlot.Type.DEFENCE, GemstoneSlot.Type.OFFENCE, GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.STRENGTH).build();
    }
}