package net.kapitencraft.mysticcraft.mixin.classes.client;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.helpers.TextHelper;
import net.kapitencraft.kap_lib.mixin.duck.MixinSelfProvider;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.client.ItemCategory;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackClientMixin implements MixinSelfProvider<ItemStack> {

    @Shadow public abstract String toString();

    @Shadow @Final private Item item;

    @Shadow @Nullable private CompoundTag tag;

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getBoolean(Ljava/lang/String;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addSoulbound(Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir, List<Component> list, MutableComponent mutablecomponent, int j) {
        if (this.tag.getBoolean(SoulbindHelper.SOULBOUND_TAG_ID)) list.add(Component.translatable("item.soulbound"));
    }

    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shouldShowInTooltip(ILnet/minecraft/world/item/ItemStack$TooltipPart;)Z", ordinal = 4), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addElytraInfo(Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir, List<Component> list) {
        CapabilityHelper.exeCapability(self(), CapabilityHelper.ELYTRA, data1 -> {
            if (Screen.hasShiftDown()) {
                ElytraData data = data1.getDataType();
                String id = "elytra_data." + data.getSerializedName();
                list.add(Component.translatable(id).withStyle(ChatFormatting.GREEN).append(" ").append(Component.translatable("enchantment.level." + data1.getLevel())));
                list.addAll(TextHelper.getAllMatchingFilter(i -> i == 0 ? id + ".desc" : id + ".desc" + i, component -> component.withStyle(ChatFormatting.YELLOW)));
            } else {
                list.add(Component.translatable("elytra_data.tooltip").withStyle(ChatFormatting.YELLOW));
            }
            TextHelper.addEmpty(list);
        });
    }

    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addGemstoneInfo(Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir, List<Component> list) {
        GemstoneHelper.getCapability(self(), iGemstoneHandler -> iGemstoneHandler.getDisplay(list));
    }

    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addSpellAndBonusTooltip(Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir, List<Component> list) {
        if (item instanceof ISpellItem) SpellHelper.appendFullDisplay(list, self(), pPlayer);
    }

    @Inject(method = "getTooltipLines", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addRarityExtension(Player pPlayer, TooltipFlag pIsAdvanced, CallbackInfoReturnable<List<Component>> cir, List<Component> list) {
        if (!(item instanceof IGuiHelper)) {
            Rarity rarity = item.getRarity(self());
            boolean flag = rarity != MiscHelper.getItemRarity(item) && !(item instanceof SpellScrollItem);
            list.add(CommonComponents.EMPTY);
            MutableComponent nameMod = createNameMod(self());
            list.add((flag ? TextHelper.wrapInObfuscation(nameMod) : nameMod).withStyle(rarity.getStyleModifier()).withStyle(ChatFormatting.BOLD));
        }
        TextHelper.removeUnnecessaryEmptyLines(list);
    }

    @SuppressWarnings("all")
    private static MutableComponent createNameMod(ItemStack stack) {
        MutableComponent component = Component.empty();
        Rarity rarity = stack.getItem().getRarity(stack);
        component.append(Component.translatable("item.indicator." + rarity.name().toLowerCase()));
        ItemCategory.Registry.appendDisplay(component, stack);
        return component;
    }

    @SuppressWarnings("all")
    private static boolean shouldShowInTooltip(int p_41627_, ItemStack.TooltipPart p_41628_) {
        return (p_41627_ & p_41628_.getMask()) == 0;
    }

    /**
     * @reason reforge name
     * @author Kapitencraft
     */
    @Overwrite
    public Component getHoverName() {
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
        CompoundTag compoundtag = self().getTagElement("display");
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
}