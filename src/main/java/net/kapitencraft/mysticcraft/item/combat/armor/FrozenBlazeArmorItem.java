package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.kap_lib.core.util.ExtraRarities;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.SimpleModelProvider;
import net.kapitencraft.kap_lib.item.creative_tab.ArmorTabGroup;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.FrozenBlazeArmorModel;
import net.kapitencraft.mysticcraft.registry.ModArmorMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FrozenBlazeArmorItem extends AbstractArmorItem {
    public static final ArmorTabGroup TAB = ArmorTabGroup.create();

    public FrozenBlazeArmorItem(ArmorItem.Type type) {
        super(ModArmorMaterials.FROZEN_BLAZE, type, MiscHelper.rarity(ExtraRarities.LEGENDARY).fireResistant().durability(type.getDurability(8)));
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(FrozenBlazeArmorModel::createBodyLayer, FrozenBlazeArmorModel::new);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, "frozen_blaze_armor");
    }
}
