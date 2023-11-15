package net.kapitencraft.mysticcraft.init;

import com.mojang.brigadier.arguments.ArgumentType;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.commands.args.info.EquipmentSlotArgTypeInfo;
import net.kapitencraft.mysticcraft.commands.args.type.EquipmentSlotArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ALL")
public interface ModCommandArguments {
    DeferredRegister<ArgumentTypeInfo<?, ?>> REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.COMMAND_ARGUMENT_TYPES);

    static <A, T extends ArgumentType<A>, K extends ArgumentTypeInfo.Template<T>, I extends ArgumentTypeInfo<T, K>> RegistryObject<I> register(String name, Class<T> infoClass, I supplier) {
        return REGISTRY.register(name, ()-> ArgumentTypeInfos.registerByClass(infoClass, supplier));
    }

    RegistryObject<EquipmentSlotArgTypeInfo> EQUIPMENT_SLOT = register("equip_slot", EquipmentSlotArgumentType.class, new EquipmentSlotArgTypeInfo());
}