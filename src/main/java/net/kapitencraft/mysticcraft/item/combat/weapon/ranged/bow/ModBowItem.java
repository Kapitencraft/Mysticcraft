package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow;

import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.attribute.BaseAttributeLocations;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public abstract class ModBowItem extends BowItem {
    public static final TabGroup BOW_GROUP = TabGroup.create(ModCreativeModTabs.WEAPONS_AND_TOOLS);

    public abstract double getDivider();

    public abstract int getKB();

    public ModBowItem(Item.Properties p_40660_) {
        super(p_40660_);
    }

    protected static ItemAttributeModifiers createAttributes(int rangedDamage) {
        return ItemAttributeModifiers.builder()
                .add(
                        ExtraAttributes.RANGED_DAMAGE,
                        new AttributeModifier(
                                BaseAttributeLocations.RANGED_DAMAGE, rangedDamage, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).build();
    }

    private static boolean isInfinite(Player player, ItemStack itemStack, ItemStack bow) {
        boolean flag1 = player.getAbilities().instabuild || (itemStack.getItem() instanceof ArrowItem && ((ArrowItem)itemStack.getItem()).isInfinite(itemStack, bow, player));
        return flag1 || player.getAbilities().instabuild && (itemStack.is(Items.SPECTRAL_ARROW) || itemStack.is(Items.TIPPED_ARROW));
    }

    protected double getSpeedMul(AbstractArrow arrow, double mul1) {
        return arrow.getDeltaMovement().x * mul1;
    }
}