package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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
                use.resultConsumer.accept(output);
                event.setOutput(output);
                event.setCost(use.xpCost);
            }
        }

    }

    public static void registerAnvilUse(Predicate<ItemStack> leftPredicate, Predicate<ItemStack> rightPredicate, Consumer<ItemStack> resultConsumer, int xpCost) {
        uses.add(new AnvilUse(leftPredicate, rightPredicate, resultConsumer, xpCost));
    }

    public static void registerUses() {
        registerAnvilUse(
                ItemStack::isDamageableItem,
                stack -> stack.is(ModItems.UNBREAKING_CORE.get()),
                stack -> stack.getOrCreateTag().putBoolean("Unbreakable", true),
                10
        );
    }

    public static class AnvilUse {
        private final Predicate<ItemStack> leftPredicate;
        private final Predicate<ItemStack> rightPredicate;
        private final Consumer<ItemStack> resultConsumer;
        private final int xpCost;

        public AnvilUse(Predicate<ItemStack> leftPredicate, Predicate<ItemStack> rightPredicate, Consumer<ItemStack> resultConsumer, int xpCost) {
            this.leftPredicate = leftPredicate;
            this.rightPredicate = rightPredicate;
            this.resultConsumer = resultConsumer;
            this.xpCost = xpCost;
        }
    }
}
