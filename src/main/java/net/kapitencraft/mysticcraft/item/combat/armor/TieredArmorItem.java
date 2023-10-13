package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public abstract class TieredArmorItem extends ModArmorItem {
    protected static final Properties NETHER_ARMOR_PROPERTIES = new Properties().rarity(FormattingCodes.LEGENDARY);

    public TieredArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_) {
        super(p_40386_, p_40387_, p_40388_);
    }

    public static boolean hasFullSetActive(ArmorTier armorTier, ArmorMaterial material, LivingEntity living) {
        for (EquipmentSlot slot : MiscHelper.ARMOR_EQUIPMENT) {
            ItemStack itemStack = living.getItemBySlot(slot);
            ArmorTier armorTier1 = getTier(itemStack);
            if (armorTier != armorTier1) {
                return false;
            }
        }
        return ModArmorItem.isFullSetActive(living, material);
    }


    @Override
    public int getMaxDamage(ItemStack stack) {
        return (int) (this.getMaxDamage() * (1 + getTier(stack).valueMul * 2));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        ArmorTier armorTier = getTier(stack);
        if (armorTier != null) {
            return armorTier == ArmorTier.DEFAULT ? super.getName(stack) : armorTier.getName().append(" ").append(super.getName(stack));
        } else {
            stack.getOrCreateTag().putString("tier", ArmorTier.DEFAULT.name);
            return super.getName(stack);
        }
    }

    public static ArmorTier getTier(ItemStack stack) {
        return ArmorTier.getByName(stack.getOrCreateTag().getString("tier"));
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        builder.putAll(super.getAttributeModifiers(slot, stack));
        ArmorTier armorTier = getTier(stack);
        return AttributeHelper.increaseByPercent(builder, armorTier == null ? 1 : armorTier.valueMul, AttributeModifier.Operation.values(), null);
    }

    public abstract List<ArmorTier> getAvailableTiers();

    public enum ArmorTier {
        DEFAULT("default", 0, 0),
        HOT("hot", 0.260689, 1),
        BURNING("burning", 0.586206, 2),
        FIERY("fiery", 1, 3),
        INFERNAL("infernal", 1.521379, 4);

        public static final List<ArmorTier> NETHER_ARMOR_TIERS = List.of(ArmorTier.HOT, ArmorTier.BURNING, ArmorTier.FIERY, ArmorTier.INFERNAL);


        final String name;
        final double valueMul;
        final int number;

        ArmorTier(String name, double valueMul, int number) {
            this.name = name;
            this.valueMul = valueMul;
            this.number = number;
        }

        public MutableComponent getName() {
            return Component.translatable("armor_tier." + this.name);
        }

        public static ArmorTier getByName(String name) {
            for (ArmorTier armorTier : values()) {
                if (Objects.equals(armorTier.name, name)) {
                    return armorTier;
                }
            }
            return null;
        }
    }
}
