package net.kapitencraft.mysticcraft.gui.screen.guild;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.gui.browse.browsables.GuildScreen;
import net.kapitencraft.mysticcraft.gui.screen.DefaultBackgroundScreen;
import net.kapitencraft.mysticcraft.gui.widgets.Checkbox;
import net.kapitencraft.mysticcraft.gui.widgets.ExtendedEditBox;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.guild.requests.CreateGuildRequestable;
import net.kapitencraft.mysticcraft.init.ModDataRequesters;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
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

    private Button joinGuildButton;
    private Button createGuildButton;
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
        this.addWidget(nameBox);
    }

    @SuppressWarnings("all")
    private void updateJoinButton(int halfWidth) {
        int width = this.font.width(JOIN_GUILD_MSG);
        int xStart = halfWidth - width / 2;
        joinGuildButton = new PlainTextButton(xStart, this.topPos + this.getImageHeight() - 12, width, 10, JOIN_GUILD_MSG, pButton ->
                this.minecraft.setScreen(new SelectGuildScreen()),
                this.font
        );
        this.addWidget(joinGuildButton);
    }
    private void updatePublicSetting(int halfWidth) {
        int publicBoxX = halfWidth - (12 + this.font.width(GUILD_PUBLIC_MSG)) / 2;
        boolean wasChecked = shouldBePublic != null && shouldBePublic.isChecked();
        shouldBePublic = new Checkbox(publicBoxX, this.topPos + this.getImageHeight() - 32, GUILD_PUBLIC_MSG, true, font, wasChecked);
        this.addWidget(shouldBePublic);
    }
    private void updateNameBox() {
        this.nameBox = new ExtendedEditBox(this.font, this.leftPos + 10, this.topPos + 25, this.getImageWidth() - 20, 10, this.nameBox, Component.translatable("gui.set_guild_name"), s ->
                GuildHandler.getClientInstance().getGuild(s) == null
        );
    }
    private void updateCreateButton(int halfWidth) {
        int width = this.font.width(this.title);
        int xStart = halfWidth - width / 2;
        createGuildButton = new PlainTextButton(xStart, this.topPos + this.getImageHeight() - 22, this.width, 10, this.title,
                pButton -> {
                    this.minecraft.setScreen(null);
                    MysticcraftClient.getInstance().handler.createRequest(ModDataRequesters.CREATE_GUILD.get(), new CreateGuildRequestable.CreateGuildData(this.nameBox.getValue(), this.shouldBePublic.isChecked(), ItemStack.EMPTY), this::takeData);
                    },
                this.font
        );
        this.addWidget(createGuildButton);
    }

    @SuppressWarnings("all")
    private void takeData(String data) {
        this.minecraft.player.sendSystemMessage(Component.translatable("guild.create." + data));
        this.minecraft.setScreen(new GuildScreen(GuildHandler.getClientInstance().getGuildForPlayer(this.minecraft.player)));
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        drawCenteredString(pPoseStack, this.font, this.title, this.leftPos + this.getImageWidth() / 2, this.topPos + 5, -1);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.joinGuildButton.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.shouldBePublic.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.nameBox.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.createGuildButton.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }


    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public void tick() {
        this.nameBox.tick();
    }
}
