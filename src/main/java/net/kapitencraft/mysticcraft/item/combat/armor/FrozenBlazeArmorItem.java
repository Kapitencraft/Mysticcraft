package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.FrozenBlazeArmorModel;
import net.kapitencraft.mysticcraft.item.combat.armor.client.renderer.ArmorRenderer;
import net.kapitencraft.mysticcraft.item.item_bonus.ExtraBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.MultiPieceBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.fullset.FrozenBlazeFullSetBonus;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.ArmorTabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.kapitencraft.mysticcraft.misc.damage_source.FrozenDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class FrozenBlazeArmorItem extends ModArmorItem implements IArmorBonusItem {
    public static final ArmorTabGroup FROZEN_BLAZE_ARMOR_GROUP = new ArmorTabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);

    @Override
    public TabGroup getGroup() {
        return FROZEN_BLAZE_ARMOR_GROUP;
    }

    private static final FrozenBlazeFullSetBonus FULL_SET_BONUS = new FrozenBlazeFullSetBonus();
    public FrozenBlazeArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.FROZEN_BLAZE, p_40387_, new Item.Properties().rarity(ModRarities.LEGENDARY).fireResistant());
    }

    @Override
    public List<MultiPieceBonus> getPieceBonni() {
        return List.of(FULL_SET_BONUS);
    }

    @Override
    public PieceBonus getPieceBonusForSlot(EquipmentSlot slot) {
        return null;
    }

    @Nullable
    @Override
    public ExtraBonus getExtraBonus(EquipmentSlot slot) {
        return null;
    }
    @Override
    public void fullSetTick(ItemStack stack, Level level, LivingEntity living) {
        List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(5), new Predicate<LivingEntity>() {
            @Override
            public boolean test(LivingEntity living) {
                return true;
            }
        });
        for (LivingEntity livingEntity : entityList) {
            if (livingEntity != living) {
                livingEntity.hurt(new FrozenDamageSource(living), 2);
            }
        }
    }

    @Override
    public boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorRenderer<?> getRenderer(LivingEntity living, ItemStack stack, EquipmentSlot slot) {
        return new ArmorRenderer<>(FrozenBlazeArmorModel::createBodyLayer, FrozenBlazeArmorModel::new);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return makeCustomTextureLocation("frozen_blaze_armor");
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        return null;
    }
}
