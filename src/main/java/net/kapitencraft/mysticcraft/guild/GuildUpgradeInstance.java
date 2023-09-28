package net.kapitencraft.mysticcraft.guild;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;

public class GuildUpgradeInstance {

    private final HashMap<GuildUpgrade, Integer> upgrades = new HashMap<>();

    public boolean upgrade(GuildUpgrade toUpgrade) {
        int value = upgrades.get(toUpgrade);
        if (value < toUpgrade.getMaxLevel()) {
            upgrades.remove(toUpgrade);
            upgrades.put(toUpgrade, value + 1);
            return true;
        }
        return false;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        upgrades.keySet().forEach(guildUpgrade -> tag.putInt(guildUpgrade.getName(), upgrades.get(guildUpgrade)));
        return tag;
    }

    public int getUpgradeLevel(GuildUpgrade upgrade) {
        return upgrades.get(upgrade);
    }

    public static GuildUpgradeInstance load(CompoundTag tag) {
        GuildUpgradeInstance instance = new GuildUpgradeInstance();
        for (GuildUpgrade upgrade : GuildUpgrades.values()) {
            if (tag.contains(upgrade.getName(), 3)) {
                instance.upgrades.put(upgrade, tag.getInt(upgrade.getName()));
            }
        }
        return instance;
    }
}
