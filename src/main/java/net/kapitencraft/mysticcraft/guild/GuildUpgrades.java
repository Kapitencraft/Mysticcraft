package net.kapitencraft.mysticcraft.guild;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.material.GuildUpgradeItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;

public enum GuildUpgrades implements GuildUpgrade {
    RANGE("range", UpgradeRarity.COMMON, 3, new ItemStack(Items.GUNPOWDER), 14),
    TELEPORT_SAFETY("teleport_safety", UpgradeRarity.EPIC, 1, new ItemStack(Items.ENDER_EYE).copyWithCount(10), 25);

    private static final TabGroup GUILD_GROUP = new TabGroup(TabRegister.TabTypes.MOD_MATERIALS);

    private final String name;
    private final UpgradeRarity rarity;

    private final int maxLevel;

    private final ItemStack defaultItem;

    private final int emeraldCost;

    GuildUpgrades(String name, UpgradeRarity rarity, int maxLevel, ItemStack defaultItem, int emeraldCost) {
        this.name = name;
        this.rarity = rarity;
        this.maxLevel = maxLevel;
        this.defaultItem = defaultItem;
        this.emeraldCost = emeraldCost;
    }


    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        tag.putString("rarity", rarity.getName());
        return tag;
    }

    public static HashMap<GuildUpgrade, RegistryObject<GuildUpgradeItem>> createRegistry() {
        return ModItems.createRegistry(GuildUpgradeItem::new, GuildUpgrade::makeRegistryName, List.of(values()), GUILD_GROUP);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String makeRegistryName() {
        return "guild_upgrade." + name;
    }

    @Override
    public UpgradeRarity getRarity() {
        return rarity;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public ItemStack mainCostItem() {
        return null;
    }

    @Override
    public int defaultCost() {
        return 0;
    }
}
