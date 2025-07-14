package net.kapitencraft.mysticcraft.mixin.classes;

import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.capability.reforging.Reforge;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
@SuppressWarnings("ALL")
public abstract class ItemMixin implements IForgeItem {

    @Shadow @Final private Rarity rarity;

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
        GemstoneHelper.getCapability(stack, iGemstoneHandler ->
                builder.merge(iGemstoneHandler.getAttributeModifiers(slot, stack))
        );
        CapabilityHelper.exeCapability(stack, CapabilityHelper.ELYTRA, data1 -> {
            ElytraData data = data1.getDataType();
            if (data == ElytraData.GRAVITY_BOOST && slot == EquipmentSlot.CHEST)
                builder.add(ForgeMod.ENTITY_GRAVITY.get(), AttributeHelper.createModifier("ElytraGravityBoost", AttributeModifier.Operation.MULTIPLY_TOTAL, data1.getLevel() * -0.2));
        });
        if (self() instanceof IStarAbleItem) {
            builder.update(value -> IStarAbleItem.modifyData(stack, value));
        }
        if (self() instanceof ITieredItem) {
            builder.mulAll(ITieredItem.getTier(stack).getValueMul() + 1);
        }
        return builder.build();
    }

    /**
     * @author Kapitencraft
     * @reason changes to Rarity
     */
    @Overwrite
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return MiscHelper.getFinalRarity(rarity, stack);
    }
}