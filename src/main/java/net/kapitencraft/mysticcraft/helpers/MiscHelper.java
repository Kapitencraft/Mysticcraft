package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.client.font.effect.EffectsStyle;
import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.client.particle.DamageIndicatorParticleOptions;
import net.kapitencraft.mysticcraft.gui.IGuiHelper;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.kapitencraft.mysticcraft.misc.VeinMinerHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MiscHelper {
    //EAST = new Rotation("x+", 90, 1);
    //WEST = new Rotation("x-",270, 3);
    //SOUTH = new Rotation("z+", 180, 2);
    //NORTH = new Rotation("z-", 360, 4);

    public static EquipmentSlot getSlotForStack(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem ? armorItem.getSlot() : stack.getItem() instanceof ShieldItem ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
    }

    public static Style withSpecial(Style style, GlyphEffect effect) {
        Style newStyle = style.withColor((ChatFormatting) null);
        EffectsStyle effectsStyle = (EffectsStyle) newStyle;
        effectsStyle.addEffect(effect);
        return newStyle;
    }

    public static <T, K extends T> K instance(T value, Class<K> target) {
        try {
            return (K) value;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T, K extends T> Stream<K> instanceStream(Stream<T> in, Class<K> kClass) {
        return CollectionHelper.mapSync(in, kClass, MiscHelper::instance);
    }

    public static int repairPlayerItems(Player player, int value, Enchantment ench) {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(ench, player, ItemStack::isDamaged);
        if (entry != null) {
            ItemStack itemstack = entry.getValue();
            int i = Math.min((int) (value * itemstack.getXpRepairRatio()), itemstack.getDamageValue());
            itemstack.setDamageValue(itemstack.getDamageValue() - i);
            int j = value - i / 2;
            return j > 0 ? repairPlayerItems(player, j, ench) : 0;
        } else {
            return value;
        }

    }


    public static void getEnchantmentLevelAndDo(ItemStack stack, Enchantment enchantment, Consumer<Integer> enchConsumer) {
        if (stack.getEnchantmentLevel(enchantment) > 0) {
            enchConsumer.accept(stack.getEnchantmentLevel(enchantment));
        }
    }

    @SuppressWarnings("ALL")
    public static Rarity getFinalRarity(Rarity rarity, ItemStack stack) {
        if (!stack.isEnchanted()) {
            return rarity;
        } else {
            return switch (rarity) {
                case COMMON -> Rarity.UNCOMMON;
                case UNCOMMON -> Rarity.RARE;
                case RARE -> Rarity.EPIC;
                case EPIC -> ModRarities.LEGENDARY;
                default -> rarity == ModRarities.LEGENDARY ? ModRarities.MYTHIC : rarity == ModRarities.MYTHIC ? ModRarities.DIVINE : Rarity.COMMON;
            };
        }
    }

    public static <T> T forDifficulty(Difficulty difficulty, T easy, T medium, T hard, T peaceful) {
        return switch (difficulty) {
            case EASY -> easy;
            case NORMAL -> medium;
            case HARD -> hard;
            case PEACEFUL -> peaceful;
        };
    }

    public static void mineMultiple(BlockPos pos, ServerPlayer serverPlayer, Block block, Consumer<BlockPos> extra, Predicate<BlockState> shouldMine, Predicate<BlockPos> shouldBreak) {
        VeinMinerHolder holder = new VeinMinerHolder(serverPlayer, block, extra, shouldMine, shouldBreak);
        holder.start(pos);
    }

    public static <T> void ifNonNull(@Nullable T t, Consumer<T> toDo) {
        if (t != null) {
            toDo.accept(t);
        }
    }

    public static <T, K> @NotNull K ifNonNullOrDefault(@Nullable T t, Function<T, K> function, Supplier<K> defaulted) {
        if (t != null) {
            return function.apply(t);
        }
        return defaulted.get();
    }

    public static Item.Properties rarity(Rarity rarity) {
        return new Item.Properties().rarity(rarity);
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

    public static <T, K> T getValue(Function<T, K> provider, T defaultValue, K key, T... values) {
        for (T t : values) {
            if (provider.apply(t).equals(key)) {
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

    public static void ensureTags(ItemStack... stacks) {
        Arrays.stream(stacks).filter(stack -> stack != ItemStack.EMPTY).forEach(ItemStack::getOrCreateTag);
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

    public static void repeat(int times, Consumer<Integer> consumer) {
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
        if (source.getMsgId().contains("ability") || source.getMsgId().contains("magic")) {
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

    public static void createDamageIndicator(LivingEntity entity, float amount, String type) {
        if (entity.getLevel() instanceof ServerLevel serverLevel) {
            float rangeOffset = entity.getBbHeight() / 2;
            ParticleHelper.sendParticles(serverLevel, new DamageIndicatorParticleOptions(TextHelper.damageIndicatorCoder(type), amount, rangeOffset), false, entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

    private static Vec3 getUpdateForPos(Vec3 cam, LivingEntity living) {
        Vec3 livingPos = MathHelper.getPosition(living);
        Vec3 delta = cam.subtract(livingPos);
        return MathHelper.setLength(delta, 0.5);
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
        repeat(in.length, integer -> copy[integer] = in[integer]);
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
        repeat(handler.getSlots(), integer -> {
            if (!(handler.getStackInSlot(integer).getItem() instanceof IGuiHelper || handler.getStackInSlot(integer).getItem() instanceof GemstoneItem)) {
                inventory.setItem(integer, handler.getStackInSlot(integer));
            }
        });
        return inventory;
    }

    public static List<ItemStack> shrinkDrops(List<ItemStack> drops, Item item, final int amount) {
        repeat(drops.size(), i -> {
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