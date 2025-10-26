package net.kapitencraft.mysticcraft.tech.gui.screen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.block.entity.ObeliskTurretBlockEntity;
import net.kapitencraft.mysticcraft.tech.gui.menu.ObeliskTurretMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ObeliskTurretScreen extends AbstractTurretScreen<ObeliskTurretBlockEntity, ObeliskTurretMenu> {
    private static final ResourceLocation TEXTURE = MysticcraftMod.res("textures/gui/background.png");
    public ObeliskTurretScreen(ObeliskTurretMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 180;
        this.imageWidth = 219;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
