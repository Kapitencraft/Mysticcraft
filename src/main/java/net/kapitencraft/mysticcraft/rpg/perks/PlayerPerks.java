package net.kapitencraft.mysticcraft.rpg.perks;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.core.string_converter.param_storage.ParamStorage;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.util.Levelable;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;

import java.util.HashMap;
import java.util.Map;

public class PlayerPerks {
    public static final Codec<PlayerPerks> CODEC = Codec.unboundedMap(RegistryFixedCodec.create(ModRegistries.Keys.PERKS), Progression.CODEC).xmap(PlayerPerks::fromCodec, p -> p.perks);

    private static PlayerPerks fromCodec(Map<Holder<Perk>, Progression> m) {
        PlayerPerks playerPerks = new PlayerPerks();
        playerPerks.perks.putAll(m);
        return playerPerks;
    }

    private final Map<Holder<Perk>, Progression> perks = new HashMap<>();

    private static class Progression extends Levelable {
        private static final Codec<Progression> CODEC = createCodec(Progression::new);
        private final Holder<Perk> owner;

        private Progression(int level, float xp, Holder<Perk> owner) {
            super(level, xp);
            this.owner = owner;
        }

        private Progression(Holder<Perk> owner) {
            this(0, 0, owner);
        }

        @Override
        protected float calculateNextRequiredXp() {
            return this.owner.value().progression().levelCostProvider().transfer(new ParamStorage<>(Map.of("l", level)));
        }
    }

    public void progress(Holder<Perk> perk, float xp) {
        Progression progression = this.perks.computeIfAbsent(perk, Progression::new);
        progression.reward(xp);
    }

    public int getLevel(Holder<Perk> perk) {
        Progression progression = this.perks.get(perk);
        return progression != null ? progression.level : 0;
    }
}
