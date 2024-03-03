package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.misc.ManaMain;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

public class SoulMageArmorFullSetBonus extends FullSetBonus {
    public SoulMageArmorFullSetBonus() {
        super("Mana Syphon");
    }

    @Override
    public void onEntityKilled(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {
        ManaMain.growMana(user, 10);
    }

    @Override
    public EnumMap<EquipmentSlot, @Nullable RegistryObject<? extends Item>> getReqPieces() {
        return new EnumMap<>(ModItems.SOUL_MAGE_ARMOR);
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("Regenerate 10 Mana on kill"));
    }
}
