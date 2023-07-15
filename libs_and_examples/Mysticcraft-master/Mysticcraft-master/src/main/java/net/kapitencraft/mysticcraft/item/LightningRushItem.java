
package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;

import net.kapitencraft.mysticcraft.procedures.LightningStrikeBaseManaProcedure;

import java.util.List;

public class LightningRushItem extends Item {
	public LightningRushItem() {
		super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.EAT;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new TextComponent("\u00A76\u00A7lSpell: Lightning Rush"));
		list.add(new TextComponent("Fires an arrow which strikes Lightning"));
		list.add(new TextComponent("on ground and explodes"));
		list.add(new TextComponent("on impact"));
		list.add(new TextComponent("\u00A74Cooldown: 20 s"));
		list.add(new TextComponent("\u00A74Mana-Use: 150"));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		LightningStrikeBaseManaProcedure.execute(itemstack);
	}
}
