package net.kapitencraft.mysticcraft.rpg.perks;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.item.bonus.Bonus;

public class Perk {
    //TODO figure out what tf to put here
    public static final Codec<Perk> DIRECT_CODEC = Bonus.CODEC.xmap(Perk::new, Perk::getBonus);
}
