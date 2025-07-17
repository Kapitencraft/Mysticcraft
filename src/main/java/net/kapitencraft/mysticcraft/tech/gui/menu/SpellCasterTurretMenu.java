package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.kap_lib.client.gui.BlockEntityMenu;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.registry.ModMenuTypes;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.tech.block.entity.SpellCasterTurretEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SpellCasterTurretMenu extends BlockEntityMenu<SpellCasterTurretEntity> {
    public SpellCasterTurretMenu(int containerId, Inventory inventory, SpellCasterTurretEntity provider) {
        super(ModMenuTypes.SPELL_CASTER_TURRET.get(), containerId, 1, inventory, provider);

        this.addSlot(new SlotItemHandler(provider.getInventory(), 0, 80, 55) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(ModItems.SPELL_SCROLL.get()) && SpellScrollItem.getSpell(stack).getTarget().getType() == SpellTarget.Type.ENTITY;
            }
        });

        addPlayerInventories(inventory, 0, 0);
    }

    public SpellCasterTurretMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, (SpellCasterTurretEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
