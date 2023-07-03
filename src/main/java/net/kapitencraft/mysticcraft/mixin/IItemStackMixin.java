package net.kapitencraft.mysticcraft.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

public interface IItemStackMixin {
    List<Component> getTooltipLines(@Nullable Player p_41652_, TooltipFlag p_41653_);

    Component getHoverName();
}