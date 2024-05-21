package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.guild.requests.*;
import net.kapitencraft.mysticcraft.init.custom.ModRegistryKeys;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModDataRequesters {
    DeferredRegister<IRequestable<?, ?>> REGISTRY = MysticcraftMod.makeRegistry(ModRegistryKeys.REQUESTABLES);

    RegistryObject<GuildDataRequestable> GUILD_DATA = REGISTRY.register("guild_data", GuildDataRequestable::new);
    RegistryObject<CreateGuildRequestable> CREATE_GUILD = REGISTRY.register("create_guild", CreateGuildRequestable::new);
    RegistryObject<KickMemberRequestable> KICK_MEMBER = REGISTRY.register("kick_guild_member", KickMemberRequestable::new);
    RegistryObject<MutePlayerRequestable> MUTE_MEMBER = REGISTRY.register("mute_guild_member", MutePlayerRequestable::new);
    RegistryObject<BanMemberRequestable> BAN_PLAYER = REGISTRY.register("ban_player", BanMemberRequestable::new);
}