package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Item.class)
@SuppressWarnings("ALL")
public abstract class ItemMixin implements IItemExtension {

    @ModifyConstant(method = "getUseDuration", constant = @Constant(intValue = 0))
    private int checkSpellItemUseDuration(int i, ItemStack obj, LivingEntity living) {
        if (obj.is(ModTags.Items.CATALYST) && living instanceof Player player) {
            Holder<Spell> spell = SpellHelper.getActiveSpell(player);
            return spell.value().getType() == Spell.Type.HOLD ? Integer.MAX_VALUE : spell.value().castDuration() + 1;
        }
        return 0;
    }
}