package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.mysticcraft.tech.block.entity.AbstractTurretBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public class AbstractTurretMenu<BE extends AbstractTurretBlockEntity> extends UpgradableBEMenu<BE> {

    protected AbstractTurretMenu(@Nullable MenuType<?> menuType, int containerId, int slotAmount, Inventory inventory, BE provider) {
        super(menuType, containerId, slotAmount, inventory, provider);
    }

    public AbstractTurretBlockEntity.TargetSelector getSelector() {
        return this.getCapabilityProvider().getSelector();
    }
}
