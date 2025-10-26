package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.mysticcraft.registry.ModMenuTypes;
import net.kapitencraft.mysticcraft.tech.block.entity.ObeliskTurretBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class ObeliskTurretMenu extends AbstractTurretMenu<ObeliskTurretBlockEntity> {

    public ObeliskTurretMenu(int containerId, Inventory inventory, ObeliskTurretBlockEntity provider) {
        super(ModMenuTypes.OBELISK_TURRET.get(), containerId, 0, inventory, provider);
    }

    public ObeliskTurretMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, (ObeliskTurretBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }
}
