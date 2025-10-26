package net.kapitencraft.mysticcraft.capability.reforging;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.item.bonus.AbstractBonusElement;
import net.kapitencraft.kap_lib.item.modifier_display.EquipmentDisplayExtension;
import net.kapitencraft.kap_lib.requirements.RequirementManager;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.requirement.type.ReforgeRequirementType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Reforges {
    private static final HashMap<ResourceLocation, Reforge> reforges = new HashMap<>();
    public static final Codec<Reforge> CODEC = ResourceLocation.CODEC.xmap(reforges::get, Reforge::getRegistryName);
    public static final StreamCodec<ByteBuf, Reforge> STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(reforges::get, Reforge::getRegistryName);

    private static final List<Rarity> list = new ArrayList<>();
    public static void registerRarities() {
        list.addAll(List.of(Rarity.values()));
        ModEventFactory.onRarityRegister(list);
    }

    public static Reforge byName(ResourceLocation name) {
        return reforges.get(name);
    }

    public static List<Rarity> getRegisteredRarities() {
        return list;
    }

    public static @Nullable Reforge getReforge(ItemStack stack) {
        return stack.get(ModDataComponentTypes.REFORGE);
    }

    public static int getReforgesSize() {
        return reforges.size();
    }


    public static HashMap<ResourceLocation, Reforge> all() {
        return reforges;
    }

    public static Reforge makeRandom(boolean withStones, ItemStack stack, Player player) {
        Reforge current = getReforge(stack);
        List<Reforge> list = reforges.values().stream()
                .filter(reforge ->
                        reforge != current &&
                                (withStones || !reforge.isOnlyFromStone()) &&
                                reforge.type().mayApply(stack) &&
                                RequirementManager.instance.meetsRequirements(ReforgeRequirementType.INSTANCE, reforge, player)
                ).toList();
        return MathHelper.pickRandom(list);
    }

    public static Reforge applyRandom(boolean withStones, ItemStack stack, Player player) {
        Reforge reforge = makeRandom(withStones, stack, player);
        reforge.saveToStack(stack);
        return reforge;
    }

    /**
     * only used withing {@link ReforgeManager#apply(Map, ResourceManager, ProfilerFiller)} <br>
     * do <i>not</i> call directly
     */
    public static void registerReforge(Reforge reforge) {
        reforges.put(reforge.getRegistryName(), reforge);
    }

    @ApiStatus.Internal
    public static void bootstrap() {
    }

    public static boolean canBeReforged(ItemStack pStack) {
        return Stream.of(Reforge.Type.values()).anyMatch(type -> type.mayApply(pStack));
    }

    public static EquipmentDisplayExtension getReforgeDisplayExtension(ItemStack stack) {
        return new EquipmentDisplayExtension() {
            @Override
            public Style getStyle() {
                return Style.EMPTY.withColor(ChatFormatting.GREEN);
            }

            public EquipmentDisplayExtension.Type getType() {
                return EquipmentDisplayExtension.Type.DEFAULT;
            }

            @Override
            public ResourceLocation getModifiersLocation() {
                return MysticcraftMod.res("reforge");
            }
        };
    }

    public static @Nullable AbstractBonusElement getReforgeBonus(ItemStack itemStack) {
        Reforge reforge = getReforge(itemStack);
        return reforge != null && reforge.getBonus() != null ? reforge : null;
    }
}