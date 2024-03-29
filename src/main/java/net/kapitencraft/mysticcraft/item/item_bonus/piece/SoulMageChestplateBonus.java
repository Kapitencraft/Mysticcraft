package net.kapitencraft.mysticcraft.item.item_bonus.piece;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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

public class SoulMageChestplateBonus extends PieceBonus {

    private static final UUID CHESTPLATE_BONUS_ID = UUID.fromString("95d00b68-2e46-47b7-b636-450674d3a3ba");
    public SoulMageChestplateBonus() {
        super("More Mana!");
    }

    @Nullable
    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        builder.put(ModAttributes.MAX_MANA.get(), new AttributeModifier(CHESTPLATE_BONUS_ID, "Chestplate Mod", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return builder;
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("Increases your maximum Mana by 20%"));
    }
}
