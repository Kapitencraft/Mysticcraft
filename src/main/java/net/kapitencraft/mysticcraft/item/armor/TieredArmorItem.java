package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.misc.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public abstract class TieredArmorItem extends ModArmorItem {

    public TieredArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_) {
        super(p_40386_, p_40387_, p_40388_);
    }


    @Override
    public void verifyTagAfterLoad(@NotNull CompoundTag tag) {
        super.verifyTagAfterLoad(tag);
    }


    public static boolean hasFullSetActive(ArmorTier armorTier, ArmorMaterial material, LivingEntity living) {
        for (EquipmentSlot slot : MiscUtils.ARMOR_EQUIPMENT) {
            ItemStack itemStack = living.getItemBySlot(slot);
            ArmorTier armorTier1 = getTier(itemStack);
            if (armorTier != armorTier1) {
                return false;
            }
        }
        if (living instanceof Player player) {
            MiscUtils.awardAchievement(player, "mysticcraft:infernal_armor");
        }
        return ModArmorItem.isFullSetActive(living, material);
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
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.putAll(super.getAttributeModifiers(slot, stack));
        ArmorTier armorTier = getTier(stack);
        assert armorTier != null;
        return AttributeUtils.increaseByPercent(builder.build(), armorTier.valueMul, AttributeModifier.Operation.values(), null);
    }

    public abstract List<ArmorTier> getAvailableTiers();

    public enum ArmorTier {
        DEFAULT("armor_tier.default", 0, 0),
        HOT("armor_tier.hot", 0.260689, 1),
        BURNING("armor_tier.burning", 0.586206, 2),
        FIERY("armor_tier.fiery", 1, 3),
        INFERNAL("armor_tier.infernal", 1.521379, 4);


        final String name;
        final double valueMul;
        final int number;

        ArmorTier(String name, double valueMul, int number) {
            this.name = name;
            this.valueMul = valueMul;
            this.number = number;
        }

        public MutableComponent getName() {
            return Component.translatable(this.name);
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