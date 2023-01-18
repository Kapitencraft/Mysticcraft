package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ModArmorItem extends ArmorItem {
    protected String dimension;
    private Entity user;

    public ModArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_) {
        super(p_40386_, p_40387_, p_40388_);
    }
    public boolean equals(Item item) {
        return item instanceof ArmorItem armorItem && armorItem.getMaterial() == this.getMaterial();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, @NotNull List<Component> toolTip, @NotNull TooltipFlag p_41424_) {
        if (stack.getItem() instanceof IGemstoneApplicable gemstoneApplicable) {
            gemstoneApplicable.getDisplay(stack, toolTip);
            toolTip.add(Component.literal(""));
        }
        if (stack.getItem() instanceof IArmorBonusItem bonusItem) {
            bonusItem.addDisplay(toolTip, this.getSlot());
            toolTip.add(Component.literal(""));
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        if (!level.isClientSide() && entity instanceof LivingEntity living ) {
            if (this.isFullSetActive(living)) {
                this.armorTick(stack, level, living);
            }
        }
        if (stack.getItem() == this) {
            updateDimension(entity.level);
            updateUser(entity);
        }
    }
    public boolean isFullSetActive(LivingEntity living) {
        ArmorItem head = living.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem armorItem ? armorItem : null;
        ArmorItem chest;
        Item chestPlate = living.getItemBySlot(EquipmentSlot.CHEST).getItem();
        if (chestPlate instanceof ElytraItem || chestPlate instanceof AirItem) {
            return false;
        } else {
            chest = (ArmorItem) living.getItemBySlot(EquipmentSlot.CHEST).getItem();
        }
        ArmorItem legs = living.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem armorItem ? armorItem : null;
        ArmorItem feet = living.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem armorItem ? armorItem : null;

        return (head != null && legs != null && feet != null) && (this.equals(head) && this.equals(chest) && this.equals(legs) && this.equals(feet));
    }

    public abstract void armorTick(ItemStack stack, Level level, LivingEntity living);

    public abstract Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot);

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        if (this.getAttributeMods(slot) != null) {
            builder.putAll(this.getAttributeMods(slot));
        }
        return builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        if (slot == this.getSlot()) {
            if (this instanceof IArmorBonusItem bonusItem && this.user instanceof LivingEntity living) {
                PieceBonus bonus = bonusItem.getPieceBonusForSlot(this.getSlot());
                if (bonus != null && bonus.getModifiers(living) != null && bonus.getModifiers(living) != null) {
                    builder.putAll(bonus.getModifiers(living));
                }
                FullSetBonus bonus1 = bonusItem.getFullSetBonus();
                if (this.getSlot() == EquipmentSlot.CHEST && this.isFullSetActive(living) && bonus1 != null && bonus1.getModifiers(living) != null) {
                    builder.putAll(bonus1.getModifiers(living));
                }
            }
        }
        return builder.build();
    }

    protected void updateDimension(Level level) {
        this.dimension = MISCTools.getDimension(level);
    }

    private void updateUser(Entity user) {
        this.user = user;
    }
}