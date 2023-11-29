package net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger;

import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public abstract class ModDaggerItem extends ModSwordItem {
    public ModDaggerItem(Tier p_43269_, int attackDamage, Properties p_43272_) {
        super(p_43269_, attackDamage, -1.8f, p_43272_);
    }


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity attacked, LivingEntity attacker) {
        ItemStack offhand = attacker.getOffhandItem();
        if (stack == attacker.getMainHandItem() && offhand.getItem() instanceof ModDaggerItem && attacker instanceof Player player) {
            attacker.getAttributes().removeAttributeModifiers(stack.getAttributeModifiers(EquipmentSlot.MAINHAND));
            attacker.getAttributes().addTransientAttributeModifiers(offhand.getAttributeModifiers(EquipmentSlot.MAINHAND));
            player.attack(attacked);
            attacker.getAttributes().removeAttributeModifiers(offhand.getAttributeModifiers(EquipmentSlot.MAINHAND));
            attacker.getAttributes().addTransientAttributeModifiers(stack.getAttributeModifiers(EquipmentSlot.MAINHAND));
        }

        return super.hurtEnemy(stack, attacked, attacker);
    }
}
