
package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;

import java.util.List;

public class TotemOfPeaceItem extends Item {
	public TotemOfPeaceItem() {
		super(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.EAT;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new TextComponent("This Item will destroy itself"));
		list.add(new TextComponent("if you die and save you. Every mob around you won`t be able to do any damage"));
		list.add(new TextComponent("for the next 5min."));
		list.add(new TextComponent("\u00A77\u00A7oThe fastest way to end a war is"));
		list.add(new TextComponent("\u00A77\u00A7oby losing it."));
	}
}
