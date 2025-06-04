package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.client.armor.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.client.armor.provider.SimpleModelProvider;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.WizardCloakModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

public class WizardCloakArmorItem extends AbstractArmorItem {

    //TODO add crafting recipe

    public WizardCloakArmorItem(ArmorItem.Type type) {
        super(ModArmorMaterials.WIZARD_CLOAK, type, MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    protected boolean withCustomModel() {
        return true;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, "wizard_cloak");
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(WizardCloakModel::createBodyLayer, WizardCloakModel::new);
    }
}
