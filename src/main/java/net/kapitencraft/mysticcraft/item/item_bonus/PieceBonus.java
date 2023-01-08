package net.kapitencraft.mysticcraft.item.item_bonus;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class PieceBonus extends Bonus {

    public PieceBonus(String name) {
        super(name);
    }

    @Override
    public List<Component> getDisplay() {
        ArrayList<Component> list = new ArrayList<>();
        list.add(Component.literal("Piece Bonus: " + this.name).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        return list;
    }
}