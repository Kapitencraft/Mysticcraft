
package net.kapitencraft.mysticcraft.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.kapitencraft.mysticcraft.entity.GoblinEntity;
import net.kapitencraft.mysticcraft.client.model.Modelsteve;

public class GoblinRenderer extends MobRenderer<GoblinEntity, Modelsteve<GoblinEntity>> {
	public GoblinRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsteve(context.bakeLayer(Modelsteve.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(GoblinEntity entity) {
		return new ResourceLocation("mysticcraft:textures/entities/goblin.png");
	}
}
