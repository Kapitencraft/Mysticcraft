package net.kapitencraft.mysticcraft.gui.screen.guild;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.gui.browse.browsables.GuildScreen;
import net.kapitencraft.mysticcraft.gui.screen.AwaitScreen;
import net.kapitencraft.mysticcraft.gui.screen.DefaultBackgroundScreen;
import net.kapitencraft.mysticcraft.gui.widgets.Checkbox;
import net.kapitencraft.mysticcraft.gui.widgets.CreateBannerWidget;
import net.kapitencraft.mysticcraft.gui.widgets.ExtendedEditBox;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.guild.requests.CreateGuildRequestable;
import net.kapitencraft.mysticcraft.init.ModDataRequesters;
import net.kapitencraft.mysticcraft.networking.SimpleSuccessResult;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

public class CreateGuildScreen extends DefaultBackgroundScreen {
    private static final Component JOIN_GUILD_MSG = Component.translatable("gui.join_guild");
    private static final Component TITLE_MSG = Component.translatable("gui.create_guild");
    private static final Component GUILD_PUBLIC_MSG = Component.translatable("gui.guild_public").withStyle(Style.EMPTY
            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("gui.guild_public.desc")))
    );

    public CreateGuildScreen() {
        super(TITLE_MSG);
    }

    private CreateBannerWidget widget;
    private ExtendedEditBox nameBox;
    private Checkbox shouldBePublic;

    @Override
    protected void init() {
        super.init();
        this.children().clear();
        int halfWidth = this.leftPos + this.getImageWidth() / 2;
        this.updateJoinButton(halfWidth);
        this.updatePublicSetting(halfWidth);
        this.updateNameBox();
        this.updateCreateButton(halfWidth);
        this.updateBannerEditor();
    }

    @SuppressWarnings("all")
    private void updateJoinButton(int halfWidth) {
        int width = this.font.width(JOIN_GUILD_MSG);
        int xStart = halfWidth - width / 2;
        Button joinGuildButton = new PlainTextButton(xStart, this.topPos + this.getImageHeight() - 12, width, 10, JOIN_GUILD_MSG, pButton ->
                this.minecraft.setScreen(new SelectGuildScreen(this)),
                this.font
        );
        this.addRenderableWidget(joinGuildButton);
    }
    private void updatePublicSetting(int halfWidth) {
        int publicBoxX = halfWidth - (12 + this.font.width(GUILD_PUBLIC_MSG)) / 2;
        boolean wasChecked = shouldBePublic != null && shouldBePublic.isChecked();
        shouldBePublic = new Checkbox(publicBoxX, this.topPos + this.getImageHeight() - 32, GUILD_PUBLIC_MSG, true, font, wasChecked);
        this.addRenderableWidget(shouldBePublic);
    }
    private void updateNameBox() {
        this.nameBox = new ExtendedEditBox(this.font, this.leftPos + 5, this.topPos + 15, this.getImageWidth() - 10, 10, this.nameBox, Component.empty(), s ->
                GuildHandler.getClientInstance().getGuild(s) == null
        );
        nameBox.setHint(Component.translatable("gui.set_guild_name"));
        this.addRenderableWidget(nameBox);
    }
    private void updateBannerEditor() {
        this.widget = new CreateBannerWidget(this.leftPos + 5, this.topPos + 26, this.getImageWidth() - 10, this.getImageHeight() - 61);
        this.addRenderableWidget(this.widget);
    }

    @SuppressWarnings("all")
    private void updateCreateButton(int halfWidth) {
        int width = this.font.width(this.title);
        int xStart = halfWidth - width / 2;
        Button createGuildButton = new PlainTextButton(xStart, this.topPos + this.getImageHeight() - 22, this.width, 10, this.title,
                pButton -> {
            if (this.nameBox.getValue().isEmpty()) Minecraft.getInstance().player.sendSystemMessage(Component.translatable("gui.guild.missing_name").withStyle(ChatFormatting.RED));
            else {
                MysticcraftClient.getInstance().handler.createRequest(ModDataRequesters.CREATE_GUILD.get(), new CreateGuildRequestable.CreateGuildData(this.nameBox.getValue(), this.shouldBePublic.isChecked(), this.widget.getBanner()), this::takeData);
                this.minecraft.setScreen(new AwaitScreen(Component.translatable("gui.guild.create_fetch")));
            }
                },
                this.font
        );
        this.addRenderableWidget(createGuildButton);
    }

    @SuppressWarnings("all")
    private void takeData(SimpleSuccessResult data) {
        this.minecraft.player.sendSystemMessage(Component.translatable("guild.create." + data.id()).withStyle(data.success() ? ChatFormatting.GREEN : ChatFormatting.RED));
        if (data.success()) this.minecraft.setScreen(new GuildScreen(GuildHandler.getClientInstance().getGuildForPlayer(this.minecraft.player)));
        else this.minecraft.setScreen(null);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        drawCenteredString(pPoseStack, this.font, this.title, this.leftPos + this.getImageWidth() / 2, this.topPos + 5, -1);
    }

    @Override
    public void tick() {
        this.nameBox.tick();
    }
}