package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.item.client.StaffOfTheWildRenderer;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class StaffOfTheWildItem extends SpellItem implements GeoItem, IGemstoneApplicable {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final int SPELL_SLOT_AMOUNT = 5;
    private ArrayList<Attribute> attributesModified;

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        this.attributeModifiers = this.getAttributeModifiers(itemStack);
        this.attributesModified = this.getAttributesModified();
        if (gemstoneSlots != null) {
            boolean flag1 = false;
            for (@Nullable GemstoneSlot slot : gemstoneSlots) {
                flag1 = slot != null && slot.getAppliedGemstone() != null;
                if (flag1) {
                    break;
                }
            }
            if (flag1) {
                if (Screen.hasShiftDown()) {
                    list.add(Component.literal("Gemstone Modifications:").withStyle(ChatFormatting.GREEN));
                    for (Attribute ignored : this.attributesModified) {
                        list.add(Component.literal(ignored.toString() + ": " + this.attributeModifiers.get(ignored)));
                    }
                } else {
                    list.add(Component.literal("press [SHIFT] for Gemstone Information"));
                }
            }
        }

    }

    private HashMap<Attribute, Double> attributeModifiers;
    private GemstoneSlot[] gemstoneSlots = new GemstoneSlot[] {GemstoneSlot.MAGIC, GemstoneSlot.INTELLIGENCE, GemstoneSlot.INTELLIGENCE, GemstoneSlot.INTELLIGENCE, GemstoneSlot.ABILITY_DAMAGE};
    public static final Component[] description = {Component.literal("As it is one of the most powerful"), Component.literal("Magical Artifacts, it is used for much greatness")};
    public static final Component[] post_description = {Component.literal(FormattingCodes.CITATION + "It`s a kind of magic! - Queen")};

    private SpellSlot[] spellSlots = new SpellSlot[SPELL_SLOT_AMOUNT];

    public StaffOfTheWildItem() {
        super(new Properties().rarity(FormattingCodes.LEGENDARY).stacksTo(1), 5, 100, 0);
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
    public int getActiveSpellIndex() {
        return 0;
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private StaffOfTheWildRenderer renderer;
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new StaffOfTheWildRenderer();
                }
                return this.renderer;
            }
        });
    }


    @Override
    public int getGemstoneSlotAmount() {
        return 5;
    }
    @Override
    public GemstoneSlot[] getGemstoneSlots() {
        return this.gemstoneSlots;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "rotation_controller", state -> PlayState.CONTINUE).triggerableAnim("idle", RawAnimation.begin().thenLoop("idle")));
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
