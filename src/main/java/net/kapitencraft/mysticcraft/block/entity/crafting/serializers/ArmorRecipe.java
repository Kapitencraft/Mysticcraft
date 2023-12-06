package net.kapitencraft.mysticcraft.block.entity.crafting.serializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.block.entity.crafting.CraftingHelper;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.init.ModRecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public class ArmorRecipe extends CustomRecipe {
    private final Ingredient material;
    private final List<ShapedRecipe> all;
    private final String group;
    public ArmorRecipe(ResourceLocation location, CraftingBookCategory p_249010_, Ingredient material, Map<ArmorType, ItemStack> all, String group) {
        super(location, p_249010_);
        this.material = material;
        this.group = group;
        this.all = MapStream.of(all).map(this::create).toList();
    }

    private ShapedRecipe create(ArmorType type, ItemStack stack) {
        NonNullList<Ingredient> cost = type.makeIngredients(this.material);
        return new ShapedRecipe(getId(), getGroup(), category(), 3, cost.size() / 3, cost, stack);
    }

    //TODO fix crafting recipes not working
    @Override
    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        for (ShapedRecipe recipe : this.all) {
            if (recipe.matches(container, level)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getGroup() {
        return group;
    }

    @SuppressWarnings("ALL")
    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer container) {
        for (ShapedRecipe recipe : all) {
            if (recipe.matches(container, null)) {
                return recipe.assemble(container);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i == 3 && j == 3;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ARMOR.get();
    }

    private enum ArmorType implements StringRepresentable {
        HELMET("helmet",
                of(List.of(true, true, true, true, false, true), true), true),
        CHESTPLATE("chestplate",
                of(List.of(true, false, true, true, true, true, true, true, true), false), false),
        LEGGINGS("leggings",
                of(List.of(true, true, true, true, false, true, true, false, true), false), false),
        BOOTS("boots",
                of(List.of(true, false, true, true, false, true), true), true);
        public static final EnumCodec<ArmorType> CODEC = StringRepresentable.fromEnum(ArmorType::values);
        public static ArmorType get(String name) {
            return CODEC.byName(name, HELMET);
        }

        private final String name;
        private final boolean small;
        private final boolean[][] data;
        private static boolean[][] of(List<Boolean> list, boolean small) {
            int height = small ? 2 : 3;
            boolean[][] map = new boolean[3][height];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < height; j++) {
                    map[i][j] = list.get(i + j * 3);
                }
            }
            return map;
        }

        ArmorType(String name, boolean[][] data, boolean small) {
            this.name = name;
            this.small = small;
            this.data = data;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }

        public NonNullList<Ingredient> makeIngredients(Ingredient main) {
            NonNullList<Ingredient> list = NonNullList.withSize(small ? 6 : 9, Ingredient.EMPTY);
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < (small ? 2 : 3); y++) {
                    if (data[x][y]) {
                        list.set(x + y*3, main);
                    }
                }
            }
            return list;
        }
    }

    public static class Serializer implements RecipeSerializer<ArmorRecipe> {

        @Override
        public @NotNull ArmorRecipe fromJson(@NotNull ResourceLocation location, @NotNull JsonObject object) {
            String group = GsonHelper.getAsString(object, "group", "");
            Ingredient material = Ingredient.fromJson(object.get("material"));
            String results = GsonHelper.getAsString(object, "results", null);
            JsonArray unused = GsonHelper.getAsJsonArray(object, "unused", new JsonArray());
            List<ArmorType> unusedSlots = unused.asList().stream().map(JsonElement::getAsString).map(ArmorType::get).toList();
            HashMap<ArmorType, ItemStack> map = new HashMap<>();
            List<ArmorType> toUse = Arrays.stream(ArmorType.values()).filter(armorType -> !unusedSlots.contains(armorType)).toList();
            if (results != null) {
                Stream<String> stream = CollectionHelper.sync(toUse.stream().map(ArmorType::getSerializedName), TextHelper::mergeRegister, results);
                MapStream<ArmorType, String> mapStream = MapStream.create(toUse, stream.toList());
                try {
                    mapStream.mapValues(ResourceLocation::new)
                            .mapValues(BuiltInRegistries.ITEM::get)
                            .mapValues(ItemStack::new)
                            .forEach(map::put);
                } catch (Exception e) {
                    MysticcraftMod.sendWarn("unable to load recipe {}: {}", CraftingHelper.RECIPE_MARKER, location, e.getMessage());
                }
            } else {
                JsonArray array = GsonHelper.getAsJsonArray(object, "results", new JsonArray());
                List<ResourceLocation> locations = array.asList().stream().map(JsonElement::getAsString).map(ResourceLocation::new).toList();
                try {
                    MapStream.create(toUse, locations)
                            .mapValues(BuiltInRegistries.ITEM::get)
                            .mapValues(ItemStack::new).forEach(map::put);
                } catch (Exception e) {
                    MysticcraftMod.sendWarn("unable to load recipe {}: {}", CraftingHelper.RECIPE_MARKER, location, e.getMessage());
                }
            }
            CraftingBookCategory category = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(object, "category", null));
            return new ArmorRecipe(location, category, material, map, group);
        }

        @Override
        public @Nullable ArmorRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, FriendlyByteBuf buf) {
            String group = buf.readUtf();
            Ingredient material = Ingredient.fromNetwork(buf);
            List<String> items = List.of(buf.readUtf(), buf.readUtf(), buf.readUtf(), buf.readUtf());
            List<ItemStack> results = items.stream().filter(s -> !Objects.equals(s, ""))
                    .map(ResourceLocation::new)
                    .map(BuiltInRegistries.ITEM::get)
                    .map(ItemStack::new).toList();
            List<ArmorType> types = Arrays.stream(ArmorType.values()).toList();
            Map<ArmorType, ItemStack> resultMap = MapStream.create(types, results).toMap();
            CraftingBookCategory category = buf.readEnum(CraftingBookCategory.class);
            return new ArmorRecipe(resourceLocation, category, material, resultMap, group);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ArmorRecipe recipe) {
            buf.writeUtf(recipe.group);
            recipe.material.toNetwork(buf);
            recipe.all.stream()
                    .map(ShapedRecipe::getResultItem)
                    .map(ItemStack::getItem)
                    .map(BuiltInRegistries.ITEM::getKey)
                    .map(ResourceLocation::toString)
                    .forEach(buf::writeUtf);
            MiscHelper.repeatXTimes(4 - recipe.all.size(), integer -> buf.writeUtf(""));
            buf.writeEnum(recipe.category());
        }
    }
}
