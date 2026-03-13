package net.kapitencraft.mysticcraft.util;

import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystalBlock;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneSeedBlock;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.item.combat.armor.TieredArmorItem;
import net.kapitencraft.mysticcraft.item.combat.shield.ModShieldItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ModBowItem;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Map;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeBow(ModItems.LONGBOW);
        createArmor(ModItems.ENDER_KNIGHT_ARMOR);
        registerBlocking(ModItems.IRON_SHIELD);
        registerBlocking(ModItems.GOLDEN_SHIELD);
        makeTieredArmor(ModItems.CRIMSON_ARMOR);
        makeTieredArmor(ModItems.SOUL_MAGE_ARMOR);
        ItemProperties.register(ModItems.GEMSTONE.get(), MysticcraftMod.res("rarity"), (stack, level, living, timeLeft) -> {
            GemstoneType.Rarity rarity = IGemstoneItem.getGemRarity(stack);
            return rarity.ordinal() * .1f;
        });
        ItemProperties.register(ModBlocks.GEMSTONE_CRYSTAL.getItem(), MysticcraftMod.res("size"), (stack, level, living, timeLeft) -> {
            GemstoneCrystalBlock.Size size = stack.getOrDefault(ModDataComponentTypes.GEMSTONE_CRYSTAL_SIZE, GemstoneCrystalBlock.Size.SMALL);
            return (size.ordinal() + 1) * .1f;
        });
        ItemProperties.register(ModBlocks.GEMSTONE_SEED.getItem(), MysticcraftMod.res("material"), (pStack, pLevel, pEntity, pSeed) -> {
            GemstoneSeedBlock.MaterialType type = GemstoneSeedBlock.getType(pStack);
            return (type.ordinal() + 1) * .1f;
        });
    }

    private static void makeBow(DeferredItem<? extends ModBowItem> bowItem) {
        ModBowItem item = bowItem.get();
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (itemStack, clientLevel, living, timeLeft) -> living != null ? (living.getUseItem() != itemStack ? 0.0F : (float)((itemStack.getUseDuration(living) - living.getUseItemRemainingTicks()) / (item.getDivider() * (1 / (living.getAttributeValue(ExtraAttributes.DRAW_SPEED) / 100))))) : 0);
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("loaded"), (itemStack, clientLevel, living, p_174679_) -> living != null ? (living.getProjectile(new ItemStack(item)) != ItemStack.EMPTY && living.getProjectile(itemStack).getItem() instanceof ArrowItem ? 1.0f : 0.0f) : 0);
    }

    private static <T extends TieredArmorItem> void makeTieredArmor(Map<ArmorItem.Type, DeferredItem<T>> armorItem) {
        for (TieredArmorItem tieredItem : armorItem.values().stream().map(DeferredItem::get).toList()) {
            ItemProperties.register(tieredItem, MysticcraftMod.res("tier"), (stack, clientLevel, living, tick) -> {
                TieredArmorItem armorItem1 = (TieredArmorItem) stack.getItem();
                List<ITieredItem.ItemTier> tiers = armorItem1.getAvailableTiers();
                return tiers.indexOf(ITieredItem.getTier(stack)) * 1f / tiers.size();
            });
        }
    }

    private static void createArmor(Map<ArmorItem.Type, ? extends DeferredItem<? extends AbstractArmorItem>> armor) {
        for (DeferredItem<? extends AbstractArmorItem> registryObject : armor.values()) {
            Item armorItem = registryObject.get();
            ItemProperties.register(armorItem, ResourceLocation.withDefaultNamespace("dimension"), ((stack, level, living, i) -> {
                if (living == null) return 0;
                ResourceKey<Level> dimension = living.level().dimension();
                if (dimension == Level.END) {
                    return 2;
                } else if (dimension == Level.NETHER) {
                    return 1;
                } else {
                    return 0;
                }
            }));
        }
    }

    private static void registerBlocking(DeferredItem<? extends ModShieldItem> registryObject) {
        ModShieldItem shieldItem = registryObject.get();
        ItemProperties.register(shieldItem, ResourceLocation.withDefaultNamespace("blocking"), (p_174590_, p_174591_, p_174592_, p_174593_) -> p_174592_ != null && p_174592_.isUsingItem() && p_174592_.getUseItem() == p_174590_ ? 1.0F : 0.0F);
    }
}
