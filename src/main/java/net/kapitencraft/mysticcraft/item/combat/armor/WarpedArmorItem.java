package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.client.particle.MagicCircleParticleType;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class WarpedArmorItem extends TieredArmorItem {
    public static final TabGroup WARPED_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);

    @Override
    public TabGroup getGroup() {
        return WARPED_GROUP;
    }

    public static final String SHIELD_ID = "magicallyProtected";

    int magicalShieldCooldown = 0;
    boolean hasSpawned = false;

    public WarpedArmorItem(EquipmentSlot slot) {
        super(ModArmorMaterials.WARPED, slot, NETHER_ARMOR_PROPERTIES);
    }

    @Override
    protected void fullSetTick(ItemStack stack, Level level, LivingEntity living) {
        if (magicalShieldCooldown <= 0) {
            if (!hasSpawned) {
                living.getPersistentData().putBoolean(SHIELD_ID, true);
                ParticleHelper.sendParticles(level, new MagicCircleParticleType(living), true, MathHelper.getPosition(living), 1, 0, 0, 0, 0);
                hasSpawned = true;
            }
            if (living.getAttributeValue(ModAttributes.MANA.get()) == 0) {
                living.getPersistentData().putBoolean(SHIELD_ID, false);
                magicalShieldCooldown = 600;
                hasSpawned = false;
            }
        } else {
            magicalShieldCooldown--;
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        return null;
    }

    @Override
    public List<ArmorTier> getAvailableTiers() {
        return ArmorTier.NETHER_ARMOR_TIERS;
    }
}
