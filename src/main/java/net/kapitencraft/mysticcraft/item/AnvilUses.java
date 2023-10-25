package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.nbt.CompoundTag;
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
                stack -> {
                    CompoundTag tag = stack.getOrCreateTag();
                    return stack.getItem() instanceof NecronSword && (tag.contains("NecronSpells") || !tag.getBoolean("SpellsMaxed"));
                },
                stack -> stack.getItem() instanceof SpellScrollItem scrollItem && scrollItem.getSpell().canApply(ModItems.NECRON_SWORD.get()),
                (stack, stack1) -> {
                    CompoundTag tag = stack.getOrCreateTag();
                    if (tag.contains("NecronSpells", 10)) {
                        NecronSword sword = (NecronSword) stack.getItem();
                        CompoundTag spells = tag.getCompound("NecronSpells");
                        if (spells.getAllKeys().size() == 2) {
                            tag.putBoolean("SpellsMaxed", true);
                        }
                        sword.removeSlot(0);
                        sword.addSlot(new SpellSlot(Spells.WITHER_IMPACT));
                        tag.remove("NecronSpells");
                    } else {
                        CompoundTag tag1 = new CompoundTag();
                        NecronSword sword = (NecronSword) stack.getItem();
                        SpellScrollItem scrollItem = (SpellScrollItem) stack1.getItem();
                        String name = scrollItem.getSpell().getName();
                        tag1.put(name, new CompoundTag());
                        tag.put("NecronSpells", tag1);
                        sword.addSlot(new SpellSlot(scrollItem.getSpell()));
                    }
                },
                20
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
