package net.kapitencraft.mysticcraft.item.item_bonus;

public abstract class FullSetBonus implements Bonus{
    private final String name;
    protected FullSetBonus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSuperName() {
        return "Full Set";
    }
}
