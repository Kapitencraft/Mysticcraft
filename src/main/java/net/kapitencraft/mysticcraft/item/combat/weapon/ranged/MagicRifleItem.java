package net.kapitencraft.mysticcraft.item.combat.weapon.ranged;

import net.kapitencraft.mysticcraft.mixin.classes.CameraMixin;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;


@Mod.EventBusSubscriber
public class MagicRifleItem extends ProjectileWeaponItem {
    private Vec3 shotPosition = Vec3.ZERO;
    private boolean aiming = false;

    private float distance = 1;

    public MagicRifleItem(Properties p_43009_) {
        super(new Properties().defaultDurability(10000).fireResistant());
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack == ItemStack.EMPTY;
    }

    private void updateShotPosition(Vec3 shotPosition) {
        this.shotPosition = shotPosition;
        Camera camera = Minecraft.getInstance().getEntityRenderDispatcher().camera;
        CameraMixin mixin = (CameraMixin) camera;
        mixin.setPosition(shotPosition);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 60;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_41432_, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (aiming) {

        }
        return InteractionResultHolder.fail(stack);
    }

    @SubscribeEvent
    public static void registerRifleAimEvent(InputEvent.MouseScrollingEvent event) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (localPlayer != null && clientLevel != null) {
            if (localPlayer.getUseItem() != ItemStack.EMPTY) {
                if (localPlayer.getUseItem().getItem() instanceof MagicRifleItem magicRifleItem) {
                    magicRifleItem.distance += event.getScrollDelta() / 2;
                    magicRifleItem.updateShotPosition(localPlayer.getViewVector(1).scale(magicRifleItem.distance));
                }
            }
        }
    }

}