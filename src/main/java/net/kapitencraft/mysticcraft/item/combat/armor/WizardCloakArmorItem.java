package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.WizardCloakModel;
import net.kapitencraft.mysticcraft.item.combat.armor.client.renderer.ArmorRenderer;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.ArmorTabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

public class WizardCloakArmorItem extends ModArmorItem {

    //TODO add crafting recipe
    public static final ArmorTabGroup WIZARD_CLOAK_GROUP = new ArmorTabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);

    public WizardCloakArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.WIZARD_CLOAK, p_40387_, MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    protected boolean withCustomModel() {
        return true;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return makeCustomTextureLocation("wizard_cloak");
    }

    @Override
    protected ArmorRenderer<?> getRenderer(LivingEntity living, ItemStack stack, EquipmentSlot slot) {
        return new ArmorRenderer<>(WizardCloakModel::createBodyLayer, WizardCloakModel::new);
    }

    @Override
    public TabGroup getGroup() {
        return WIZARD_CLOAK_GROUP;
    }
}
