package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.item.combat.armor.ModArmorItem;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderKnightArmorItem extends ModArmorItem {

    public EnderKnightArmorItem(ArmorItem.Type type) {
        super(ModArmorMaterials.ENDER_KNIGHT, type, new Properties().rarity(ExtraRarities.LEGENDARY).fireResistant());
    }

    @Override
    public boolean withCustomModel() {
        return false;
    }

    public static EnderKnightArmorItem create(ArmorItem.Type slot) {
        return new EnderKnightArmorItem(slot);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifierMultimap = super.getAttributeModifiers(slot, stack);
        //if (this.dimension == Level.END) {
        //    modifierMultimap = AttributeHelper.increaseByPercent(modifierMultimap, 1, new AttributeModifier.Operation[]{AttributeModifier.Operation.ADDITION, AttributeModifier.Operation.MULTIPLY_BASE, AttributeModifier.Operation.MULTIPLY_TOTAL}, null);
        //}
        return modifierMultimap;
    }

    public HashMultimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> preReturn = HashMultimap.create();
        if (slot == this.getEquipmentSlot()) {
            preReturn.put(ExtraAttributes.CRIT_DAMAGE.get(), AttributeHelper.createModifier("EnderKnightCritDamage", AttributeModifier.Operation.ADDITION, 27));
            preReturn.put(ExtraAttributes.STRENGTH.get(), AttributeHelper.createModifier("EnderKnightStrenght", AttributeModifier.Operation.ADDITION, 58));
            preReturn.put(Attributes.MAX_HEALTH, AttributeHelper.createModifier("EnderKnightMaxHealth", AttributeModifier.Operation.ADDITION, 4));
        }
        return preReturn;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.literal("This Armor get`s double stats in the End"));
        pTooltipComponents.add(Component.literal(""));
    }
}