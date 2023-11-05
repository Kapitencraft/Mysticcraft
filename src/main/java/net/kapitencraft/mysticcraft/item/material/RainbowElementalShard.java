package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.client.ClientData;
import net.kapitencraft.mysticcraft.client.render.ColorAnimator;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.spell.Element;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RainbowElementalShard extends ElementalShard implements DyeableLeatherItem {
    private static final ColorAnimator animator = ColorAnimator.createLightRainbow(100);
    private static final Element RAINBOW = () -> "rainbow";
    public RainbowElementalShard() {
        super(RAINBOW);
    }

    @Override
    public int getColor(@NotNull ItemStack stack) {
        return MathHelper.RGBtoInt(animator.getColor(ClientData.getTime()));
    }
}
