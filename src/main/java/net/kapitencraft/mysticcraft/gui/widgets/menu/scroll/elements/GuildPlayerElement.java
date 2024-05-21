package net.kapitencraft.mysticcraft.gui.widgets.menu.scroll.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.gui.widgets.menu.Menu;
import net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down.DropDownMenu;
import net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down.elements.ButtonElement;
import net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down.elements.TimeSelectorElement;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.guild.requests.GuildDataRequestable;
import net.kapitencraft.mysticcraft.init.ModDataRequesters;
import net.kapitencraft.mysticcraft.networking.RequestHandler;
import net.kapitencraft.mysticcraft.networking.SimpleSuccessResult;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GuildPlayerElement extends ScrollElement {
    private final ResourceLocation skin;
    private final String name;
    private final UUID playerUUID;

    public GuildPlayerElement(PlayerInfo info) {
        this.name = info.getProfile().getName();
        this.skin = info.getSkinLocation();
        this.playerUUID = info.getProfile().getId();
    }

    public GuildPlayerElement(GuildDataRequestable.GuildPlayerData data) {
        this.name = data.getName();
        this.skin = data.getSkin();
        this.playerUUID = data.getUUID();
    }


    @Override
    public int getWidth() {
        return 12 + Minecraft.getInstance().font.width(this.name);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderTexture(0, this.skin);
        PlayerFaceRenderer.draw(pPoseStack, this.x + 1, this.y + 1, 8);
        Minecraft.getInstance().font.draw(pPoseStack, this.name, this.x + 10, this.y + 1, -1);
    }

    @Override
    public Menu createMenu(int x, int y) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.getUUID() == playerUUID) return null;
        Guild.IRank rank = GuildHandler.getClientInstance().getGuildForPlayer(player)
                .getRank(player);
        DropDownMenu menu = new DropDownMenu(x, y, this);
        RequestHandler handler = MysticcraftClient.getInstance().handler;
        if (rank.getPermissionLevel() >= 2) menu.addElement(listElement -> new TimeSelectorElement(listElement, menu, Component.translatable("gui.guild.set_muted"), aLong ->
                handler.createRequest(ModDataRequesters.MUTE_MEMBER.get(), new Pair<>(this.playerUUID, aLong), simpleSuccessResult ->
                        sendMessage(player, "mute_member", simpleSuccessResult)
                )
        ));
        if (rank.getPermissionLevel() >= 3) menu.addElement(listElement -> new ButtonElement(listElement, menu, Component.translatable("gui.guild.kick_member"), () ->
                handler.createRequest(ModDataRequesters.KICK_MEMBER.get(), this.playerUUID, simpleSuccessResult ->
                        sendMessage(player, "kick_member", simpleSuccessResult)
                )
        ));
        if (rank.getPermissionLevel() >= 4) menu.addElement(listElement -> new TimeSelectorElement(listElement, menu, Component.translatable("gui.guild.ban_member"), aLong ->
                handler.createRequest(ModDataRequesters.BAN_PLAYER.get(), new Pair<>(this.playerUUID, aLong), simpleSuccessResult ->
                        sendMessage(player, "ban_player", simpleSuccessResult)
                )
        ));
        return menu;
    }

    private void sendMessage(Player player, String id, SimpleSuccessResult simpleSuccessResult) {
        player.sendSystemMessage(Component.translatable("gui.guild." + id + "." + simpleSuccessResult.id()).withStyle(simpleSuccessResult.success() ? ChatFormatting.GREEN : ChatFormatting.RED));
    }
}
