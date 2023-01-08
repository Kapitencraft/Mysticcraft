package net.kapitencraft.mysticcraft.entity.model;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.SchnauzenPluesch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class SchnauzenPlueschModel<T extends Entity> extends DefaultedItemGeoModel<SchnauzenPluesch> {
	public SchnauzenPlueschModel() {
		super(new ResourceLocation(MysticcraftMod.MOD_ID, "schnauzen_pluesch"));
	}
}