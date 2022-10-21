package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.init.ModCreativeModeTabs;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.client.StaffOfTheWildRenderer;
import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class StaffOfTheWildItem extends SpellItem implements IAnimatable, IGemstoneApplicable {
    public final int SPELL_SLOT_AMOUNT = 5;
    private ArrayList<Attribute> attributesModified;

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        HashMap<Attribute, Double> attributeModifier = new HashMap<>();
        ArrayList<Attribute> attributes = new ArrayList<>();
        double gemstoneModifier;
        @Nullable Attribute attribute;
        @Nullable Gemstone gemstone;
        for (@Nullable GemstoneSlot slot : gemstoneSlots) {
            if (slot != null) {
                gemstone = slot.getAppliedGemstone();
                if (gemstone != null) {
                    attribute = gemstone.modifiedAttribute;
                    gemstoneModifier = gemstone.BASE_VALUE * gemstone.getRarity().modMul * (1 + itemStack.getEnchantmentLevel(ModEnchantments.EFFICIENT_JEWELLING.get()) * 0.02);
                    if (attributeModifier.containsKey(attribute)) {
                        attributeModifier.put(attribute, attributeModifier.get(attribute) + gemstoneModifier);
                    } else {
                        attributeModifier.put(attribute, gemstoneModifier);
                        attributes.add(attribute);
                    }
                }
            }
        }
        this.attributeModifiers = attributeModifier;
        this.attributesModified = attributes;
        if (flag.isAdvanced() && gemstoneSlots != null) {
            boolean flag1 = false;
            for (@Nullable GemstoneSlot slot : gemstoneSlots) {
                flag1 = slot != null && slot.getAppliedGemstone() != null;
                if (flag1) {
                    break;
                }
            }
            if (flag1) {
                list.add(Component.literal("Gemstone Modifications:").withStyle(ChatFormatting.GREEN));
                for (Attribute ignored : attributes) {
                    list.add(Component.literal(ignored.toString() + ": " + attributeModifier.get(ignored)));
                }
            }
        }

    }

    private final int GEMSTONE_SLOT_AMOUNT = 5;
    private HashMap<Attribute, Double> attributeModifiers;
    private GemstoneSlot[] gemstoneSlots = new GemstoneSlot[] {GemstoneSlot.MAGIC, GemstoneSlot.INTELLIGENCE, GemstoneSlot.INTELLIGENCE, GemstoneSlot.INTELLIGENCE, GemstoneSlot.ABILITY_DAMAGE};
    public AnimationFactory factory = new AnimationFactory(this);
    public static final Component[] description = {Component.literal("As it is one of the most powerful"), Component.literal("Magical Artifacts, it is used for much greatness")};
    public static final Component[] post_description = {Component.literal(FormattingCodes.CITATION + "It`s a kind of magic! - Queen")};

    private SpellSlot[] spellSlots = new SpellSlot[SPELL_SLOT_AMOUNT];

    public StaffOfTheWildItem() {
        super(new Properties().tab(ModCreativeModeTabs.SPELL_AND_GEMSTONE).rarity(FormattingCodes.LEGENDARY).stacksTo(1));
    }

    @Override
    public List<Component> getItemDescription() {
        return List.of(description);
    }

    @Override
    public List<Component> getPostDescription() {
        return List.of(post_description);
    }

    @Override
    public SpellSlot[] getSpellSlots() {
        return spellSlots;
    }

    @Override
    public int getActiveSpell() {
        return 0;
    }

    @Override
    public int getSpellSlotAmount() {
        return SPELL_SLOT_AMOUNT;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new StaffOfTheWildRenderer();

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));

    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> animationEvent) {
        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.staff_of_the_wild.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int getGemstoneSlotAmount() {
        return 5;
    }

    @Override
    public GemstoneSlot getGemstoneSlot(int slotLoc) {
        return this.gemstoneSlots[slotLoc];
    }

    @Override
    public GemstoneSlot[] getGemstoneSlots() {
        return this.gemstoneSlots;
    }

    @Override
    public HashMap<Attribute, Double> getAttributeModifiers() {
        return attributeModifiers;
    }

    @Override
    public ArrayList<Attribute> getAttributesModified() {
        return attributesModified;
    }
}
