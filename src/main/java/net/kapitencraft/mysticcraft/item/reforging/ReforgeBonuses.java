package net.kapitencraft.mysticcraft.item.reforging;

import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.kapitencraft.mysticcraft.item.reforging.bonuses.SacredBonus;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.SaveAbleEnum;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum ReforgeBonuses implements SaveAbleEnum {
    EMPTY_BONUS(()-> new ReforgingBonus("Empty") {
        @Override
        public Consumer<List<Component>> getDisplay() {
            return list -> list.add(Component.literal("empty"));
        }
    }, "empty"),
    SACRED_BONUS(SacredBonus::new, "sacred");


    private final ReforgingBonus bonus;
    private final String name;

    ReforgeBonuses(Supplier<ReforgingBonus> bonus, String name) {
        this.bonus = bonus.get();
        this.name = name;
    }

    public static ReforgeBonuses byName(String name) {
        return SaveAbleEnum.getValue(EMPTY_BONUS, name, values());
    }

    public static ReforgeBonuses byBonus(ReforgingBonus bonus) {
        return MiscUtils.getValue(ReforgeBonuses::getBonus, EMPTY_BONUS, bonus, values());
    }

    public ReforgingBonus getBonus() {
        return bonus;
    }

    @Override
    public String getName() {
        return name;
    }
}
