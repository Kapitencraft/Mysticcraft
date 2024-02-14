package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.particle.flame.FlamesForColors;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.combat.armor.client.NetherArmorItem;
import net.kapitencraft.mysticcraft.item.item_bonus.ExtraBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.fullset.SoulMageArmorFullSetBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.piece.SoulMageChestplateBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.piece.SoulMageHelmetBonus;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.ArmorTabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.kapitencraft.mysticcraft.misc.particle_help.ParticleAnimator;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class SoulMageArmorItem extends NetherArmorItem implements IArmorBonusItem {
    public static final ArmorTabGroup SOUL_MAGE_ARMOR_GROUP = new ArmorTabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    public static final String HELPER_STRING = "SoulMageFullSet";
    private static final PieceBonus HELMET_BONUS = new SoulMageHelmetBonus();
    private static final PieceBonus CHEST_BONUS = new SoulMageChestplateBonus();
    private static final FullSetBonus SET_BONUS = new SoulMageArmorFullSetBonus();

    @Override
    public boolean withCustomModel() {
        return false;
    }

    public SoulMageArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.SOUL_MAGE, p_40387_, new Properties().rarity(ModRarities.LEGENDARY));
    }

    @Override
    protected void initFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        new ParticleAnimator(HELPER_STRING, living, ParticleAnimator.Type.ORBIT, ParticleAnimator.createOrbitProperties(0, 1000, 0, 0, 3, FlamesForColors.PURPLE, 0.75f));
        new ParticleAnimator(HELPER_STRING, living, ParticleAnimator.Type.ORBIT, ParticleAnimator.createOrbitProperties(0, 1000, 180, 0, 3, FlamesForColors.PURPLE, 0.75f));
    }

    @Override
    protected void postFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        ParticleAnimator.clearAllHelpers(HELPER_STRING, living);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        if (slot == this.getSlot()) {
            builder.put(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscHelper.createCustomIndex(slot)], "Intelligence", 345, AttributeModifier.Operation.ADDITION));
        }
        return builder;
    }

    @Override
    public FullSetBonus getFullSetBonus() {
        return SET_BONUS;
    }

    @Override
    public PieceBonus getPieceBonusForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case CHEST -> CHEST_BONUS;
            case HEAD -> HELMET_BONUS;
            default -> null;
        };
    }

    @Nullable
    @Override
    public ExtraBonus getExtraBonus(EquipmentSlot slot) {
        return null;
    }

    @Override
    public TabGroup getGroup() {
        return SOUL_MAGE_ARMOR_GROUP;
    }

    @Override
    public List<ItemStack> getMatCost(ItemStack stack) {
        return null;
    }


    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiersForSlot(ItemStack stack, ItemTier tier) {
        return null;
    }
}
