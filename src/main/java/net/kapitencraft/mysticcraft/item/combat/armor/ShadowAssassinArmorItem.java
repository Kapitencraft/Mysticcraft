package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.ShadowAssassinArmorModel;
import net.kapitencraft.mysticcraft.item.combat.armor.client.renderer.ArmorRenderer;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ShadowAssassinArmorItem extends ModArmorItem {
    public static final TabGroup SA_ARMOR_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);

    @Override
    public TabGroup getGroup() {
        return SA_ARMOR_GROUP;
    }

    private final DustParticleOptions SHADOW_OPTIONS = new DustParticleOptions(Vec3.fromRGB24(0).toVector3f(), 2);
    public ShadowAssassinArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.SHADOW_ASSASSIN, p_40387_, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    protected void postFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        CompoundTag tag = living.getPersistentData();
        living.setInvisible(false);
        tag.putBoolean("Invisible", false);
    }

    @Override
    protected void fullSetTick(ItemStack stack, Level level, LivingEntity living) {
        CompoundTag tag = living.getPersistentData();
        if (living.isCrouching()) {
            if (!tag.getBoolean("isCrouching") || !tag.contains("isCrouching")) {
                living.setInvisible(!living.isInvisible());
                tag.putBoolean("isCrouching", true);
                tag.putBoolean("Invisible", living.isInvisible());
                if (living.isInvisible()) {
                    ParticleHelper.sendParticles(SHADOW_OPTIONS, false, living, 100 , 1.5, 1.5, 1.5, 0.01);
                }
            }
        } else {
            tag.putBoolean("isCrouching", false);
        }
        if (tag.getBoolean("Invisible")) {
            living.setInvisible(true);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        return null;
    }

    @Override
    boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorRenderer<?> getRenderer(LivingEntity living, ItemStack stack, EquipmentSlot slot) {
        return new ArmorRenderer<>(ShadowAssassinArmorModel::createBodyLayer, ShadowAssassinArmorModel::new);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return makeCustomTextureLocation(entity.getPersistentData().getBoolean("Invisible") ? "shadow_assassin_empty" : "shadow_assassin_armor");
    }
}