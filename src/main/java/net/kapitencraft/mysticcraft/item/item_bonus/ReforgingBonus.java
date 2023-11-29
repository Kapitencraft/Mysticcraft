package net.kapitencraft.mysticcraft.item.item_bonus;

public abstract class ReforgingBonus implements Bonus {
    private final String name;
    protected ReforgingBonus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSuperName() {
        return "Reforge";
    }
}