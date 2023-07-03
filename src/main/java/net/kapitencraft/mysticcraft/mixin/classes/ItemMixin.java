package net.kapitencraft.mysticcraft.mixin.classes;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.reforging.Reforge;
import net.kapitencraft.mysticcraft.misc.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.mixin.IItemMixin;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ItemMixin implements IItemMixin {

    private Item self() {return (Item) (Object) this;}

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        AttributeUtils.AttributeBuilder builder = new AttributeUtils.AttributeBuilder(self().getDefaultAttributeModifiers(slot));
        Reforge reforge = Reforge.getFromStack(stack);
        if (((stack.getItem() instanceof TieredItem && slot == EquipmentSlot.MAINHAND) || stack.getItem() instanceof ArmorItem armorItem && slot == armorItem.getSlot()) && reforge != null) {
            builder.merge(reforge.applyModifiers(self().getRarity(stack)), AttributeModifier.Operation.ADDITION);
        }
        if (self() instanceof IGemstoneApplicable applicable) {
            builder.merge(AttributeUtils.fromMap(applicable.getAttributeModifiers(stack)));
        }
        return builder.build();
    }
}