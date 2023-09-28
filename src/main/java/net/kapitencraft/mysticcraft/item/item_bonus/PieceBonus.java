package net.kapitencraft.mysticcraft.item.item_bonus;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;

public abstract class PieceBonus extends Bonus {

    public PieceBonus(String name) {
        super(name, "Piece");
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("Piece Bonus: " + this.name).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
    }
}