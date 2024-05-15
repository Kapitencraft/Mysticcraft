package net.kapitencraft.mysticcraft.gui.screen.guild;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.screen.DefaultBackgroundScreen;
import net.kapitencraft.mysticcraft.gui.widgets.ExtendedEditBox;
import net.kapitencraft.mysticcraft.gui.widgets.menu.scroll.ScrollableMenu;
import net.kapitencraft.mysticcraft.gui.widgets.menu.scroll.elements.GuildElement;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;


//TODO add search bar
public class SelectGuildScreen extends DefaultBackgroundScreen {
    private final CreateGuildScreen source;
    protected SelectGuildScreen(CreateGuildScreen source) {
        super(Component.translatable("gui.select_guild"));
        this.source = source;
    }

    private ExtendedEditBox searchField;

    @Override
    protected void init() {
        super.init();
        ScrollableMenu menu = new ScrollableMenu(this.leftPos + 5, this.topPos + 25, this, 10, this.getImageWidth() - 10);
        List<Guild> guilds = fetchGuilds();
        List<String> guildNames = guilds.stream().map(Guild::display).toList();
        guilds.stream().map(GuildElement::new).forEach(menu::addScrollable);
        this.addRenderableWidget(menu);
        this.searchField = new ExtendedEditBox(this.font, this.leftPos + 30, this.topPos + 15, this.getImageWidth() - 60, 10, Component.empty(),
                guildNames::contains);
        this.searchField.setHint(Component.translatable("gui.search"));
        this.addRenderableWidget(searchField);
    }

    private static List<Guild> fetchGuilds() {
        GuildHandler handler = GuildHandler.getClientInstance();
        return handler.allGuilds().stream().filter(Guild::isPublic).sorted(NAME_SORTER).toList();
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        GuiComponent.drawCenteredString(pPoseStack, this.font, this.title, this.leftPos + this.getImageWidth() / 2, this.topPos + 5, -1);
    }

    private static final Comparator<Guild> NAME_SORTER = Comparator.comparing(Guild::getGuildName);
}
