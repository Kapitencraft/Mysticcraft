package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.world.item.Tier;

public class DoubleSword extends ModSwordItem {
    public static final TabGroup DOUBLE_SWORD_GROUP = TabGroup.create(ModCreativeModTabs.WEAPONS_AND_TOOLS);;
    public DoubleSword(Tier tier, Properties properties, int damage, int strength, int critDamage) {
        super(tier, properties.attributes(createAttributes(tier, damage, -2.1f, strength, critDamage)));
    }
}
