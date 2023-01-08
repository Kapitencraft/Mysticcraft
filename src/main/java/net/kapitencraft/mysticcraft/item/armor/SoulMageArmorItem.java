package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.item_bonus.ExtraBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.fullset.SoulMageArmorFullSetBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.piece.SoulMageChestplateBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.piece.SoulMageHelmetBonus;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class SoulMageArmorItem extends ModArmorItem implements IArmorBonusItem {

    private static final PieceBonus HELMET_BONUS = new SoulMageHelmetBonus();
    private static final PieceBonus CHEST_BONUS = new SoulMageChestplateBonus();
    private static final FullSetBonus SET_BONUS = new SoulMageArmorFullSetBonus();
    public SoulMageArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.SOUL_MAGE, p_40387_, new Properties().rarity(FormattingCodes.MYTHIC));
    }

    public static HashMap<EquipmentSlot, RegistryObject<Item>> createRegistry(DeferredRegister<Item> register, String registryName) {
        HashMap<EquipmentSlot, RegistryObject<Item>> registry = new HashMap<>();
        registry.put(EquipmentSlot.HEAD, register.register(registryName + "_helmet", ()-> new SoulMageArmorItem(EquipmentSlot.HEAD)));
        registry.put(EquipmentSlot.CHEST, register.register(registryName + "_chestplate", ()-> new SoulMageArmorItem(EquipmentSlot.CHEST)));
        registry.put(EquipmentSlot.LEGS, register.register(registryName + "_leggings", ()-> new SoulMageArmorItem(EquipmentSlot.LEGS)));
        registry.put(EquipmentSlot.FEET, register.register(registryName + "_boots", ()-> new SoulMageArmorItem(EquipmentSlot.FEET)));
        return registry;
    }

    protected static SoulMageArmorItem create(EquipmentSlot slot) {
        return new SoulMageArmorItem(slot);
    }

    @Override
    public void armorTick(ItemStack stack, Level level, LivingEntity living) {

    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == this.getSlot()) {
            builder.put(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(slot)], "Intelligence", 345, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    @Override
    public FullSetBonus getFullSetBonus() {
        return SET_BONUS;
    }

    @Override
    public PieceBonus getPieceBonusForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HELMET_BONUS;
            case CHEST -> CHEST_BONUS;
            default -> null;
        };
    }

    @Nullable
    @Override
    public ExtraBonus getExtraBonus(EquipmentSlot slot) {
        return null;
    }
}
