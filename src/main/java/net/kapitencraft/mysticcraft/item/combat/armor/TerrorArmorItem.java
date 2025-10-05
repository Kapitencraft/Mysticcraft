package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.item.creative_tab.ArmorTabGroup;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.item.combat.armor.client.NetherArmorItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class TerrorArmorItem extends NetherArmorItem {
    public static final ArmorTabGroup TAB = ArmorTabGroup.create();

    public TerrorArmorItem(Type type) {
        super(ModArmorMaterials.TERROR, type, NETHER_ARMOR_PROPERTIES);
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiersForSlot(ItemStack stack, ItemTier tier) {
        return multimap -> {
            multimap.put(ExtraAttributes.STRENGTH.get(), AttributeHelper.createModifierForSlot("Terror Armor", AttributeModifier.Operation.ADDITION,
                    3 * this.getMaterial().getDefenseForType(this.type) * tier.getValueMul() , getEquipmentSlot()));
            multimap.put(ExtraAttributes.CRIT_DAMAGE.get(), AttributeHelper.createModifierForSlot("Terror Armor", AttributeModifier.Operation.ADDITION,
                    this.getMaterial().getDefenseForType(this.type) * tier.getValueMul(), getEquipmentSlot()));
            multimap.put(Attributes.MAX_HEALTH, AttributeHelper.createModifierForSlot("Terror Armor", AttributeModifier.Operation.ADDITION,
                    this.getMaterial().getDefenseForType(this.type) * 0.4 * tier.getValueMul(), getEquipmentSlot()));
        };
    }

    @Override
    public List<ItemStack> getMatCost(ItemStack stack) {
        return null;
    }

    @Override
    protected boolean withCustomModel() {
        return false;
    }
}
