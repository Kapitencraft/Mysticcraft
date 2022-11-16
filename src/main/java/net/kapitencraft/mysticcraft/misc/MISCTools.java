package net.kapitencraft.mysticcraft.misc;

import com.google.common.collect.ImmutableMultimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.APITools;
import net.kapitencraft.mysticcraft.item.bow.ShortBowItem;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.sword.LongSwordItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import javax.annotation.Nullable;
import java.util.*;

import static java.lang.Math.sin;

public class MISCTools {
    record Rotation(String relative, int vecRot, int vecNum) {
    }
    public final Rotation EAST = new Rotation("x+", 90, 1);
    public final Rotation WEST = new Rotation("x-", 270,3);
    public final Rotation SOUTH = new Rotation("z+", 180, 2);
    public final Rotation NORTH = new Rotation("z-", 360, 4);

    public static ArrayList<Vec3> LineOfSight(Entity entity, double range, double scaling) {
        Vec3 vec3 = entity.position();
        Vec2 vec2 = entity.getRotationVector();
        double relativeX = 0;
        double relativeY;
        double relativeZ = 0;
        boolean flag = vec2.x < 0;
        double B = flag ? vec2.x * -1 : vec2.x;
        double A = 90;
        double yaw = vec2.y;
        yaw += 180;
        double C = 180 - A - B;
        double dist = scaling;
        double b;
        double c;
        ArrayList<Vec3> line = new ArrayList<>();
        int vector = (int) Math.ceil(yaw / 90);
        double relativeYaw = yaw % 90;
        relativeYaw = relativeYaw < 0 ? relativeYaw * -1 : relativeYaw;
        while (range >= dist) {
            b = dist * sin(B) / sin(A);
            c = dist * sin(C) / sin(A);
            relativeY = flag ? b * -1 : b;
            C = 180 - A - relativeYaw;
            b = c * sin(B) / sin(A);
            c = c * sin(C) / sin(A);
            switch (vector) {
                case 1 : relativeX +=c;
                    if (yaw < 90) {
                        relativeZ -=b;
                    } else {
                        relativeZ +=b;
                    }
                case 2 : relativeZ +=c;
                    if (yaw < 90) {
                        relativeX +=b;
                    } else {
                        relativeX -=b;
                    }
                case 3 : relativeX -=c;
                    if (yaw < 270) {
                        relativeZ +=b;
                    } else {
                        relativeZ -=b;
                    }
                case 4 : relativeZ -=c;
                    if (yaw < 90) {
                        relativeX -=b;
                    } else {
                        relativeX +=b;
                    }
            }
            line.add(new Vec3(vec3.x + relativeX, vec3.y + relativeY, vec3.z + relativeZ));
            dist += scaling;
        }
        return line;
    }

    public static  <V> ArrayList<V> invertList(ArrayList<V> list) {
        ArrayList<V> out = new ArrayList<>();
        for (int i = list.size(); i >= 0; i--) {

            out.add(list.get((i - 1)));
        }
        return out;
    }

