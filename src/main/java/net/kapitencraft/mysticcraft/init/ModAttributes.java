package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModAttributes {
    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MysticcraftMod.MOD_ID);
    private static RegistryObject<Attribute> register(String name, double initValue, double minValue, double maxValue) {
        return REGISTRY.register("generic." + name, ()-> new RangedAttribute("generic." + name, initValue, minValue, maxValue).setSyncable(true));
    }

    private static RegistryObject<Attribute> register0Max(String name, double initValue) {
        return register(name, initValue, 0, Double.MAX_VALUE);
    }

    //Defensive Stats
    public static final RegistryObject<Attribute> DODGE = register("dodge", 0, 0, 100);
    public static final RegistryObject<Attribute> MAGIC_DEFENCE = register0Max("magic_defence", 0);
    public static final RegistryObject<Attribute> TRUE_DEFENCE = register0Max("true_defence", 0);
    public static final RegistryObject<Attribute> DOUBLE_JUMP = register("double_jump", 0, 0, 20);
    public static final RegistryObject<Attribute> VITALITY = register0Max("vitality", 0);
    public static final RegistryObject<Attribute> HEALTH_REGEN = register0Max("health_regen", 0);

    //Aggressive Stats
    public static final RegistryObject<Attribute> BONUS_ATTACK_SPEED = register("bonus_attack_speed", 0, 0, 100);
    public static final RegistryObject<Attribute> INTELLIGENCE = register0Max("intel", 0);
    public static final RegistryObject<Attribute> STRENGTH = register0Max("strenght", 0);
    public static final RegistryObject<Attribute> MAGIC_FIND = register0Max("magic_find", 0);
    public static final RegistryObject<Attribute> FISHING_SPEED = register0Max("fishing_speed", 0);
    public static final RegistryObject<Attribute> CRIT_DAMAGE = register0Max("crit_damage", 50);
    public static final RegistryObject<Attribute> CRIT_CHANCE = register("crit_chance", 0, 0, 100);
    public static final RegistryObject<Attribute> FEROCITY = register("ferocity", 0, 0, 500);
    public static final RegistryObject<Attribute> ABILITY_DAMAGE = register0Max("ability_damage", 0);
    public static final RegistryObject<Attribute> RANGED_DAMAGE = register("ranged_damage", 0, 0, 100);
    //Misc
    public static final RegistryObject<Attribute> DRAW_SPEED = register("draw_speed", 100, 0, 1000);
    public static final RegistryObject<Attribute> LIVE_STEAL = register("live_steal", 0, 0, 10);
    public static final RegistryObject<Attribute> ARMOR_SHREDDER = register("armor_shredder", 0, 0, 100);
    public static final RegistryObject<Attribute> ARROW_SPEED = register("arrow_speed", 0, 0, 10000);

    //Mana
    public static final RegistryObject<Attribute> MAX_MANA = register0Max("max_mana", 0);
    public static final RegistryObject<Attribute> MANA = register0Max("mana", 100);
    public static final RegistryObject<Attribute> MANA_COST = register("mana_cost", 0, 0, 100000);
    public static final RegistryObject<Attribute> MANA_REGEN = register0Max("mana_regen", 0);
}