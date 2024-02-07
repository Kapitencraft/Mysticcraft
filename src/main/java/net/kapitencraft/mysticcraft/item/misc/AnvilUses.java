package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraCapability;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class AnvilUses {
    private static final List<AnvilUse> uses = new ArrayList<>();

    @SubscribeEvent
    public static void anvilEvent(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        for (AnvilUse use : uses) {
            if (use.bothPredicate.test(left, right)) {
                ItemStack output = left.copy();
                use.resultConsumer.accept(output, right);
                event.setOutput(output);
                event.setCost(use.xpCost);
            }
        }
    }

    public static void registerAnvilUse(BiPredicate<ItemStack, ItemStack> bothPredicate, BiConsumer<ItemStack, ItemStack> resultConsumer, int xpCost) {
        uses.add(new AnvilUse(bothPredicate, resultConsumer, xpCost));
    }

    public static void registerUses() {
        registerAnvilUse(
                both(
                        ItemStack::isDamageableItem,
                        stack -> stack.is(ModItems.UNBREAKING_CORE.get())
                ),
                (stack, stack1) -> stack.getOrCreateTag().putBoolean("Unbreakable", true),
                10
        );
        registerAnvilUse(
                both(
                        stack -> stack.getItem() instanceof NecronSword sword && !sword.hasSpell(stack, Spells.WITHER_IMPACT),
                        stack -> stack.getItem() instanceof SpellScrollItem && SpellScrollItem.getSpell(stack).canApply(ModItems.NECRON_SWORD.get())
                ),
                (stack, stack1) -> {
                    NecronSword sword = (NecronSword) stack.getItem();
                    if (sword.hasAnySpell(stack)) sword.setSlot(0, new SpellSlot(Spells.WITHER_IMPACT), stack);
                    else sword.setSlot(0, new SpellSlot(SpellScrollItem.getSpell(stack)), stack);
                },
                20
        );
        registerAnvilUse(
                both(
                        SoulbindHelper::isNotSoulbound,
                        stack -> stack.getItem() == ModItems.SOULBOUND_CORE.get()
                ),
                (stack, stack1) -> SoulbindHelper.setSoulbound(stack),
                15
        );
        registerAnvilUse(
                simple(CapabilityHelper::hasElytraCapability).and((stack, stack2) ->
                        CapabilityHelper.testCapability(stack, CapabilityHelper.ELYTRA, iElytraData -> {
                                Reference<Boolean> reference = Reference.of(false);
                                CapabilityHelper.exeCapability(stack2, CapabilityHelper.ELYTRA, iElytraData1 ->
                                        reference.setValue(iElytraData.getData() == iElytraData1.getData()));
                                return reference.getValue();
                            }
                        )),
                ElytraCapability::merge,
                1
        );
    }

    public static class AnvilUse {
        private final BiPredicate<ItemStack, ItemStack> bothPredicate;
        private final BiConsumer<ItemStack, ItemStack> resultConsumer;
        private final int xpCost;

        public AnvilUse(BiPredicate<ItemStack, ItemStack> bothPredicate, BiConsumer<ItemStack, ItemStack> resultConsumer, int xpCost) {
            this.bothPredicate = bothPredicate;
            this.resultConsumer = resultConsumer;
            this.xpCost = xpCost;
        }
    }

    private static BiPredicate<ItemStack, ItemStack> simple(Predicate<ItemStack> both) {
        return (stack, stack2) -> both.test(stack) && both.test(stack2);
    }

    private static BiPredicate<ItemStack, ItemStack> both(Predicate<ItemStack> first, Predicate<ItemStack> second) {
        return (stack, stack2) -> first.test(stack) && second.test(stack2);
    }
}
