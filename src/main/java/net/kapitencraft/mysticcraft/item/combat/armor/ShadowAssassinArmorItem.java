package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.item.combat.armor.ModArmorItem;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.SimpleModelProvider;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.ShadowAssassinArmorModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ShadowAssassinArmorItem extends ModArmorItem {

    public ShadowAssassinArmorItem(ArmorItem.Type type) {
        super(ModArmorMaterials.SHADOW_ASSASSIN, type, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    protected void postFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        CompoundTag tag = living.getPersistentData();
        living.setInvisible(false);
        tag.putBoolean("Invisible", false);
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
    protected ArmorModelProvider getModelProvider() {
        return new SimpleModelProvider(ShadowAssassinArmorModel::createBodyLayer, ShadowAssassinArmorModel::new);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, entity.isInvisible() ? "shadow_assassin_empty" : "shadow_assassin_armor");
    }
}