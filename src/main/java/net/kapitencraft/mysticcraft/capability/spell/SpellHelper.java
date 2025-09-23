package net.kapitencraft.mysticcraft.capability.spell;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.TextHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.requirements.RequirementManager;
import net.kapitencraft.kap_lib.requirements.type.RequirementType;
import net.kapitencraft.kap_lib.util.ManaHandler;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.event.advancement.ModCriteriaTriggers;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SpellHelper {

    static SpellCapability getCapability(ItemStack stack) {
        return CapabilityHelper.getCapability(stack, CapabilityHelper.SPELL);
    }

    @SuppressWarnings("DataFlowIssue")
    static @Nullable BlockPos getBlockTarget(Player entity) {
        if (!entity.isUsingItem()) return null;
        ItemStack stack = entity.getUseItem();
        if (!(stack.getItem() instanceof SpellItem)) return null;
        Spell spell = getActiveSpell(stack);
        if (spell.getTarget().getType() != SpellTarget.Type.BLOCK) return null;
        return BlockPos.of(stack.getTag().getLong("target"));
    }

    static Spell getActiveSpell(ItemStack stack) {
        return SpellHelper.getCapability(stack).getSlot(0).getSpell();
    }


    static void setSpell(ItemStack stack, int i, Spell spell) {
        CapabilityHelper.exeCapability(stack, CapabilityHelper.SPELL, spellCapability -> spellCapability.setSlot(i, new SpellSlot(spell)));
    }

    static boolean hasSpell(ItemStack stack, Spell spell) {
        return SpellHelper.getCapability(stack).hasSpell(spell);
    }

    static boolean hasAnySpell(ItemStack stack) {
        return SpellHelper.getCapability(stack).getFirstEmpty() != 0;
    }

    static boolean canExecuteSpell(LivingEntity user, Spell spell, ItemStack stack) {
        if (user.getAttribute(ExtraAttributes.INTELLIGENCE.get()) == null || user instanceof Player player && !RequirementManager.instance.meetsRequirements(RequirementType.ITEM, stack.getItem(), player)) {
            return false;
        }
        double manaToUse = spell.getManaCostForUser(user);
        AttributeInstance manaInstance = user.getAttribute(ExtraAttributes.MANA.get());
        Cooldown cooldown = spell.getCooldown();
        if (cooldown != null && cooldown.isActive(user)) {
            if (user instanceof Player player) player.displayClientMessage(Component.translatable("spell.cast.failed.cooldown").withStyle(ChatFormatting.RED), true);
            return false;
        }
        return manaInstance != null && ManaHandler.hasMana(user, manaToUse);
    }

    @SuppressWarnings("DataFlowIssue")
    static boolean handleManaAndExecute(LivingEntity user, Spell spell, ItemStack stack) {
        if (canExecuteSpell(user, spell, stack)) {
            SpellCastContext.Builder builder = new SpellCastContext.Builder();
            builder.addParam(SpellCastContextParams.CASTER, user);
            SpellTarget.Type<?> type = spell.getTarget().getType();
            if (type == SpellTarget.Type.BLOCK) builder.addParam(SpellCastContextParams.TARGET_BLOCK, BlockPos.of(stack.getTag().getLong("target")));
            else if (type == SpellTarget.Type.ENTITY) builder.addParam(SpellCastContextParams.TARGET, user.level().getEntity(stack.getTag().getInt("target")));
            try {
                spell.cast(builder.build(user.level()));
            } catch (SpellExecutionFailedException e) {
                if (user instanceof Player player) {
                    player.displayClientMessage(Component.translatable(e.getMsg()), true);
                    return false;
                }
            }
            double manaToUse = spell.getManaCostForUser(user);

            Cooldown cooldown = spell.getCooldown();

            if (ManaHandler.consumeMana(user, manaToUse)) {
                if (user instanceof ServerPlayer player) {
                    ModCriteriaTriggers.USE_MANA.trigger(player, manaToUse);
                    if (cooldown != null) cooldown.applyCooldown(user, true);
                }
                sendUseDisplay(user, spell);
                return true;
            }
        }
        return false;
    }

    private static void sendUseDisplay(LivingEntity user, Spell spell) {
        if (user instanceof Player player) {
            double manaCost = spell.getManaCostForUser(player);
            MutableComponent visible;
            String wrappedManaUsage = TextHelper.wrapInRed("-" + manaCost + " Mana");
            if (spell.getType() == Spell.Type.RELEASE) visible = Component.translatable("spell.cast", Component.translatable(spell.getDescriptionId()), wrappedManaUsage);
            else visible = Component.translatable("spell.use", Component.translatable(spell.getDescriptionId()), wrappedManaUsage);
            TextHelper.setHotbarDisplay(player, visible.withStyle(ChatFormatting.AQUA));
        }
    }

    static void appendFullDisplay(List<Component> list, ItemStack stack, Player player) {
        Spell spell = SpellScrollItem.getSpell(stack);
        if (spell == null) return;
        list.add(Component.translatable("spell.title", Component.translatable(spell.getDescriptionId())).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        list.addAll(spell.getDescription());
        MutableComponent component = null;
        if (spell.castDuration() > 0) component = Component.translatable("cast_duration.display", MathHelper.shortRound(spell.castDuration() / 20.));
        if (spell.getCooldown() != null && player != null) {
            if (component == null)
                component = (MutableComponent) spell.getCooldown().createDisplay(player);
            else component.append(", ").append(spell.getCooldown().createDisplay(player));
        }
        if (component != null) list.add(component);
    }

    /**
     * side vector scale
     */
    @ApiStatus.Internal
    double CAST_OFFSET_SCALE = 0.31;

    static Vec3 getCastOffset(Vec2 rotationVec, boolean left) {
        Vec3 lookVec = MathHelper.calculateViewVector(rotationVec.x, rotationVec.y).scale(0.60);
        Vec3 sideOffset = MathHelper.calculateViewVector(rotationVec.x, rotationVec.y + 90).scale(left ? -CAST_OFFSET_SCALE : CAST_OFFSET_SCALE);
        return lookVec.add(sideOffset).add(0, -0.275, 0);
    }
}