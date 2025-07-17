package net.kapitencraft.mysticcraft.tech.gui.screen;

import net.kapitencraft.kap_lib.client.gui.screen.BlockEntityScreen;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.block.entity.SpellCasterTurretEntity;
import net.kapitencraft.mysticcraft.tech.gui.menu.SpellCasterTurretMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SpellCasterTurretScreen extends BlockEntityScreen<SpellCasterTurretEntity, SpellCasterTurretMenu> {
    private static final ResourceLocation BACKGROUND = MysticcraftMod.res("textures/gui/spell_caster_turret.png");

    public SpellCasterTurretScreen(SpellCasterTurretMenu p_97741_, Inventory p_97742_, Component component) {
        super(p_97741_, p_97742_, component);
    }

    @Override
    public ResourceLocation getTexture() {
        return BACKGROUND;
    }
}
