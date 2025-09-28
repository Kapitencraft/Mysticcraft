package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.client.armor.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.client.armor.provider.SimpleModelProvider;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.WizardHatModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WizardHatItem extends AbstractArmorItem {

    public WizardHatItem() {
        super(ModArmorMaterials.WIZARD_HAT, Type.HELMET, new Item.Properties().rarity(Rarity.RARE));
    }

    private static final String ModifierName = "Modded Attribute Modifier";
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        if (slot == EquipmentSlot.HEAD) {
            builder.put(ExtraAttributes.MANA_COST.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_MUL_FOR_SLOT[0], ModifierName, -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL));
            builder.put(ExtraAttributes.MAGIC_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_MUL_FOR_SLOT[0], ModifierName, 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        return builder;
    }

    @Override
    public boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(WizardHatModel::createBodyLayer, WizardHatModel::new);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return AbstractArmorItem.makeCustomTextureLocation(MysticcraftMod.MOD_ID, "wizard_hat/wizard_hat_green");
    }
}
