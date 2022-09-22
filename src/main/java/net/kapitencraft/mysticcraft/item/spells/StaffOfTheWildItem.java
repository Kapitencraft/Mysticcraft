package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.item.client.StaffOfTheWildRenderer;
import net.kapitencraft.mysticcraft.item.gemstone_slot.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone_slot.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.function.Consumer;

public class StaffOfTheWildItem extends SpellItem implements IAnimatable, IGemstoneApplicable {
    public final int SPELL_SLOT_AMOUNT = 5;

    private final int GEMSTONE_SLOT_AMOUNT = 5;
    private GemstoneSlot[] gemstoneSlots = new GemstoneSlot[GEMSTONE_SLOT_AMOUNT];
    public AnimationFactory factory = new AnimationFactory(this);
    public static final Component[] description = {Component.Serializer.fromJson("As it is one of the most powerful"), Component.Serializer.fromJson("Magical Artifacts, it is used for much greatness")};
    public static final Component[] post_description = {Component.Serializer.fromJson(FormattingCodes.CITATION + "It`s a kind of magic! - Queen")};

    private SpellSlot[] spellSlots = new SpellSlot[SPELL_SLOT_AMOUNT];

    public StaffOfTheWildItem() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.EPIC).stacksTo(1));
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
        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
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
}
