package net.kapitencraft.mysticcraft.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.kapitencraft.mysticcraft.item.item_bonus.ExtraBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.fullset.SoulMageArmorFullSetBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.piece.SoulMageChestplateBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.piece.SoulMageHelmetBonus;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.particle_help.ParticleHelper;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SoulMageArmorItem extends ModArmorItem implements IArmorBonusItem {
    public static final String helperString = "SoulMageFullSet";
    private static final PieceBonus HELMET_BONUS = new SoulMageHelmetBonus();
    private static final PieceBonus CHEST_BONUS = new SoulMageChestplateBonus();
    private static final FullSetBonus SET_BONUS = new SoulMageArmorFullSetBonus();
    public SoulMageArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.SOUL_MAGE, p_40387_, new Properties().rarity(FormattingCodes.LEGENDARY));
    }

    @Override
    public void fullSetTick(ItemStack stack, Level level, LivingEntity living) {
    }

    @Override
    protected void initFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        MysticcraftMod.sendInfo("SoulMage");
        new ParticleHelper(helperString, living, ParticleHelper.Type.ORBIT, ParticleHelper.createOrbitProperties(0, 1000, 0, 0, 3, (SimpleParticleType) ModParticleTypes.PURPLE_FLAME.get(), 0.75f));
        new ParticleHelper(helperString, living, ParticleHelper.Type.ORBIT, ParticleHelper.createOrbitProperties(0, 1000, 180, 0, 3, (SimpleParticleType) ModParticleTypes.PURPLE_FLAME.get(), 0.75f));
    }

    @Override
    protected void postFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        ParticleHelper.clearAllHelpers(helperString, living);
    }

    @Override
    protected void clientFullSetTick(ItemStack stack, Level level, LivingEntity living) {

    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == this.getSlot()) {
            builder.put(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "Intelligence", 345, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    @Override
    public FullSetBonus getFullSetBonus() {
        return SET_BONUS;
    }

    @Override
    public PieceBonus getPieceBonusForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HELMET_BONUS;
            case CHEST -> CHEST_BONUS;
            default -> null;
        };
    }

    @Nullable
    @Override
    public ExtraBonus getExtraBonus(EquipmentSlot slot) {
        return null;
    }
}
