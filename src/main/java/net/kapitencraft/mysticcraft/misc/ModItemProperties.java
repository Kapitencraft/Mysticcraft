package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystal;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneSeedBlock;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.item.combat.armor.TieredArmorItem;
import net.kapitencraft.mysticcraft.item.combat.shield.ModShieldItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.QuiverItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ModBowItem;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeBow(ModItems.LONGBOW);
        createArmor(ModItems.ENDER_KNIGHT_ARMOR);
        registerBlocking(ModItems.IRON_SHIELD);
        registerBlocking(ModItems.GOLDEN_SHIELD);
        makeQuiver(ModItems.AMETHYST_QUIVER);
        makeTieredArmor(ModItems.CRIMSON_ARMOR);
        makeTieredArmor(ModItems.SOUL_MAGE_ARMOR);
        ItemProperties.register(ModItems.GEMSTONE.get(), MysticcraftMod.res("rarity"), (stack, level, living, timeLeft) -> {
            GemstoneType.Rarity rarity = IGemstoneItem.getGemRarity(stack);
            return switch (rarity) {
                case ROUGH -> 0.1f;
                case FLAWED -> 0.2f;
                case FINE -> 0.3f;
                case FLAWLESS -> 0.4f;
                case PERFECT -> 0.5f;
                default -> 0;
            };
        });
        ItemProperties.register(ModBlocks.GEMSTONE_CRYSTAL.getItem(), MysticcraftMod.res("size"), (stack, level, living, timeLeft) -> {
            GemstoneCrystal.Size size = GemstoneCrystal.Size.CODEC.byName(stack.getOrCreateTag().getString("Size"), GemstoneCrystal.Size.SMALL);
            return (size.ordinal() + 1) * .1f;
        });
        ItemProperties.register(ModBlocks.GEMSTONE_SEED.getItem(), MysticcraftMod.res("material"), (pStack, pLevel, pEntity, pSeed) -> {
            GemstoneSeedBlock.MaterialType type = GemstoneSeedBlock.getType(pStack);
            return (type.ordinal() + 1) * .1f;
        });
    }

    private static void makeBow(RegistryObject<? extends ModBowItem> bowItem) {
        ModBowItem item = bowItem.get();
        ItemProperties.register(item, new ResourceLocation("pull"), (itemStack, clientLevel, living, timeLeft) -> living != null ? (living.getUseItem() != itemStack ? 0.0F : (float)((itemStack.getUseDuration() - living.getUseItemRemainingTicks()) / (item.getDivider() * (1 / (living.getAttributeValue(ExtraAttributes.DRAW_SPEED.get()) / 100))))) : 0);
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);
        ItemProperties.register(item, new ResourceLocation("loaded"), (itemStack, clientLevel, living, p_174679_) -> living != null ? (living.getProjectile(new ItemStack(item)) != ItemStack.EMPTY && living.getProjectile(itemStack).getItem() instanceof ArrowItem ? 1.0f : 0.0f) : 0);
    }

    private static <T extends TieredArmorItem> void makeTieredArmor(Map<ArmorItem.Type, RegistryObject<T>> armorItem) {
        for (TieredArmorItem tieredItem : armorItem.values().stream().map(RegistryObject::get).toList()) {
            ItemProperties.register(tieredItem, MysticcraftMod.res("tier"), (stack, clientLevel, living, tick) -> {
                TieredArmorItem armorItem1 = (TieredArmorItem) stack.getItem();
                List<ITieredItem.ItemTier> tiers = armorItem1.getAvailableTiers();
                return tiers.indexOf(ITieredItem.getTier(stack)) * 1f / tiers.size();
            });
        }
    }

    private static void makeQuiver(RegistryObject<? extends QuiverItem> quiver) {
        QuiverItem quiverItem = quiver.get();
        ItemProperties.register(quiverItem, new ResourceLocation("filled"), (itemStack, clientLevel, living, timeLeft) -> {
            QuiverItem item = (QuiverItem) itemStack.getItem();
            return item.getUsedCapacity(itemStack) * 1f / (item.getCapacity(itemStack));
         });
    }

    private static void createArmor(Map<ArmorItem.Type, ? extends RegistryObject<? extends AbstractArmorItem>> armor) {
        for (RegistryObject<? extends AbstractArmorItem> registryObject : armor.values()) {
            Item armorItem = registryObject.get();
            ItemProperties.register(armorItem, new ResourceLocation("dimension"), ((stack, level, living, i) -> {
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

    private static void registerBlocking(RegistryObject<? extends ModShieldItem> registryObject) {
        ModShieldItem shieldItem = registryObject.get();
        ItemProperties.register(shieldItem, new ResourceLocation("blocking"), (p_174590_, p_174591_, p_174592_, p_174593_) -> p_174592_ != null && p_174592_.isUsingItem() && p_174592_.getUseItem() == p_174590_ ? 1.0F : 0.0F);
    }
}
