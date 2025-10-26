package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.item.creative_tab.ArmorTabGroup;
import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.registry.ModArmorMaterials;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EnderKnightArmorItem extends AbstractArmorItem {
    public static final TabGroup TAB = ArmorTabGroup.create();

    public EnderKnightArmorItem(ArmorItem.Type type) {
        super(ModArmorMaterials.ENDER_KNIGHT, type, MiscHelper.rarity(ExtraRarities.LEGENDARY)
                .fireResistant()
                .durability(type.getDurability(25))
                .component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.DEFENCE, GemstoneSlot.Type.OFFENCE, GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.STRENGTH))
        );
    }

    @Override
    public boolean withCustomModel() {
        return false;
    }

    public static EnderKnightArmorItem create(ArmorItem.Type slot) {
        return new EnderKnightArmorItem(slot);
    }


    public HashMultimap<Holder<Attribute>, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        HashMultimap<Holder<Attribute>, AttributeModifier> preReturn = HashMultimap.create();
        if (slot == this.getEquipmentSlot()) {
            preReturn.put(ExtraAttributes.CRIT_DAMAGE, new AttributeModifier(MysticcraftMod.res("ender_knight_crit_damage"), 27, AttributeModifier.Operation.ADD_VALUE));
            preReturn.put(ExtraAttributes.STRENGTH, new AttributeModifier(MysticcraftMod.res("ender_knight_strength"), 58, AttributeModifier.Operation.ADD_VALUE));
            preReturn.put(Attributes.MAX_HEALTH, new AttributeModifier(MysticcraftMod.res("ender_knight_max_health"), 4, AttributeModifier.Operation.ADD_VALUE));
        }
        return preReturn;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("This Armor get's double stats in the End"));
        tooltipComponents.add(Component.literal(""));
    }
}