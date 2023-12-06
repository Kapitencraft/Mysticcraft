package net.kapitencraft.mysticcraft.block.entity.crafting;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.ModMarker;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class CraftingHelper {

    public interface AmountValue {
        int getAmount();
    }

    public static class TagAmountValue implements Ingredient.Value, AmountValue {
        private final TagKey<Item> key;
        private final int amount;

        public TagAmountValue(TagKey<Item> key, int amount) {
            this.key = key;
            this.amount = amount;
        }

        @Override
        public int getAmount() {
            return amount;
        }

        @Override
        public @NotNull Collection<ItemStack> getItems() {
            List<ItemStack> list = Lists.newArrayList();

            for(Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(this.key)) {
                list.add(new ItemStack(holder).copyWithCount(amount));
            }

            if (list.size() == 0) {
                list.add(new ItemStack(net.minecraft.world.level.block.Blocks.BARRIER).setHoverName(net.minecraft.network.chat.Component.literal("Empty Tag: " + this.key.location())));
            }
            return list;
        }

        @Override
        public @NotNull JsonObject serialize() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("tag", this.key.location().toString());
            jsonobject.addProperty("amount", this.amount);
            return jsonobject;
        }
    }

    public static class ItemAmountValue implements Ingredient.Value, AmountValue {
        private final Item item;
        private final int amount;

        public ItemAmountValue(Item item, int amount) {
            this.item = item;
            this.amount = amount;
        }

        public ItemAmountValue(ItemStack stack) {
            this(stack.getItem(), stack.getCount());
        }

        @Override
        public int getAmount() {
            return amount;
        }

        @Override
        public @NotNull Collection<ItemStack> getItems() {
            return Collections.singleton(new ItemStack(item).copyWithCount(amount));
        }

        @Override
        public @NotNull JsonObject serialize() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(item).toString());
            jsonobject.addProperty("amount", this.amount);
            return jsonobject;
        }

        public static class Serializer implements IIngredientSerializer<AmountIngredient> {

            @Override
            public AmountIngredient parse(FriendlyByteBuf buffer) {
                return null;
            }

            @Override
            public AmountIngredient parse(JsonObject json) {
                return null;
            }

            @Override
            public void write(FriendlyByteBuf buffer, AmountIngredient ingredient) {

            }
        }
    }

    public static class AmountIngredient extends Ingredient {

        public AmountIngredient(Stream<? extends Value> p_43907_) {
            super(p_43907_);
        }
    }

    public static final ModMarker RECIPE_MARKER = new ModMarker("Recipes");
}