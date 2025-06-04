package net.kapitencraft.mysticcraft.item.bonus;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MagicConversionBonus implements Bonus<MagicConversionBonus> {
    public static final DataPackSerializer<MagicConversionBonus> SERIALIZER = DataPackSerializer.unit(MagicConversionBonus::new);
    private static final UUID ID = UUID.fromString("fedb2655-2723-4fb9-b744-0e37a4988dbb");

    @Override
    public DataPackSerializer<MagicConversionBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @Nullable Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        builder.put(ExtraAttributes.ABILITY_DAMAGE.get(), AttributeHelper.addLiquidModifier(ID, "Soul Mage Helmet Bonus", AttributeModifier.Operation.ADDITION, (living1 -> living1.getAttributeValue(ExtraAttributes.INTELLIGENCE.get()) / 25), living));
        return builder;
    }
}
