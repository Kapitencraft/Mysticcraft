package net.kapitencraft.mysticcraft.block.entity.pedestal;

import com.google.common.base.Preconditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.registry.ModRecipeSerializers;
import net.kapitencraft.mysticcraft.registry.ModRecipeTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AltarRecipe implements Recipe<AltarRecipeInput> {
    public static final MapCodec<AltarRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(a -> a.ingredients),
            ItemStack.CODEC.fieldOf("result").forGetter(a -> a.result)
    ).apply(i, AltarRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AltarRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), a -> a.ingredients,
            ItemStack.STREAM_CODEC, a -> a.result,
            AltarRecipe::new
    );

    private final List<Ingredient> ingredients;
    private final ItemStack result;

    public AltarRecipe(List<Ingredient> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    public static Builder builder(ItemStack result, RecipeCategory category) {
        return new Builder(result, category);
    }

    @Override
    public boolean matches(AltarRecipeInput input, Level pLevel) {
        for (int i = 0; i < 9; i++) {
            if (!ingredients.get(i).test(input.getItem(i))) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(AltarRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result.copy();
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
        public MapCodec<AltarRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AltarRecipe> streamCodec() {
            return STREAM_CODEC;
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
        public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
            advancement.addCriterion(name, criterion);
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
        public void save(RecipeOutput recipeOutput, ResourceLocation id) {
            recipeOutput.accept(id, new AltarRecipe(List.of(this.ingredients), this.result), this.advancement.build(id.withPrefix("recipes/")));
        }
    }
}