    public static  @Nullable LivingEntity getAttacker(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Projectile projectile) {
            if (projectile.getOwner() instanceof LivingEntity living) {
                return living;
            }
        } else if (event.getSource().getEntity() instanceof LivingEntity living) {
            return living;
        }
        return null;
    }

    public static ArmorStand createDamageIndicator(LivingEntity entity, double amount, String type) {
        ArmorStand dmgInc = new ArmorStand(entity.level, entity.getX() + Math.random() - 0.5, entity.getY() - 1, entity.getZ() + Math.random() - 0.5);
        dmgInc.setNoGravity(true);
        dmgInc.setInvisible(true);
        dmgInc.setInvulnerable(true);
        dmgInc.setBoundingBox(new AABB(0,0,0,0,0,0));
        dmgInc.setCustomNameVisible(true);
        dmgInc.setCustomName(Component.literal(String.valueOf(amount)).withStyle(damageIndicatorColorGenerator(type)));
        CompoundTag persistentData = dmgInc.getPersistentData();
        persistentData.putBoolean("isDamageIndicator", true);
        persistentData.putInt("time", 0);
        persistentData.putUUID("targetUUID", entity.getUUID());
        entity.level.addFreshEntity(dmgInc);
        return dmgInc;
    }

    public static @Nullable LivingEntity[] sortLowestDistance(Entity source, List<LivingEntity> list) {
        if (list.isEmpty()) {
            return null;
        }
        LivingEntity[] ret = APITools.entityListToArray(list);
        if (ret.length == 1) {
            return ret;
        }
        boolean isDone = false;
        LivingEntity temp;
        double distance1;
        double distance2;
        int i = 0;
        while (!isDone) {
            do {
                distance1 = source.distanceTo(list.get(i));
                distance2 = source.distanceTo(list.get(i + 1));
                i++;
            } while (distance1 < distance2 && i < list.size() - 1);
            i--;
            temp = list.get(i);
            ret[i] = list.get(i + 1);
            ret[i + 1] = temp;
            for (int j = 0; j <= ret.length - 2; j++) {
                if (source.distanceTo(list.get(j)) > source.distanceTo(list.get(j + 1))) {
                    break;
                } else if (j == ret.length - 1) {
                    isDone = true;
                }
            }
        }
        return ret;
    }

    public static String getNameModifier(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof SpellItem) {
            return "SPELL ITEM";
        } else if (item instanceof LongSwordItem) {
            return "LONG SWORD";
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
            case "damage_generic" -> ChatFormatting.RED;
            case "drown" -> ChatFormatting.AQUA;
            default -> ChatFormatting.WHITE;
        };
    }

    public static Rarity getItemRarity(Item item) {
        return item.getRarity(new ItemStack(item));
    }

    public static final EquipmentSlot[] allEquipmentSlots = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.FEET};

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
    public static EnchantmentCategory SPELL_ITEM = EnchantmentCategory.create("SPELL_ITEM", item -> item instanceof SpellItem);

    public static EnchantmentCategory GEMSTONE_ITEM = EnchantmentCategory.create("GEMSTONE_ITEM", item -> item instanceof IGemstoneApplicable);

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
            return true;
        } else {
            return false;
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

    public static <T> boolean listHas(T[] collection, T obj) {
        for (T t : collection) {
            if (t == obj) {
                return true;
            }
        }
        return false;
    }

    public static ImmutableMultimap<Attribute, AttributeModifier> increaseByPercent(ImmutableMultimap<Attribute, AttributeModifier> multimap, double percent, AttributeModifier.Operation[] operations, @Nullable Attribute attributeReq) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> toReturn = new ImmutableMultimap.Builder<>();
        Collection<AttributeModifier> attributeModifiers;
        for (Attribute attribute : multimap.keys()) {
            if (attributeReq == null || attribute == attributeReq) {
                attributeModifiers = multimap.get(attribute);
                for (AttributeModifier modifier : attributeModifiers) {
                    if (listHas(operations, modifier.getOperation())) {
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

    public static ImmutableMultimap<Attribute, AttributeModifier> increaseByAmount(ImmutableMultimap<Attribute, AttributeModifier> multimap, double amount, AttributeModifier.Operation[] operations, @Nullable Attribute attributeReq) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> toReturn = new ImmutableMultimap.Builder<>();
        boolean hasBeenAdded = attributeReq == null;
        Collection<AttributeModifier> attributeModifiers;
        for (Attribute attribute : multimap.keys()) {
            if (attributeReq == null || attribute == attributeReq) {
                attributeModifiers = multimap.get(attribute);
                for (AttributeModifier modifier : attributeModifiers) {
                    if (listHas(operations, modifier.getOperation())) {
                        toReturn.put(attribute, new AttributeModifier(modifier.getId(), modifier.getName(), modifier.getAmount() + amount, modifier.getOperation()));
                        hasBeenAdded = true;
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
            MysticcraftMod.LOGGER.warn("tried to load UUID Array from Tag but Tag isn`t Array Tag");
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
}
