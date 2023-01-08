package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ManaSteelArmor extends ModArmorItem{
    public ManaSteelArmor(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.MANA_STEEL, p_40387_, new Properties().rarity(FormattingCodes.MYTHIC));
    }

    @Override
    public void armorTick(ItemStack stack, Level level, LivingEntity living) {
        if (this.isFullSetActive(living)) {
            living.getAttribute(ModAttributes.MAX_MANA.get()).addTransientModifier(new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(slot)], "Full Set Bonus", 30, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(this.slot)], "Intelligence", 300, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    protected static ManaSteelArmor create(EquipmentSlot slot) {
        return new ManaSteelArmor(slot);
    }
}
