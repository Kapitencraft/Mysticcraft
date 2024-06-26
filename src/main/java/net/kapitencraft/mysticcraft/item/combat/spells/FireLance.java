package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.client.model.ModPoses;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.lance.LanceItem;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class FireLance extends LanceItem implements ISpellItem {

    public FireLance() {
        super(ModTiers.SPELL_TIER, 7, MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public void generateSlots(SpellHelper stack) {
        stack.setSlot(0, new SpellSlot(Spells.FIRE_LANCE));
    }

    @Override
    public int getSlotAmount() {
        return 1;
    }

    @Override
    public double getStrenght() {
        return 0;
    }

    @Override
    public double getCritDamage() {
        return 20;
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return entityLiving.isUsingItem() ? ModPoses.CAST_SPELL : HumanoidModel.ArmPose.ITEM;
            }

        });
    }
}