package net.kapitencraft.mysticcraft.misc.utils;

import net.kapitencraft.mysticcraft.ModConstance;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.spells.NormalSpellItem;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.weapon.melee.sword.LongSwordItem;
import net.kapitencraft.mysticcraft.item.weapon.ranged.bow.ShortBowItem;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MiscUtils {
    //EAST = new Rotation("x+", 90, 1);
    //WEST = new Rotation("x-",270, 3);
    //SOUTH = new Rotation("z+", 180, 2);
    //NORTH = new Rotation("z-", 360, 4);

    public static EquipmentSlot getSlotForStack(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem ? armorItem.getSlot() : EquipmentSlot.MAINHAND;
    }


    public static boolean awardAchievement(Player player, String achievementName) {
        if (player instanceof ServerPlayer _player) {
            ServerAdvancementManager manager = _player.server.getAdvancements();
            Advancement _adv = manager.getAdvancement(new ResourceLocation(achievementName));
            MysticcraftMod.sendInfo(manager.getAllAdvancements().toString());
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
    public static ArrayList<Vec3> lineOfSight(Entity entity, double range, double scaling) {
        ArrayList<Vec3> line = new ArrayList<>();
        Vec3 vec3;
        for (double i = 0; i <= range; i+=scaling) {
            vec3 = entity.getLookAngle().scale(i).add(entity.getX(), entity.getY(), entity.getZ()).add(0, entity.getEyeHeight(), 0);
            line.add(vec3);
        }
        return line;
    }

    public static boolean saveTeleport(Entity entity, double maxRange) {
        ArrayList<Vec3> lineOfSight = lineOfSight(entity, maxRange, 0.1);
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
        MISC;
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


    public static final String TIMER_ID = "time";
    public static ArmorStand createDamageIndicator(LivingEntity entity, double amount, String type) {
        ArmorStand dmgInc = createMarker(new Vec3(entity.getX() + Math.random() - 0.5, entity.getY() - 1, entity.getZ() + Math.random() - 0.5), entity.level, true);
        boolean dodge = Objects.equals(type, "dodge");
        dmgInc.setCustomNameVisible(true);
        dmgInc.setCustomName(Component.literal(!dodge ? String.valueOf(ModConstance.MAIN_FORMAT.format(amount)) : "DODGE").withStyle(damageIndicatorColorGenerator(type)));
        CompoundTag persistentData = dmgInc.getPersistentData();
        persistentData.putBoolean("isDamageIndicator", true);
        persistentData.putInt(TIMER_ID, 0);
        entity.level.addFreshEntity(dmgInc);
        return dmgInc;
    }

    public static @Nullable List<LivingEntity> sortLowestDistance(Entity source, List<LivingEntity> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(MathUtils.getPosition(source)))).collect(Collectors.toList());
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

    private static final List<String> NUMBERS = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    public static String removeNumbers(String string) {
        for (String number : NUMBERS) {
            string = string.replace(number, "");
        }
        return string;
    }

    public static Rarity getItemRarity(Item item) {
        return item.getRarity(new ItemStack(item));
    }

    public static String fromVec3(Vec3 vec3) {
        return "Pos: [" + vec3.x + ", " + vec3.y + ", " + vec3.z + "]";
    }

    public static final EquipmentSlot[] allEquipmentSlots = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.FEET};
    public static final EquipmentSlot[] ARMOR_EQUIPMENT = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final EquipmentSlot[] WEAPON_SLOT = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    public static String getRegistryNameForSlot(EquipmentSlot slot) {
        HashMap<EquipmentSlot, String> hashMap = new HashMap<>();
        hashMap.put(EquipmentSlot.HEAD, "helmet");
        hashMap.put(EquipmentSlot.CHEST, "chestplate");
        hashMap.put(EquipmentSlot.LEGS, "leggings");
        hashMap.put(EquipmentSlot.FEET, "boots");
        hashMap.put(EquipmentSlot.MAINHAND, "mainhand");
        hashMap.put(EquipmentSlot.OFFHAND, "offhand");
        return hashMap.get(slot);
    }

    public static <T> boolean arrayContains(T[] array, T t) {
        for (T t1 : array) {
            if (t1 == t) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean listContains(List<T> list, T t) {
        for (T t1 : list) {
            if (t1 == t) {
                return true;
            }
        }
        return false;
    }

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

    public static final EnchantmentCategory SPELL_ITEM = EnchantmentCategory.create("SPELL_ITEM", item -> item instanceof SpellItem);
    public static final EnchantmentCategory TOOL = EnchantmentCategory.create("TOOL", item -> item instanceof DiggerItem || item instanceof SwordItem || item instanceof BowItem || item instanceof SpellItem);
    public static final EnchantmentCategory GEMSTONE_ITEM = EnchantmentCategory.create("GEMSTONE_ITEM", item -> item instanceof IGemstoneApplicable);

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