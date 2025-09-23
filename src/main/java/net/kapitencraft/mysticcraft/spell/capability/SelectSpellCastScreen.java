package net.kapitencraft.mysticcraft.spell.capability;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SelectSpellCastScreen extends Screen {
    protected SelectSpellCastScreen() {
        super(Component.translatable("spell.select.title"));
    }
}
