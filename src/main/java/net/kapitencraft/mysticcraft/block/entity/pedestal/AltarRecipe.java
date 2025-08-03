package net.kapitencraft.mysticcraft.block.entity.pedestal;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.kapitencraft.kap_lib.helpers.CollectorHelper;
import net.kapitencraft.kap_lib.helpers.NetworkHelper;
import net.kapitencraft.mysticcraft.registry.ModRecipeSerializers;
import net.kapitencraft.mysticcraft.registry.ModRecipeTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.function.Consumer;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AltarRecipe implements Recipe<SimpleContainer> {
    private final Ingredient[] ingredients;
    private final ItemStack result;
    private final ResourceLocation id;

    public AltarRecipe(Ingredient[] ingredients, ItemStack result, ResourceLocation id) {
        this.ingredients = ingredients;
        this.result = result;
        this.id = id;
    }

    public static Builder builder(ItemStack result, RecipeCategory category) {
        return new Builder(result, category);
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        for (int i = 0; i < 9; i++) {
            if (!ingredients[i].test(pContainer.getItem(i))) return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ALTAR.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.ALTAR.get();
    }

    public static class Serializer implements RecipeSerializer<AltarRecipe> {

        @Override
        public AltarRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient[] ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients")
                    .asList().stream().map(Ingredient::fromJson).toArray(Ingredient[]::new);
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            return new AltarRecipe(ingredients, result, pRecipeId);
        }

        @Override
        public @Nullable AltarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient[] ingredients = NetworkHelper.readArray(pBuffer, Ingredient[]::new, Ingredient::fromNetwork);
            ItemStack result = pBuffer.readItem();
            return new AltarRecipe(ingredients, result, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AltarRecipe pRecipe) {
            NetworkHelper.writeArray(pBuffer, pRecipe.ingredients, (buf, ingredient) -> ingredient.toNetwork(buf));
            pBuffer.writeItem(pRecipe.result);
        }
    }

    public static class Builder implements RecipeBuilder {
        private String groupName;
        private final ItemStack result;
        private final Ingredient[] ingredients = new Ingredient[9];
        private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
        private final RecipeCategory category;

        public Builder(ItemStack result, RecipeCategory category) {
            this.result = result;
            this.category = category;
        }

        public Builder ingredient(int index, Ingredient ingredient) {
            Preconditions.checkElementIndex(index - 1, 8);
            this.ingredients[index] = ingredient;
            return this;
        }

        public Builder allIngredient(Ingredient ingredient) {
            for (int i = 0; i < 8; i++) {
                this.ingredients[i + 1] = ingredient;
            }
            return this;
        }

        public Builder middleIngredient(Ingredient ingredient) {
            this.ingredients[0] = ingredient;
            return this;
        }

        public Builder crossIngredient(Ingredient crossA, Ingredient crossB) {
            for (int i = 0; i < 8; i++) {
                if ((i & 1) == 0)
                    this.ingredients[i + 1] = crossA;
                else
                    this.ingredients[i + 1] = crossB;
            }
            return this;
        }

        @Override
        public Builder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
            advancement.addCriterion(pCriterionName, pCriterionTrigger);
            return this;
        }

        @Override
        public Builder group(@Nullable String pGroupName) {
            this.groupName = pGroupName;
            return this;
        }

        @Override
        public Item getResult() {
            return result.getItem();
        }

        @Override
        public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
            pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.ingredients, this.result, this.advancement, pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
        }

        private record Result(ResourceLocation id, Ingredient[] ingredients, ItemStack result,
                              Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {

            @Override
            public void serializeRecipeData(JsonObject pJson) {
                JsonArray array = Arrays.stream(ingredients).map(Ingredient::toJson).collect(CollectorHelper.toJsonArray());
                pJson.add("ingredients", array);

                JsonObject object = new JsonObject();
                object.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result.getItem()).toString());
                if (this.result.getCount() > 1) object.addProperty("count", this.result.getCount());
                pJson.add("result", object);
            }

            @Override
            public ResourceLocation getId() {
                return id;
            }

            @Override
            public RecipeSerializer<?> getType() {
                return ModRecipeSerializers.ALTAR.get();
            }

            @Override
            public @NotNull JsonObject serializeAdvancement() {
                return advancement.serializeToJson();
            }

            @Override
            public @Nullable ResourceLocation getAdvancementId() {
                return advancementId;
            }
        }
    }
}
