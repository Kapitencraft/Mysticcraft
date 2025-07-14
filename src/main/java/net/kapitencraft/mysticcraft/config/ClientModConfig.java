package net.kapitencraft.mysticcraft.config;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}