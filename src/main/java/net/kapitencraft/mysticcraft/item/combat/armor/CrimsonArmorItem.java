package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.SimpleModelProvider;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.capability.ITieredItem;
import net.kapitencraft.mysticcraft.item.capability.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.item.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.item.combat.armor.client.NetherArmorItem;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.CrimsonArmorModel;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.ArmorTabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class CrimsonArmorItem extends NetherArmorItem implements IModItem {


    public static final ArmorTabGroup CRIMSON_ARMOR_GROUP = new ArmorTabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    //private static final OrbitAnimationElement element = new OrbitAnimationElement("CrimsonArmorFullset", 2, 3, FlamesForColors.RED, -1, 0, 0);

    public CrimsonArmorItem(ArmorItem.Type p_40387_) {
        super(ModArmorMaterials.CRIMSON, p_40387_, NETHER_ARMOR_PROPERTIES);
    }

    public static ItemStack createAdvancementStack() {
        ItemStack stack = new ItemStack(ModItems.CRIMSON_ARMOR.get(Type.CHESTPLATE).get());
        ItemTier.INFERNAL.saveToStack(stack);
        return stack;
    }

    @Override
    protected void initFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        //TODO add animation
        //IAnimatable.get(living).addElement(element);
    }

    @Override
    public boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorModelProvider getModelProvider() {
        return new SimpleModelProvider(CrimsonArmorModel::createBodyLayer, CrimsonArmorModel::new);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, "crimson_armor");
    }

    @Override
    public TabGroup getGroup() {
        return CRIMSON_ARMOR_GROUP;
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