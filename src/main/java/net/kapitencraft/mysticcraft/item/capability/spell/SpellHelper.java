package net.kapitencraft.mysticcraft.item.capability.spell;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.TextHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.requirements.RequirementManager;
import net.kapitencraft.kap_lib.requirements.type.RequirementType;
import net.kapitencraft.kap_lib.util.ManaHandler;
import net.kapitencraft.mysticcraft.event.advancement.ModCriteriaTriggers;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.ChatFormatting;
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

import java.util.List;

public interface SpellHelper {

    static int getItemUseDuration(ItemStack stack) {
        SpellCapability capability = SpellHelper.getCapability(stack);
        Spell spell = capability.getSlot(0).getSpell();
        return spell == null ? -1 : spell.getType() == Spell.Type.RELEASE ? 2 : Integer.MAX_VALUE;
    }

    static SpellCapability getCapability(ItemStack stack) {
        return CapabilityHelper.getCapability(stack, CapabilityHelper.SPELL);
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

    static boolean handleManaAndExecute(LivingEntity user, Spell spell, ItemStack stack) {
        if (user.getAttribute(ExtraAttributes.INTELLIGENCE.get()) == null || user instanceof Player player && !RequirementManager.instance.meetsRequirements(RequirementType.ITEM, stack.getItem(), player)) {
            return false;
        }
        double manaToUse = spell.getManaCostForUser(user);
        if (user instanceof ServerPlayer player) {
            ModCriteriaTriggers.USE_MANA.trigger(player, manaToUse);
        }
        AttributeInstance manaInstance = user.getAttribute(ExtraAttributes.MANA.get());
        Cooldown cooldown = spell.getCooldown();
        boolean hasCooldown = cooldown != null;
        if (!hasCooldown || !cooldown.isActive(user) && manaInstance != null && ManaHandler.hasMana(user, manaToUse)) {
            SpellCastContext.Builder builder = new SpellCastContext.Builder();
            builder.addParam(SpellCastContextParams.CASTER, user);
            builder.addParam(SpellCastContextParams.WAND, stack);
            try {
                spell.cast(builder.build());
            } catch (SpellExecutionFailedException e) {
                if (user instanceof Player player) {
                    player.displayClientMessage(Component.translatable(e.getMsg()), true);
                    return false;
                }
            }

            if (ManaHandler.consumeMana(user, manaToUse)) {
                if (cooldown != null) cooldown.applyCooldown(user, true);
                sendUseDisplay(user, spell);

                //List<Element> elements = spell.elements(); //TODO
                //if (!elements.isEmpty()) {//spawn spell shards
                //    ItemStack spellShardRNG = new ItemStack(ModItems.ELEMENTAL_SHARDS.get(MathHelper.pickRandom(elements)).get());
                //    RNGHelper.calculateAndDrop(spellShardRNG, 0.00002f, user, user.position());
                //}
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

    static void appendFullDisplay(List<Component> list, ItemStack stack) {
        Spell spell = SpellScrollItem.getSpell(stack);
        if (spell == null) return;
        list.add(Component.translatable("spell.title", Component.translatable(spell.getDescriptionId())).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        list.addAll(spell.getDescription());
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