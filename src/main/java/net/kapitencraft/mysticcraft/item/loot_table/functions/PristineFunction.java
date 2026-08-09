package net.kapitencraft.mysticcraft.item.loot_table.functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.kap_lib.core.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.core.helpers.MathHelper;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.registry.ModAttributes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.registry.ModLootItemFunctions;
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

import java.util.List;

public class PristineFunction extends LootItemConditionalFunction {
    public static final MapCodec<PristineFunction> CODEC = RecordCodecBuilder.mapCodec(i -> commonFields(i).apply(i, PristineFunction::new));

    public PristineFunction(List<LootItemCondition> p_80678_) {
        super(p_80678_);
    }

    @Override
    protected @NotNull ItemStack run(@NotNull ItemStack stack, @NotNull LootContext context) {
        if (stack.getItem() instanceof GemstoneItem) {
            int gemstoneLevel, oldGemstoneLevel = gemstoneLevel = IGemstoneItem.getGemRarity(stack).level;
            if (gemstoneLevel == 0) {
                return stack;
            } else {
                Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
                if (entity instanceof LivingEntity living) {
                    double pristine = AttributeHelper.getSaveAttributeValue(ModAttributes.PRISTINE, living);
                    while (gemstoneLevel < 5 && pristine > 0) {
                        if (MathHelper.chance(pristine / 100, living)) {
                            gemstoneLevel++;
                        }
                        pristine -= 100;
                    }
                }
                if (gemstoneLevel != oldGemstoneLevel && entity instanceof Player player) {
                    GemstoneType.Rarity rarity = GemstoneType.Rarity.byLevel(gemstoneLevel);
                    ItemStack item = IGemstoneItem.createData(rarity, IGemstoneItem.getGemstone(stack), ModItems.GEMSTONE).copyWithCount(stack.getCount());
                    player.sendSystemMessage(Component.literal("§5PRISTINE§a you found " + stack.getCount() + " ").append(item.getHoverName()));
                    return item;
                }
            }
        }
        return stack;
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return ModLootItemFunctions.PRISTINE_MODIFIER.get();
    }
}
