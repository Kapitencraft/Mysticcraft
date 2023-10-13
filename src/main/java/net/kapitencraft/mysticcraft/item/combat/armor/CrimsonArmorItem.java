package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.kapitencraft.mysticcraft.item.combat.armor.client.CrimsonArmorRenderer;
import net.kapitencraft.mysticcraft.item.item_bonus.ExtraBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.kapitencraft.mysticcraft.item.item_bonus.fullset.CrimsonArmorFullSetBonus;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.misc.particle_help.ParticleHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class CrimsonArmorItem extends TieredArmorItem implements GeoItem, IArmorBonusItem {
    public static final TabGroup CRIMSON_ARMOR_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    private static final String helperString = "crimsonParticles";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final FullSetBonus FULL_SET_BONUS = new CrimsonArmorFullSetBonus();
    public CrimsonArmorItem(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.CRIMSON, p_40387_, NETHER_ARMOR_PROPERTIES);
    }

    @Override
    protected void initFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        new ParticleHelper(helperString, living, ParticleHelper.Type.ORBIT, ParticleHelper.createOrbitProperties(0, 1000, 0, 0, 3, (SimpleParticleType) ModParticleTypes.RED_FLAME.get(), 0.75f));
        new ParticleHelper(helperString, living, ParticleHelper.Type.ORBIT, ParticleHelper.createOrbitProperties(0, 1000, 180, 0, 3, (SimpleParticleType) ModParticleTypes.RED_FLAME.get(), 0.75f));

    }

    @Override
    protected void postFullSetTick(ItemStack stack, Level level, LivingEntity living) {
        ParticleHelper.clearAllHelpers(helperString, living);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
                HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
                if (slot == this.slot) {
                    builder.put(ModAttributes.STRENGTH.get(), AttributeHelper.createModifierForSlot("Crimson Armor", AttributeModifier.Operation.ADDITION, 3 * this.getMaterial().getDefenseForSlot(this.getSlot()), slot));
                    builder.put(ModAttributes.CRIT_DAMAGE.get(), AttributeHelper.createModifierForSlot("Crimson Armor", AttributeModifier.Operation.ADDITION, this.getMaterial().getDefenseForSlot(this.getSlot()), slot));
                    builder.put(Attributes.MAX_HEALTH, AttributeHelper.createModifierForSlot("Crimson Armor", AttributeModifier.Operation.ADDITION, this.getMaterial().getDefenseForSlot(this.getSlot()) / 2.5, slot));
                }
                return builder;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public FullSetBonus getFullSetBonus() {
        return FULL_SET_BONUS;
    }

    @Override
    public PieceBonus getPieceBonusForSlot(EquipmentSlot slot) {
        return null;
    }

    @Nullable
    @Override
    public ExtraBonus getExtraBonus(EquipmentSlot slot) {
        return null;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private CrimsonArmorRenderer renderer;
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null) {
                    renderer = new CrimsonArmorRenderer();
                }
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return renderer;
            }
        });
    }

    @Override
    public List<ArmorTier> getAvailableTiers() {
        return ArmorTier.NETHER_ARMOR_TIERS;
    }

    @Override
    public TabGroup getGroup() {
        return CRIMSON_ARMOR_GROUP;
    }
}
