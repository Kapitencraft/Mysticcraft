package net.kapitencraft.mysticcraft.block.entity.crafting;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ShapedLargeCraftingRecipe extends LargeCraftingRecipe {
    final int width;
    final int height;
    final NonNullList<Ingredient> recipeItems;
    final ItemStack result;
    private final ResourceLocation id;
    final String group;
    final CraftingBookCategory category;

    public ShapedLargeCraftingRecipe(ResourceLocation id, String group, CraftingBookCategory category, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
        this.width = width;
        this.height = height;
        this.recipeItems = recipeItems;
        this.result = result;
        this.id = id;
        this.group = group;
        this.category = category;
    }


    @Override
    public boolean matches(CraftingContainer container, Level p_44003_) {
        for(int i = 0; i <= container.getWidth() - this.width; ++i) {
            for(int j = 0; j <= container.getHeight() - this.height; ++j) {
                if (this.matches(container, i, j, true)) {
                    return true;
                }

                if (this.matches(container, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matches(CraftingContainer p_44171_, int p_44172_, int p_44173_, boolean p_44174_) {
        for(int i = 0; i < p_44171_.getWidth(); ++i) {
            for(int j = 0; j < p_44171_.getHeight(); ++j) {
                int k = i - p_44172_;
                int l = j - p_44173_;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (p_44174_) {
                        ingredient = this.recipeItems.get(this.width - k - 1 + l * this.width);
                    } else {
                        ingredient = this.recipeItems.get(k + l * this.width);
                    }
                }

                if (!ingredient.test(p_44171_.getItem(i + j * p_44171_.getWidth()))) {
                    return false;
                }
            }
        }

        return true;
    }


    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer p_44001_) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x >= this.width && y >= this.height;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.LARGE_CRAFTING;
    }


    static Map<String, Ingredient> keyFromJson(JsonObject p_44211_) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : p_44211_.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static Ingredient fromJson(@Nullable JsonElement p_43918_) {
        if (p_43918_ != null && !p_43918_.isJsonNull()) {
            if (p_43918_.isJsonObject()) {
                return Ingredient.fromValues(Stream.of(valueFromJson(p_43918_.getAsJsonObject())));
            } else if (p_43918_.isJsonArray()) {
                JsonArray jsonarray = p_43918_.getAsJsonArray();
                if (jsonarray.size() == 0) {
                    throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
                } else {
                    return Ingredient.fromValues(StreamSupport.stream(jsonarray.spliterator(), false).map((jsonElement) -> valueFromJson(GsonHelper.convertToJsonObject(jsonElement, "item"))));
                }
            } else {
                throw new JsonSyntaxException("Expected item to be object or array of objects");
            }
        } else {
            throw new JsonSyntaxException("Item cannot be null");
        }
    }



    private static Ingredient.Value valueFromJson(JsonObject element) {
        if (element.has("item") && element.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
        } else if (element.has("item")) {
            Item item = ShapedRecipe.itemFromJson(element);
            int amount = 1;
            if (element.has("amount")) {
                JsonPrimitive primitive = element.getAsJsonPrimitive("amount");
                amount = primitive.getAsInt();
            }
            return new Ingredient.ItemValue(new ItemStack(item).copyWithCount(amount));
        } else if (element.has("tag")) {
            int amount = 1;
            if (element.has("amount")) {
                JsonPrimitive primitive = element.getAsJsonPrimitive("amount");
                amount = primitive.getAsInt();
            }
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(element, "tag"));
            TagKey<Item> tagkey = TagKey.create(Registries.ITEM, resourcelocation);
            return new CraftingUtils.TagAmountValue(tagkey, amount);
        } else {
            throw new JsonParseException("An ingredient entry needs either a tag or an item");
        }

    }

    public static ItemStack itemStackFromJson(JsonObject p_151275_) {
        return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(p_151275_, true, true);
    }

    public static Item itemFromJson(JsonObject p_151279_) {
        String s = GsonHelper.getAsString(p_151279_, "item");
        Item item = BuiltInRegistries.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });
        if (item == Items.AIR) {
            throw new JsonSyntaxException("Invalid item: " + s);
        } else {
            return item;
        }
    }

    static String[] shrink(String... p_44187_) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int i1 = 0; i1 < p_44187_.length; ++i1) {
            String s = p_44187_[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (p_44187_.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[p_44187_.length - l - k];

            for(int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = p_44187_[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    public boolean isIncomplete() {
        NonNullList<Ingredient> nonnulllist = this.getIngredients();
        return nonnulllist.isEmpty() || nonnulllist.stream().filter((p_151277_) -> {
            return !p_151277_.isEmpty();
        }).anyMatch(ForgeHooks::hasNoElements);
    }

    private static int firstNonSpace(String p_44185_) {
        int i;
        for(i = 0; i < p_44185_.length() && p_44185_.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int lastNonSpace(String p_44201_) {
        int i;
        for(i = p_44201_.length() - 1; i >= 0 && p_44201_.charAt(i) == ' '; --i) {
        }

        return i;
    }

    static String[] patternFromJson(JsonArray p_44197_) {
        String[] astring = new String[p_44197_.size()];
        if (astring.length > 9) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 9 is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < astring.length; ++i) {
                String s = GsonHelper.convertToString(p_44197_.get(i), "pattern[" + i + "]");
                if (s.length() > 9) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, 9 is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    static NonNullList<Ingredient> dissolvePattern(String[] p_44203_, Map<String, Ingredient> p_44204_, int p_44205_, int p_44206_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(p_44205_ * p_44206_, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(p_44204_.keySet());
        set.remove(" ");

        for(int i = 0; i < p_44203_.length; ++i) {
            for(int j = 0; j < p_44203_[i].length(); ++j) {
                String s = p_44203_[i].substring(j, j + 1);
                Ingredient ingredient = p_44204_.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + p_44205_ * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    public static class Serializer implements RecipeSerializer<ShapedLargeCraftingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("minecraft", "crafting_shaped");
        public @NotNull ShapedLargeCraftingRecipe fromJson(@NotNull ResourceLocation p_44236_, @NotNull JsonObject p_44237_) {
            String s = GsonHelper.getAsString(p_44237_, "group", "");
            CraftingBookCategory craftingbookcategory = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(p_44237_, "category", null), CraftingBookCategory.MISC);
            Map<String, Ingredient> map = keyFromJson(GsonHelper.getAsJsonObject(p_44237_, "key"));
            String[] astring = shrink(patternFromJson(GsonHelper.getAsJsonArray(p_44237_, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            NonNullList<Ingredient> nonnulllist = dissolvePattern(astring, map, i, j);
            ItemStack itemstack = itemStackFromJson(GsonHelper.getAsJsonObject(p_44237_, "result"));
            return new ShapedLargeCraftingRecipe(p_44236_, s, craftingbookcategory, i, j, nonnulllist, itemstack);
        }

        public ShapedLargeCraftingRecipe fromNetwork(ResourceLocation p_44239_, FriendlyByteBuf p_44240_) {
            int i = p_44240_.readVarInt();
            int j = p_44240_.readVarInt();
            String s = p_44240_.readUtf();
            CraftingBookCategory craftingbookcategory = p_44240_.readEnum(CraftingBookCategory.class);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(p_44240_));
            }

            ItemStack itemstack = p_44240_.readItem();
            return new ShapedLargeCraftingRecipe(p_44239_, s, craftingbookcategory, i, j, nonnulllist, itemstack);
        }

        public void toNetwork(FriendlyByteBuf p_44227_, ShapedLargeCraftingRecipe p_44228_) {
            p_44227_.writeVarInt(p_44228_.width);
            p_44227_.writeVarInt(p_44228_.height);
            p_44227_.writeUtf(p_44228_.group);
            p_44227_.writeEnum(p_44228_.category);

            for(Ingredient ingredient : p_44228_.recipeItems) {
                ingredient.toNetwork(p_44227_);
            }

            p_44227_.writeItem(p_44228_.result);
        }
    }

}
