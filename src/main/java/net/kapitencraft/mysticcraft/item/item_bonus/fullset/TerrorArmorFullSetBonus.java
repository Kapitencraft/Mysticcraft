package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.misc.attribute.ChangingAttributeModifier;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class TerrorArmorFullSetBonus extends KillIncreasingStackFullSetBonus {
    private static final UUID MODIFIER_UUID = UUID.fromString("fa581ef5-92dd-43e9-95f1-b4b8f949f139");
    private static final String TWO_ARROWS = "SpawnExtraArrows";

    public TerrorArmorFullSetBonus() {
        super("Hydra", damageType -> MiscHelper.DamageType.RANGED == damageType, ModAttributes.ARROW_SPEED, 1f, 120);
    }

    @Override
    public void onApply(LivingEntity living) {
        AttributeInstance instance = living.getAttribute(ModAttributes.ARROW_COUNT.get());
        if (instance != null) {
            instance.addTransientModifier(new ChangingAttributeModifier(MODIFIER_UUID, "TerrorArmorFullSetBonus", AttributeModifier.Operation.ADDITION, living, living1 -> living1.getPersistentData().getBoolean(TWO_ARROWS) ? 2. : 0));
        }
    }

    @Override
    public void onRemove(LivingEntity living) {
        AttributeInstance instance = living.getAttribute(ModAttributes.ARROW_COUNT.get());
        if (instance != null) {
            instance.removeModifier(MODIFIER_UUID);
        }
    }

    @Override
    protected void killWhen10(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {
        user.getPersistentData().putBoolean(TWO_ARROWS, true);
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return components -> components.addAll(
                List.of(
                    Component.literal("Shooting Arrows gives you on Stack of §6Hydra§r"),
                    CommonComponents.EMPTY,
                    Component.literal("every stack grants +1 arrow speed"),
                    CommonComponents.EMPTY,
                    Component.literal("when reaching 10 Stacks, you shoot 2 extra arrows with your next shot")
                )
        );
    }

    @Override
    public EnumMap<EquipmentSlot, @Nullable RegistryObject<? extends Item>> getReqPieces() {
        return new EnumMap<>(ModItems.TERROR_ARMOR);
    }
}
