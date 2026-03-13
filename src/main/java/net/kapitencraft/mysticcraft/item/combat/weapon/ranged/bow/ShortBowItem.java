package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow;

import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.core.helpers.MathHelper;
import net.kapitencraft.kap_lib.item.ExtendedItem;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ShortBowItem extends ModBowItem implements ExtendedItem {
    public static final String COOLDOWN_ID = "Cooldown";

    @Override
    public double getDivider() {
        return 0;
    }

    public ShortBowItem(Properties p_40660_) {
        super(p_40660_);
    }

    //TODO re-implement

    public float createCooldown(LivingEntity archer) {
        float base_cooldown = this.getShotCooldown();
        if (archer != null) base_cooldown *= (1 / ((float) archer.getAttributeValue(ExtraAttributes.DRAW_SPEED) / 100));
        return (float) MathHelper.defRound(base_cooldown);
    }

    public abstract float getShotCooldown();

    public boolean canShoot(ItemStack bow, Level world) {
        return !world.isClientSide && !bow.has(ModDataComponentTypes.SHORTBOW_COOLDOWN);
    }

    @Override
    public void inventoryTick(ItemStack bow, @NotNull Level p_41405_, @NotNull Entity p_41406_, int p_41407_, boolean p_41408_) {
        Integer i = bow.get(ModDataComponentTypes.SHORTBOW_COOLDOWN);
        if (i != null) {
            if (i-- > 0) {
                bow.set(ModDataComponentTypes.SHORTBOW_COOLDOWN, i);
            } else {
                bow.remove(ModDataComponentTypes.SHORTBOW_COOLDOWN);
            }
        }
    }

    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag, @Nullable Player player) {
        super.appendHoverText(itemStack, context, list, flag);
        list.add(CommonComponents.EMPTY);
        list.add(Component.literal("Shot Cooldown: ").append(this.createCooldown(player) + "s").withStyle(ChatFormatting.GREEN));
        list.add(CommonComponents.EMPTY);
        list.add(Component.literal("Short Bow: Instantly Shoots!").withStyle(ChatFormatting.DARK_PURPLE));

    }
}
