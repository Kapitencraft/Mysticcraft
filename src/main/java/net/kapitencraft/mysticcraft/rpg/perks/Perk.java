package net.kapitencraft.mysticcraft.rpg.perks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.kap_lib.core.string_converter.converter.TextToIntConverter;

import java.util.List;

public class Perk {
    //TODO figure out what tf to put here
    public static final Codec<Perk> DIRECT_CODEC = Progression.CODEC.xmap(Perk::new, Perk::getProgression);

    private final Progression progression;

    public Perk(Progression progression) {
        this.progression = progression;
    }

    public Progression getProgression() {
        return progression;
    }

    /**
     * @param maxLevel      the max level this perk can achieve
     * @param masteryLevels required levels to reach next mastery level, like villager profession levels
     */
    public record Progression(int maxLevel, List<Integer> masteryLevels, TextToIntConverter levelCostProvider) {
        public static final Codec<Progression> CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.INT.fieldOf("max_level").forGetter(Progression::maxLevel),
                Codec.INT.listOf().fieldOf("mastery_levels").forGetter(Progression::masteryLevels),
                TextToIntConverter.CODEC.fieldOf("cost_provider").forGetter(Progression::levelCostProvider)
        ).apply(i, Progression::new));
    }
}
