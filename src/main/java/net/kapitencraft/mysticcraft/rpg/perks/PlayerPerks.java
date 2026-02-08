package net.kapitencraft.mysticcraft.rpg.perks;

import com.mojang.serialization.Codec;
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

        private Progression(int level, float xp) {
            super(level, xp);
        }

        private Progression() {
            this(0, 0);
        }

        @Override
        protected float calculateNextRequiredXp() {
            return 0;
        }
    }

    public void progress(Holder<Perk> perk, float xp) {
        Progression progression = this.perks.computeIfAbsent(perk, h -> new Progression());
        progression.reward(xp);
    }

    public int getLevel(Holder<Perk> perk) {
        Progression progression = this.perks.get(perk);
        return progression != null ? progression.level : 0;
    }
}
