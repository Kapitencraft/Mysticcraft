package net.kapitencraft.mysticcraft.mixin.classes;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.ITieredItem;
import net.kapitencraft.mysticcraft.item.data.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.item.data.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.data.reforging.Reforge;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
@SuppressWarnings("ALL")
public abstract class ItemMixin implements IForgeItem {

    private Item self() {return (Item) (Object) this;}

    /**
     * @reason implementation of reforge, gemstone item dungeon star / item and stat boost enchantment modifications
     * @author Kapitencraft
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        AttributeHelper.AttributeBuilder builder = new AttributeHelper.AttributeBuilder(self().getDefaultAttributeModifiers(slot));
        Reforge reforge = Reforge.getFromStack(stack);
        if (slot == MiscHelper.getSlotForStack(stack) && reforge != null) {
            builder.merge(reforge.applyModifiers(self().getRarity(stack)), AttributeModifier.Operation.ADDITION);
        }
        if (self() instanceof IGemstoneApplicable applicable) {
            builder.merge(AttributeHelper.fromMap(applicable.getAttributeModifiers(stack, slot)));
        }
        if (self() instanceof IStarAbleItem) {
            builder.update(value -> IStarAbleItem.modifyData(stack, value));
        }
        builder.merge(StatBoostEnchantment.getAllModifiers(stack, slot));
        if (self() instanceof ITieredItem) {
            builder.mulAll(ITieredItem.getTier(stack).getValueMul() + 1);
        }
        return builder.build();
    }
}