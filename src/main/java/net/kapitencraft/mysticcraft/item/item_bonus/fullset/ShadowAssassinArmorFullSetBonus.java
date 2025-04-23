package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

public class ShadowAssassinArmorFullSetBonus extends FullSetBonus {
    private final DustParticleOptions SHADOW_OPTIONS = new DustParticleOptions(Vec3.fromRGB24(0).toVector3f(), 2);

    public ShadowAssassinArmorFullSetBonus() {
        super("Shadow Assassin");
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> {
            list.add(Component.literal("makes you invisible until you sneak or get hit"));
            list.add(Component.literal("disables armor renderer as soon as you get invisible"));
            list.add(Component.literal("use with SNEAK"));
        };
    }

    @Override
    public void onTick(Level level, @NotNull LivingEntity entity) {
        CompoundTag tag = entity.getPersistentData();
        if (entity.isCrouching()) {
            if (!tag.getBoolean("isCrouching") || !tag.contains("isCrouching")) {
                entity.setInvisible(!entity.isInvisible());
                tag.putBoolean("isCrouching", true);
                tag.putBoolean("Invisible", entity.isInvisible());
                if (entity.isInvisible()) {
                    ParticleHelper.sendParticles(SHADOW_OPTIONS, false, entity, 100 , 1.5, 1.5, 1.5, 0.01);
                }
            }
        } else {
            tag.putBoolean("isCrouching", false);
        }
        if (tag.getBoolean("Invisible")) {
            entity.setInvisible(true);
        }
    }

    @Override
    public void onRemove(LivingEntity living) {
        living.setInvisible(false);
        living.getPersistentData().putBoolean("Invisible", false);
    }

    @Override
    public EnumMap<EquipmentSlot, @Nullable RegistryObject<? extends Item>> getReqPieces() {
        return new EnumMap<>(ModItems.SHADOW_ASSASSIN_ARMOR);
    }

    @Override
    public float onTakeDamage(LivingEntity hurt, LivingEntity source, MiscHelper.DamageType type, float damage) {
        hurt.getPersistentData().putBoolean("Invisible", false);
        hurt.setInvisible(false);
        return damage;
    }
}
