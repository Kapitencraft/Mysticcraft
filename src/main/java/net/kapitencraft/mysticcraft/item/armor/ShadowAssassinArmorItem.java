package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.armor.client.ShadowAssassinArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ShadowAssassinArmorItem extends ModArmorItem implements GeoItem {
    public static String INVISIBLE_ID = "Invisible";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public ShadowAssassinArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.SHADOW_ASSASSIN, p_40387_, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public void clientFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        CompoundTag tag = living.getPersistentData();
        if (living.isCrouching()) {
            if (!tag.getBoolean("isCrouching") || !tag.contains("isCrouching")) {
                MysticcraftMod.sendInfo("crouching");
                tag.putBoolean(INVISIBLE_ID, !tag.getBoolean(INVISIBLE_ID));
                tag.putBoolean("isCrouching", true);
            }
        } else {
            tag.putBoolean("isCrouching", false);
        }
    }

    @Override
    protected void initFullSetTick(ItemStack stack, Level level, LivingEntity living) {
    }

    @Override
    protected void postFullSetTick(ItemStack stack, Level level, LivingEntity living) {
    }

    @Override
    protected void fullSetTick(ItemStack stack, Level level, LivingEntity living) {

    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ShadowAssassinArmorRenderer renderer;
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null) {
                    renderer = new ShadowAssassinArmorRenderer();
                }

                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                CompoundTag tag = livingEntity.getPersistentData();
                renderer.hide(tag.getBoolean(INVISIBLE_ID));
                return renderer;
            }
        });
    }
}