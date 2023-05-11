package net.kapitencraft.mysticcraft.misc.utils;

import net.kapitencraft.mysticcraft.item.spells.NormalSpellItem;
import net.kapitencraft.mysticcraft.item.weapon.melee.sword.LongSwordItem;
import net.kapitencraft.mysticcraft.item.weapon.ranged.bow.ShortBowItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class TextUtils {

    public static void sendTitle(Player player, Component title) {
        if (player instanceof ServerPlayer serverPlayer) {
            Function<Component, Packet<?>> function = ClientboundSetTitleTextPacket::new;
            serverPlayer.connection.send(function.apply(title));
        }
    }

    public static void sendSubTitle(Player player, Component subtitle) {
        if (player instanceof ServerPlayer serverPlayer) {
            Function<Component, Packet<?>> function = ClientboundSetSubtitleTextPacket::new;
            serverPlayer.connection.send(function.apply(subtitle));
        }
    }

    public static void clearTitle(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ClientboundClearTitlesPacket clearTitlesPacket = new ClientboundClearTitlesPacket(true);
            serverPlayer.connection.send(clearTitlesPacket);
        }
    }

    public static String getNameModifier(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof NormalSpellItem) {
            return "SPELL ITEM";
        } else if (item instanceof LongSwordItem) {
            return "LONGSWORD";
        } else if (item instanceof ShortBowItem) {
            return "SHORT BOW";
        } else if (item instanceof SwordItem) {
            return "SWORD";
        } else if (item instanceof PickaxeItem) {
            return "PICKAXE";
        } else if (item instanceof AxeItem) {
            return "AXE";
        } else if (item instanceof ShovelItem) {
            return "SHOVEL";
        } else if (item instanceof HoeItem) {
            return "HOE";
        } else if (item instanceof BowItem) {
            return "BOW";
        } else if (item instanceof CrossbowItem) {
            return "CROSSBOW";
        } else if (item instanceof EnchantedBookItem) {
            return "ENCHANTED BOOK";
        } else if (item instanceof ArmorItem armorItem) {
            if (armorItem.getSlot() == EquipmentSlot.FEET) {
                return "BOOTS";
            } else if (armorItem.getSlot() == EquipmentSlot.LEGS) {
                return "LEGGINGS";
            } else if (armorItem.getSlot() == EquipmentSlot.CHEST) {
                return "CHEST PLATE";
            } else if (armorItem.getSlot() == EquipmentSlot.HEAD) {
                return "HELMET";
            }
        } else if (item instanceof BlockItem) {
            return "BLOCK";
        } else if (item instanceof BoatItem) {
            return "BOAT";
        } else if (item instanceof FishingRodItem) {
            return "FISHING ROD";
        }
        return "ITEM";
    }

    private static final List<String> NUMBERS = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private static final List<String> LETTERS_SMALL = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
    private static final List<String> LETTERS_BIG = LETTERS_SMALL.stream().map(String::toUpperCase).toList();

    public static String removeNumbers(String string) {
        for (String number : NUMBERS) {
            string = string.replace(number, "");
        }
        return string;
    }

    public static String createRandom(int length) {
        StringBuilder s = new StringBuilder();
        final List<String> ALL_DEFAULT = new ArrayList<>();
        ALL_DEFAULT.addAll(NUMBERS);
        ALL_DEFAULT.addAll(LETTERS_SMALL);
        ALL_DEFAULT.addAll(LETTERS_BIG);
        for (int i = 0; i < length; i++) {
            int index = Mth.nextInt(RandomSource.createNewThreadLocalInstance(), 0, ALL_DEFAULT.size()-1);
            s.append(ALL_DEFAULT.get(index));
        }
        return s.toString();
    }

    public static ChatFormatting damageIndicatorColorGenerator(String type) {
        return switch (type) {
            case "heal" -> ChatFormatting.GREEN;
            case "wither" -> ChatFormatting.BLACK;
            case "ferocity" -> ChatFormatting.GOLD;
            case "drown" -> ChatFormatting.AQUA;
            case "ability" -> ChatFormatting.DARK_RED;
            case "dodge" -> ChatFormatting.DARK_GRAY;
            default -> ChatFormatting.RED;
        };
    }

    public static String fromVec3(Vec3 vec3) {
        return "Pos: [" + vec3.x + ", " + vec3.y + ", " + vec3.z + "]";
    }

    public static String getRegistryNameForSlot(EquipmentSlot slot) {
        HashMap<EquipmentSlot, String> hashMap = new HashMap<>();
        hashMap.put(EquipmentSlot.HEAD, "helmet");
        hashMap.put(EquipmentSlot.CHEST, "chestplate");
        hashMap.put(EquipmentSlot.LEGS, "leggings");
        hashMap.put(EquipmentSlot.FEET, "boots");
        hashMap.put(EquipmentSlot.MAINHAND, "mainhand");
        hashMap.put(EquipmentSlot.OFFHAND, "offhand");
        return hashMap.get(slot);
    }

}
