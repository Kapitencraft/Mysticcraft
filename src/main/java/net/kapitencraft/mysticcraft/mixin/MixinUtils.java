package net.kapitencraft.mysticcraft.mixin;

import com.google.gson.*;
import net.kapitencraft.mysticcraft.ModMarker;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.crafting.CraftingUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.slf4j.Marker;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MixinUtils {

    public static void add(Supplier<Integer> getter, Consumer<Integer> setter, int change) {
        setter.accept(getter.get() + change);
    }

    public static void mul(Supplier<Integer> getter, Consumer<Integer> setter, int mul) {
        setter.accept(getter.get() * mul);
    }

    public static void mul(Supplier<Float> getter, Consumer<Float> setter, float mul) {
        setter.accept(getter.get() * mul);
    }


    public static Ingredient fromJson(JsonElement element) {
        if (element != null && !element.isJsonNull()) {
            if (element.isJsonObject()) {
                return fromValues(Stream.of(valueFromJson(element.getAsJsonObject())));
            } else if (element.isJsonArray()) {
                JsonArray jsonarray = element.getAsJsonArray();
                if (jsonarray.size() == 0) {
                    throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
                } else {
                    return fromValues(StreamSupport.stream(jsonarray.spliterator(), false).map((p_151264_) -> valueFromJson(GsonHelper.convertToJsonObject(p_151264_, "item"))));
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

    public static Ingredient.Value valueFromJson(JsonObject object) {
        if (object.has("item") && object.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
        } else if (object.has("item")) {
            Item item = ShapedRecipe.itemFromJson(object);
            return new CraftingUtils.ItemAmountValue(item, getAmount(object));
        } else if (object.has("tag")) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(object, "tag"));
            TagKey<Item> tagkey = TagKey.create(Registries.ITEM, resourcelocation);
            return new CraftingUtils.TagAmountValue(tagkey, getAmount(object));
        } else {
            throw new JsonParseException("An ingredient entry needs either a tag or an item");
        }
    }

    private static int getAmount(JsonObject object) {
        int amount = object.has("amount") ? object.getAsJsonPrimitive("amount").getAsInt() : 1;
        if (amount != 1) {
            MysticcraftMod.sendInfo("read not-1 value amount: " + amount, false, MARKER);
        }
        return amount;
    }

    private static final Marker MARKER = new ModMarker("RecipeModifier");
}
