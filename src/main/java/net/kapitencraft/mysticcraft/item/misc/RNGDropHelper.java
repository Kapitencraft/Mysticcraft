package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RNGDropHelper {



    public static ItemEntity calculateAndDrop(ItemStack stack, float chance, LivingEntity source, Vec3 spawnPos) {
        double magicFind = getMagicFind(source);
        if (Math.random() <= getFinalChance(chance, magicFind)) {
            trySendDropMessage(stack, magicFind, source, chance);
            if (source instanceof Player player && player.getMainHandItem().getEnchantmentLevel(ModEnchantments.TELEKINESIS.get()) > 0){
                player.getInventory().add(stack);
            } else {
                ItemEntity entity = new ItemEntity(source.level, spawnPos.x, spawnPos.y, spawnPos.z, stack);
                source.level.addFreshEntity(entity);
                return entity;
            }
        }
        return null;
    }

    private static double getFinalChance(float chance, double magicFind) {
        return chance * (1 + magicFind / 100);
    }

    private static double getMagicFind(@Nullable LivingEntity source) {
        return (source != null) ? AttributeHelper.getSaveAttributeValue(ModAttributes.MAGIC_FIND.get(), source) : 0;
    }

    public static ItemStack calculateAndDontDrop(Item item, int maxAmount, @Nullable LivingEntity source, float chance) {
        double magicFind = getMagicFind(source);
        int amount = 0;
        while (chance > 1) {
            amount++;
            chance -= 1;
        }
        for (int i = 0; i < maxAmount; i++) {
            if (Math.random() <= getFinalChance(chance, magicFind)) {
                amount++;
            }
        }
        return finalize(item, amount, magicFind, chance, source);
    }

    private static ItemStack finalize(Item item, int amount, double magicFind, float chance, LivingEntity source) {
        if (amount > 0) {
            ItemStack stack = new ItemStack(item, amount);
            trySendDropMessage(stack, magicFind, source, chance);
            return stack;
        }
        return ItemStack.EMPTY;
    }


    private static Component createRareDropMessage(ItemStack drop, double magic_find, float chance) {
        return DropRarities.getRarity(chance).makeDisplay().append("§l: §r").append(drop.getHoverName()).append("§b (+" + magic_find + " Magic Find)");
    }

    public static void trySendDropMessage(ItemStack drop, double magicFind, LivingEntity toSend, float chance) {
        if (toSend instanceof Player player && chance < 0.2) {
            player.sendSystemMessage(createRareDropMessage(drop, magicFind, chance));
        }
    }

    private interface DropRarity {
        float getMaxChance();
        String getTranslateKey();
    }

    public enum DropRarities implements DropRarity {
        RNGESUS_INCARNATE(0.0001f, "rngesus_incarnate", ChatFormatting.RED),
        PRAY_RNGESUS(0.005f, "pray_rngesus", ChatFormatting.LIGHT_PURPLE),
        EXTRAORDINARY(0.02f, "extraordinary", ChatFormatting.DARK_PURPLE),
        RARE(0.1f, "rare", ChatFormatting.AQUA),
        OCCASIONAL(0.2f, "occasional", ChatFormatting.BLUE);

        private final float maxChance;
        private final String translateKey;
        private final ChatFormatting color;

        DropRarities(float maxChance, String translateKey, ChatFormatting color) {
            this.maxChance = maxChance;
            this.translateKey = translateKey;
            this.color = color;
        }

        @Override
        public float getMaxChance() {
            return maxChance;
        }

        public static DropRarities getRarity(float chance) {
            for (DropRarities rarities : values()) {
                if (rarities.maxChance >= chance) {
                    return rarities;
                }
            }
            return OCCASIONAL;
        }


        public MutableComponent makeDisplay() {
            return Component.translatable("rng_drop.rarity." + translateKey).withStyle(ChatFormatting.BOLD).withStyle(color);
        }

        @Override
        public String getTranslateKey() {
            return translateKey;
        }
    }
}