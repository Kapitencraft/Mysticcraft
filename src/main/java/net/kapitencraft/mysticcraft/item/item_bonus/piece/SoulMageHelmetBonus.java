package net.kapitencraft.mysticcraft.item.item_bonus.piece;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.utils.AttributeUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class SoulMageHelmetBonus extends PieceBonus {
    public SoulMageHelmetBonus() {
        super("Magic Conversation");
    }
    private static final UUID ID = UUID.fromString("fedb2655-2723-4fb9-b744-0e37a4988dbb");

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.addAll(List.of(Component.literal("Grants " + FormattingCodes.RED + "+1 Ability Damage" + FormattingCodes.RESET + " per " + FormattingCodes.AQUA + "25 Intelligence"), Component.literal("you have")));
    }

    @Nullable
    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(ModAttributes.ABILITY_DAMAGE.get(), AttributeUtils.addLiquidModifier(ID, "Soul Mage Helmet Bonus",AttributeModifier.Operation.ADDITION, (living1 -> living1.getAttributeValue(ModAttributes.INTELLIGENCE.get()) / 25), living));
        return builder.build();
    }
}