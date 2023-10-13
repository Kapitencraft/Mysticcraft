package net.kapitencraft.mysticcraft.mixin.classes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.kapitencraft.mysticcraft.mixin.MixinUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShapelessRecipe.Serializer.class)
public class ShapelessRecipeSerializerMixin implements RecipeSerializer<ShapelessRecipe> {
    @Override
    public ShapelessRecipe fromJson(ResourceLocation location, JsonObject object) {
        String s = GsonHelper.getAsString(object, "group", "");
        CraftingBookCategory craftingbookcategory = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(object, "category", null), CraftingBookCategory.MISC);
        NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(object, "ingredients"));
        if (nonnulllist.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
        } else if (nonnulllist.size() > 9) {
            throw new JsonParseException("Too many ingredients for shapeless recipe. The maximum is 9");
        } else {
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(object, "result"));
            return new ShapelessRecipe(location, s, craftingbookcategory, itemstack, nonnulllist);
        }
    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray p_44276_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(int i = 0; i < p_44276_.size(); ++i) {
            Ingredient ingredient = MixinUtils.fromJson(p_44276_.get(i));
            nonnulllist.add(ingredient);
        }

        return nonnulllist;
    }

    @Override
    public @Nullable ShapelessRecipe fromNetwork(ResourceLocation p_44105_, FriendlyByteBuf p_44106_) {
        return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf p_44101_, ShapelessRecipe p_44102_) {

    }
}
