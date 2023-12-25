package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class AnvilUses {
    private static final List<AnvilUse> uses = new ArrayList<>();

    @SubscribeEvent
    public static void anvilEvent(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        for (AnvilUse use : uses) {
            if (use.leftPredicate.test(left) && use.rightPredicate.test(right)) {
                ItemStack output = left.copy();
                use.resultConsumer.accept(output, right);
                event.setOutput(output);
                event.setCost(use.xpCost);
            }
        }
    }

    public static void registerAnvilUse(Predicate<ItemStack> leftPredicate, Predicate<ItemStack> rightPredicate, BiConsumer<ItemStack, ItemStack> resultConsumer, int xpCost) {
        uses.add(new AnvilUse(leftPredicate, rightPredicate, resultConsumer, xpCost));
    }

    public static void registerUses() {
        registerAnvilUse(
                ItemStack::isDamageableItem,
                stack -> stack.is(ModItems.UNBREAKING_CORE.get()),
                (stack, stack1) -> stack.getOrCreateTag().putBoolean("Unbreakable", true),
                10
        );
        registerAnvilUse(
                stack -> stack.getItem() instanceof NecronSword sword && !sword.hasSpell(stack, Spells.WITHER_IMPACT),
                stack -> stack.getItem() instanceof SpellScrollItem scrollItem && scrollItem.getSpell().canApply(ModItems.NECRON_SWORD.get()),
                (stack, stack1) -> {
                    NecronSword sword = (NecronSword) stack.getItem();
                    if (sword.hasAnySpell(stack)) sword.setSlot(0, new SpellSlot(Spells.WITHER_IMPACT), stack);
                    else sword.setSlot(0, new SpellSlot(((SpellScrollItem) stack1.getItem()).getSpell()), stack);
                },
                20
        );
        registerAnvilUse(
                SoulbindHelper::isNotSoulbound,
                stack -> stack.getItem() == ModItems.SOULBOUND_CORE.get(),
                (stack, stack1) -> SoulbindHelper.setSoulbound(stack),
                15
        );
    }

    public static class AnvilUse {
        private final Predicate<ItemStack> leftPredicate;
        private final Predicate<ItemStack> rightPredicate;
        private final BiConsumer<ItemStack, ItemStack> resultConsumer;
        private final int xpCost;

        public AnvilUse(Predicate<ItemStack> leftPredicate, Predicate<ItemStack> rightPredicate, BiConsumer<ItemStack, ItemStack> resultConsumer, int xpCost) {
            this.leftPredicate = leftPredicate;
            this.rightPredicate = rightPredicate;
            this.resultConsumer = resultConsumer;
            this.xpCost = xpCost;
        }
    }
}
