package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.item.combat.armor.client.NetherArmorItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.ArmorTabGroup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class SoulMageArmorItem extends NetherArmorItem {
    public static final ArmorTabGroup TAB = ArmorTabGroup.create();

    @Override
    public boolean withCustomModel() {
        return false;
    }

    public SoulMageArmorItem(Type type) {
        super(ModArmorMaterials.SOUL_MAGE, type, new Properties().rarity(ExtraRarities.LEGENDARY));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        if (slot == this.getEquipmentSlot()) {
            builder.put(ExtraAttributes.MAX_MANA.get(), AttributeHelper.createModifier("SoulMageArmorMaxMana", AttributeModifier.Operation.ADDITION,345));
            builder.put(ExtraAttributes.MAGIC_DAMAGE.get(), AttributeHelper.createModifier("SoulMageArmorDamage", AttributeModifier.Operation.ADDITION, 3));
        }
        return builder;
    }

    @Override
    public List<ItemStack> getMatCost(ItemStack stack) {
        return null;
    }


    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiersForSlot(ItemStack stack, ItemTier tier) {
        return null;
    }
}
