package net.kapitencraft.mysticcraft.bestiary;

import net.kapitencraft.mysticcraft.api.SaveAbleEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class Bestiary {

    final List<MutableComponent> description;
    private final double combatXp;

    private final Type type;

    public Bestiary(List<String> description, double combatXp, Type type) {
        this.description = description.stream().map(Component::translatable).toList();
        this.combatXp = combatXp;
        this.type = type;
    }

    public double getCombatXp() {
        return combatXp;
    }

    public Type getType() {
        return type;
    }

    public List<MutableComponent> getDescription() {
        return description;
    }

    public enum Type implements SaveAbleEnum {
        NORMAL("normal"),
        MINI_BOSS("mini_boss"),
        BOSS("boss");


        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        public static Type getByName(String name) {
            return SaveAbleEnum.getValue(NORMAL, name, values());
        }
    }
}
