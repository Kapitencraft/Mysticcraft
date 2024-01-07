package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.misc.string_converter.args.MathArgument;
import net.kapitencraft.mysticcraft.misc.string_converter.args.TransferArg;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TextHelper {
    public static final Component EMPTY = Component.literal("");

    public static void sendTitle(Player player, Component title) {
        if (player instanceof ServerPlayer serverPlayer) {
            Function<Component, Packet<?>> function = ClientboundSetTitleTextPacket::new;
            serverPlayer.connection.send(function.apply(title));
        }
    }

    public static String wrapInObfuscation(String source) {
        return wrapInFormatting(source, ChatFormatting.OBFUSCATED);
    }

    public static String wrapInFormatting(Object source, ChatFormatting formatting) {
        char id = formatting.getChar();
        boolean obfuscate = id == 'k';
        StringBuilder builder = new StringBuilder();
        String format = "§" + id;
        builder.append(format);
        if (obfuscate) builder.append("k§r ");
        builder.append(source);
        if (obfuscate) builder.append(" §kk");
        builder.append("§r");
        return builder.toString();
    }

    public static String mergeRegister(String a, String b) {
        return a + "_" + b;
    }

    public static String swappedMergeRegister(String a, String b) {
        return mergeRegister(b, a);
    }

    public static void removeUnnecessaryEmptyLines(List<Component> components) {
        Reference<Component> reference = Reference.of(null);
        components.removeIf(component -> {
            if (component.getString().equals("")) {
                if (reference.getValue() != null && reference.getValue().getString().equals("")) {
                    return true;
                }
            }
            reference.setValue(component);
            return false;
        });
    }

    public static String createGiveFromStack(String name, ItemStack stack) {
        return "/give " + name + " " + BuiltInRegistries.ITEM.getKey(stack.getItem()) + stack.getOrCreateTag();
    }

    public static Component getStackNameWithoutBrackets(ItemStack stack) {
        MutableComponent mutablecomponent = Component.empty().append(stack.getHoverName());
        if (stack.hasCustomHoverName()) {
            mutablecomponent.withStyle(ChatFormatting.ITALIC);
        }

        mutablecomponent.withStyle(stack.getRarity().getStyleModifier()).withStyle((p_220170_) ->
                p_220170_.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(stack))));
        return mutablecomponent;
    }

    public static MutableComponent wrapInObfuscation(MutableComponent source, boolean really) {
        return really ? Component.literal("§kA§r ").append(source).append(" §kA§r") : source;
    }

    public static String makeDescriptionId(Item item) {
        return Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(item));
    }

    public static String wrapInNameMarkers(String name) {
        return "'" + name + "'";
    }

    public static String wrapInRed(Object toWrap) {
        return wrapInFormatting(toWrap, ChatFormatting.RED);
    }

    public static void setHotbarDisplay(Player player, Component display) {
        player.displayClientMessage(display, true);
    }

    public static String Vec3fToString(Vector3f vector3f) {
        return "[" + vector3f.x + ", " + vector3f.y + ", " + vector3f.z + "]";
    }

    public static void sendSubTitle(Player player, Component subtitle) {
        if (player instanceof ServerPlayer serverPlayer) {
            Function<Component, Packet<?>> function = ClientboundSetSubtitleTextPacket::new;
            serverPlayer.connection.send(function.apply(subtitle));
        }
    }

    public static String getTextId(Item toGet) {
        return BuiltInRegistries.ITEM.getKey(toGet).toString();
    }

    public static Item getFromId(String id) {
        return BuiltInRegistries.ITEM.get(new ResourceLocation(id));
    }

    public static void clearTitle(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ClientboundClearTitlesPacket clearTitlesPacket = new ClientboundClearTitlesPacket(true);
            serverPlayer.connection.send(clearTitlesPacket);
        }
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
            s.append(MathHelper.pickRandom(ALL_DEFAULT));
        }
        return s.toString();
    }

    private String createListName(List<TransferArg<Integer>> list) {
        StringBuilder builder = new StringBuilder();
        for (TransferArg<Integer> integerTransferArg : list) {
            builder.append(" ");
            if (integerTransferArg instanceof MathArgument<Integer> mathArgument) {
                builder.append(mathArgument.getName());
            } else {
                builder.append(integerTransferArg.value());
            }
        }
        return builder.toString();
    }

    public static ChatFormatting damageIndicatorColorGenerator(String type) {
        return switch (type) {
            case "heal" -> ChatFormatting.GREEN;
            case "wither" -> ChatFormatting.BLACK;
            case "ferocity" -> ChatFormatting.GOLD;
            case "drown" -> ChatFormatting.AQUA;
            case "ability", "fire" -> ChatFormatting.DARK_RED;
            case "dodge" -> ChatFormatting.DARK_GRAY;
            default -> ChatFormatting.RED;
        };
    }

    public static @NotNull ChatFormatting damageIndicatorColorFromDouble(double in) {
        return damageIndicatorColorGenerator(damageIndicatorDecoder(in));
    }

    public static String damageIndicatorDecoder(double in) {
        return switch ((int) in) {
            case 1 -> "heal";
            case 2 -> "wither";
            case 3 -> "ferocity";
            case 4 -> "drown";
            case 5 -> "ability";
            case 6 -> "dodge";
            case 7 -> "fire";
            default -> "normal";
        };
    }

    public static int damageIndicatorCoder(String id) {
        return switch (id) {
            case "heal" -> 1;
            case "wither" -> 2;
            case "ferocity" -> 3;
            case "drown" -> 4;
            case "ability" -> 5;
            case "dodge" -> 6;
            case "fire" -> 7;
            default -> 0;
        };
    }

    public static String fromVec3(Vec3 vec3) {
        return "Pos: [" + vec3.x + ", " + vec3.y + ", " + vec3.z + "]";
    }

    public static String fromBlockPos(BlockPos pos) {
        return fromVec3(new Vec3(pos.getX(), pos.getY(), pos.getZ()));
    }

    public static <T, K> T makeList(List<K> toMerge, Supplier<T> generator, Function<K, T> transfer, BiConsumer<T, T> useConsumer) {
        T t = generator.get();
        for (K k : toMerge) {
            T transferred = transfer.apply(k);
            useConsumer.accept(t, transferred);
        }
        return t;
    }

    public static <T> String makeList(List<T> toMerge, Function<T, String> provider) {
        StringBuilder builder = new StringBuilder();
        for (T t : toMerge) {
            String s = provider.apply(t);
            builder.append(s);
            if (t != toMerge.get(toMerge.size() - 1)) {
                builder.append(", ");
            }
        }
        return builder.toString();
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

    public static String makeGrammar(String toName) {
        String val1 = toName.replace("_", " ");
        char[] chars = val1.toCharArray();
        return fromStrings(makeCapital(fromChars(chars)));
    }

    private static String[] fromChars(char[] chars) {
        String[] strings = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            strings[i] = String.valueOf(chars[i]);
        }
        return strings;
    }

    private static String fromStrings(String[] strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
        }
        return builder.toString();
    }

    private static String[] makeCapital(String[] input) {
        boolean nextCapital = false;
        for (int i = 0; i < input.length; i++) {
            if (Objects.equals(input[i], " ")) {
                nextCapital = true;
            } else if (nextCapital || i == 0) {
                input[i] = input[i].toUpperCase();
                nextCapital = false;
            }
        }
        if (nextCapital) {
            String[] toReturn = new String[input.length - 1];
            System.arraycopy(input, 0, toReturn, 0, toReturn.length);
            return toReturn;
        }
        return input;
    }
}