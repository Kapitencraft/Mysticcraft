package net.kapitencraft.mysticcraft.item.combat.weapon.ranged;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

//TODO
public class ArrowSwapperItem extends Item {
    public ArrowSwapperItem() {
        super(MiscHelper.rarity(Rarity.UNCOMMON));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_41432_, @NotNull Player p_41433_, @NotNull InteractionHand p_41434_) {

        return super.use(p_41432_, p_41433_, p_41434_);
    }
}