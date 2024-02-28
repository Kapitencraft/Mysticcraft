package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.content.CrimsonDeathRayHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.function.Consumer;

public class CrimsonArmorFullSetBonus extends KillIncreasingStackFullSetBonus {
    public static final String COOLDOWN_ID = "DominusCooldown";
    public static final String DOMINUS_ID = "Dominus";
    public CrimsonArmorFullSetBonus() {
        super("Dominus", type -> type == MiscHelper.DamageType.MELEE, ForgeMod.ATTACK_RANGE, 0.1f, 120);
    }

    @Override
    protected void killWhen10(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {
        CrimsonDeathRayHelper.add(user);
    }

    //TODO fix Cooldown usage

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.addAll(
                List.of(Component.literal("every melee kill grants 1 stack of §6Dominus§r."),
                Component.literal(""),
                Component.literal("for every stack of §6Dominus§r gain +0.1 attack range"),
                Component.literal(""),
                Component.literal("when reaching 10 stacks of §6Dominus§r, spawn 3"),
                Component.literal("§4Crimson Death Ray§rs that follow enemies for 4 sec."),
                Component.literal(""),
                Component.literal("lose 1 Dominus after not getting"),
                Component.literal("a stack of §6Dominus§r for 6 seconds.")));
    }
}
