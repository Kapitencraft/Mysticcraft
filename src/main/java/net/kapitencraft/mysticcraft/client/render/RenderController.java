package net.kapitencraft.mysticcraft.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.client.render.holder.MultiHolder;
import net.kapitencraft.mysticcraft.client.render.holder.RenderHolder;
import net.kapitencraft.mysticcraft.client.render.holder.SimpleHolder;
import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.MiscRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderController {
    private static final int WHITE_COLOR = MathHelper.RGBtoInt(255, 255, 255);
    private static final Codec<RenderController> CODEC = RecordCodecBuilder.create(
            renderControllerInstance -> renderControllerInstance.group(
                    Codec.unboundedMap(IOHelper.UUID_CODEC, PositionHolder.CODEC).fieldOf("storage").forGetter(RenderController::getLocations)
            ).apply(renderControllerInstance, RenderController::fromCodec)
    );

    private static RenderController fromCodec(Map<UUID, PositionHolder> map) {
        RenderController controller = new RenderController();
        controller.loadedPositions.putAll(map);
        return controller;
    }


    private Map<UUID, PositionHolder> getLocations() {
        return MapStream.of(map).mapValues(RenderHolder::getPos).toMap();
    }

    //TODO fix rendering all over the place

    private static final File PERSISTENT_FILE = new File(MysticcraftClient.CLIENT_FILES, "gui-locations.json");

    public static RenderController load() {
        return IOHelper.loadFile(PERSISTENT_FILE, CODEC, RenderController::new);
    }

    private final Map<UUID, RenderHolder> map = new HashMap<>();
    private final Map<UUID, PositionHolder> loadedPositions = new HashMap<>();

    static {
        RenderController controller = MysticcraftClient.getInstance().renderController;
        controller.addRenderer(UUID.fromString("589c7ac3-4dc1-487b-b4b8-90524ce97bdc"), new SimpleHolder(
                new PositionHolder(-203, -116),
                player -> Component.literal(MathHelper.round(player.getAttributeValue(ModAttributes.MANA.get()), 1) + " [+" + MathHelper.round(player.getPersistentData().getDouble(MiscRegister.OVERFLOW_MANA_ID), 1) + " Overflow] §r / §1" + player.getAttributeValue(ModAttributes.MAX_MANA.get()) + " (+" + MathHelper.round(player.getPersistentData().getDouble("manaRegen") * 20, 2) + "/s)").withStyle(ChatFormatting.BLUE),
                RenderType.BIG
        ));
        controller.addRenderer(UUID.fromString("cf4eb19d-aec8-4e65-8b43-c5e573b4561b"), new MultiHolder(
                new PositionHolder(-90, 330),
                RenderType.SMALL,
                -10,
                List.of(
                        player -> Component.literal("Protection: " + getDamageProtection(player) + "%").withStyle(ChatFormatting.DARK_BLUE),
                        player -> Component.literal("Effective HP: " + MathHelper.defRound(player.getHealth() * 100 / (100 - getDamageProtection(player)))).withStyle(ChatFormatting.DARK_AQUA),
                        player -> Component.literal("Current Speed: " + MathHelper.defRound(player.getDeltaMovement().length()) + " b/s").withStyle(ChatFormatting.YELLOW)
                )
        ));
    }

    public void save() {
        IOHelper.saveFile(PERSISTENT_FILE, CODEC, this);
    }
    private static double getDamageProtection(LivingEntity living) {
        return MathHelper.defRound(100 - MathHelper.calculateDamage(100, living.getAttributeValue(Attributes.ARMOR), living.getAttributeValue(Attributes.ARMOR_TOUGHNESS)));
    }

    public void renderSavable(LocalPlayer player, Font font) {
        map.values().forEach(renderHolder -> {
            List<Vec2> vertexPoints = getVertexPoints(renderHolder, player, font);
            for (Vec2 loc : vertexPoints) {
                Gui.fill(new PoseStack(), (int) (loc.x - 3), (int) (loc.y - 3), (int) (loc.x + 3), (int) (loc.y + 3), WHITE_COLOR);
            }

        });
    }

    private static List<Vec2> getVertexPoints(RenderHolder renderHolder, LocalPlayer player, Font font) {
        PositionHolder holder = renderHolder.getPos();
        float x = holder.getX();
        float y = holder.getY();
        return List.of(
                new Vec2(x, y),
                new Vec2(x, y + renderHolder.getHeight(player, font)),
                new Vec2(x + renderHolder.getWidth(player, font), y),
                new Vec2(x + renderHolder.getWidth(player, font), y)
        );
    }

    @SubscribeEvent
    public static void overlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("main", MysticcraftClient.getInstance().renderController::render);
    }

    private void render(ForgeGui forgeGui, PoseStack ignored, float partialTicks, int screenWidth, int screenHeight) {
        int posX = screenWidth / 2;
        int posY = screenHeight / 2;
        LocalPlayer entity = Minecraft.getInstance().player;
        if (entity != null) {
            map.forEach((uuid, renderHolder) -> renderHolder.render(posX, posY, entity));
        }

    }

    public void addRenderer(UUID uuid, RenderHolder holder) {
        if (loadedPositions.containsKey(uuid))
            holder.getPos().copy(loadedPositions.get(uuid));
        map.put(uuid, holder);
    }


    public static class PositionHolder {
        private static final Codec<PositionHolder> CODEC = RecordCodecBuilder.create(positionHolderInstance ->
                positionHolderInstance.group(
                        Codec.FLOAT.fieldOf("x").forGetter(PositionHolder::getX),
                        Codec.FLOAT.fieldOf("y").forGetter(PositionHolder::getY),
                        Codec.FLOAT.optionalFieldOf("sX", 1f).forGetter(PositionHolder::getSX),
                        Codec.FLOAT.optionalFieldOf("sY", 1f).forGetter(PositionHolder::getSY)
                ).apply(positionHolderInstance, PositionHolder::new)
        );
        private float x;
        private float y;
        private float sX;
        private float sY;

        public PositionHolder(float x, float y) {
            this.x = x;
            this.y = y;
            this.sX = 1;
            this.sY = 1;
        }

        public PositionHolder(float x, float y, float sX, float sY) {
            this(x, y);
            this.sX = sX;
            this.sY = sY;
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public void setSX(float sX) {
            this.sX = sX;
        }

        public void setSY(float sY) {
            this.sY = sY;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public PositionHolder copy(PositionHolder other) {
            this.x = other.x;
            this.y = other.y;
            this.sX = other.sX;
            this.sY = other.sY;
            return this;
        }

        public float getSX() {
            return sX;
        }

        public float getSY() {
            return sY;
        }
    }

    public enum RenderType implements StringRepresentable {
        BIG("big", stack -> {}, stack -> {}),
        MEDIUM("medium", stack -> stack.scale(0.9f, 0.9f, 0.9f), stack -> stack.scale(1/0.9f, 1/0.9f, 1/0.9f)),
        SMALL("small", stack -> stack.scale(0.65f, 0.65f, 0.65f), stack -> stack.scale(1/0.65f, 1/0.65f, 1/0.65f));

        private static final StringRepresentable.EnumCodec<RenderType> CODEC = StringRepresentable.fromEnum(RenderType::values);

        public final Consumer<PoseStack> onInit;
        public final Consumer<PoseStack> onDone;
        private final String name;

        RenderType(String name, Consumer<PoseStack> onInit, Consumer<PoseStack> onDone) {
            this.onInit = onInit;
            this.onDone = onDone;
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
