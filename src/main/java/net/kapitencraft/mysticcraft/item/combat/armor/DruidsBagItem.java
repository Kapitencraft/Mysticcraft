package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DruidsBagItem extends AbstractArmorItem {
    private static final ResourceLocation TEXTURE = makeCustomTextureLocation(MysticcraftMod.MOD_ID, "druids_bag");

    public DruidsBagItem(Properties properties) {
        super(ArmorMaterials.LEATHER, Type.CHESTPLATE, properties);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return TEXTURE;
    }
}
