package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.item.armor.client.ShadowAssassinArmorRenderer;
import net.kapitencraft.mysticcraft.item.armor.client.ShadowAssassinEmptyRenderer;
import net.kapitencraft.mysticcraft.misc.utils.ParticleUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ShadowAssassinArmorItem extends ModArmorItem implements GeoItem {
    private final DustParticleOptions SHADOW_OPTIONS = new DustParticleOptions(Vec3.fromRGB24(0).toVector3f(), 2);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
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
                    ParticleUtils.sendParticles(SHADOW_OPTIONS, false, living, 100 , 1.5, 1.5, 1.5, 0.01);
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
            private ShadowAssassinEmptyRenderer emptyRenderer;
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (livingEntity.isInvisible()) {
                    if (emptyRenderer == null) {
                        emptyRenderer = new ShadowAssassinEmptyRenderer();
                    }
                    emptyRenderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                    return emptyRenderer;
                }
                if (renderer == null) {
                    renderer = new ShadowAssassinArmorRenderer();
                }
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return renderer;
            }
        });
    }
}