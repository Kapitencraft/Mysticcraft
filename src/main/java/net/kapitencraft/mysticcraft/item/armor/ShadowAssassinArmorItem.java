package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.item.armor.client.ShadowAssassinArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.function.Consumer;

public class ShadowAssassinArmorItem extends ModArmorItem implements GeoItem {
    private boolean isHidden;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public ShadowAssassinArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.SHADOW_ASSASSIN, p_40387_, new Properties().rarity(Rarity.EPIC));
        this.isHidden = false;
    }

    public static HashMap<EquipmentSlot, RegistryObject<Item>> createRegistry(DeferredRegister<Item> register, String registryName) {
        HashMap<EquipmentSlot, RegistryObject<Item>> registry = new HashMap<>();
        registry.put(EquipmentSlot.HEAD, register.register(registryName + "_helmet", ()-> new ShadowAssassinArmorItem(EquipmentSlot.HEAD)));
        registry.put(EquipmentSlot.CHEST, register.register(registryName + "_chestplate", ()-> new ShadowAssassinArmorItem(EquipmentSlot.CHEST)));
        registry.put(EquipmentSlot.LEGS, register.register(registryName + "_leggings", ()-> new ShadowAssassinArmorItem(EquipmentSlot.LEGS)));
        registry.put(EquipmentSlot.FEET, register.register(registryName + "_boots", ()-> new ShadowAssassinArmorItem(EquipmentSlot.FEET)));
        return registry;
    }

    @Override
    public void fullSetTick(ItemStack stack, Level level, LivingEntity living) {
        if (living.isCrouching() && !living.getPersistentData().getBoolean("isCrouching")) {
            this.isHidden = !isHidden;
            living.getPersistentData().putBoolean("isCrouching", true);
        } else {
            living.getPersistentData().putBoolean("isCrouching", false);
        }
    }

    @Override
    protected void initFullSetTick(ItemStack stack, Level level, LivingEntity living) {
    }

    @Override
    protected void postFullSetTick(ItemStack stack, Level level, LivingEntity living) {
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
                renderer.hide(isHidden);
                return renderer;
            }
        });
    }
}