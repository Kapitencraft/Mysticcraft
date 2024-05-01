package net.kapitencraft.mysticcraft.gui.browse;

import net.kapitencraft.mysticcraft.gui.screen.DefaultBackgroundScreen;
import net.minecraft.network.chat.Component;

public class LoadingScreen extends DefaultBackgroundScreen {
    private final DefaultBackgroundScreen onComplete;

    protected LoadingScreen(Component pTitle, DefaultBackgroundScreen onComplete) {
        super(pTitle);
        this.onComplete = onComplete;
    }
}
