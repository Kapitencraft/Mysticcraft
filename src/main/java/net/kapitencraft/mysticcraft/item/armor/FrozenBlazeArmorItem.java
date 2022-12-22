package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.FrozenDamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class FrozenBlazeArmorItem extends ModArmorItem{
    private static final String registryName = "frozen_blaze_";
    public FrozenBlazeArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.FROZEN_BLAZE, p_40387_, new Item.Properties().rarity(FormattingCodes.LEGENDARY).fireResistant());
    }

    @Override
    public void armorTick(ItemStack stack, Level level, LivingEntity living) {
        List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(5), new Predicate<LivingEntity>() {
            @Override
            public boolean test(LivingEntity living) {
                return true;
            }
        });
        for (LivingEntity livingEntity : entityList) {
            if (livingEntity != living) {
                livingEntity.hurt(new FrozenDamageSource(living), 2);
            }
        }
    }

    public static HashMap<EquipmentSlot, RegistryObject<Item>> createRegistry(DeferredRegister<Item> register) {
        HashMap<EquipmentSlot, RegistryObject<Item>> registry = new HashMap<>();
        registry.put(EquipmentSlot.HEAD, register.register(registryName + "helmet", ()-> new FrozenBlazeArmorItem(EquipmentSlot.HEAD)));
        registry.put(EquipmentSlot.CHEST, register.register(registryName + "chestplate", ()-> new FrozenBlazeArmorItem(EquipmentSlot.CHEST)));
        registry.put(EquipmentSlot.LEGS, register.register(registryName + "leggings", ()-> new FrozenBlazeArmorItem(EquipmentSlot.LEGS)));
        registry.put(EquipmentSlot.FEET, register.register(registryName + "boots", ()-> new FrozenBlazeArmorItem(EquipmentSlot.FEET)));
        return registry;
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        return null;
    }
}