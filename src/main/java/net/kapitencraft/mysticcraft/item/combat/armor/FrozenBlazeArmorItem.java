package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.client.armor.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.client.armor.provider.SimpleModelProvider;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.FrozenBlazeArmorModel;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.ArmorTabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FrozenBlazeArmorItem extends AbstractArmorItem {

    public FrozenBlazeArmorItem(ArmorItem.Type type) {
        super(ModArmorMaterials.FROZEN_BLAZE, type, new Item.Properties().rarity(ExtraRarities.LEGENDARY).fireResistant());
    }

    @Override
    public boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(FrozenBlazeArmorModel::createBodyLayer, FrozenBlazeArmorModel::new);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, "frozen_blaze_armor");
    }
}
