package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.kap_lib.core.util.ExtraRarities;
import net.kapitencraft.kap_lib.item.ExtendedItem;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaSteelSwordItem extends LongSwordItem implements ExtendedItem {
    public ManaSteelSwordItem() {
        super(ModTiers.MANA_STEEL,
                MiscHelper.rarity(ExtraRarities.MYTHIC)
                        .attributes(createLongSwordAttributes(ModTiers.MANA_STEEL, 3, -3.3f, 150, 50, 3))
                        .component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT))
        );
    }

    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag, @Nullable Player player) {
        list.add(Component.literal("Regenerate 2 hp on hit").withStyle(ChatFormatting.GREEN));
    }
}