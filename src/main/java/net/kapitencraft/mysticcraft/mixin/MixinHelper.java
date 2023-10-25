package net.kapitencraft.mysticcraft.mixin;

import com.google.gson.*;
import net.kapitencraft.mysticcraft.ModMarker;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.crafting.CraftingUtils;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.slf4j.Marker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MixinHelper {

    public static void add(Supplier<Integer> getter, Consumer<Integer> setter, int change) {
        setter.accept(getter.get() + change);
    }

    public static void mul(Supplier<Integer> getter, Consumer<Integer> setter, int mul) {
        setter.accept(getter.get() * mul);
    }

    public static void mul(Supplier<Float> getter, Consumer<Float> setter, float mul) {
        setter.accept(getter.get() * mul);
    }

    public static Ingredient fromNetwork(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        if (size == -1) return CraftingHelper.getIngredient(buf.readResourceLocation(), buf);
        return MixinHelper.fromValues(Stream.generate(() -> new CraftingUtils.ItemAmountValue(buf.readItem())).limit(size));
    }


    public static List<Ingredient> fromJson(JsonElement element, boolean shapeless) {
        if (element != null && !element.isJsonNull()) {
            if (element.isJsonObject()) {
                return load(element.getAsJsonObject(), shapeless);
            } else if (element.isJsonArray()) {
                JsonArray jsonarray = element.getAsJsonArray();
                if (jsonarray.size() == 0) {
                    throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
                } else {
                    return CollectionHelper.merge(StreamSupport.stream(jsonarray.spliterator(), false).map((p_151264_) -> load(GsonHelper.convertToJsonObject(p_151264_, "item"), shapeless)).toList());
                }
            } else {
                throw new JsonSyntaxException("Expected item to be object or array of objects");
            }
        } else {
            throw new JsonSyntaxException("Item cannot be null");
        }
    }

    public static Ingredient fromValues(Stream<? extends Ingredient.Value> p_43939_) {
        Ingredient ingredient = new CraftingUtils.AmountIngredient(p_43939_);
        return ingredient.isEmpty() ? Ingredient.EMPTY : ingredient;
    }

    public static List<Ingredient> load(JsonObject object, boolean shapeless) {
        List<Ingredient> ingredients = new ArrayList<>();
        if (object.has("item") && object.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
        } else if (object.has("item")) {
            Item item = ShapedRecipe.itemFromJson(object);
            int amount = getAmount(object);
            if (amount > 64) {
                if (shapeless) {
                    while (amount > 64) {
                        ingredients.add(makeAmountIngredient(item, 64));
                        amount-=64;
                    }

                }
            }
            ingredients.add(makeAmountIngredient(item, amount));
        } else if (object.has("tag")) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(object, "tag"));
            TagKey<Item> tagkey = TagKey.create(Registries.ITEM, resourcelocation);
            int amount = getAmount(object);
            if (amount > 64) {
                if (shapeless) {
                    while (amount > 64) {
                        ingredients.add(makeTagIngredient(tagkey, 64));
                        amount-=64;
                    }
                }
            }
            ingredients.add(makeTagIngredient(tagkey, amount));
        } else {
            throw new JsonParseException("An ingredient entry needs either a tag or an item");
        }
        return ingredients;
    }

    private static Ingredient makeAmountIngredient(Item item, int amount) {
        return fromValues(Stream.of(new CraftingUtils.ItemAmountValue(item, amount)));
    }

    private static Ingredient makeTagIngredient(TagKey<Item> tagKey, int amount) {
        return fromValues(Stream.of(new CraftingUtils.TagAmountValue(tagKey, amount)));
    }

    private static int getAmount(JsonObject object) {
        int amount = getOrDefault(object, "amount", 1);
        if (amount != 1) {
            MysticcraftMod.sendInfo("read not-1 value amount: " + amount, false, RECIPE_MODIFIER_MARKER);
        }
        return amount;
    }

    private static int getOrDefault(JsonObject toGetFrom, String member, int defaultValue) {
        return toGetFrom.has(member) ? toGetFrom.getAsJsonPrimitive(member).getAsInt() : defaultValue;
    }

    private static final Marker RECIPE_MODIFIER_MARKER = new ModMarker("RecipeModifier");

}
