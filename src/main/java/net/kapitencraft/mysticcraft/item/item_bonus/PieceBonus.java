package net.kapitencraft.mysticcraft.item.item_bonus;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;

public abstract class PieceBonus implements Bonus {
    private final String name;

    public PieceBonus(String name) {
        this.name = name;
    }

    @Override
    public String getSuperName() {
        return "Piece";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("Piece Bonus: " + this.name).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
    }
}