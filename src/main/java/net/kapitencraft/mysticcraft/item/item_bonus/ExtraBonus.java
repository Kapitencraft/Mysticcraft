package net.kapitencraft.mysticcraft.item.item_bonus;

public abstract class ExtraBonus implements Bonus {
    private final String name;
    protected ExtraBonus(String name) {
        this.name = name;
    }

    @Override
    public String getSuperName() {
        return "Extra";
    }

    @Override
    public String getName() {
        return name;
    }
}
