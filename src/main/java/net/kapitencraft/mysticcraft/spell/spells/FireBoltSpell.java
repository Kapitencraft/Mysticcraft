package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.item.spells.IFireScytheItem;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class FireBoltSpell extends Spell {
    private final double damage;
    private final boolean explosive;
    private final Component[] description;
    public FireBoltSpell(double base_damage, boolean explosive) {
        super(50, "Fire Bolt", "0110011", Spells.RELEASE, Rarity.UNCOMMON, "fire_bolt");
        this.damage = base_damage;
        this.explosive = explosive;
        this.description = new Component[]{Component.literal("Fires a Fire Bolt dealing"), Component.literal(FormattingCodes.RED + this.getDamage() + FormattingCodes.RESET + " Base Ability Damage")};
    }

    @Override
    public void execute(LivingEntity user, ItemStack stack) {
        FireBoltProjectile projectile = FireBoltProjectile.createProjectile(user.level, user, this.explosive, this.damage, this);
        projectile.shootFromRotation(user, user.getXRot(), user.getYRot(), 0, 2, 1);
        projectile.setBaseDamage(this.damage);
        user.level.addFreshEntity(projectile);
    }

    @Override
    public boolean canApply(Item stack) {
        return stack instanceof IFireScytheItem;
    }

    @Override
    public List<Component> getDescription() {
        return List.of(description);
    }

    private double getDamage() {
        return this.damage;
    }

}