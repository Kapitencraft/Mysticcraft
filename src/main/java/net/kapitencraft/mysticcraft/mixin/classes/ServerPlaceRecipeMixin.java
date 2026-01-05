package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlaceRecipe.class)
public abstract class ServerPlaceRecipeMixin implements PlaceRecipe<Integer> {

    @Shadow protected Inventory inventory;
}
