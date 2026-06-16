package net.kapitencraft.mysticcraft.rpg.classes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.rpg.perks.PerkInstance;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RPGCharacter {

    public static final Codec<RPGCharacter> CODEC = RecordCodecBuilder.create(i -> i.group(
            RegistryFixedCodec.create(ModRegistries.Keys.CLASSES).fieldOf("class").forGetter(c -> c.rpgClass),
            Codec.INT.fieldOf("level").forGetter(c -> c.level),
            Codec.FLOAT.fieldOf("xp").forGetter(c -> c.xp)
    ).apply(i, RPGCharacter::fromCodec));

    private static RPGCharacter fromCodec(Holder<RPGClass> rpgClassHolder, int level, float xp) {
        RPGCharacter character = new RPGCharacter(rpgClassHolder);
        character.level = level;
        character.xp = xp;
        return character;
    }

    private final Holder<RPGClass> rpgClass;
    private final List<PerkInstance> perks = new ArrayList<>();
    private int level;
    private float xp;

    public RPGCharacter(Holder<RPGClass> rpgClass) {
        this.rpgClass = rpgClass;
    }

    public void awardXp(float amount) {
        this.xp += amount;
        while (this.xp >= 100) {
            this.level++;
            this.xp -= 100;
        }
    }

    public void setXp(float amount) {
        this.xp = amount;
    }

    public float getXp() {
        return xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public @NotNull Holder<RPGClass> getRpgClass() {
        return rpgClass;
    }

    public static RPGCharacter createEmpty() {
        return new RPGCharacter(null);
    }


}
