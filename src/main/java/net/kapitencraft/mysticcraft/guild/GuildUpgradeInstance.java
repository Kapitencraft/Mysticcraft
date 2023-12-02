package net.kapitencraft.mysticcraft.guild;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class GuildUpgradeInstance {
    private static final Codec<Map<GuildUpgrades, Integer>> VALUE_CODEC = Codec.unboundedMap(GuildUpgrades.CODEC, Codec.INT);
    public static final Codec<GuildUpgradeInstance> CODEC = RecordCodecBuilder.create(guildUpgradeInstanceInstance ->
            guildUpgradeInstanceInstance.group(
                    VALUE_CODEC.fieldOf("content").forGetter(GuildUpgradeInstance::getUpgrades)
            ).apply(guildUpgradeInstanceInstance, GuildUpgradeInstance::new)
    );

    private final Map<GuildUpgrades, Integer> upgrades = new HashMap<>();

    private GuildUpgradeInstance(Map<GuildUpgrades, Integer> map) {
        upgrades.putAll(map);
    }

    GuildUpgradeInstance() {}

    public boolean upgrade(GuildUpgrades toUpgrade) {
        int value = upgrades.get(toUpgrade);
        if (value < toUpgrade.getMaxLevel()) {
            upgrades.remove(toUpgrade);
            upgrades.put(toUpgrade, value + 1);
            return true;
        }
        return false;
    }

    public Map<GuildUpgrades, Integer> getUpgrades() {
        return upgrades;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        upgrades.keySet().forEach(guildUpgrade -> tag.putInt(guildUpgrade.getName(), upgrades.get(guildUpgrade)));
        return tag;
    }

    public int getUpgradeLevel(GuildUpgrades upgrade) {
        return upgrades.get(upgrade);
    }

    public static GuildUpgradeInstance load(CompoundTag tag) {
        GuildUpgradeInstance instance = new GuildUpgradeInstance();
        for (GuildUpgrades upgrade : GuildUpgrades.values()) {
            if (tag.contains(upgrade.getName(), 3)) {
                instance.upgrades.put(upgrade, tag.getInt(upgrade.getName()));
            }
        }
        return instance;
    }
}
