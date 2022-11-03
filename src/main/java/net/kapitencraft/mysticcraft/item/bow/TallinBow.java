package net.kapitencraft.mysticcraft.item.bow;

import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TallinBow extends ShortBowItem{
    public TallinBow() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT).rarity(FormattingCodes.LEGENDARY).durability(1500));
    }

    @Override
    public int getKB() {
        return 1;
    }

    @Override
    protected void createArrows(@NotNull ItemStack bow, @NotNull Level world, @NotNull LivingEntity archer) {
        super.createArrows(bow, world, archer);
        this.createArrowProperties(archer, bow, this.getKB(), archer.getXRot(), archer.getYRot() + 12f);
        this.createArrowProperties(archer, bow, this.getKB(), archer.getXRot(), archer.getYRot() - 12f);
    }

    @Override
    public double getDamage() {
        return 7;
    }

    @Override
    public float getShotCooldown() {
        return 0.3f;
    }
}
