package net.kapitencraft.mysticcraft.mixin.classes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.kapitencraft.mysticcraft.mixin.MixinHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class ShapelessRecipeSerializerMixin<T extends Recipe<?>> implements RecipeSerializer<T> {
    @Override
    public @NotNull T fromJson(@NotNull ResourceLocation location, @NotNull JsonObject object) {
        String s = GsonHelper.getAsString(object, "group", "");
        CraftingBookCategory craftingbookcategory = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(object, "category", null), CraftingBookCategory.MISC);
        NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(object, "ingredients"));
        if (nonnulllist.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
        } else if (nonnulllist.size() > 9) {
            throw new JsonParseException("Too many ingredients for shapeless recipe. The maximum is 9");
        } else {
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(object, "result"));
            return (T) new ShapelessRecipe(location, s, craftingbookcategory, itemstack, nonnulllist);
        }
    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray p_44276_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(int i = 0; i < p_44276_.size(); ++i) {
            nonnulllist.addAll(MixinHelper.fromJson(p_44276_.get(i), true));
        }

        return nonnulllist;
    }

    @Override
    public @Nullable T fromNetwork(ResourceLocation location, FriendlyByteBuf buf) {
        String s = buf.readUtf();
        CraftingBookCategory craftingbookcategory = buf.readEnum(CraftingBookCategory.class);
        int i = buf.readVarInt();
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

        nonnulllist.replaceAll(ignored -> MixinHelper.fromNetwork(buf));

        ItemStack itemstack = buf.readItem();
        return (T) new ShapelessRecipe(location, s, craftingbookcategory, itemstack, nonnulllist);
    }
}
