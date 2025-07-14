package net.kapitencraft.mysticcraft.registry;

import com.mojang.brigadier.arguments.ArgumentType;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.commands.args.PerkTreeArg;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModCommandArgumentTypes {

    DeferredRegister<ArgumentTypeInfo<?, ?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.COMMAND_ARGUMENT_TYPES);

    RegistryObject<? extends SingletonArgumentInfo<PerkTreeArg>> PERK_TREE = register("perk_tree", PerkTreeArg.class, SingletonArgumentInfo.contextFree(PerkTreeArg::perkTree));

    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> RegistryObject<? extends I> register(String name, Class<A> aClass, I typeInfo) {
        return REGISTRY.register(name, () -> ArgumentTypeInfos.registerByClass(aClass, typeInfo));
    }
}
