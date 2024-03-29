package net.kapitencraft.mysticcraft.item.item_bonus.piece;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
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
        return list -> list.addAll(List.of(Component.literal("Grants " + TextHelper.wrapInRed("+1 Ability Damage") + " per §b25 Intelligence§r"), Component.literal("you have")));
    }

    @Nullable
    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        builder.put(ModAttributes.ABILITY_DAMAGE.get(), AttributeHelper.addLiquidModifier(ID, "Soul Mage Helmet Bonus",AttributeModifier.Operation.ADDITION, (living1 -> living1.getAttributeValue(ModAttributes.INTELLIGENCE.get()) / 25), living));
        return builder;
    }
}