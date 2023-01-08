package net.kapitencraft.mysticcraft.item.item_bonus.piece;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulMageHelmetBonus extends PieceBonus {
    public SoulMageHelmetBonus() {
        super("Magic Conversation");
    }

    @Override
    public List<Component> getDisplay() {
        List<Component> list = super.getDisplay();
        list.add(Component.literal("Grants " + FormattingCodes.RED + "+1 Ability Damage" + FormattingCodes.RESET + " per " + FormattingCodes.AQUA + "25 Intelligence"));
        list.add(Component.literal("you have"));
        return list;
    }

    @Override
    public void onEntityKilled(LivingEntity killed, LivingEntity user) {

    }

    @Nullable
    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(ModAttributes.ABILITY_DAMAGE.get(), new AttributeModifier("Helmet Bonus", living.getAttributeValue(ModAttributes.INTELLIGENCE.get()) / 25, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
