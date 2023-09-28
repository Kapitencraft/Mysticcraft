package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.item_bonus.ExtraBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.kapitencraft.mysticcraft.utils.TextUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public abstract class ModArmorItem extends ArmorItem implements IModItem {
    private static final String FULL_SET_ID = "hadFullSet";
    protected String dimension;
    private Entity user;
    protected int fullSetTick = 0;

    public ModArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_) {
        super(p_40386_, p_40387_, p_40388_);
    }
    public boolean equals(ArmorItem item) {
        MysticcraftMod.sendInfo(item.getMaterial().getName());
        return this == item || item.getMaterial() == this.getMaterial();
    }

    public interface Creator {
        ModArmorItem create(EquipmentSlot slot);
    }

    public static HashMap<EquipmentSlot, RegistryObject<Item>> createRegistry(String registryName, Creator creator) {
        return ModItems.createRegistry(creator::create, slot -> registryName + "_" + TextUtils.getRegistryNameForSlot(slot), List.of(MiscUtils.ARMOR_EQUIPMENT), ModItems.TabTypes.WEAPONS_AND_TOOLS);
    }

    public static boolean hadFullSet(ModArmorMaterials materials, LivingEntity living) {
        return !ModArmorItem.isFullSetActive(living, materials) && living.getPersistentData().getString("lastFullSet").equals(materials.getName());
    }


    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> toolTip, @NotNull TooltipFlag flag, Player player) {
        if (stack.getItem() instanceof IGemstoneApplicable gemstoneApplicable) {
            gemstoneApplicable.appendDisplay(stack, toolTip);
            toolTip.add(Component.literal(""));
        }
        if (stack.getItem() instanceof IArmorBonusItem bonusItem) {
            bonusItem.addDisplay(toolTip, this.getSlot());
            toolTip.add(Component.literal(""));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, @NotNull List<Component> toolTip, @NotNull TooltipFlag p_41424_) {
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotID, boolean isSelected) {
        if (entity instanceof LivingEntity living) {
            CompoundTag tag = living.getPersistentData();
            if (this.isFullSetActive(living)) {
                if (this.getSlot() == EquipmentSlot.CHEST) {
                    if (this.fullSetTick == 0) {
                        if (!level.isClientSide) this.initFullSetTick(stack, level, living);
                        tag.putBoolean(FULL_SET_ID, true);
                        tag.putString("lastFullSet", this.getMaterial().getName());
                    }
                    this.fullSetTick++;
                    if (level.isClientSide) {
                        this.clientFullSetTick(stack, level, living);
                    } else {
                        this.fullSetTick(stack, level, living);
                    }
                }
            } else {
                if (living.getPersistentData().getBoolean(FULL_SET_ID)) {
                    if (!level.isClientSide) postFullSetTick(stack, level, living);
                    tag.putBoolean(FULL_SET_ID, false);
                }
                fullSetTick = 0;
            }
        }
        if (stack.getItem() == this) {
            updateDimension(entity.level);
            updateUser(entity);
        }
    }
    public boolean isFullSetActive(LivingEntity living) {
        return isFullSetActive(living, this.getMaterial());
    }

    public static boolean isFullSetActive(LivingEntity living, ArmorMaterial materials) {
        if (living == null) {
            return false;
        }
        ArmorItem head = living.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem armorItem ? armorItem : null;
        Item chestPlate = living.getItemBySlot(EquipmentSlot.CHEST).getItem();
        ArmorItem chest;
        if (chestPlate instanceof ElytraItem || chestPlate instanceof AirItem) {
            return false;
        } else {
            chest = (ArmorItem) living.getItemBySlot(EquipmentSlot.CHEST).getItem();
        }
        ArmorItem legs = living.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem armorItem ? armorItem : null;
        ArmorItem feet = living.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem armorItem ? armorItem : null;
        return (head != null && legs != null && feet != null) && (head.getMaterial() == materials && chest.getMaterial() == materials && legs.getMaterial() == materials && feet.getMaterial() == materials);
    }

    protected void fullSetTick(ItemStack stack, Level level, LivingEntity living) {}
    protected void initFullSetTick(ItemStack stack, Level level, LivingEntity living) {}
    protected void postFullSetTick(ItemStack stack, Level level, LivingEntity living) {}
    protected void clientFullSetTick(ItemStack stack, Level level, LivingEntity living) {}

    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {return null;}

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        if (this.getAttributeMods(slot) != null) {
            builder.putAll(this.getAttributeMods(slot));
        }
        return builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        Multimap<Attribute, AttributeModifier> map = null;
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        if (slot == this.getSlot()) {
            if (this instanceof IArmorBonusItem bonusItem && this.user instanceof LivingEntity living) {
                PieceBonus pieceBonus = bonusItem.getPieceBonusForSlot(this.getSlot());
                if (pieceBonus != null) {
                    Multimap<Attribute, AttributeModifier> bonusMods = pieceBonus.getModifiers(living);
                    if (bonusMods != null) {
                        builder.putAll(bonusMods);
                    }
                }
                FullSetBonus fullSetBonus = bonusItem.getFullSetBonus();
                if (fullSetBonus != null) {
                    Multimap<Attribute, AttributeModifier> bonusMods = fullSetBonus.getModifiers(living);
                    if (this.getSlot() == EquipmentSlot.CHEST && this.isFullSetActive(living) && bonusMods != null) {
                        builder.putAll(bonusMods);
                    }
                }
                ExtraBonus extraBonus = bonusItem.getExtraBonus(this.getSlot());
                if (extraBonus != null) {
                    Multimap<Attribute, AttributeModifier> extraMods = extraBonus.getModifiers(living);
                    if (extraMods != null) {
                        builder.putAll(extraMods);
                    }
                }
            }
            if (this instanceof IGemstoneApplicable applicable) {
                map = AttributeUtils.increaseAllByAmount(builder.build(), applicable.getAttributeModifiers(stack, slot));
            }
        }
        if (stack.getTag() != null && stack.getTag().getBoolean("isOP")) {
            return AttributeUtils.increaseByPercent(map == null ? builder.build() : map, 300, new AttributeModifier.Operation[]{AttributeModifier.Operation.ADDITION, AttributeModifier.Operation.MULTIPLY_TOTAL, AttributeModifier.Operation.MULTIPLY_BASE}, null);
        }
        return builder.build();
    }

    protected void updateDimension(Level level) {
        this.dimension = MiscUtils.getDimension(level);
    }

    private void updateUser(Entity user) {
        this.user = user;
    }
}