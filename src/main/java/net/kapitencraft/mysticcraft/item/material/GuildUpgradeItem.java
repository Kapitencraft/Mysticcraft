package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.guild.GuildUpgrade;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GuildUpgradeItem extends Item implements IModItem {
    public static final TabGroup group = new TabGroup(TabRegister.TabTypes.TOOLS_AND_UTILITIES);
    private final GuildUpgrade upgrade;

    public GuildUpgradeItem(GuildUpgrade upgrade) {
        super(new Properties().rarity(Rarity.RARE));
        this.upgrade = upgrade;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_41432_, @NotNull Player player, @NotNull InteractionHand hand) {
        Guild guild = GuildHandler.getInstance().getGuildForPlayer(player);
        if (guild != null && guild.upgrade(this.upgrade)) {
            player.sendSystemMessage(Component.translatable("guild.upgrade.gain", Component.translatable("guild.upgrade.name." + this.upgrade.getName())));
            return InteractionResultHolder.success(player.getItemInHand(hand));
        } else {
            player.sendSystemMessage(Component.translatable("guild.upgrade.gain.failed").withStyle(ChatFormatting.RED));
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
    }

    @Override
    public TabGroup getGroup() {
        return group;
    }
}
