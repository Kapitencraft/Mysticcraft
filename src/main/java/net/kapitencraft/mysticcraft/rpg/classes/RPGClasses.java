package net.kapitencraft.mysticcraft.rpg.classes;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.rpg.traits.Traits;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public interface RPGClasses {
    ResourceKey<RPGClass> WIZARD = key("wizard");
    ResourceKey<RPGClass> WARRIOR = key("warrior");
    ResourceKey<RPGClass> PRIEST = key("priest");
    ResourceKey<RPGClass> ARCHER = key("archer");
    ResourceKey<RPGClass> SHAMAN = key("shaman");

    private static ResourceKey<RPGClass> key(String name) {
        return ResourceKey.create(ModRegistries.Keys.CLASSES, MysticcraftMod.res(name));
    }

    static void bootstrap(BootstrapContext<RPGClass> context) {
        context.register(WIZARD, RPGClass.builder()
                .addTrait(Traits.Type.INTELLIGENCE, 2)
                .build()
        );
        context.register(WARRIOR, RPGClass.builder()
                .addTrait(Traits.Type.STRENGHT, 1)
                .addTrait(Traits.Type.CONSTITUTION, 1)
                .build()
        );
        context.register(PRIEST, RPGClass.builder()
                .addTrait(Traits.Type.INTELLIGENCE, 1)
                .addTrait(Traits.Type.DEXTERITY, 1)
                .build()
        );
        context.register(ARCHER, RPGClass.builder()
                .addTrait(Traits.Type.DEXTERITY, 1)
                .addTrait(Traits.Type.STRENGHT, 1)
                .build()
        );
        context.register(SHAMAN, RPGClass.builder()
                .addTrait(Traits.Type.DEXTERITY, 1)
                .addTrait(Traits.Type.INTELLIGENCE, 1)
                .build()
        );
    }
}
