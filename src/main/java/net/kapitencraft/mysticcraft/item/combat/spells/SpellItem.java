package net.kapitencraft.mysticcraft.item.combat.spells;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class SpellItem extends SwordItem implements ISpellItem {
    public static final TabGroup SPELL_GROUP = new TabGroup(TabRegister.TabTypes.SPELL, TabRegister.TabTypes.WEAPONS_AND_TOOLS);

    private final int intelligence;
    private final int ability_damage;

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