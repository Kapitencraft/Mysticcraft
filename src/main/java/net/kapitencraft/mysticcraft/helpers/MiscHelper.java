package net.kapitencraft.mysticcraft.helpers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Provider;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.S2C.DamageIndicatorPacket;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MiscHelper {
    //EAST = new Rotation("x+", 90, 1);
    //WEST = new Rotation("x-",270, 3);
    //SOUTH = new Rotation("z+", 180, 2);
    //NORTH = new Rotation("z-", 360, 4);

    public static EquipmentSlot getSlotForStack(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem ? armorItem.getSlot() : EquipmentSlot.MAINHAND;
    }

    public static <T> T forDifficulty(Difficulty difficulty, T easy, T medium, T hard, T peaceful) {
        return switch (difficulty) {
            case EASY -> easy;
            case NORMAL -> medium;
            case HARD -> hard;
            case PEACEFUL -> peaceful;
        };
    }

    public static void writeVec3(FriendlyByteBuf buf, Vec3 vec3) {
        buf.writeDouble(vec3.x);
        buf.writeDouble(vec3.y);
        buf.writeDouble(vec3.z);
    }
    public static <T> void giveNullOrElse(@Nullable T t, Consumer<T> toDo) {
        if (t != null) {
            toDo.accept(t);
        }
    }

    public static Item.Properties rarity(Rarity rarity) {
        return new Item.Properties().rarity(rarity);
    }



    private static final ResourceLocation GUARDIAN_BEAM_LOCATION = new ResourceLocation("textures/entity/guardian_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_LOCATION);


    public static void renderBeam(Vec3 start, LivingEntity living, int r, int g, int b, float p_114831_, PoseStack stack, MultiBufferSource p_114833_) {
        float f1 = (float)living.level.getGameTime() + p_114831_;
        float f2 = f1 * 0.5F % 1.0F;
        stack.pushPose();
        Vec3 stop = new Vec3(living.getX(), living.getY(), living.getZ()).add(0, living.getBbHeight() * 0.5, 0);
        Vec3 vec32 = stop.subtract(start);
        float f4 = (float)(vec32.length() + 1.0D);
        vec32 = vec32.normalize();
        float f5 = (float)Math.acos(vec32.y);
        float f6 = (float)Math.atan2(vec32.z, vec32.x);
        stack.mulPose(Axis.YP.rotationDegrees((((float)Math.PI / 2F) - f6) * (180F / (float)Math.PI)));
        stack.mulPose(Axis.XP.rotationDegrees(f5 * (180F / (float)Math.PI)));
        float f7 = f1 * 0.05F * -1.5F;
        float f11 = Mth.cos(f7 + 2.3561945F) * 0.282F;
        float f12 = Mth.sin(f7 + 2.3561945F) * 0.282F;
        float f13 = Mth.cos(f7 + ((float)Math.PI / 4F)) * 0.282F;
        float f14 = Mth.sin(f7 + ((float)Math.PI / 4F)) * 0.282F;
        float f15 = Mth.cos(f7 + 3.926991F) * 0.282F;
        float f16 = Mth.sin(f7 + 3.926991F) * 0.282F;
        float f17 = Mth.cos(f7 + 5.4977875F) * 0.282F;
        float f18 = Mth.sin(f7 + 5.4977875F) * 0.282F;
        float f19 = Mth.cos(f7 + (float)Math.PI) * 0.2F;
        float f20 = Mth.sin(f7 + (float)Math.PI) * 0.2F;
        float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
        float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
        float f23 = Mth.cos(f7 + ((float)Math.PI / 2F)) * 0.2F;
        float f24 = Mth.sin(f7 + ((float)Math.PI / 2F)) * 0.2F;
        float f25 = Mth.cos(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
        float f26 = Mth.sin(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
        float f29 = -1.0F + f2;
        float f30 = f4 * 2.5F + f29;
        VertexConsumer vertexconsumer = p_114833_.getBuffer(BEAM_RENDER_TYPE);
        PoseStack.Pose pose = stack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        vertex(vertexconsumer, matrix4f, matrix3f, f19, f4, f20, r, g, b, 0.4999F, f30);
        vertex(vertexconsumer, matrix4f, matrix3f, f19, 0.0F, f20, r, g, b, 0.4999F, f29);
        vertex(vertexconsumer, matrix4f, matrix3f, f21, 0.0F, f22, r, g, b, 0.0F, f29);
        vertex(vertexconsumer, matrix4f, matrix3f, f21, f4, f22, r, g, b, 0.0F, f30);
        vertex(vertexconsumer, matrix4f, matrix3f, f23, f4, f24, r, g, b, 0.4999F, f30);
        vertex(vertexconsumer, matrix4f, matrix3f, f23, 0.0F, f24, r, g, b, 0.4999F, f29);
        vertex(vertexconsumer, matrix4f, matrix3f, f25, 0.0F, f26, r, g, b, 0.0F, f29);
        vertex(vertexconsumer, matrix4f, matrix3f, f25, f4, f26, r, g, b, 0.0F, f30);
        float f31 = 0.0F;
        if (living.tickCount % 2 == 0) {
            f31 = 0.5F;
        }
        vertex(vertexconsumer, matrix4f, matrix3f, f11, f4, f12, r, g, b, 0.5F, f31 + 0.5F);
        vertex(vertexconsumer, matrix4f, matrix3f, f13, f4, f14, r, g, b, 1.0F, f31 + 0.5F);
        vertex(vertexconsumer, matrix4f, matrix3f, f17, f4, f18, r, g, b, 1.0F, f31);
        vertex(vertexconsumer, matrix4f, matrix3f, f15, f4, f16, r, g, b, 0.5F, f31);
        stack.popPose();
    }

    private static void vertex(VertexConsumer p_253637_, Matrix4f p_253920_, Matrix3f p_253881_, float p_253994_, float p_254492_, float p_254474_, int p_254080_, int p_253655_, int p_254133_, float p_254233_, float p_253939_) {
        p_253637_.vertex(p_253920_, p_253994_, p_254492_, p_254474_).color(p_254080_, p_253655_, p_254133_, 255).uv(p_254233_, p_253939_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253881_, 0.0F, 1.0F, 0.0F).endVertex();
    }



    public static boolean awardAchievement(Player player, String achievementName) {
        if (player instanceof ServerPlayer _player) {
            ServerAdvancementManager manager = _player.server.getAdvancements();
            Advancement _adv = manager.getAdvancement(new ResourceLocation(achievementName));
            PlayerAdvancements advancements = _player.getAdvancements();
            if (_adv != null) {
                AdvancementProgress _ap = advancements.getOrStartProgress(_adv);
                if (!_ap.isDone()) {
                    for (String s : _ap.getRemainingCriteria()) advancements.award(_adv, s);
                    return true;
                }
            }
        }
        return false;
    }

    public static <T, K> T getValue(Provider<K, T> provider, T defaultValue, K key, T... values) {
        for (T t : values) {
            if (provider.provide(t).equals(key)) {
                return t;
            }
        }
        return defaultValue;
    }

    public interface delayRun {
        void run();
    }




    public static void delayed(int delayTicks, delayRun run) {
        new Object() {
            private int ticks = 0;
            private float waitTicks;

            public void start(int waitTicks) {
                this.waitTicks = waitTicks;
                MinecraftForge.EVENT_BUS.register(this);
            }
            @SubscribeEvent
            public void tick(TickEvent.ServerTickEvent event) {
                if (event.phase == TickEvent.Phase.END) {
                    this.ticks += 1;
                    if (this.ticks >= this.waitTicks)
                        run();
                }
            }
            private void run() {
                MinecraftForge.EVENT_BUS.unregister(this);
                run.run();
            }
        }.start(delayTicks);
    }

    public static boolean saveTeleportTest(Entity entity, double maxRange) {
        Vec3 targetPos = entity.getLookAngle().scale(maxRange).add(MathHelper.getPosition(entity));
        entity.move(MoverType.SELF, targetPos);
        return true;
    }

    public static boolean saveTeleport(Entity entity, double maxRange) {
        ArrayList<Vec3> lineOfSight = MathHelper.lineOfSight(entity, maxRange, 0.1);
        entity.stopRiding();
        Level level = entity.level;
        Vec3 teleportPosition;
        for (Vec3 vec3 : lineOfSight) {
            BlockPos pos = new BlockPos(vec3);
            if (level.getBlockState(pos).canOcclude() && lineOfSight.indexOf(vec3) > 0) {
                teleportPosition = lineOfSight.get(lineOfSight.indexOf(vec3) - 1);
                teleport(entity, teleportPosition);
                return true;
            } else if (lineOfSight.indexOf(vec3) == lineOfSight.size() - 1) {
                teleportPosition = lineOfSight.get(lineOfSight.indexOf(vec3) - 1);
                teleport(entity, teleportPosition);
                return true;
            }
        }
        return false;
    }

    public static void teleport(Entity entity, Vec3 teleportPosition) {
        entity.teleportTo(teleportPosition.x, teleportPosition.y, teleportPosition.z);
        entity.fallDistance = 0;
        entity.level.playSound(entity, new BlockPos(MathHelper.getPosition(entity)), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 1f);
    }

    public static void repeatXTimes(int times, Consumer<Integer> consumer) {
        for (int i = 0; i < times; i++) {
            consumer.accept(i);
        }
    }

    public static final MutableComponent SPLIT = Component.literal(" ");
    public static MutableComponent buildComponent(MutableComponent... components) {
        MutableComponent empty = Component.empty();
        for (MutableComponent component : components) {
            empty.append(component);
        }
        return empty;
    }



    public static @Nullable LivingEntity getAttacker(DamageSource source) {
        if (source.getEntity() instanceof Projectile projectile && projectile.getOwner() instanceof LivingEntity living) {
            return living;
        } else if (source.getEntity() instanceof LivingEntity living) {
            return living;
        }
        return null;
    }

    public static DamageType getDamageType(DamageSource source) {
        if (source.getMsgId().contains("ability")) {
            return DamageType.MAGIC;
        }
        if (source instanceof IndirectEntityDamageSource) {
            return DamageType.RANGED;
        }
        if (source instanceof EntityDamageSource) {
            return DamageType.MELEE;
        }
        return DamageType.MISC;
    }

    public enum DamageType {
        RANGED,
        MELEE,
        MAGIC,
        MISC
    }

    public static ArmorStand createMarker(Vec3 pos, Level level, boolean invisible) {
        ArmorStand stand = new ArmorStand(level, pos.x, pos.y, pos.z);
        CompoundTag tag = stand.getPersistentData();
        tag.putBoolean("Marker", true);
        stand.setInvulnerable(true);
        stand.setInvisible(invisible);
        stand.setNoGravity(true);
        stand.setBoundingBox(new AABB(0,0,0,0,0,0));
        return stand;
    }


    public static <T> T of(Supplier<T> sup) {
        return sup.get();
    }

    public static void createDamageIndicator(LivingEntity entity, double amount, String type) {
        if (entity.getLevel() instanceof ServerLevel serverLevel) {
            ModMessages.sendToAllConnectedPlayers(new DamageIndicatorPacket(entity.getId(), (float) amount, TextHelper.damageIndicatorCoder(type)), serverLevel);
        }
    }

    public static final char HEART = '\u2661';

    public static ArmorStand createHealthIndicator(LivingEntity target) {
        ArmorStand marker = createMarker(MathHelper.getPosition(target).add(0, 0.5, 0), target.getLevel(), true);
        CompoundTag tag = marker.getPersistentData();
        tag.putBoolean("healthMarker", true);
        marker.setCustomName(Component.literal(HEART + " " + target.getHealth() + "/" + target.getMaxHealth()));
        marker.setCustomNameVisible(true);
        target.getLevel().addFreshEntity(marker);
        return marker;
    }




    public static Rarity getItemRarity(Item item) {
        return item.getRarity(new ItemStack(item));
    }


    public static final EquipmentSlot[] allEquipmentSlots = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.FEET};
    public static final EquipmentSlot[] ARMOR_EQUIPMENT = new EquipmentSlot[]{EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.FEET};
    public static final EquipmentSlot[] WEAPON_SLOT = new EquipmentSlot[]{EquipmentSlot.MAINHAND};






    public static int createCustomIndex(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> 0;
            case CHEST -> 1;
            case LEGS -> 2;
            case FEET -> 3;
            case OFFHAND -> 4;
            case MAINHAND -> 5;
        };
    }

    public static boolean increaseEffectDuration(LivingEntity living, MobEffect effect, int ticks) {
        if (living.hasEffect(effect)) {
            MobEffectInstance oldInstance = living.getEffect(effect);
            assert oldInstance != null;
            MobEffectInstance effectInstance = new MobEffectInstance(effect, oldInstance.getDuration() + ticks, oldInstance.getAmplifier(), oldInstance.isAmbient(), oldInstance.isVisible(), oldInstance.showIcon(), oldInstance, oldInstance.getFactorData());
            living.removeEffect(effect);
            living.addEffect(effectInstance);
            return false;
        } else {
            living.addEffect(new MobEffectInstance(effect, 1, ticks));
            return true;
        }
    }



    public static char[] append(char[] in, char toAppend) {
        char[] copy = new char[in.length + 1];
        repeatXTimes(in.length, integer -> copy[integer] = in[integer]);
        copy[in.length] = toAppend;
        return copy;
    }

    public static String stabiliseDouble(String doubleValue, int expectedLength) {
        char[] values = doubleValue.toCharArray();
        int start = 0;
        int curPos = 0;
        boolean reachedDot = false;
        for (char c : values) {
            if (c == '.') {
                start = curPos + 1;
                reachedDot = true;
                curPos = values.length;
                break;
            }
            curPos++;
        }
        if (!reachedDot) {
            values = append(values, '.');
            for (int i = 0; i<expectedLength; i++) {
                values = append(values, '0');
            }
        } else if ((values.length) - start < expectedLength) {
            int toAdd = expectedLength - (curPos - start);
            while (toAdd > 0) {
                toAdd--;
                values = append(values, '0');
            }
        }
        return new String(values);
    }

    public static String getDimension(Level level) {
        return level.dimension().toString();
    }

    public static HashMap<ResourceKey<Level>, String> getDimensionRegistries() {
        HashMap<ResourceKey<Level>, String> Registries = new HashMap<>();
        Registries.put(Level.END, Level.END.toString());
        Registries.put(Level.NETHER, Level.NETHER.toString());
        Registries.put(Level.OVERWORLD, Level.OVERWORLD.toString());
        return Registries;
    }

    public static SimpleContainer containerOf(ItemStackHandler handler) {
        SimpleContainer inventory = new SimpleContainer(handler.getSlots());
        repeatXTimes(handler.getSlots(), integer -> {
            if (!(handler.getStackInSlot(integer).getItem() instanceof IGuiHelper || handler.getStackInSlot(integer).getItem() instanceof GemstoneItem)) {
                inventory.setItem(integer, handler.getStackInSlot(integer));
            }
        });
        return inventory;
    }

    public static List<ItemStack> shrinkDrops(List<ItemStack> drops, Item item, final int amount) {
        repeatXTimes(drops.size(), i -> {
            int varAmount = amount;
            ItemStack stack = drops.get(i);
            if (stack.getItem() == item) {
                while (varAmount > 0) {
                    stack.shrink(1);
                    varAmount--;
                    if (stack.isEmpty()) {
                        drops.remove(i);
                        break;
                    }
                }
            }
        });
        return drops;
    }
}