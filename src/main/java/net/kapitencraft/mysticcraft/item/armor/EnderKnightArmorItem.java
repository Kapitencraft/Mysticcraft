package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EnderKnightArmorItem extends ModArmorItem implements IGemstoneApplicable {
    public EnderKnightArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.ENDER_KNIGHT, p_40387_, new Properties().rarity(FormattingCodes.LEGENDARY).fireResistant());
    }

    public static HashMap<EquipmentSlot, RegistryObject<Item>> createRegistry(DeferredRegister<Item> register, String registryName) {
        HashMap<EquipmentSlot, RegistryObject<Item>> registry = new HashMap<>();
        registry.put(EquipmentSlot.HEAD, register.register(registryName + "_helmet", ()-> new EnderKnightArmorItem(EquipmentSlot.HEAD)));
        registry.put(EquipmentSlot.CHEST, register.register(registryName + "_chestplate", ()-> new EnderKnightArmorItem(EquipmentSlot.CHEST)));
        registry.put(EquipmentSlot.LEGS, register.register(registryName + "_leggings", ()-> new EnderKnightArmorItem(EquipmentSlot.LEGS)));
        registry.put(EquipmentSlot.FEET, register.register(registryName + "_boots", ()-> new EnderKnightArmorItem(EquipmentSlot.FEET)));
        return registry;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap<Attribute, AttributeModifier> modifierMultimap = (ImmutableMultimap<Attribute, AttributeModifier>) super.getAttributeModifiers(slot, stack);
        if (Objects.equals(this.dimension, MiscUtils.getDimensionRegistries().get(Level.END))) {
            modifierMultimap = AttributeUtils.increaseByPercent(modifierMultimap, 1, new AttributeModifier.Operation[]{AttributeModifier.Operation.ADDITION, AttributeModifier.Operation.MULTIPLY_BASE, AttributeModifier.Operation.MULTIPLY_TOTAL}, null);
        }
        return modifierMultimap;
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

    protected static EnderKnightArmorItem create(EquipmentSlot slot) {
        return new EnderKnightArmorItem(slot);
    }

    public ImmutableMultimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> preReturn = new ImmutableMultimap.Builder<>();
        if (slot == this.slot) {
            preReturn.put(ModAttributes.CRIT_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "Modded Attributes", 27, AttributeModifier.Operation.ADDITION));
            preReturn.put(ModAttributes.STRENGTH.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "Modded Attributes", 58, AttributeModifier.Operation.ADDITION));
            preReturn.put(Attributes.MAX_HEALTH, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "Modded Modifier", 4, AttributeModifier.Operation.ADDITION));
        }
        return preReturn.build();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> list, @NotNull TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
        list.add(Component.literal(""));
        list.add(Component.literal("This Armor get`s double stats in the End"));
        list.add(Component.literal(""));
    }

    @Override
    public GemstoneHelper getHelper() {
        return new GemstoneHelper(getDefaultSlots());
    }

    @Override
    public GemstoneSlot[] getDefaultSlots() {
        return new GemstoneSlot[] {GemstoneSlot.DEFENCE, GemstoneSlot.OFFENSIVE, GemstoneSlot.COMBAT, GemstoneSlot.COMBAT, GemstoneSlot.STRENGHT};
    }
}