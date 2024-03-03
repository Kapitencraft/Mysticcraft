package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.combat.armor.client.NetherArmorItem;
import net.kapitencraft.mysticcraft.item.item_bonus.*;
import net.kapitencraft.mysticcraft.item.item_bonus.fullset.TerrorArmorFullSetBonus;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.ArmorTabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class TerrorArmorItem extends NetherArmorItem implements IArmorBonusItem {

    public static final ArmorTabGroup TERROR_ARMOR_GROUP = new ArmorTabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);


    private static final FullSetBonus FULL_SET_BONUS = new TerrorArmorFullSetBonus();


    public TerrorArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.TERROR, p_40387_, NETHER_ARMOR_PROPERTIES);
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiersForSlot(ItemStack stack, ItemTier tier) {
        return multimap -> {
            multimap.put(ModAttributes.STRENGTH.get(), AttributeHelper.createModifierForSlot("Terror Armor", AttributeModifier.Operation.ADDITION,
                    3 * this.getMaterial().getDefenseForSlot(this.getSlot()) * tier.getValueMul() , slot));
            multimap.put(ModAttributes.CRIT_DAMAGE.get(), AttributeHelper.createModifierForSlot("Terror Armor", AttributeModifier.Operation.ADDITION,
                    this.getMaterial().getDefenseForSlot(this.getSlot()) * tier.getValueMul(), slot));
            multimap.put(Attributes.MAX_HEALTH, AttributeHelper.createModifierForSlot("Terror Armor", AttributeModifier.Operation.ADDITION,
                    this.getMaterial().getDefenseForSlot(this.getSlot()) * 0.4 * tier.getValueMul(), slot));
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

    @Override
    public List<MultiPieceBonus> getPieceBonni() {
        return List.of(FULL_SET_BONUS);
    }

    @Override
    public PieceBonus getPieceBonusForSlot(EquipmentSlot slot) {
        return null;
    }

    @Override
    public @Nullable ExtraBonus getExtraBonus(EquipmentSlot slot) {
        return null;
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }

}
