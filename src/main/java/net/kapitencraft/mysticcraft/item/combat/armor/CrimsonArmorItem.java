package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.client.armor.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.client.armor.provider.SimpleModelProvider;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.item.creative_tab.ArmorTabGroup;
import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.capability.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.item.combat.armor.client.NetherArmorItem;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.CrimsonArmorModel;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class CrimsonArmorItem extends NetherArmorItem {
    public static final TabGroup TAB = ArmorTabGroup.create();

    public CrimsonArmorItem(ArmorItem.Type p_40387_) {
        super(ModArmorMaterials.CRIMSON, p_40387_, NETHER_ARMOR_PROPERTIES);
    }

    public static ItemStack createAdvancementStack() {
        ItemStack stack = new ItemStack(ModItems.CRIMSON_ARMOR.get(Type.CHESTPLATE).get());
        ItemTier.INFERNAL.saveToStack(stack);
        return stack;
    }

    @Override
    public boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(CrimsonArmorModel::createBodyLayer, CrimsonArmorModel::new);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, "crimson_armor");
    }

    @Override
    public List<ItemStack> getMatCost(ItemStack stack) {
        int stars = IStarAbleItem.getStars(stack) + 5;
        int prestige = ITieredItem.getTier(stack).getNumber();
        return List.of(
                IPrestigeAbleItem.essence(EssenceType.CRIMSON, (int) Math.pow(stars, prestige))
        );
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiersForSlot(ItemStack stack, ItemTier tier) {
        return multimap -> {
            multimap.put(ExtraAttributes.STRENGTH.get(), AttributeHelper.createModifierForSlot("Crimson Armor", AttributeModifier.Operation.ADDITION,
                    3 * this.getMaterial().getDefenseForType(this.type) * tier.getValueMul(), this.getEquipmentSlot()));
            multimap.put(ExtraAttributes.CRIT_DAMAGE.get(), AttributeHelper.createModifierForSlot("Crimson Armor", AttributeModifier.Operation.ADDITION,
                    this.getMaterial().getDefenseForType(this.type) * tier.getValueMul(), this.getEquipmentSlot()));
            multimap.put(Attributes.MAX_HEALTH, AttributeHelper.createModifierForSlot("Crimson Armor", AttributeModifier.Operation.ADDITION,
                    this.getMaterial().getDefenseForType(this.type) * 0.4 * tier.getValueMul(), this.getEquipmentSlot()));
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        if (slot == this.getEquipmentSlot()) {
        }
        return builder;
    }
}