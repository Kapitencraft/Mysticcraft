package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow;

import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TallinBow extends ShortBowItem {
    public TallinBow() {
        super(new Properties().rarity(ModRarities.LEGENDARY).durability(1500));
    }

    @Override
    public int getKB() {
        return 1;
    }

    @Override
    public void createArrows(@NotNull ItemStack bow, @NotNull Level world, @NotNull LivingEntity archer) {
        super.createArrows(bow, world, archer);
        createArrowProperties(archer, bow, this.getKB(), archer.getXRot(), archer.getYRot() + 4f);
        createArrowProperties(archer, bow, this.getKB(), archer.getXRot(), archer.getYRot() - 4f);
    }

    @Override
    public double getDamage() {
        return 3;
    }

    @Override
    public float getShotCooldown() {
        return 0.3f;
    }
}
