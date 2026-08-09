package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.item.creative_tab.ArmorTabGroup;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModArmorMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

public class ShadowAssassinArmorItem extends AbstractArmorItem {
    public static final ArmorTabGroup TAB = ArmorTabGroup.create();

    public ShadowAssassinArmorItem(ArmorItem.Type type) {
        super(ModArmorMaterials.SHADOW_ASSASSIN, type, MiscHelper.rarity(Rarity.EPIC).durability(type.getDurability(17)));
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, "shadow_assassin_armor");
    }
}