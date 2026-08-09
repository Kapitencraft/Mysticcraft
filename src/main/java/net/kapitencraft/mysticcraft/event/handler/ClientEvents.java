package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.kap_lib.enchantment.event.custom.RegisterEnchantmentApplicableCharsEvent;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.item.combat.armor.client.ArmorClientExtension;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.SimpleModelProvider;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.ModKeyMappings;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.*;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ShortBowItem;
import net.kapitencraft.mysticcraft.network.packets.C2S.UseShortBowPacket;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.spell.capability.SelectSpellCastScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Map;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void sendLeftClickShortBow(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (event.isAttack() && player != null && player.getMainHandItem().getItem() instanceof ShortBowItem) {
            event.setCanceled(true);
            player.swing(InteractionHand.MAIN_HAND);
            PacketDistributor.sendToServer(UseShortBowPacket.INSTANCE);
        }
    }

    @SubscribeEvent
    public static void onScreenKeyInput(InputEvent.Key event) {
        if (ModKeyMappings.SELECT_SPELL_CAST.consumeClick()) {
            Minecraft.getInstance().setScreen(new SelectSpellCastScreen());
        }
    }

    @SubscribeEvent
    public static void onRegisterEnchantmentApplicableChars(RegisterEnchantmentApplicableCharsEvent event) {
        event.register(ModItems.DIAMOND_HAMMER.get(), MysticcraftMod.res("item/hammer/diamond"));
    }

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        registerArmorExtension(ModItems.CRIMSON_ARMOR, event, new SimpleModelProvider(CrimsonArmorModel::createBodyLayer, CrimsonArmorModel::new));
        registerArmorExtension(ModItems.SHADOW_ASSASSIN_ARMOR, event, new SimpleModelProvider(ShadowAssassinArmorModel::createBodyLayer, ShadowAssassinArmorModel::new));
        registerArmorExtension(ModItems.WIZARD_CLOAK_ARMOR, event, new SimpleModelProvider(WizardCloakModel::createBodyLayer, WizardCloakModel::new));
        event.registerItem(new ArmorClientExtension(new SimpleModelProvider(WizardHatModel::createBodyLayer, WizardHatModel::new)), ModItems.WIZARD_HAT);
        registerArmorExtension(ModItems.FROZEN_BLAZE_ARMOR, event, new SimpleModelProvider(FrozenBlazeArmorModel::createBodyLayer, FrozenBlazeArmorModel::new));
    }

    @SuppressWarnings("unchecked")
    private static <T extends AbstractArmorItem> void registerArmorExtension(Map<ArmorItem.Type, DeferredItem<T>> map, RegisterClientExtensionsEvent event, ArmorModelProvider provider) {
        event.registerItem(new ArmorClientExtension(provider), map.values().toArray(DeferredItem[]::new));
    }
}
