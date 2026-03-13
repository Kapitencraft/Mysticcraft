package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.attribute.BaseAttributeLocations;
import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.SimpleModelProvider;
import net.kapitencraft.kap_lib.mana.ManaAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.WizardHatModel;
import net.kapitencraft.mysticcraft.registry.ModArmorMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;

public class WizardHatItem extends AbstractArmorItem {

    public WizardHatItem() {
        super(ModArmorMaterials.WIZARD_HAT, Type.HELMET, MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return ItemAttributeModifiers.builder()
                .add(
                        ManaAttributes.MANA_COST,
                        new AttributeModifier(
                                ManaAttributes.BASE_MANA_COST_LOC, -.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        ExtraAttributes.MAGIC_DAMAGE,
                        new AttributeModifier(
                                BaseAttributeLocations.MAGIC_DAMAGE, .2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        Attributes.ARMOR,
                        new AttributeModifier(
                                ResourceLocation.withDefaultNamespace("armor.helmet"),
                                7,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).build();
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(WizardHatModel::createBodyLayer, WizardHatModel::new);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, "wizard_hat/wizard_hat_green");
    }
}
