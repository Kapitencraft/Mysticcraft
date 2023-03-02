package net.kapitencraft.mysticcraft.misc;

import com.google.common.collect.ImmutableMultimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.spells.NormalSpellItem;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.weapon.melee.sword.LongSwordItem;
import net.kapitencraft.mysticcraft.item.weapon.ranged.bow.ShortBowItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MISCTools {

    record Rotation(String relative, int vecRot, int vecNum) {
        }
    public final Rotation EAST = new Rotation("x+", 90, 1);
    public final Rotation WEST = new Rotation("x-",270, 3);
    public final Rotation SOUTH = new Rotation("z+", 180, 2);
    public final Rotation NORTH = new Rotation("z-", 360, 4);

    public static EquipmentSlot getSlotForStack(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem ? armorItem.getSlot() : EquipmentSlot.MAINHAND;
    }

    public interface delayRun {
        void run();
    }

    public static void delayed(int delay, delayRun run) {
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
        }.start(delay);
    }
    public static ArrayList<Vec3> LineOfSight(Entity entity, double range, double scaling) {
        ArrayList<Vec3> line = new ArrayList<>();
        Vec3 vec3;
        for (double i = 0; i <= range; i+=scaling) {
            vec3 = entity.getLookAngle().scale(i).add(entity.getX(), entity.getY(), entity.getZ()).add(0, entity.getEyeHeight(), 0);
            line.add(vec3);
        }
        return line;
    }

    public static float increaseFloatTagValue(CompoundTag tag, String name, float f) {
        float value = tag.getFloat(name)+f;
        tag.putFloat(name, value);
        return value;
    }

    public static int increaseIntegerTagValue(CompoundTag tag, String name, int i) {
        int value = tag.getInt(name)+i;
        tag.putInt(name, value);
        return value;
    }

    public static double getSaveAttributeValue(Attribute attribute, LivingEntity living) {
        if (living.getAttribute(attribute) != null) {
            return living.getAttributeValue(attribute);
        }
        return -1;
    }

    public static double getAttributeValue(@Nullable AttributeInstance instance, double baseValue) {
        if (instance == null) {
            MysticcraftMod.sendInfo("Instance is null");
            return 0;
        }
        double d0 = baseValue;

        for (AttributeModifier attributemodifier : instance.getModifiers(AttributeModifier.Operation.ADDITION)) {
            d0 += attributemodifier.getAmount();
        }

        double d1 = d0;

        for (AttributeModifier attributeModifier1 : instance.getModifiers(AttributeModifier.Operation.MULTIPLY_BASE)) {
            d1 += d0 * attributeModifier1.getAmount();
        }

        for (AttributeModifier attributeModifier2 : instance.getModifiers(AttributeModifier.Operation.MULTIPLY_TOTAL)) {
            d1 *= 1.0D + attributeModifier2.getAmount();
        }
        return instance.getAttribute().sanitizeValue(d1);
    }

    public static boolean saveTeleport(Entity entity, double maxRange) {
        ArrayList<Vec3> lineOfSight = LineOfSight(entity, maxRange, 0.1);
        Vec3 teleportPosition;
        for (Vec3 vec3 : lineOfSight) {
            BlockPos pos = new BlockPos(vec3);
            if (entity.level.getBlockState(pos).canOcclude() && lineOfSight.indexOf(vec3) > 0) {
                teleportPosition = lineOfSight.get(lineOfSight.indexOf(vec3) - 1);
                entity.teleportTo(teleportPosition.x, teleportPosition.y, teleportPosition.z);
                entity.fallDistance = 0;
                return true;
            } else if (lineOfSight.indexOf(vec3) == lineOfSight.size() - 1) {
                teleportPosition = lineOfSight.get(lineOfSight.indexOf(vec3) - 1);
                entity.teleportTo(teleportPosition.x, teleportPosition.y, teleportPosition.z);
                entity.fallDistance = 0;
                return true;
            }
        }
        return false;
    }

    public static  <V> ArrayList<V> invertList(ArrayList<V> list) {
        ArrayList<V> out = new ArrayList<>();
        for (int i = list.size(); i > 0; i--) {

            out.add(list.get((i - 1)));
        }
        return out;
    }

    public static  @Nullable LivingEntity getAttacker(DamageSource source) {
        if (source.getEntity() instanceof Projectile projectile && projectile.getOwner() instanceof LivingEntity living) {
            return living;
        } else if (source.getEntity() instanceof LivingEntity living) {
            return living;
        }
        return null;
    }

    public static void sendTitle(Player player, Component title) {
        if (player instanceof ServerPlayer serverPlayer) {
            Function<Component, Packet<?>> function = ClientboundSetTitleTextPacket::new;
            serverPlayer.connection.send(function.apply(title));
        }
    }

    public static void sendSubTitle(Player player, Component subtitle) {
        if (player instanceof ServerPlayer serverPlayer) {
            Function<Component, Packet<?>> function = ClientboundSetSubtitleTextPacket::new;
            serverPlayer.connection.send(function.apply(subtitle));
        }
    }

    public static void clearTitle(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ClientboundClearTitlesPacket clearTitlesPacket = new ClientboundClearTitlesPacket(true);
            serverPlayer.connection.send(clearTitlesPacket);
        }
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

    public static ArmorStand createDamageIndicator(LivingEntity entity, double amount, String type) {
        ArmorStand dmgInc = createMarker(new Vec3(entity.getX() + Math.random() - 0.5, entity.getY() - 1, entity.getZ() + Math.random() - 0.5), entity.level, true);
        boolean dodge = Objects.equals(type, "dodge");
        dmgInc.setCustomNameVisible(true);
        dmgInc.setCustomName(Component.literal(!dodge ? String.valueOf(MysticcraftMod.MAIN_FORMAT.format(amount)) : "DODGE").withStyle(damageIndicatorColorGenerator(type)));
        CompoundTag persistentData = dmgInc.getPersistentData();
        persistentData.putBoolean("isDamageIndicator", true);
        persistentData.putInt("time", 0);
        entity.level.addFreshEntity(dmgInc);
        return dmgInc;
    }

    public static @Nullable List<LivingEntity> sortLowestDistance(Entity source, List<LivingEntity> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(getPosition(source)))).collect(Collectors.toList());
    }

    public static String getNameModifier(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof NormalSpellItem) {
            return "SPELL ITEM";
        } else if (item instanceof LongSwordItem) {
            return "LONGSWORD";
        } else if (item instanceof ShortBowItem) {
            return "SHORT BOW";
        } else if (item instanceof SwordItem) {
            return "SWORD";
        } else if (item instanceof PickaxeItem) {
            return "PICKAXE";
        } else if (item instanceof AxeItem) {
            return "AXE";
        } else if (item instanceof ShovelItem) {
            return "SHOVEL";
        } else if (item instanceof HoeItem) {
            return "HOE";
        } else if (item instanceof BowItem) {
            return "BOW";
        } else if (item instanceof CrossbowItem) {
            return "CROSSBOW";
        } else if (item instanceof EnchantedBookItem) {
            return "ENCHANTED BOOK";
        } else if (item instanceof ArmorItem armorItem) {
            if (armorItem.getSlot() == EquipmentSlot.FEET) {
                return "BOOTS";
            } else if (armorItem.getSlot() == EquipmentSlot.LEGS) {
                return "LEGGINGS";
            } else if (armorItem.getSlot() == EquipmentSlot.CHEST) {
                return "CHEST PLATE";
            } else if (armorItem.getSlot() == EquipmentSlot.HEAD) {
                return "HELMET";
            }
        } else if (item instanceof BlockItem) {
            return "BLOCK";
        } else if (item instanceof BoatItem) {
            return "BOAT";
        } else if (item instanceof FishingRodItem) {
            return "FISHING ROD";
        }
        return "ITEM";
    }

    private static ChatFormatting damageIndicatorColorGenerator(String type) {
        return switch (type) {
            case "heal" -> ChatFormatting.GREEN;
            case "wither" -> ChatFormatting.BLACK;
            case "ferocity" -> ChatFormatting.GOLD;
            case "drown" -> ChatFormatting.AQUA;
            case "ability" -> ChatFormatting.DARK_RED;
            case "dodge" -> ChatFormatting.DARK_GRAY;
            default -> ChatFormatting.RED;
        };
    }

    public static Rarity getItemRarity(Item item) {
        return item.getRarity(new ItemStack(item));
    }

    public static <T extends ParticleOptions> int sendParticles(Level level, T type, boolean force, double x, double y, double z, int amount, double deltaX, double deltaY, double deltaZ, double speed) {
        if (level instanceof ServerLevel serverLevel) {
            List<ServerPlayer> players = serverLevel.getPlayers(serverPlayer -> true);
            int i;
            for (i = 0; i < players.size(); i++) {
                ServerPlayer serverPlayer = players.get(i);
                serverLevel.sendParticles(serverPlayer, type, force, x, y, z, amount, deltaX, deltaY, deltaZ, speed);
            }
            return i;
        }
        return -1;
    }

    public static <T extends ParticleOptions> int sendParticles(Level level, T type, boolean force, Vec3 loc, int amount, double deltaX, double deltaY, double deltaZ, double speed) {
        return sendParticles(level, type, force, loc.x, loc.y, loc.z, amount, deltaX, deltaY, deltaZ, speed);
    }

    public static <T extends ParticleOptions> int sendParticles(Level level, boolean force, Vec3 loc, double deltaX, double deltaY, double deltaZ, double speed, ParticleAmountHolder holder) {
        return sendParticles(level, (ParticleOptions) holder.particleType(), force, loc.x, loc.y, loc.z, holder.amount(), deltaX, deltaY, deltaZ, speed);
    }

    public static <T extends ParticleOptions> int sendParticles(Level level, boolean force, Vec3 loc, double deltaX, double deltaY, double deltaZ, double speed, ParticleGradientHolder holder) {
        return sendParticles(level, force, loc, deltaX, deltaY, deltaZ, speed, holder.getHolder1()) + sendParticles(level, force, loc, deltaX, deltaY, deltaZ, speed, holder.getHolder2());
    }

    public static Vec2 getTargetRotation(Entity source, Entity target) {
        double d0 = target.getX() - source.getX();
        double d1 = target.getY() - source.getY();
        double d2 = target.getZ() - source.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return new Vec2(Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)))), Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F));
    }

    public static Vec3 calculateViewVector(float p_20172_, float p_20173_) {
        float f = p_20172_ * ((float)Math.PI / 180F);
        float f1 = -p_20173_ * ((float)Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3(f3 * f4, -f5, f2 * f4);
    }


    public static Vec3 getPosition(Entity entity) {
        return new Vec3(entity.getX(), entity.getY(), entity.getZ());
    }

    public static String fromVec3(Vec3 vec3) {
        return "Pos: [" + vec3.x + ", " + vec3.y + ", " + vec3.z + "]";
    }

    public static final EquipmentSlot[] allEquipmentSlots = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.FEET};
    public static final EquipmentSlot[] ARMOR_EQUIPMENT = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final EquipmentSlot[] WEAPON_SLOT = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    public static <T> boolean ArrayContains(T[] array, T t) {
        for (T t1 : array) {
            if (t1 == t) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean ListContains(List<T> list, T t) {
        for (T t1 : list) {
            if (t1 == t) {
                return true;
            }
        }
        return false;
    }

    public static int createCustomIndex(EquipmentSlot slot) {
        return switch (slot) {
            case LEGS -> 2;
            case FEET -> 3;
            case HEAD -> 0;
            case CHEST -> 1;
            case MAINHAND -> 5;
            case OFFHAND -> 4;
        };
    }

    public static final EnchantmentCategory SPELL_ITEM = EnchantmentCategory.create("SPELL_ITEM", item -> item instanceof SpellItem);
    public static final EnchantmentCategory TOOL = EnchantmentCategory.create("TOOL", item -> item instanceof DiggerItem || item instanceof SwordItem || item instanceof BowItem || item instanceof SpellItem);
    public static final EnchantmentCategory GEMSTONE_ITEM = EnchantmentCategory.create("GEMSTONE_ITEM", item -> item instanceof IGemstoneApplicable);

    public static double round(double no, int num) {
        return Math.floor(no * (num * 10)) / (num * 10);
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
            return true;
        }
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

    public static ImmutableMultimap<Attribute, AttributeModifier> increaseByPercent(ImmutableMultimap<Attribute, AttributeModifier> multimap, double percent, AttributeModifier.Operation[] operations, @Nullable Attribute attributeReq) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> toReturn = new ImmutableMultimap.Builder<>();
        Collection<AttributeModifier> attributeModifiers;
        for (Attribute attribute : multimap.keys()) {
            if (attributeReq == null || attribute == attributeReq) {
                attributeModifiers = multimap.get(attribute);
                for (AttributeModifier modifier : attributeModifiers) {
                    if (ArrayContains(operations, modifier.getOperation())) {
                        toReturn.put(attribute, new AttributeModifier(modifier.getId(), modifier.getName(), modifier.getAmount() * (1 + percent), modifier.getOperation()));
                    } else {
                        toReturn.put(attribute, modifier);
                    }
                }
            } else {
                attributeModifiers = multimap.get(attribute);
                for (AttributeModifier modifier : attributeModifiers) {
                    toReturn.put(attribute, modifier);
                }
            }
        }
        return toReturn.build();
    }

    public static ImmutableMultimap<Attribute, AttributeModifier> increaseByAmount(ImmutableMultimap<Attribute, AttributeModifier> multimap, double amount, AttributeModifier.Operation operation, @Nullable Attribute attributeReq) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> toReturn = new ImmutableMultimap.Builder<>();
        boolean hasBeenAdded = attributeReq == null;
        Collection<AttributeModifier> attributeModifiers;
        for (Attribute attribute : multimap.keys()) {
            attributeModifiers = multimap.get(attribute);
            for (AttributeModifier modifier : attributeModifiers) {
                if (!hasBeenAdded && attribute == attributeReq && operation == modifier.getOperation()) {
                        toReturn.put(attribute, new AttributeModifier(modifier.getId(), modifier.getName(), modifier.getAmount() + amount, modifier.getOperation()));
                        hasBeenAdded = true;
                } else {
                    toReturn.put(attribute, modifier);
                }
            }
        }
        if (!hasBeenAdded) {
            toReturn.put(attributeReq, new AttributeModifier(UUID.randomUUID(), "Custom Attribute", amount, AttributeModifier.Operation.ADDITION));
        }
        multimap = toReturn.build();
        return multimap;
    }

    public static CompoundTag putHashMapTag(HashMap<UUID, Integer> hashMap) {
        CompoundTag mapTag = new CompoundTag();
        UUID[] UuidArray = hashMap.keySet().toArray(new UUID[0]);
        List<Integer> IntArray = collectionToList(hashMap.values());
        mapTag.put("Uuids", putUuidArray(UuidArray));
        mapTag.putIntArray("Ints", IntArray);
        return mapTag;
    }

    public static HashMap<UUID, Integer> getHashMapTag(CompoundTag tag) {
        HashMap<UUID, Integer> hashMap = new HashMap<>();
        if (tag == null) {
            return hashMap;
        }
        int[] intArray = tag.getIntArray("Ints");
        UUID[] UuidArray = getUuidArray((CompoundTag) Objects.requireNonNull(tag.get("Uuids")));
        for (int i = 0; i < (intArray.length == Objects.requireNonNull(UuidArray).length ? intArray.length : 0); i++) {
            hashMap.put(UuidArray[i], intArray[i]);
        }
        return hashMap;
    }

    public static CompoundTag putUuidArray(UUID[] array) {
        CompoundTag arrayTag = new CompoundTag();
        for (int i = 0; i < array.length; i++) {
            arrayTag.putUUID(String.valueOf(i), array[i]);
            arrayTag.putInt("Length", array.length);
        }
        return arrayTag;
    }

    public static UUID[] getUuidArray(CompoundTag arrayTag) {
        if (!arrayTag.contains("Length")) {
            MysticcraftMod.sendWarn("tried to load UUID Array from Tag but Tag isn`t Array Tag");
        } else {
            int length = arrayTag.getInt("Length");
            UUID[] array = new UUID[length];
            for (int i = 0; i < length; i++) {
                array[i] = arrayTag.getUUID(String.valueOf(i));
            }
            return array;
        }
        return null;
    }
    
    public static <T> List<T> collectionToList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    public static SimpleContainer ContainerOf(ItemStackHandler handler) {
        SimpleContainer inventory = new SimpleContainer(handler.getSlots());
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!(handler.getStackInSlot(i).getItem() instanceof IGuiHelper || handler.getStackInSlot(i).getItem() instanceof GemstoneItem)) {
                inventory.setItem(i, handler.getStackInSlot(i));
            }
        }
        return inventory;
    }

    public static List<ItemStack> shrinkDrops(List<ItemStack> drops, Item item, int amount) {
        for (int i = 0; i < drops.size(); i++) {
            ItemStack stack = drops.get(i);
            if (stack.getItem() == item) {
                while (amount > 0) {
                    stack.shrink(1);
                    amount--;
                    if (stack.isEmpty()) {
                        drops.remove(i);
                        break;
                    }
                }
            }
        }
        return drops;
    }
}