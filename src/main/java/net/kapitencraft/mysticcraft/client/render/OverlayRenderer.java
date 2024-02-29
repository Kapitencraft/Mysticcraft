package net.kapitencraft.mysticcraft.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.client.render.overlay.PositionHolder;
import net.kapitencraft.mysticcraft.client.render.overlay.box.InteractiveBox;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.MultiHolder;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.RenderHolder;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.SimpleHolder;
import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.MiscRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OverlayRenderer {
    private static final Codec<OverlayRenderer> CODEC = RecordCodecBuilder.create(
            renderControllerInstance -> renderControllerInstance.group(
                    Codec.unboundedMap(IOHelper.UUID_CODEC, PositionHolder.CODEC).fieldOf("storage").forGetter(OverlayRenderer::getLocations)
            ).apply(renderControllerInstance, OverlayRenderer::fromCodec)
    );

    private static OverlayRenderer fromCodec(Map<UUID, PositionHolder> map) {
        OverlayRenderer controller = new OverlayRenderer();
        controller.loadedPositions.putAll(map);
        return controller;
    }


    private Map<UUID, PositionHolder> getLocations() {
        return MapStream.of(map).mapValues(RenderHolder::getPos).toMap();
    }

    private static final File PERSISTENT_FILE = new File(MysticcraftClient.CLIENT_FILES, "gui-locations.json");

    public static OverlayRenderer load() {
        return IOHelper.loadFile(PERSISTENT_FILE, CODEC, OverlayRenderer::new);
    }

    private final Map<UUID, RenderHolder> map = new HashMap<>();
    private final Map<UUID, PositionHolder> loadedPositions = new HashMap<>();

    static {
        OverlayRenderer controller = MysticcraftClient.getInstance().renderController;
        controller.addRenderer(UUID.fromString("589c7ac3-4dc1-487b-b4b8-90524ce97bdc"), new SimpleHolder(
                new PositionHolder( 2f, 5, 1, 1, PositionHolder.Alignment.TOP_LEFT, PositionHolder.Alignment.TOP_LEFT),
                player -> Component.literal(MathHelper.round(player.getAttributeValue(ModAttributes.MANA.get()), 1) + " [+" + MathHelper.round(player.getPersistentData().getDouble(MiscRegister.OVERFLOW_MANA_ID), 1) + " Overflow] §r / §1" + player.getAttributeValue(ModAttributes.MAX_MANA.get()) + " (+" + MathHelper.round(player.getPersistentData().getDouble("manaRegen") * 20, 2) + "/s)").withStyle(ChatFormatting.BLUE)
        ));
        controller.addRenderer(UUID.fromString("cf4eb19d-aec8-4e65-8b43-c5e573b4561b"), new MultiHolder(
                new PositionHolder(-183, 21, .75f, .75f, PositionHolder.Alignment.MIDDLE, PositionHolder.Alignment.BOTTOM_RIGHT),
                -10,
                List.of(
                        player -> Component.literal("Protection: " + getDamageProtection(player) + "%").withStyle(ChatFormatting.DARK_BLUE),
                        player -> Component.literal("Effective HP: " + MathHelper.defRound(player.getHealth() * 100 / (100 - getDamageProtection(player)))).withStyle(ChatFormatting.DARK_AQUA),
                        player -> Component.literal("Current Speed: " + cancelGravityMovement(player) + " b/s").withStyle(ChatFormatting.YELLOW)
                )
        ));
    }

    public void save() {
        IOHelper.saveFile(PERSISTENT_FILE, CODEC, this);
    }
    private static double getDamageProtection(LivingEntity living) {
        return MathHelper.defRound(100 - MathHelper.calculateDamage(100, living.getAttributeValue(Attributes.ARMOR), living.getAttributeValue(Attributes.ARMOR_TOUGHNESS)));
    }

    private static double cancelGravityMovement(LivingEntity living) {
        Vec3 delta = living.getDeltaMovement();
        if (living.isOnGround())
            delta = delta.add(0, living.getAttributeValue(ForgeMod.ENTITY_GRAVITY.get()), 0);
        return MathHelper.defRound(delta.length());
    }

    @SubscribeEvent
    public static void overlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("main", MysticcraftClient.getInstance().renderController::render);
    }

    public void fillRenderBoxes(Consumer<InteractiveBox> acceptor, LocalPlayer player, Font font, float posX, float posY) {
        this.map.values().stream().map(renderHolder -> renderHolder.newBox(posX, posY, player, font)).forEach(acceptor);
    }

    private void render(ForgeGui forgeGui, PoseStack ignored, float partialTicks, int screenWidth, int screenHeight) {
        LocalPlayer entity = Minecraft.getInstance().player;
        if (entity != null) {
            map.forEach((uuid, renderHolder) -> renderHolder.render(screenWidth, screenHeight, entity));
        }

    }

    public void addRenderer(UUID uuid, RenderHolder holder) {
        if (loadedPositions.containsKey(uuid))
            holder.getPos().copy(loadedPositions.get(uuid));
        map.put(uuid, holder);
    }
}
