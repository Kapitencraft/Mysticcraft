package net.kapitencraft.mysticcraft.requirements;

import net.kapitencraft.mysticcraft.init.custom.ModRegistries;
import net.kapitencraft.mysticcraft.requirements.type.RequirementType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class Requirement {

    private final RequirementType type;
    private final RegistryObject<? extends Item> target;

    public Requirement(RequirementType type, RegistryObject<? extends Item> target) {
        this.type = type;
        this.target = target;
    }

    public boolean related(ItemStack stack) {
        return stack.is(target.get());
    }


    public static boolean meetsRequirements(ServerPlayer player, ItemStack stack) {
        return getReqs(stack).stream().allMatch(requirement -> requirement.type.matchesPlayer(player));
    }

    public static List<Requirement> getReqs(ItemStack stack) {
        return ModRegistries.REQUIREMENT_REGISTRY.stream().filter(requirement -> requirement.related(stack)).toList();
    }
}