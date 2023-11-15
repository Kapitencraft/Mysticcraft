package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.data.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ModSwordItem extends SwordItem implements IModItem {
    public static final float DEFAULT_ATTACK_SPEED = -2.4f;
    public static final TabGroup SWORD_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    public ModSwordItem(Tier p_43269_, int attackDamage, float attackSpeed, Properties p_43272_) {
        super(p_43269_, attackDamage, attackSpeed, p_43272_);
    }

    public abstract double getStrenght();
    public abstract double getCritDamage();

    @Override
    public TabGroup getGroup() {
        return SWORD_GROUP;
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return MiscHelper.getFinalRarity(super::getRarity, stack);
    }


    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        if (itemStack.getItem() instanceof IGemstoneApplicable applicable) {
            applicable.appendDisplay(itemStack, list);
            list.add(Component.literal(""));
        }
    }

    @Override
    public @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
        return super.getSweepHitBox(stack, player, target).inflate((player.getAttributeValue(ForgeMod.ATTACK_RANGE.get()) - 3) * 1/3);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(ModAttributes.STRENGTH.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[5], "Default Attribute modification", this.getStrenght(), AttributeModifier.Operation.ADDITION));
            builder.put(ModAttributes.CRIT_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[5], "Default Attribute modification", this.getCritDamage(), AttributeModifier.Operation.ADDITION));
        }
        return builder;
    }
}