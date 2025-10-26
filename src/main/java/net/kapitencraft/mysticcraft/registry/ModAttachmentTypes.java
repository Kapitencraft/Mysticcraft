package net.kapitencraft.mysticcraft.registry;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.classes.RPGCharacter;
import net.kapitencraft.mysticcraft.rpg.skill.PlayerSkills;
import net.kapitencraft.mysticcraft.rpg.traits.Traits;
import net.kapitencraft.mysticcraft.spell.capability.PlayerSpells;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public interface ModAttachmentTypes {
    DeferredRegister<AttachmentType<?>> REGISTRY = MysticcraftMod.registry(NeoForgeRegistries.Keys.ATTACHMENT_TYPES);

    Supplier<AttachmentType<PlayerSpells>> PLAYER_SPELLS = REGISTRY.register("player_spells", () -> AttachmentType.builder(PlayerSpells::create).serialize(PlayerSpells.CODEC).build());
    Supplier<AttachmentType<Integer>> SELECTED_SPELL_SLOT = REGISTRY.register("selected_spell_slot", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).sync(ByteBufCodecs.INT).build());
    Supplier<AttachmentType<Float>> NUMBNESS_DAMAGE = REGISTRY.register("numbness_damage", () -> AttachmentType.builder(() -> 0f).serialize(Codec.FLOAT).build());
    Supplier<AttachmentType<PlayerSkills>> SKILLS = REGISTRY.register("skills", () -> AttachmentType.builder(PlayerSkills::new).serialize(PlayerSkills.CODEC).sync(PlayerSkills.STREAM_CODEC).build());
    Supplier<AttachmentType<Traits>> TRAITS = REGISTRY.register("traits", () -> AttachmentType.builder(Traits::new).serialize(Traits.CODEC).sync(Traits.STREAM_CODEC).build());
    Supplier<AttachmentType<RPGCharacter>> CHARACTER = REGISTRY.register("class", () -> AttachmentType.builder(RPGCharacter::createEmpty).serialize(RPGCharacter.CODEC).build());
}