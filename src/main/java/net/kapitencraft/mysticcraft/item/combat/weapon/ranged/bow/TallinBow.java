package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.util.ExtraRarities;

public class TallinBow extends ShortBowItem {
    public TallinBow() {
        super(MiscHelper.rarity(ExtraRarities.LEGENDARY).durability(1500).attributes(createAttributes(3)));
    }

    @Override
    public int getKB() {
        return 1;
    }

    @Override
    public float getShotCooldown() {
        return 0.3f;
    }
}
