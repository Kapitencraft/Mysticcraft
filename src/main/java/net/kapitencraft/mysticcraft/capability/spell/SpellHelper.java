package net.kapitencraft.mysticcraft.capability.spell;

import com.mojang.datafixers.util.Either;
import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.core.helpers.MathHelper;
import net.kapitencraft.kap_lib.core.helpers.TextHelper;
import net.kapitencraft.kap_lib.mana.ManaAttributes;
import net.kapitencraft.kap_lib.mana.ManaHandler;
import net.kapitencraft.kap_lib.requirement.RequirementManager;
import net.kapitencraft.kap_lib.requirement.type.RequirementType;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.capability.PlayerSpells;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface SpellHelper {

    static ItemSpells getSpells(ItemStack stack) {
        return stack.get(ModDataComponentTypes.ITEM_SPELLS);
    }

    static List<SpellSlot> getAvailableSpells(Player player) {
        List<SpellSlot> list = new ArrayList<>(PlayerSpells.get(player).slots());
        if (!player.getMainHandItem().isEmpty()) {
            ItemSpells capability = getSpells(player.getMainHandItem());
            if (capability != null) list.addAll(capability.slots());
        }
        return list;
    }

    static Holder<Spell> getActiveSpell(Player player) {
        return getActiveSpellSlot(player).getSpell();
    }

    static SpellSlot getActiveSpellSlot(Player player) {
        return getAvailableSpells(player).get(player.getData(ModAttachmentTypes.SELECTED_SPELL_SLOT));
    }

    static @Nullable BlockPos getBlockTarget(Player entity) {
        if (!entity.isUsingItem()) return null;
        ItemStack stack = entity.getUseItem();
        if (!(stack.getItem() instanceof SpellItem)) return null;
        Holder<Spell> spell = getActiveSpell(entity);
        if (spell.value().getTarget().getType() != SpellTarget.Type.BLOCK) return null;
        Either<BlockPos, Integer> either = stack.get(ModDataComponentTypes.SPELL_TARGET);
        return either != null ? either.left().orElse(null) : null;
    }

    static boolean isSpellTarget(ItemStack useItem, Holder<Spell> spell, Entity target) {
        if (!(useItem.getItem() instanceof SpellItem)) return false;
        if (spell.value().getTarget().getType() != SpellTarget.Type.BLOCK) return false;
        Either<BlockPos, Integer> either = useItem.get(ModDataComponentTypes.SPELL_TARGET);
        return either != null ? either.right().map(e -> e == target.getId()).orElse(false) : false;
    }

    static void setSpell(ItemStack stack, int i, SpellSlot spell) {
        stack.update(ModDataComponentTypes.ITEM_SPELLS, new ItemSpells(List.of(SpellSlot.EMPTY)), s -> s.setSlot(i, spell));
    }

    static void setSpell(ItemStack stack, int i, Holder<Spell> spell) {
        setSpell(stack, i, new SpellSlot(spell));
    }

    static boolean hasSpell(ItemStack stack, Spell spell) {
        return SpellHelper.getSpells(stack).hasSpell(spell);
    }

    static boolean hasAnySpell(ItemStack stack) {
        return stack.has(ModDataComponentTypes.ITEM_SPELLS) && SpellHelper.getSpells(stack).getFirstEmpty() != 0;
    }

    static boolean canExecuteSpell(LivingEntity user, Spell spell, ItemStack stack) {
        if (user.getAttribute(ManaAttributes.MAX_MANA) == null || user instanceof Player player && !RequirementManager.instance.meetsRequirements(RequirementType.ITEM, stack.getItem(), player)) {
            return false;
        }
        double manaToUse = spell.getManaCostForUser(user);
        Cooldown cooldown = spell.getCooldown();
        if (cooldown != null && cooldown.isActive(user)) {
            if (user instanceof Player player) player.displayClientMessage(Component.translatable("spell.cast.failed.cooldown").withStyle(ChatFormatting.RED), true);
            return false;
        }
        return ManaHandler.isMagical(user) && ManaHandler.hasMana(user, manaToUse);
    }

    @SuppressWarnings("DataFlowIssue")
    static boolean handleManaAndExecute(LivingEntity user, Holder<Spell> spellHolder, int level, ItemStack stack) {
        Spell spell = spellHolder.value();
        if (canExecuteSpell(user, spell, stack)) {
            SpellCastContext.Builder builder = new SpellCastContext.Builder();
            builder.addParam(SpellCastContextParams.CASTER, user);
            SpellTarget.Type<?> type = spell.getTarget().getType();
            Either<BlockPos, Integer> either = stack.get(ModDataComponentTypes.SPELL_TARGET);
            if (type == SpellTarget.Type.BLOCK) builder.addParam(SpellCastContextParams.TARGET_BLOCK, either.left().get());
            else if (type == SpellTarget.Type.ENTITY) builder.addParam(SpellCastContextParams.TARGET, user.level().getEntity(either.right().get()));
            try {
                spell.cast(builder.build(user.level(), level));
            } catch (SpellExecutionFailedException e) {
                if (user instanceof Player player) {
                    player.displayClientMessage(Component.translatable(e.getMsg()), true);
                    return false;
                }
            }
            double manaToUse = spell.getManaCostForUser(user);

            Cooldown cooldown = spell.getCooldown();

            if (ManaHandler.consumeMana(user, manaToUse)) {
                if (user instanceof ServerPlayer && cooldown != null) {
                    cooldown.applyCooldown(user, true);
                }
                sendUseDisplay(user, spellHolder);
                return true;
            }
        }
        return false;
    }

    private static void sendUseDisplay(LivingEntity user, Holder<Spell> spell) {
        if (user instanceof Player player) {
            double manaCost = spell.value().getManaCostForUser(player);
            MutableComponent title = Component.translatable(Util.makeDescriptionId("spell", spell.getKey().location()));
            MutableComponent visible;
            Component wrappedManaUsage = Component.literal("-" + manaCost + " Mana").withStyle(ChatFormatting.RED);
            if (spell.value().getType() == Spell.Type.RELEASE) visible = Component.translatable("spell.cast", title, wrappedManaUsage);
            else visible = Component.translatable("spell.use", title, wrappedManaUsage);
            TextHelper.setActionbar(player, visible.withStyle(ChatFormatting.AQUA));
        }
    }

    static void appendFullDisplay(List<Component> list, ItemStack stack, Player player) {
        SpellSlot spellSlot = SpellScrollItem.getSpell(stack);
        if (spellSlot == null) return;
        Holder<Spell> spellHolder = spellSlot.getSpell();
        Spell spell = spellHolder.value();
        list.add(Component.translatable("spell.title", spellSlot.description()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        list.addAll(TextHelper.getDescriptionOrEmpty(Util.makeDescriptionId("spell", spellHolder.getKey().location()), c -> c));
        MutableComponent component = null;
        if (spell.castDuration() > 0) component = Component.translatable("cast_duration.display", MathHelper.shortRound(spell.castDuration() / 20.));
        if (spell.getCooldown() != null && player != null) {
            if (component == null)
                component = (MutableComponent) spell.getCooldown().createDisplay(player, true);
            else component.append(", ").append(spell.getCooldown().createDisplay(player, true));
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