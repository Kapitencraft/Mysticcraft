package net.kapitencraft.mysticcraft.item.combat.weapon.melee;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;

public class RainbowSwordItem extends ModSwordItem {
    public RainbowSwordItem() {
        super(Tiers.IRON, MiscHelper.rarity(Rarity.RARE).attributes(createAttributes(Tiers.IRON, 10, -2.4f, 25, 60)));
    }
}
