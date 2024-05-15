package net.kapitencraft.mysticcraft.gui.browse.browsables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.kapitencraft.mysticcraft.client.BannerPatternRenderer;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.gui.browse.BrowserScreen;
import net.kapitencraft.mysticcraft.gui.widgets.menu.scroll.ScrollableMenu;
import net.kapitencraft.mysticcraft.gui.widgets.menu.scroll.elements.GuildPlayerElement;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.requests.GuildDataRequestable;
import net.kapitencraft.mysticcraft.helpers.CollectorHelper;
import net.kapitencraft.mysticcraft.init.ModDataRequesters;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GuildScreen extends BrowserScreen<Guild> {
    public GuildScreen(Guild guild) {
        super(guild);
        this.requestData();
    }

    private final List<Pair<Holder<BannerPattern>, DyeColor>> bannerPatterns = generatePatterns();
    private final List<GuildDataRequestable.GuildPlayerData> memberInfos = new ArrayList<>();
    private boolean awaitsServerData;
    private ScrollableMenu players;

    private void requestData() {
        ClientPacketListener listener = Minecraft.getInstance().getConnection();
        if (listener == null) return;
        Map<UUID, PlayerInfo> data = new HashMap<>();
        UUID[] missing = this.browsable.getAllPlayerIds()
                .stream()
                .collect(CollectorHelper.toValueMappedStream(listener::getPlayerInfo))
                .filterValues(Objects::isNull, data::put)
                .toMap().keySet().toArray(UUID[]::new);
        data.values().stream().map(GuildDataRequestable.GuildPlayerData::new).forEach(memberInfos::add);
        if (missing.length == 0) return;
        MysticcraftClient.getInstance().handler.createRequest(ModDataRequesters.GUILD_DATA.get(), missing, this::acceptRequestResults);
        this.awaitsServerData = true;
    }

    private void acceptRequestResults(GuildDataRequestable.GuildPlayerData[] data) {
        this.memberInfos.addAll(Arrays.asList(data));
        this.awaitsServerData = false;
    }

    @Override
    protected void init() {
        ScrollableMenu menu = new ScrollableMenu(this.leftPos + 8, this.topPos + 72, this, 5, 50);
        this.memberInfos.stream().map(GuildPlayerElement::new).forEach(menu::addScrollable);
        this.players = menu;
    }

    private List<Pair<Holder<BannerPattern>, DyeColor>> generatePatterns() {
        return BannerPatternRenderer.fromStack(getBanner());
    }

    private ItemStack getBanner() {
        return this.browsable.getBanner();
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partial) {
        super.render(stack, mouseX, mouseY, partial);
        if (awaitsServerData) {
            renderFetchingString(stack);
        } else {
            this.font.draw(stack, this.title, this.leftPos + 95, this.topPos + 12, -1);
            this.font.draw(stack, Component.translatable("gui.guilds.members_title"), this.leftPos + 8, this.topPos + 58, -1);
            this.players.render(stack, mouseX, mouseY, partial);
            renderBanner(stack);
        }
    }

    @SuppressWarnings("all")
    private void renderBanner(PoseStack pPoseStack) {
        BannerPatternRenderer.renderBanner(pPoseStack, leftPos + 14, topPos + 11, bannerPatterns, 40);
    }
}