package net.kapitencraft.mysticcraft.gui.screen.guild;

import net.kapitencraft.mysticcraft.gui.screen.DefaultBackgroundScreen;
import net.kapitencraft.mysticcraft.gui.screen.menu.scroll.ScrollableMenu;
import net.kapitencraft.mysticcraft.gui.screen.menu.scroll.elements.GuildElement;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.minecraft.network.chat.Component;

import java.util.Comparator;
import java.util.List;


//TODO add search bar
public class SelectGuildScreen extends DefaultBackgroundScreen {
    protected SelectGuildScreen() {
        super(Component.translatable("gui.select_guild"));
    }


    @Override
    protected void init() {
        super.init();
        ScrollableMenu menu = new ScrollableMenu(this.leftPos + 5, this.topPos + 10, this, 10, this.getImageWidth() - 10);
        fetchGuilds().stream().map(GuildElement::new).forEach(menu::addScrollable);
        this.renderables.add(menu);
    }

    private static List<Guild> fetchGuilds() {
        GuildHandler handler = GuildHandler.getClientInstance();
        return handler.allGuilds().stream().filter(Guild::isPublic).sorted(NAME_SORTER).toList();
    }

    private static final Comparator<Guild> NAME_SORTER = Comparator.comparing(Guild::getGuildName);
}
