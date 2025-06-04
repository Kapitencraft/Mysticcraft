package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.client.armor.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.client.armor.provider.SimpleModelProvider;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.ShadowAssassinArmorModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

public class ShadowAssassinArmorItem extends AbstractArmorItem {

    public ShadowAssassinArmorItem(ArmorItem.Type type) {
        super(ModArmorMaterials.SHADOW_ASSASSIN, type, new Properties().rarity(Rarity.EPIC));
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        return null;
    }

    @Override
    public boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(ShadowAssassinArmorModel::createBodyLayer, ShadowAssassinArmorModel::new);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, entity.isInvisible() ? "shadow_assassin_empty" : "shadow_assassin_armor");
    }
}