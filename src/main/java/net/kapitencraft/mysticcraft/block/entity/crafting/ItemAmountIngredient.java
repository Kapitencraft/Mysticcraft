package net.kapitencraft.mysticcraft.block.entity.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemAmountIngredient extends AbstractIngredient {
    private final Item item;
    private final int amount;

    public ItemAmountIngredient(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public @NotNull Serializer getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull JsonElement toJson() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(item).toString());
        jsonobject.addProperty("amount", this.amount);
        return jsonobject;
    }

    @Override
    public boolean test(@Nullable ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        } else {
            return itemStack.getItem() == item && itemStack.getCount() >= amount;
        }
    }

    public static class Serializer implements IIngredientSerializer<ItemAmountIngredient> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull ItemAmountIngredient parse(@NotNull FriendlyByteBuf buffer) {
            ItemStack stack = buffer.readItem();
            return new ItemAmountIngredient(stack.getItem(), stack.getCount());
        }

        @Override
        public @NotNull ItemAmountIngredient parse(@NotNull JsonObject json) {
            Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(json.getAsJsonPrimitive("item").getAsString()));
            int amount = json.getAsJsonPrimitive("amount").getAsInt();
            return new ItemAmountIngredient(item, amount);
        }

        @Override
        public void write(FriendlyByteBuf buffer, ItemAmountIngredient ingredient) {
            ItemStack stack = new ItemStack(ingredient.item);
            stack.setCount(ingredient.amount);
            buffer.writeItem(stack);
        }
    }
}