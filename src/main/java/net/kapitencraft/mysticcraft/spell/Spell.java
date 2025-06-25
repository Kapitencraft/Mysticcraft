package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.TextHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.cooldown.Cooldowns;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.Item;

import java.util.List;

public interface Spell {

    void cast(SpellCastContext context) throws SpellExecutionFailedException;

    double manaCost();

    Type getType();

    int getCooldownTime();

    default Cooldown getCooldown() {
        return Cooldowns.SPELLS.getOrCreate(this, getCooldownTime());
    }

    default List<Component> getDescription() {
        return TextHelper.getDescriptionOrEmpty(this.getDescriptionId(), null);
    }

    default double getManaCostForUser(LivingEntity user) {
        AttributeInstance instance = user.getAttribute(ExtraAttributes.MANA_COST.get());
        return MathHelper.defRound(AttributeHelper.getAttributeValue(instance, this.manaCost()));
    }

    default String getDescriptionId() {
        ResourceLocation location = ModRegistries.SPELLS.getKey(this);
        if (location == null) throw new IllegalArgumentException("unknown spell: " + this);
        return "spell." + location.getNamespace() + "." + location.getPath();
    }

    enum Type {
        RELEASE,
        CYCLE
    }

    boolean canApply(Item item);
}