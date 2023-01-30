package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class HugeHealSpell extends Spell {
    public HugeHealSpell() {
        super(120, "Huge Heal", "0011011", Spell.RELEASE, Rarity.UNCOMMON);
    }

    @Override
    public void execute(LivingEntity user, ItemStack stack) {
        user.heal(5f);
    }

    @Override
    public boolean canApply(Item stack) {
        return true;
    }

    @Override
    public List<Component> getDescription() {
        return List.of(Component.literal("Heals for 5 health"));
    }
}
