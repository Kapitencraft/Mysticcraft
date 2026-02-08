package net.kapitencraft.mysticcraft.gui.screen;

import net.kapitencraft.mysticcraft.gui.widgets.FluidTankWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

public class TestScreen extends Screen {
    public TestScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        this.addRenderableWidget(new FluidTankWidget(100, 20, 35, 100, () -> new FluidStack(Fluids.WATER, 879), () -> 1000));
        super.init();
    }
}
