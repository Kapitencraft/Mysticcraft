package net.kapitencraft.mysticcraft.gui.containable;

import net.kapitencraft.mysticcraft.gui.ModScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ContainableScreen extends ModScreen<ItemStack, ContainableMenu<?>> {
    //TODO add containable guis
    public ContainableScreen(ContainableMenu<?> p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected String getTextureName() {
        return "containable";
    }
}
