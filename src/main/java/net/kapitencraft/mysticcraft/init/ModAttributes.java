package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModAttributes {
    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MysticcraftMod.MOD_ID);
    public static final RegistryObject<Attribute> DODGE = REGISTRY.register("generic.dodge", () -> new RangedAttribute(FormattingCodes.BLACK.UNICODE + "Dodge", 0D, 0D, 100D).setSyncable(true));
    public static final RegistryObject<Attribute> INTELLIGENCE = REGISTRY.register("generic.intel", () -> new RangedAttribute(FormattingCodes.AQUA.UNICODE + "Intelligence", 0D, 0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> STRENGTH = REGISTRY.register("generic.strength", () -> new RangedAttribute(FormattingCodes.ORANGE.UNICODE + "Strength", 0D, 0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_FIND = REGISTRY.register("generic.magic_find", () -> new RangedAttribute(FormattingCodes.GOLD.UNICODE + "Magic Find", 0D, 0D, Double.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> CRIT_DAMAGE = REGISTRY.register("generic.crit_damage", () -> new RangedAttribute(FormattingCodes.BLUE.UNICODE + "Crit Damage", 50D, 0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> FEROCITY = REGISTRY.register("generic.ferocity", () -> new RangedAttribute(FormattingCodes.ORANGE.UNICODE + "Ferocity", 0D, 0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MAX_MANA = REGISTRY.register("generic.max_mana", () -> new RangedAttribute(FormattingCodes.DARK_BLUE.UNICODE + "Max Mana", 100D, 0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MANA = REGISTRY.register("generic.mana", () -> new RangedAttribute(FormattingCodes.LIGHT_PURPLE.UNICODE + "Mana", 100D, 0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MANA_COST = REGISTRY.register("generic.mana_cost", () -> new RangedAttribute(FormattingCodes.DARK_PURPLE.UNICODE + "Mana Cost", 0D, 0D, 1000.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ABILITY_DAMAGE = REGISTRY.register("generic.ability_damage", () -> new RangedAttribute(FormattingCodes.RED.UNICODE + "Ability Damage", 0D, 0D, 1000.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MANA_REGEN = REGISTRY.register("generic.mana_regen", () -> new RangedAttribute(FormattingCodes.LIGHT_PURPLE.UNICODE + "Mana Regen", 0D, 0D, 1000.0D).setSyncable(true));
    public static final RegistryObject<Attribute> HEALTH_REGEN = REGISTRY.register("generic.health_regen", () -> new RangedAttribute(FormattingCodes.DARK_RED.UNICODE + "Health Regen", 100D, 0D, 1000.0D).setSyncable(true));
}