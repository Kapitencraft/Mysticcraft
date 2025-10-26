package net.kapitencraft.mysticcraft.registry;

import com.mojang.brigadier.arguments.ArgumentType;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.commands.args.SkillArgType;
import net.kapitencraft.mysticcraft.commands.args.SkillArgTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModCommandArgumentTypes {

    DeferredRegister<ArgumentTypeInfo<?, ?>> REGISTRY = MysticcraftMod.registry(Registries.COMMAND_ARGUMENT_TYPE);

    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> Supplier<I> register(String name, Class<A> aClass, I typeInfo) {
        return REGISTRY.register(name, () -> ArgumentTypeInfos.registerByClass(aClass, typeInfo));
    }

    Supplier<SkillArgTypeInfo> SKILL = register("skill", SkillArgType.class, new SkillArgTypeInfo());
}
