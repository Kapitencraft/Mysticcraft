package net.kapitencraft.mysticcraft.mixin.classes;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.ITieredItem;
import net.kapitencraft.mysticcraft.item.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.kapitencraft.mysticcraft.item.combat.spells.SpellItem.SPELL_EXE;
import static net.kapitencraft.mysticcraft.item.combat.spells.SpellItem.SPELL_EXECUTION_DUR;

@Mixin(Item.class)
@SuppressWarnings("ALL")
public abstract class ItemMixin implements IForgeItem {

    @Shadow @Final private Rarity rarity;

    private Item self() {return (Item) (Object) this;}

    /**
     * @reason implementation of reforge, gemstone item dungeon star / item and stat boost enchantment modifications
     * @author Kapitencraft
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        AttributeHelper.AttributeBuilder builder = new AttributeHelper.AttributeBuilder(self().getDefaultAttributeModifiers(slot));
        Reforge reforge = Reforge.getFromStack(stack);
        if (slot == MiscHelper.getSlotForStack(stack) && reforge != null) {
            builder.merge(reforge.applyModifiers(self().getRarity(stack)), AttributeModifier.Operation.ADDITION);
        }
        GemstoneHelper.getCapability(stack, iGemstoneHandler ->
                builder.merge(CollectionHelper.fromMap(iGemstoneHandler.getAttributeModifiers(slot, stack)))
        );
        CapabilityHelper.exeCapability(stack, CapabilityHelper.ELYTRA, data1 -> {
            ElytraData data = data1.getData();
            if (data == ElytraData.GRAVITY_BOOST && slot == EquipmentSlot.CHEST)
                builder.add(ForgeMod.ENTITY_GRAVITY.get(), AttributeHelper.createModifier("ElytraGravityBoost", AttributeModifier.Operation.MULTIPLY_TOTAL, data1.getLevel() * -0.2));
        });
        if (self() instanceof IStarAbleItem) {
            builder.update(value -> IStarAbleItem.modifyData(stack, value));
        }
        builder.merge(StatBoostEnchantment.getAllModifiers(stack, slot));
        if (self() instanceof ITieredItem) {
            builder.mulAll(ITieredItem.getTier(stack).getValueMul() + 1);
        }
        return builder.build();
    }

    /**
     * @author Kapitencraft
     * @reason spell item
     */
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(@NotNull Level level, Player player, @NotNull InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {

        CompoundTag tag = player.getPersistentData();
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() instanceof ISpellItem item) {
            if (item.getSlotAmount() > 1) {
                tag.putString(SPELL_EXE, tag.getString(SPELL_EXE) + "1");
                TextHelper.sendTitle(player, Component.literal(Spells.getPattern(tag.getString(SPELL_EXE))));
                if (item.getClosestSpell(tag.getString(SPELL_EXE)) != null) {
                    TextHelper.sendSubTitle(player, Component.literal( "§6\u27A4 " + item.getClosestSpell(tag.getString(SPELL_EXE)).getName()));
                }
                tag.putByte(SPELL_EXECUTION_DUR, (byte) 20);
                cir.setReturnValue(InteractionResultHolder.consume(itemstack));
            } else {
                if (item.getActiveSpell(itemstack).getType() == Spells.Type.RELEASE) {
                    if (item.handleActiveMana(player, itemstack)) {
                        cir.setReturnValue(InteractionResultHolder.consume(itemstack));
                    }
                } else {
                    player.startUsingItem(hand);
                    cir.setReturnValue(InteractionResultHolder.consume(itemstack));
                }
            }
        }
    }

    /**
     * @author Kapitencraft
     * @reason spell items
     */
    @Inject(method = "getUseDuration", at = @At("HEAD"), cancellable = true)
    public void getUseDuration(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem() instanceof ISpellItem spellItem) {
            cir.setReturnValue(spellItem.getItemUseDuration(stack));
        }
    }

    /**
     * @author Kapitencraft
     * @reason spell usage
     */
    @Inject(method = "onUseTick", at = @At("HEAD"))
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity user, @NotNull ItemStack stack, int count, CallbackInfo info) {
        if (self() instanceof ISpellItem spellItem) {
            Spell spell = spellItem.getActiveSpell(stack);
            if (spell.getType() == Spells.Type.CYCLE && (Integer.MAX_VALUE - count & 2) == 0) {
                spellItem.handleMana(user, spell, stack);
            }
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity living) {
        if (self() instanceof ISpellItem spellItem && living instanceof Player player && spellItem.getSlotAmount() > 1) {
            CompoundTag tag = living.getPersistentData();
            tag.putString(SPELL_EXE, tag.getString(SPELL_EXE) + "0");
            TextHelper.sendTitle(player, Component.literal(Spells.getPattern(tag.getString(SPELL_EXE))));
            if (spellItem.getClosestSpell(tag.getString(SPELL_EXE)) != null) {
                TextHelper.sendSubTitle(player, Component.literal("§6\u27A4 " + spellItem.getClosestSpell(tag.getString(SPELL_EXE)).getName()));
            }
            tag.putByte(SPELL_EXECUTION_DUR, (byte) 20);
            return true;
        }
        return false;
    }

    /**
     * @author Kapitencraft
     * @reason changes to Rarity
     */
    @Overwrite
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return MiscHelper.getFinalRarity(rarity, stack);
    }
}