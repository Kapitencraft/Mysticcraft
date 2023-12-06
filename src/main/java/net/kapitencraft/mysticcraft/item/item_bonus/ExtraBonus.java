package net.kapitencraft.mysticcraft.item.item_bonus;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class ExtraBonus implements Bonus {

    @Override
    public String getSuperName() {
        return "";
    }


    @Override
    public List<Component> makeDisplay() {
        List<Component> list = new ArrayList<>();
        getDisplay().accept(list);
        return list;
    }

    @Override
    public String getName() {
        return "";
    }
}
