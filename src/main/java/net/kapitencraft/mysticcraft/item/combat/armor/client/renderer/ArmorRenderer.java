package net.kapitencraft.mysticcraft.item.combat.armor.client.renderer;

import net.kapitencraft.mysticcraft.item.combat.armor.client.model.ArmorModel;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Provider;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

public class ArmorRenderer<T extends ArmorModel> {
    private final T model;
    public ArmorRenderer(Supplier<LayerDefinition> supplier, Provider<T, ModelPart> modelConstructor) {
        this.model = modelConstructor.provide(supplier.get().bakeRoot());
    }

    public ArmorRenderer<T> makeInvisible(boolean invisible) {
        this.model.makeInvisible(invisible);
        return this;
    }

    private static final ModelPart EMPTY_PART = new ModelPart(Collections.emptyList(), Collections.emptyMap());

    public ModelPart makeArmorParts(EquipmentSlot slot) {
        return switch (slot) {
            case FEET -> new ModelPart(Collections.emptyList(),
                    Map.of("head", EMPTY_PART,
                            "hat", EMPTY_PART,
                            "body", EMPTY_PART,
                            "right_arm", EMPTY_PART,
                            "left_arm", EMPTY_PART,
                            "right_leg", checkNonNull(model.armorRightBoot),
                            "left_leg", checkNonNull(model.armorLeftBoot)
                    )
            );
            case LEGS -> new ModelPart(Collections.emptyList(),
                    Map.of("head", EMPTY_PART,
                            "hat", EMPTY_PART,
                            "body", EMPTY_PART,
                            "right_arm", EMPTY_PART,
                            "left_arm", EMPTY_PART,
                            "right_leg", checkNonNull(model.armorRightLeg),
                            "left_leg", checkNonNull(model.armorLeftLeg)
                    )
            );
            case CHEST -> new ModelPart(Collections.emptyList(),
                    Map.of("head", EMPTY_PART,
                            "hat", EMPTY_PART,
                            "body", checkNonNull(model.armorChest),
                            "right_arm", checkNonNull(model.armorRightArm),
                            "left_arm", checkNonNull(model.armorLeftArm),
                            "right_leg", EMPTY_PART,
                            "left_leg", EMPTY_PART
                    )
            );
            case HEAD -> new ModelPart(Collections.emptyList(),
                    Map.of("head", checkNonNull(model.armorHead),
                            "hat", EMPTY_PART,
                            "body", EMPTY_PART,
                            "right_arm", EMPTY_PART,
                            "left_arm", EMPTY_PART,
                            "right_leg", EMPTY_PART,
                            "left_leg", EMPTY_PART)
            );
            default -> EMPTY_PART;
        };
    }
    private static @NotNull ModelPart checkNonNull(@Nullable ModelPart part) {
        return part == null ? EMPTY_PART : part;
    }
}
