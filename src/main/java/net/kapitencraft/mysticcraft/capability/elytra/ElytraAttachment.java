package net.kapitencraft.mysticcraft.capability.elytra;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.TextHelper;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


public record ElytraAttachment(ElytraData data, int level) implements TooltipProvider {

    public static final Codec<ElytraAttachment> CODEC = RecordCodecBuilder.create(i -> i.group(
            ElytraData.CODEC.fieldOf("data").forGetter(ElytraAttachment::data),
            Codec.INT.fieldOf("level").forGetter(ElytraAttachment::level)
    ).apply(i, ElytraAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ElytraAttachment> STREAM_CODEC = StreamCodec.composite(
            ElytraData.STREAM_CODEC, ElytraAttachment::data,
            ByteBufCodecs.INT, ElytraAttachment::level,
            ElytraAttachment::new
    );

    public static void merge(ItemStack stack, ItemStack stack1) {
        stack.update(ModDataComponentTypes.ELYTRA, random(), ElytraAttachment::increaseLevel);
    }

    private static ElytraAttachment random() {
        return new ElytraAttachment(MathHelper.pickRandom(List.of(ElytraData.values())), 1);
    }

    private ElytraAttachment increaseLevel() {
        return new ElytraAttachment(this.data, this.level);
    }

    public static boolean canCombine(ItemStack stack, ItemStack stack1) {
        ElytraAttachment at = stack.get(ModDataComponentTypes.ELYTRA);
        ElytraAttachment at1 = stack1.get(ModDataComponentTypes.ELYTRA);
        return at != null && Objects.equals(at, at1);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            String id = "elytra_data." + data.getSerializedName();
            tooltipAdder.accept(Component.translatable(id).withStyle(ChatFormatting.GREEN).append(" ").append(Component.translatable("enchantment.level." + level)));
            TextHelper.getDescriptionOrEmpty(id, component -> component.withStyle(ChatFormatting.YELLOW)).forEach(tooltipAdder);
        } else {
            tooltipAdder.accept(Component.translatable("elytra_data.tooltip").withStyle(ChatFormatting.YELLOW));
        }
    }
}
