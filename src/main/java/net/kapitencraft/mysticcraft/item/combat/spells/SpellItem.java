package net.kapitencraft.mysticcraft.item.combat.spells;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
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
    public static final TabGroup SPELL_GROUP = TabGroup.builder().tab(ModCreativeModTabs.SPELLS).tab(ModCreativeModTabs.WEAPONS_AND_TOOLS).build();

    private final int maxMana;
    private final int magicDamage;

    public SpellItem(Properties p_41383_, int damage, float speed, int maxMana, int magicDamage) {
        super(ModTiers.SPELL_TIER, damage, speed,  p_41383_.stacksTo(1));
        this.maxMana = maxMana;
        this.magicDamage = magicDamage;
    }

    //Creating The Attribute Modifiers for this to work
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            if (this.maxMana > 0) {
                builder.put(ExtraAttributes.MAX_MANA.get(), AttributeHelper.createModifier("SpellItemIntelligence", AttributeModifier.Operation.ADDITION, maxMana));
            }
            if (this.magicDamage > 0) {
                builder.put(ExtraAttributes.MAGIC_DAMAGE.get(), AttributeHelper.createModifier("SpellItemAbilityDamage", AttributeModifier.Operation.ADDITION, magicDamage));
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