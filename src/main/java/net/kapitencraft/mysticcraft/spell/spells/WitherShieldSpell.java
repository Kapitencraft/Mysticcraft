package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.cooldown.Cooldowns;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WitherShieldSpell {

    private static final List<Component> description = List.of(Component.literal("Gain a Absorption Shield of 30% of your Crit Damage."), Component.literal("also, gain 50% of this shield as healing after 5 seconds"));
    public static final String DAMAGE_REDUCTION_TIME = "WS-DamageReductionTime";
    public static final String ABSORPTION_AMOUNT_ID = "WS-AbsorptionAmount";

    public static boolean execute(@NotNull LivingEntity user, ItemStack ignored) {
        CompoundTag tag = user.getPersistentData();
        Cooldowns.WITHER_SHIELD.applyCooldown(user, true);
        float absorption = (float) (user.getAttributeValue(ModAttributes.CRIT_DAMAGE.get()) * 0.3);
        if (tag.getFloat(ABSORPTION_AMOUNT_ID) <= 0 || !tag.contains(ABSORPTION_AMOUNT_ID)) {
            user.setAbsorptionAmount(user.getAbsorptionAmount() + absorption);
        }
        tag.putFloat(ABSORPTION_AMOUNT_ID, absorption);
        return true;
    }

    public static List<Component> getDescription() {
        return description;
    }
}
