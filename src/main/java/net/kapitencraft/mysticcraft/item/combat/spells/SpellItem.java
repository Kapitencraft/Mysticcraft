package net.kapitencraft.mysticcraft.item.combat.spells;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.item.ExtendedItem;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public abstract class SpellItem extends SwordItem implements ExtendedItem, ISpellItem {
    public static final TabGroup SPELL_GROUP = new TabGroup(TabRegister.TabTypes.SPELL, TabRegister.TabTypes.WEAPONS_AND_TOOLS);

    private final int intelligence;
    private final int ability_damage;

    //Display settings
    public abstract List<Component> getItemDescription();
    public abstract List<Component> getPostDescription();


    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        if (this.getItemDescription() != null) {
            list.addAll(this.getItemDescription());
            list.add(Component.literal(""));
        }
        if (this.getPostDescription() != null) {
            list.addAll(this.getPostDescription());
        }
    }

    public SpellItem(Properties p_41383_, int damage, float speed, int intelligence, int ability_damage) {
        super(ModTiers.SPELL_TIER, damage, speed,  p_41383_.stacksTo(1));
        this.intelligence = intelligence;
        this.ability_damage = ability_damage;
    }

    //Creating The Attribute Modifiers for this to work
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            if (this.intelligence > 0) {
                builder.put(ExtraAttributes.INTELLIGENCE.get(), AttributeHelper.createModifier("SpellItemIntelligence", AttributeModifier.Operation.ADDITION, intelligence));
            }
            if (this.ability_damage > 0) {
                builder.put(ExtraAttributes.ABILITY_DAMAGE.get(), AttributeHelper.createModifier("SpellItemAbilityDamage", AttributeModifier.Operation.ADDITION, ability_damage));
            }
        }
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        return builder;
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        int duration = this.getUseDuration(pStack) - pRemainingUseDuration;
        Spell spell = SpellHelper.getActiveSpell(pStack);
        if (duration >= spell.castDuration()) {
            if (!SpellHelper.handleManaAndExecute(pLivingEntity, spell, pStack) || spell.getType() == Spell.Type.RELEASE) pLivingEntity.stopUsingItem();
        } else {
            switch (spell.getTarget()) {
                case SELF -> {}
                case BLOCK -> {
                    BlockHitResult result = pLevel.clip(new ClipContext(
                            pLivingEntity.getEyePosition(),
                            pLivingEntity.getLookAngle().scale(100).add(pLivingEntity.getEyePosition()),
                            ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pLivingEntity
                    ));
                    if (result.getType() == HitResult.Type.MISS) {
                        if (pLivingEntity instanceof Player player && pLevel.isClientSide) player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                        pLivingEntity.stopUsingItem();
                        break;
                    }
                    CompoundTag tag = pStack.getTag();
                    if (tag != null && tag.contains("target", Tag.TAG_LONG)) {
                        if (!BlockPos.of(tag.getLong("target")).equals(result.getBlockPos())) {
                            if (pLivingEntity instanceof Player player && pLevel.isClientSide) player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                            pLivingEntity.stopUsingItem();
                        }
                    } else pStack.getOrCreateTag().putLong("target", result.getBlockPos().asLong());
                }
                case ENTITY -> {
                    Vec3 start = pLivingEntity.getEyePosition();
                    Vec3 end = pLivingEntity.getLookAngle().scale(100).add(pLivingEntity.getEyePosition());
                    EntityHitResult result = ProjectileUtil.getEntityHitResult(pLevel, pLivingEntity,
                            start,
                            end,
                            new AABB(start, end),
                            entity -> entity != pLivingEntity,
                            0
                    );
                    if (result == null) {
                        if (pLivingEntity instanceof Player player && pLevel.isClientSide) player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                        pLivingEntity.stopUsingItem();
                        break;
                    }
                    CompoundTag tag = pStack.getTag();
                    if (tag != null && tag.contains("target", Tag.TAG_LONG)) {
                        if (tag.getInt("target") != result.getEntity().getId()) {
                            if (pLivingEntity instanceof Player player && pLevel.isClientSide) player.sendSystemMessage(Component.translatable("spell.cast.failed"));
                            pLivingEntity.stopUsingItem();
                        }
                    } else pStack.getOrCreateTag().putLong("target", result.getEntity().getId());
                }
            }
        }
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (stack.hasTag()) stack.getTag().remove("target");
        Spell spell = SpellHelper.getActiveSpell(stack);
        if (SpellHelper.canExecuteSpell(pPlayer, spell, stack)) {
            if (spell.castDuration() == 0) SpellHelper.handleManaAndExecute(pPlayer, spell, stack);
            else pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        Spell spell = SpellHelper.getActiveSpell(pStack);
        return spell.getType() == Spell.Type.CYCLE ? Integer.MAX_VALUE : spell.castDuration() + 1;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return entityLiving.isUsingItem() && entityLiving.getUsedItemHand() == hand ? HumanoidModel.ArmPose.THROW_SPEAR : null;
            }
        });
        super.initializeClient(consumer);
    }
}