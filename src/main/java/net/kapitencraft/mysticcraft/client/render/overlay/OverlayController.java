package net.kapitencraft.mysticcraft.client.render.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.client.render.overlay.box.InteractiveBox;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.MultiHolder;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.RenderHolder;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.SimpleHolder;
import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.event.custom.mod.RegisterOverlaysEvent;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
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
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OverlayController {
    private static final Codec<OverlayController> CODEC = RecordCodecBuilder.create(
            renderControllerInstance -> renderControllerInstance.group(
                    Codec.unboundedMap(IOHelper.UUID_CODEC, PositionHolder.CODEC).fieldOf("storage").forGetter(OverlayController::getLocations)
            ).apply(renderControllerInstance, OverlayController::fromCodec)
    );

    private static OverlayController fromCodec(Map<UUID, PositionHolder> map) {
        OverlayController controller = new OverlayController();
        controller.loadedPositions.putAll(map);
        return controller;
    }

    private Map<UUID, PositionHolder> getLocations() {
        return MapStream.of(map).mapValues(RenderHolder::getPos).mapKeys(OverlayLocation::getUUID).toMap();
    }

    private static final File PERSISTENT_FILE = new File(MysticcraftClient.CLIENT_FILES, "gui-locations.json");

    public static OverlayController load() {
        return IOHelper.loadFile(PERSISTENT_FILE, CODEC, OverlayController::new);
    }

    private final Map<OverlayLocation, Function<PositionHolder, RenderHolder>> constructors = new HashMap<>();
    public final Map<OverlayLocation, RenderHolder> map = new HashMap<>();
    private final Map<UUID, PositionHolder> loadedPositions = new HashMap<>();

    private OverlayController() {
        this.register();
    }

    private void register() {
        this.createRenderer(OverlayLocations.MANA, positionHolder -> new SimpleHolder(
                positionHolder,
                player -> Component.literal(MathHelper.round(player.getAttributeValue(ModAttributes.MANA.get()), 1) + " [+" + MathHelper.round(player.getPersistentData().getDouble(MiscRegister.OVERFLOW_MANA_ID), 1) + " Overflow] §r / §1" + player.getAttributeValue(ModAttributes.MAX_MANA.get()) + " (+" + MathHelper.round(player.getPersistentData().getDouble("manaRegen") * 20, 2) + "/s)").withStyle(ChatFormatting.BLUE)
        ));
        this.createRenderer(OverlayLocations.STATS, positionHolder -> new MultiHolder(
                positionHolder,
                -10,
                List.of(
                        player -> Component.literal("Protection: " + getDamageProtection(player) + "%").withStyle(ChatFormatting.DARK_BLUE),
                        player -> Component.literal("Effective HP: " + MathHelper.defRound(player.getHealth() * 100 / (100 - getDamageProtection(player)))).withStyle(ChatFormatting.DARK_AQUA),
                        player -> Component.literal("Current Speed: " + cancelGravityMovement(player) + " b/s").withStyle(ChatFormatting.YELLOW)
                )
        ));
        ModEventFactory.fireModEvent(new RegisterOverlaysEvent(this::createRenderer));
        construct();
    }

    private void createRenderer(OverlayLocation provider, Function<PositionHolder, RenderHolder> constructor) {
        if (this.constructors.containsKey(provider))
            throw new IllegalStateException("detected double registered Overlay with UUID '" + provider.getUUID() + "'");
        this.constructors.put(provider, constructor);
    }

    public static void save() {
        IOHelper.saveFile(PERSISTENT_FILE, CODEC, MysticcraftClient.getInstance().overlayController);
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
        event.registerAboveAll("main", MysticcraftClient.getInstance().overlayController::render);
    }

    public void fillRenderBoxes(Consumer<InteractiveBox> acceptor, LocalPlayer player, Font font, float width, float height) {
        this.map.values().stream().map(renderHolder -> renderHolder.newBox(width, height, player, font)).forEach(acceptor);
    }

    private void render(ForgeGui forgeGui, PoseStack ignored, float partialTicks, int screenWidth, int screenHeight) {
        LocalPlayer entity = Minecraft.getInstance().player;
        if (entity != null) {
            map.forEach((uuid, renderHolder) -> renderHolder.render(screenWidth, screenHeight, entity));
        }
    }

    private void construct() {
        this.constructors.forEach((location, constructor) -> {
            PositionHolder holder = MiscHelper.nonNullOr(this.loadedPositions.get(location.getUUID()), location.getDefault());
            this.map.put(location, constructor.apply(holder));
        });
    }

    @SuppressWarnings("all")
    public void reset(RenderHolder dedicatedHolder) {
        if (this.map.containsValue(dedicatedHolder)) {
            OverlayLocation location = CollectionHelper.getKeyForValue(this.map, dedicatedHolder);
            dedicatedHolder.getPos().copy(location.getDefault());
            return;
        }
        throw new IllegalStateException("attempted to reset non-existing Holder");
    }

    public static void resetAll() {
        OverlayController controller = MysticcraftClient.getInstance().overlayController;
        Collection<OverlayLocation> locations = controller.constructors.keySet();
        locations.forEach(location -> {
            RenderHolder holder = controller.map.get(location);
            holder.getPos().copy(location.getDefault());
        });
    }
}