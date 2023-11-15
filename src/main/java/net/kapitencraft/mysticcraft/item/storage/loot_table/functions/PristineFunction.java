package net.kapitencraft.mysticcraft.item.storage.loot_table.functions;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.LootTableHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.init.ModLootItemFunctions;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.storage.loot_table.IConditional;
import net.kapitencraft.mysticcraft.misc.serialization.JsonSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class PristineFunction extends LootItemConditionalFunction implements IConditional {
    private static final Codec<PristineFunction> CODEC = LootTableHelper.simpleCodec(PristineFunction::new);
    protected PristineFunction(LootItemCondition[] p_80678_) {
        super(p_80678_);
    }

    @Override
    protected @NotNull ItemStack run(@NotNull ItemStack stack, @NotNull LootContext context) {
        if (stack.getItem() instanceof GemstoneItem gemstoneItem) {
            int gemstoneLevel, oldGemstoneLevel = gemstoneLevel = gemstoneItem.getRarity().level;
            if (gemstoneLevel == 0) {
                return stack;
            } else {
                Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
                if (entity instanceof LivingEntity living) {
                    double pristine = AttributeHelper.getSaveAttributeValue(ModAttributes.PRISTINE.get(), living);
                    while (gemstoneLevel < 5 && pristine > 0) {
                        if (MathHelper.chance(pristine / 100, living)) {
                            gemstoneLevel++;
                        }
                        pristine-=100;
                    }
                }
                if (gemstoneLevel != oldGemstoneLevel && entity instanceof Player player) {
                    GemstoneType.Rarity rarity = GemstoneType.Rarity.byLevel(gemstoneLevel);
                    GemstoneItem item = ModItems.GEMSTONES.get(gemstoneItem.getGemstone()).get(rarity).get();
                    ItemStack pristinedStack = new ItemStack(item).copyWithCount(stack.getCount());
                    player.sendSystemMessage(Component.literal("§5PRISTINE§a you found " + stack.getCount() + " ").append(TextHelper.getStackNameWithoutBrackets(pristinedStack)));
                    return pristinedStack;
                }
            }
        }
        return stack;
    }

    public static final JsonSerializer<PristineFunction> SERIALIZER = new JsonSerializer<>(CODEC);

    @Override
    public @NotNull LootItemFunctionType getType() {
        return ModLootItemFunctions.PRISTINE_MODIFIER.get();
    }

    @Override
    public LootItemCondition[] getConditions() {
        return predicates;
    }
}
