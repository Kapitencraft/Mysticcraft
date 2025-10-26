package net.kapitencraft.mysticcraft.mixin.classes.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.helpers.TextHelper;
import net.kapitencraft.kap_lib.mixin.duck.MixinSelfProvider;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.client.ItemCategory;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackClientMixin implements DataComponentHolder, MixinSelfProvider<ItemStack> {

    @Shadow public abstract String toString();

    @Shadow @Final private Item item;

    @Shadow public abstract Rarity getRarity();

    @Shadow public abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> component, Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag);

    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", ordinal = 6), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addSoulbound(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, List<Component> list, MutableComponent mutablecomponent, Consumer<Component> consumer) {
        if (has(ModDataComponentTypes.SOUL_BOUND)) list.add(Component.translatable("item.soulbound"));
    }

    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", ordinal = 2))
    private void addElytraAndSpellInfo(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, @Local List<Component> list) {
        addToTooltip(ModDataComponentTypes.ELYTRA.get(), tooltipContext, list::add, tooltipFlag);
        if (has(ModDataComponentTypes.ITEM_SPELLS))
            SpellHelper.appendFullDisplay(list, self(), player);
    }

    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addGemstoneInfo(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, List<Component> list, MutableComponent mutablecomponent) {
        addToTooltip(ModDataComponentTypes.EMBEDDED_GEMSTONES, tooltipContext, list::add, tooltipFlag);
    }

    @Inject(method = "getTooltipLines", at = @At("TAIL"))
    private void addRarityExtension(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, @Local List<Component> list) {
        if (!(item instanceof IGuiHelper)) {
            Rarity rarity = getRarity();
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
        Rarity rarity = stack.getRarity();
        component.append(Component.translatable("item.indicator." + rarity.name().toLowerCase()));
        ItemCategory.Registry.appendDisplay(component, stack);
        return component;
    }

    /**
     * @reason reforge name
     * @author Kapitencraft
     */
    @Overwrite
    public Component getHoverName() {
        MutableComponent name = Component.empty();
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
        Component component = this.get(DataComponents.CUSTOM_NAME);
        if (component != null) {
            name.append(component);
        } else {
            Component component1 = this.get(DataComponents.ITEM_NAME);
            name.append(component1 != null ? component1 : item.getName(self()));
        }
        if (item instanceof IStarAbleItem && IStarAbleItem.hasStars(self())) {
            name.append(" ");
            name.append(IStarAbleItem.getStarDisplay(self()));
        }
        return name;
    }
}