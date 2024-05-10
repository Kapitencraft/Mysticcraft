package net.kapitencraft.mysticcraft.mixin.duck;

import net.minecraft.world.entity.player.Player;

public interface IAttacker {

    void setOffhandAttack();
    void setMainhandAttack();
    boolean isOffhandAttack();

    static IAttacker of(Player player) {
        return (IAttacker) player;
    }
}
