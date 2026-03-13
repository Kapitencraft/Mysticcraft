package net.kapitencraft.mysticcraft.rpg.skill;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.kapitencraft.kap_lib.core.helpers.ExtraStreamCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public enum Skill implements StringRepresentable {
    FISHING(Items.FISHING_ROD),
    COMBAT(Items.DIAMOND_SWORD),
    MINING(Items.DIAMOND_PICKAXE),
    FARMING(Items.DIAMOND_HOE),
    FORAGING(Items.DIAMOND_AXE),
    ENCHANTING(Items.ENCHANTING_TABLE),
    ALCHEMY(Items.POTION),
    CARPENTRY(Items.CRAFTING_TABLE);

    public static final Codec<Skill> CODEC = StringRepresentable.fromEnum(Skill::values);
    public static final StreamCodec<ByteBuf, Skill> STREAM_CODEC = ExtraStreamCodecs.enumCodec(Skill.values());

    Skill(Item item) {
        this.item = item;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase();
    }

    private final Item item;

    public Item getItem() {
        return item;
    }
}
