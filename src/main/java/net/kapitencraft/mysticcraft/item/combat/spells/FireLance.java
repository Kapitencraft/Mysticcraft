package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.client.model.ModPoses;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.lance.LanceItem;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.registry.Spells;
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
    public double getStrenght() {
        return 0;
    }

    @Override
    public double getCritDamage() {
        return 20;
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

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.with(Spells.FIRE_LANCE);
    }
}