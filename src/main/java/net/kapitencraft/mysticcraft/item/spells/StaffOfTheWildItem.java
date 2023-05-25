package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.item.client.StaffOfTheWildRenderer;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
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
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class StaffOfTheWildItem extends NormalSpellItem implements GeoItem, IGemstoneApplicable {
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final int SPELL_SLOT_AMOUNT = 5;

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        boolean flag1 = false;
        for (@Nullable GemstoneSlot slot : this.getGemstoneSlots(itemStack)) {
            flag1 = slot != null && slot.getAppliedGemstone() != null;
            if (flag1) {
                break;
            }
        }
        if (flag1) {
            this.addModInfo(itemStack, list);
        }
    }
    public static final Component[] description = {Component.literal("As it is one of the most powerful"), Component.literal("Magical Artifacts, it is used for much greatness")};
    public static final Component[] post_description = {Component.literal(FormattingCodes.CITATION + "It`s a kind of magic! - Queen")};

    private final SpellSlot[] spellSlots = new SpellSlot[SPELL_SLOT_AMOUNT];

    public StaffOfTheWildItem() {
        super(new Properties().rarity(FormattingCodes.LEGENDARY).stacksTo(1), 5, 200, 0);
        this.addSlot(new SpellSlot(Spells.HUGE_HEAL));
        this.addSlot(new SpellSlot(Spells.INSTANT_TRANSMISSION));
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
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
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
    public GemstoneSlot[] getDefaultSlots() {
        return new GemstoneSlot.Builder(GemstoneSlot.Type.MAGIC, GemstoneSlot.Type.INTELLIGENCE, GemstoneSlot.Type.INTELLIGENCE, GemstoneSlot.Type.INTELLIGENCE, GemstoneSlot.Type.ABILITY_DAMAGE).build();
    }

    private PlayState predicate(AnimationState<?> state) {
        state.getController().setAnimation(IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle_controller", this::predicate));
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
