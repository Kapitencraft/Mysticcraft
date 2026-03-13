package net.kapitencraft.mysticcraft.rpg.perks;

import net.minecraft.core.Holder;

public class PerkInstance {
    private final Holder<Perk> perkHolder;
    private int xp, level;

    public PerkInstance(Holder<Perk> perkHolder) {
        this.perkHolder = perkHolder;
    }

    public void rewardXp(int xp) {

    }
}
