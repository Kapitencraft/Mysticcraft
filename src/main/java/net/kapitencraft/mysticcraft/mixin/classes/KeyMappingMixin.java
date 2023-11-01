package net.kapitencraft.mysticcraft.mixin.classes;


import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.extensions.IForgeKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin implements IForgeKeyMapping {

    private static boolean isStunned() {
        LocalPlayer local = Minecraft.getInstance().player;
        if (local != null && local.hasEffect(ModMobEffects.STUN.get())) {
            return true;
        }
        return false;
    }

    /**
     * @reason stun effect
     * @author Kapitencraft
     */
    @Overwrite
    public boolean consumeClick() {
        if (isStunned()) {
            this.setClickCount(0);
            return false;
        }
        if (this.getClickCount() == 0) {
            return false;
        } else {
            MathHelper.add(this::getClickCount, this::setClickCount, -1);
            return true;
        }

    }


    /**
     * @reason stun effect
     * @author Kapitencraft
     */
    @Overwrite
    public boolean isDown() {
        return !isStunned() && this.getIsDown() && isConflictContextAndModifierActive();
    }

    @Accessor
    abstract public boolean getIsDown();

    @Accessor
    abstract public int getClickCount();

    @Accessor
    abstract public void setClickCount(int clickCount);
}