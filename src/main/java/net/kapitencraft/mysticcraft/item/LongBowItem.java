package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class LongBowItem extends ModdedBows {
    public final double DIVIDER = 40;
    public static final double ARROW_SPEED_MUL = 5;

    public LongBowItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1320).rarity(Rarity.RARE));
    }






}
