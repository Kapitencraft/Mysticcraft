package net.kapitencraft.mysticcraft.gui.screen;

import net.kapitencraft.mysticcraft.gui.ModMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public abstract class ModScreen<K extends ICapabilityProvider, T extends ModMenu<K>> extends SimpleScreen<T> {
    public ModScreen(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }
}