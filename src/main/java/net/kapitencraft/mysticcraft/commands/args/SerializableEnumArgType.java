package net.kapitencraft.mysticcraft.commands.args;

import net.minecraft.util.StringRepresentable;

public class SerializableEnumArgType<T extends Enum<T> & StringRepresentable> extends SimpleEnumArgType<T> {
    protected SerializableEnumArgType(T[] elements) {
        super(StringRepresentable::getSerializedName, elements);
    }
}
