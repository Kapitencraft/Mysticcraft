package net.kapitencraft.mysticcraft.commands.args;

import net.minecraft.util.StringRepresentable;

public class SerializableEnumArg<T extends Enum<T> & StringRepresentable> extends SimpleEnumArg<T> {
    protected SerializableEnumArg(T[] elements) {
        super(StringRepresentable::getSerializedName, elements);
    }
}
