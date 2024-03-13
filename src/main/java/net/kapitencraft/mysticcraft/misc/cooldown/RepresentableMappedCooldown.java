package net.kapitencraft.mysticcraft.misc.cooldown;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;

public class RepresentableMappedCooldown<T extends StringRepresentable> extends MappedCooldown<T> {
    public RepresentableMappedCooldown(String path, Consumer<Entity> exe) {
        super(path, StringRepresentable::getSerializedName, exe);
    }
}
