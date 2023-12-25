package net.kapitencraft.mysticcraft.item.data.reforging;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.data.reforging.bonuses.SacredBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum ReforgeBonuses implements StringRepresentable {
    EMPTY_BONUS(()-> new ReforgingBonus("Empty") {
        @Override
        public Consumer<List<Component>> getDisplay() {
            return list -> list.add(Component.literal("empty"));
        }
    }, "empty"),
    SACRED_BONUS(SacredBonus::new, "sacred");


    public static final EnumCodec<ReforgeBonuses> CODEC = StringRepresentable.fromEnum(ReforgeBonuses::values);

    private final ReforgingBonus bonus;
    private final String name;

    ReforgeBonuses(Supplier<ReforgingBonus> bonus, String name) {
        this.bonus = bonus.get();
        this.name = name;
    }

    public static ReforgeBonuses byName(String name) {
        return CODEC.byName(name, EMPTY_BONUS);
    }

    public static ReforgeBonuses byBonus(ReforgingBonus bonus) {
        return MiscHelper.getValue(ReforgeBonuses::getBonus, EMPTY_BONUS, bonus, values());
    }

    public ReforgingBonus getBonus() {
        return bonus;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
