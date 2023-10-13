package net.kapitencraft.mysticcraft.item.combat.shield;

import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.world.item.ShieldItem;

public abstract class ModShieldItem extends ShieldItem implements IModItem {
    public static final TabGroup SHIELD_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    public ModShieldItem(Properties p_43089_, int durability) {
        super(p_43089_.durability(durability));
    }

    @Override
    public TabGroup getGroup() {
        return SHIELD_GROUP;
    }
}