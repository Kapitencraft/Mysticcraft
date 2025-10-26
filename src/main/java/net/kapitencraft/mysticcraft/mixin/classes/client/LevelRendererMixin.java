package net.kapitencraft.mysticcraft.mixin.classes.client;

import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow @Final private Minecraft minecraft;

    @SuppressWarnings("DataFlowIssue")
    @Redirect(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;shouldEntityAppearGlowing(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean checkCastTarget(Minecraft instance, Entity pEntity) {
        ItemStack item = instance.player.getUseItem();
        return !item.isEmpty() && SpellHelper.isSpellTarget(item, SpellHelper.getActiveSpell(instance.player), pEntity) || minecraft.shouldEntityAppearGlowing(pEntity);
    }

    @Redirect(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getTeamColor()I"))
    private int redirectTargetColor(Entity entity) {
        Minecraft instance = this.minecraft;
        ItemStack item = instance.player.getUseItem();
        if (SpellHelper.isSpellTarget(item, SpellHelper.getActiveSpell(instance.player), entity)) return 0x007FCC;
        return entity.getTeamColor();
    }
}
