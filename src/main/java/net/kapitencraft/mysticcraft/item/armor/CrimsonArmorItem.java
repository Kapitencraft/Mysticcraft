package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
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

public class CrimsonArmorItem extends ModArmorItem {
    public CrimsonArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.CRIMSON, p_40387_, new Properties().rarity(FormattingCodes.LEGENDARY));
    }

    @Override
    public void fullSetTick(ItemStack stack, Level level, LivingEntity living) {
    }

    @Override
    protected void initFullSetTick(ItemStack stack, Level level, LivingEntity living) {
    }

    @Override
    protected void postFullSetTick(ItemStack stack, Level level, LivingEntity living) {
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(ModAttributes.STRENGTH.get(), MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, 5*this.getMaterial().getDefenseForSlot(this.getSlot()), this.getSlot()));
        builder.put(ModAttributes.CRIT_DAMAGE.get(), MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, this.getMaterial().getDefenseForSlot(this.getSlot()), this.getSlot()));
        return builder.build();
    }

    public static HashMap<EquipmentSlot, RegistryObject<Item>> createRegistry(DeferredRegister<Item> register, String registryName) {
        HashMap<EquipmentSlot, RegistryObject<Item>> registry = new HashMap<>();
        registry.put(EquipmentSlot.HEAD, register.register(registryName + "_helmet", ()-> new CrimsonArmorItem(EquipmentSlot.HEAD)));
        registry.put(EquipmentSlot.CHEST, register.register(registryName + "_chestplate", ()-> new CrimsonArmorItem(EquipmentSlot.CHEST)));
        registry.put(EquipmentSlot.LEGS, register.register(registryName + "_leggings", ()-> new CrimsonArmorItem(EquipmentSlot.LEGS)));
        registry.put(EquipmentSlot.FEET, register.register(registryName + "_boots", ()-> new CrimsonArmorItem(EquipmentSlot.FEET)));
        return registry;
    }

}
