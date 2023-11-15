package net.kapitencraft.mysticcraft.mixin.classes;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.ServerData;
import net.kapitencraft.mysticcraft.client.render.ColorAnimator;
import net.kapitencraft.mysticcraft.config.ClientModConfig;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.ITieredItem;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.LongSwordItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.QuiverItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ShortBowItem;
import net.kapitencraft.mysticcraft.item.data.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.item.data.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.data.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

@Mixin(ItemStack.class)
@SuppressWarnings("ALL")
public abstract class ItemStackMixin {
    private static final Style LORE_STYLE = Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE).withItalic(true);
    private static final ColorAnimator RAINBOW_ANIMATOR = ColorAnimator.createRainbow(30 * (10 - ClientModConfig.rgbSpeed));

    private ItemStack self() {
        return (ItemStack) (Object) this;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(CallbackInfo info) {
        ModEventFactory.onLoadingItemStack(self());
    }

    @Inject(method = "save", at = @At("HEAD"))
    private void save(CompoundTag tag, CallbackInfoReturnable<CompoundTag> callbackInfoReturnable) {
        ModEventFactory.onSavingItemStack(self(), tag);
    }

    /**
     * @reason custom attribute and enchantment description
     * @author Kapitencraft
     */
    @Overwrite
    public List<Component> getTooltipLines(@Nullable Player player, TooltipFlag tooltipFlag) {
        List<Component> list = Lists.newArrayList();
        Item item = self().getItem();
        CompoundTag tag = self().getOrCreateTag();
        MutableComponent name = Component.empty().append(self().getHoverName()).withStyle(self().getRarity().getStyleModifier());
        if (this.callHasCustomHoverName()) {
            name.withStyle(ChatFormatting.ITALIC);
        }

        list.add(name);
        if (!tooltipFlag.isAdvanced() && !self().hasCustomHoverName() && self().is(Items.FILLED_MAP)) {
            Integer integer = MapItem.getMapId(self());
            if (integer != null) {
                list.add(Component.literal("#" + integer).withStyle(ChatFormatting.GRAY));
            }
        }

        int j = callGetHideFlags();
        if (shouldShowInTooltip(j, ItemStack.TooltipPart.ADDITIONAL)) {
            if (item instanceof IModItem modItem) {
                modItem.appendHoverTextWithPlayer(self(), player == null ? null : player.level, list, tooltipFlag, player);
            } else {
                item.appendHoverText(self(), player == null ? null : player.level, list, tooltipFlag);
            }
        }

        if (shouldShowInTooltip(j, ItemStack.TooltipPart.ENCHANTMENTS)) {
                ListTag enchantmentTags = self().getEnchantmentTags();
                for(int i = 0; i < enchantmentTags.size(); ++i) {
                    CompoundTag compoundtag = enchantmentTags.getCompound(i);
                    Optional<Enchantment> enchantmentOptional = BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(compoundtag));
                    if (enchantmentOptional.isPresent()) {
                        Enchantment enchantment = enchantmentOptional.get();
                        int level = self().getEnchantmentLevel(enchantment);
                        final MutableComponent[] components = new MutableComponent[1];
                        enchantmentOptional.ifPresent((p_41708_) -> components[0] = (MutableComponent) p_41708_.getFullname(EnchantmentHelper.getEnchantmentLevel(compoundtag)));
                        MutableComponent component = components[0];
                        if (!(enchantment.isCurse())) {
                            if (enchantment instanceof IUltimateEnchantment) {
                                component.withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD);
                            } else if (level == enchantment.getMaxLevel()) {
                                component.withStyle(ChatFormatting.GOLD);
                            } else if (level > enchantment.getMaxLevel()) {
                                component.withStyle(Style.EMPTY.withColor(RAINBOW_ANIMATOR.makeTextColor(ServerData.getTime())));
                            }
                        }
                        list.add(component);
                    }
                }
            }
        if (tag.contains("display", 10)) {
            CompoundTag compoundtag = tag.getCompound("display");
            if (shouldShowInTooltip(j, ItemStack.TooltipPart.DYE) && compoundtag.contains("color", 99)) {
                if (tooltipFlag.isAdvanced()) {
                    list.add(Component.translatable("item.color", String.format(Locale.ROOT, "#%06X", compoundtag.getInt("color"))).withStyle(ChatFormatting.GRAY));
                } else {
                    list.add(Component.translatable("item.dyed").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
                }
            }
            if (compoundtag.getTagType("Lore") == 9) {
                ListTag listtag = compoundtag.getList("Lore", 8);
                for(int i = 0; i < listtag.size(); ++i) {
                    String s = listtag.getString(i);
                    try {
                        MutableComponent mutablecomponent1 = Component.Serializer.fromJson(s);
                        if (mutablecomponent1 != null) {
                            list.add(ComponentUtils.mergeStyles(mutablecomponent1, LORE_STYLE));
                        }
                    } catch (Exception exception) {
                        compoundtag.remove("Lore");
                    }
                 }
            }
        }
        if (shouldShowInTooltip(j, ItemStack.TooltipPart.MODIFIERS)) {
            Reforge reforge = Reforge.getFromStack(self());
            for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                Multimap<Attribute, AttributeModifier> multimap = self().getAttributeModifiers(equipmentslot);
                if (!multimap.isEmpty()) {
                    list.add(CommonComponents.EMPTY);
                    list.add(Component.translatable("item.modifiers." + equipmentslot.getName()).withStyle(ChatFormatting.GRAY));
                    for(Map.Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
                        AttributeModifier modifier = entry.getValue();
                        double d0 = modifier.getAmount();
                        boolean flag = false;
                        if (player != null) {
                            if (SpellItem.ATTACK_DAMAGE_UUID.equals(modifier.getId())) {
                                d0 += player.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
                                d0 += EnchantmentHelper.getDamageBonus(self(), MobType.UNDEFINED);
                                flag = true;
                            } else if (modifier.getId() == SpellItem.ATTACK_SPEED_UUID) {
                                d0 += player.getAttributeBaseValue(Attributes.ATTACK_SPEED);
                                flag = true;
                            } else if (modifier.getId() == LongSwordItem.ATTACK_RANGE_ID) {
                                d0 += player.getAttributeBaseValue(ForgeMod.ATTACK_RANGE.get());
                                flag = true;
                            } else if (modifier.getId() == AttributeHelper.getByName("Digger Item Speed Modifier")) {
                                d0 += player.getAttributeBaseValue(ModAttributes.MINING_SPEED.get());
                                flag = true;
                            }
                        }
                        double d1;
                        switch (modifier.getOperation()) {
                            case ADDITION -> {
                                if (entry.getKey().equals(Attributes.KNOCKBACK_RESISTANCE)) {
                                    d1 = d0 * 10.0D;
                                } else {
                                    d1 = d0;
                                }
                            }
                            case MULTIPLY_BASE -> d1 = d0 * 100;
                            default -> d1 = d0;
                        }
                        MutableComponent toAppend = Component.empty();
                        if (flag) {
                            //Base Values
                            toAppend.append(Component.literal(" ").append(Component.translatable("attribute.modifier.equals." + modifier.getOperation().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(entry.getKey().getDescriptionId()))).withStyle(ChatFormatting.DARK_GREEN));
                        } else if (d0 > 0.0D || d0 < 0.0D) {
                            if (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                                d1+=1;
                                toAppend.append(MiscHelper.buildComponent(Component.literal(String.valueOf(MathHelper.defRound(d1))), Component.literal("x "), Component.translatable(entry.getKey().getDescriptionId())).withStyle(ChatFormatting.BLUE));
                            } else {
                                String id = "plus";
                                ChatFormatting formatting = ChatFormatting.BLUE;
                                if (d0 < 0.0D) {
                                    d1 *= -1;
                                    id = "take";
                                    formatting = ChatFormatting.RED;
                                }
                                toAppend.append(Component.translatable("attribute.modifier." + id + "." + modifier.getOperation().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(entry.getKey().getDescriptionId())).withStyle(formatting));
                            }
                        }
                        if (modifier.getOperation() == AttributeModifier.Operation.ADDITION || (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE && (entry.getKey() == Attributes.MOVEMENT_SPEED || entry.getKey() == ForgeMod.SWIM_SPEED.get()))) {
                            if (reforge != null && reforge.hasModifier(entry.getKey())) {
                                Double reforgeValue = reforge.applyModifiers(self().getRarity()).get(entry.getKey());
                                String reforgeStringValue = reforgeValue > 0 ? "+" + ATTRIBUTE_MODIFIER_FORMAT.format(reforgeValue) : ATTRIBUTE_MODIFIER_FORMAT.format(reforgeValue);
                                toAppend.append(Component.literal(" (" + reforgeStringValue + ")").withStyle(ChatFormatting.GREEN));
                            }
                            if (self().getItem() instanceof IGemstoneApplicable applicable) {
                                HashMap<Attribute, AttributeModifier> mods = applicable.getAttributeModifiers(self(), equipmentslot);
                                if (mods.containsKey(entry.getKey())) {
                                    double value = mods.get(entry.getKey()).getAmount();
                                    double rounded = MathHelper.round(value, 2);
                                    toAppend.append(Component.literal(" (+" + rounded + ")").withStyle(ChatFormatting.LIGHT_PURPLE));
                                }
                            }
                        }
                        list.add(toAppend);
                    }
                }
            }
        }

        if (self().hasTag()) {
            if (shouldShowInTooltip(j, ItemStack.TooltipPart.UNBREAKABLE) && tag.getBoolean("Unbreakable")) {
                list.add(Component.translatable("item.unbreakable").withStyle(ChatFormatting.BLUE));
            }

            if (shouldShowInTooltip(j, ItemStack.TooltipPart.CAN_DESTROY) && tag.contains("CanDestroy", 9)) {
                ListTag listtag1 = tag.getList("CanDestroy", 8);
                if (!listtag1.isEmpty()) {
                    list.add(CommonComponents.EMPTY);
                    list.add(Component.translatable("item.canBreak").withStyle(ChatFormatting.GRAY));

                    for(int k = 0; k < listtag1.size(); ++k) {
                        list.addAll(callExpandBlockState(listtag1.getString(k)));
                    }
                }
            }

            if (shouldShowInTooltip(j, ItemStack.TooltipPart.CAN_PLACE) && tag.contains("CanPlaceOn", 9)) {
                ListTag listtag2 = tag.getList("CanPlaceOn", 8);
                if (!listtag2.isEmpty()) {
                    list.add(CommonComponents.EMPTY);
                    list.add(Component.translatable("item.canPlace").withStyle(ChatFormatting.GRAY));

                    for(int l = 0; l < listtag2.size(); ++l) {
                        list.addAll(callExpandBlockState(listtag2.getString(l)));
                    }
                }
            }
        }

        if (tooltipFlag.isAdvanced()) {
            if (self().isDamaged()) {
                list.add(Component.translatable("item.durability", self().getMaxDamage() - self().getDamageValue(), self().getMaxDamage()));
            }

            list.add(Component.literal(BuiltInRegistries.ITEM.getKey(item).toString()).withStyle(ChatFormatting.DARK_GRAY));
            if (self().hasTag()) {
                list.add(Component.translatable("item.nbt_tags", tag.getAllKeys().size()).withStyle(ChatFormatting.DARK_GRAY));
                if (Screen.hasAltDown() && self().getTag() != null) {
                    list.add(NbtUtils.toPrettyComponent(self().getTag()));
                }
            }
        }

        if (player != null && !item.isEnabled(player.getLevel().enabledFeatures())) {
            list.add(Component.translatable("item.disabled").withStyle(ChatFormatting.RED));
        }
        ForgeEventFactory.onItemTooltip(self(), player, list, tooltipFlag);
        if (!(item instanceof IGuiHelper)) {
            Rarity rarity = item.getRarity(self());
            boolean flag = rarity != MiscHelper.getItemRarity(item);
            String RarityMod = FormattingCodes.OBFUSCATED + "A" + FormattingCodes.RESET;
            MutableComponent obfuscated = Component.literal(flag ? RarityMod : "");
            list.add(Component.literal(""));
            list.add(MiscHelper.buildComponent(Component.literal(flag ? RarityMod + " " : "") ,  createNameMod(self()), MiscHelper.SPLIT, obfuscated).withStyle(rarity.getStyleModifier()).withStyle(ChatFormatting.BOLD));
        }

        TextHelper.removeUnnecessaryEmptyLines(list);
        return list;
    }

    private static Component getNameModifier(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof LongSwordItem) {
            return indicator("longsword");
        } else if (item instanceof ShortBowItem) {
            return indicator("short_bow");
        } else if (item instanceof SwordItem) {
            return indicator("sword");
        } else if (item instanceof PickaxeItem) {
            return indicator("pickaxe");
        } else if (item instanceof AxeItem) {
            return indicator("axe");
        } else if (item instanceof ShovelItem) {
            return indicator("shovel");
        } else if (item instanceof HoeItem) {
            return indicator("hoe");
        } else if (item instanceof BowItem) {
            return indicator("bow");
        } else if (item instanceof CrossbowItem) {
            return indicator("crossbow");
        } else if (item instanceof EnchantedBookItem) {
            return indicator("enchanted_book");
        } else if (item instanceof ArmorItem armorItem) {
            if (armorItem.getSlot() == EquipmentSlot.FEET) {
                return indicator("boots");
            } else if (armorItem.getSlot() == EquipmentSlot.LEGS) {
                return indicator("legs");
            } else if (armorItem.getSlot() == EquipmentSlot.CHEST) {
                return indicator("chestplate");
            } else if (armorItem.getSlot() == EquipmentSlot.HEAD) {
                return indicator("helmet");
            }
        } else if (item instanceof BlockItem) {
            return indicator("block");
        } else if (item instanceof BoatItem) {
            return indicator("boat");
        } else if (item instanceof FishingRodItem) {
            return indicator("fishing_rod");
        } else if (item instanceof QuiverItem) {
            return indicator("quiver");
        } else if (item instanceof ShieldItem) {
            return indicator("shield");
        }
        return indicator("item");
    }

    private static MutableComponent indicator(String id) {
        return Component.translatable("item.indicator." + id);
    }

    private static MutableComponent createNameMod(ItemStack stack) {
        MutableComponent component = Component.empty();
        Rarity rarity = stack.getItem().getRarity(stack);
        component.append(Component.literal(rarity + " "));
        if (stack.getItem() instanceof SpellItem) {
            component.append(Component.translatable("item.indicator.spell_catalyst"));
            component.append(" ");
        }
        component.append(getNameModifier(stack));
        return component;
    }


    private static boolean shouldShowInTooltip(int p_41627_, ItemStack.TooltipPart p_41628_) {
        return (p_41627_ & p_41628_.getMask()) == 0;
    }


    /**
     * @reason reforge name
     * @author Kapitencraft
     */
    @Overwrite
    public Component getHoverName() {
        CompoundTag compoundtag = self().getTagElement("display");
        MutableComponent name = Component.empty();
        Component component = null;
        Item item = self().getItem();
        Reforge reforge = Reforge.getFromStack(self());
        if (reforge != null) {
            name.append(reforge.getName());
            name.append(" ");
        }

        if (item instanceof ITieredItem) {
            ITieredItem.ItemTier tier = ITieredItem.getTier(self());
            if (tier != ITieredItem.ItemTier.DEFAULT) {
                name.append(tier.getName());
                name.append(" ");
            }

        }
        if (compoundtag != null && compoundtag.contains("Name", 8)) {
            try {
                component = Component.Serializer.fromJson(compoundtag.getString("Name"));
                compoundtag.remove("Name");
            } catch (Exception exception) {
                compoundtag.remove("Name");
            }
        }
        if (component == null) {
            component = self().getItem().getName(self());
        }
        name.append(component);
        if (item instanceof IStarAbleItem && IStarAbleItem.hasStars(self())) {
            name.append(" ");
            name.append(IStarAbleItem.getStarDisplay(self()));
        }
        return name;
    }

    @Invoker
    abstract boolean callHasCustomHoverName();

    @Invoker
    abstract int callGetHideFlags();

    @Invoker
    abstract Collection<Component> callExpandBlockState(String s);
}
