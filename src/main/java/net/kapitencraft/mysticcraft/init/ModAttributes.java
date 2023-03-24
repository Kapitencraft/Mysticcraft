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

    public static final RegistryObject<Attribute> DODGE = register("dodge", 0, 0, 100);
    public static final RegistryObject<Attribute> INTELLIGENCE = register("intel", 0, 0, Double.MAX_VALUE);
    public static final RegistryObject<Attribute> STRENGTH = register("strenght", 0, 0, Double.MAX_VALUE);
    public static final RegistryObject<Attribute> MAGIC_FIND = register("magic_find", 0, 0, Double.MAX_VALUE);
    public static final RegistryObject<Attribute> CRIT_DAMAGE = register("crit_damage", 50, 0, Double.MAX_VALUE);
    public static final RegistryObject<Attribute> FEROCITY = register("ferocity", 0, 0, 500);
    public static final RegistryObject<Attribute> MAX_MANA = register("max_mana", 0, 0, Double.MAX_VALUE);
    public static final RegistryObject<Attribute> MANA = register("mana", 100, 0, Double.MAX_VALUE);
    public static final RegistryObject<Attribute> MANA_COST = register("mana_cost", 0, 0, 1000);
    public static final RegistryObject<Attribute> ABILITY_DAMAGE = register("ability_damage", 0, 0, Double.MAX_VALUE);
    public static final RegistryObject<Attribute> MANA_REGEN = register("mana_regen", 0, 0, Double.MAX_VALUE);
    public static final RegistryObject<Attribute> HEALTH_REGEN = register("health_regen", 0, 0, Double.MAX_VALUE);
    public static final RegistryObject<Attribute> ARMOR_SHREDDER = register("armor_shredder", 0, 0, 100);
    public static final RegistryObject<Attribute> ARROW_SPEED = register("arrow_speed", 0, 0, 100);
    public static final RegistryObject<Attribute> LIVE_STEAL = register("live_steal", 0, 0, 10);
    public static final RegistryObject<Attribute> RANGED_DAMAGE = register("ranged_damage", 0, 0, 100);
    public static final RegistryObject<Attribute> DRAW_SPEED = register("draw_speed", 100, 0, 1000);
}