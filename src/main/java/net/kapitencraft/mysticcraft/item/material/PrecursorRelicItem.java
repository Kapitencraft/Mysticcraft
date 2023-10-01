package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.SaveAbleEnum;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class PrecursorRelicItem extends Item {
    private static final UUID DAMAGE_BOOST = UUID.fromString("2e00584a-53e6-4ddf-926f-e13a4c229bdb");
    private static final UUID HP_BOOST = UUID.fromString("e8132f3b-d111-41ae-962c-ebd3385aafdf");

    private final String translationKey;

    public PrecursorRelicItem(BossType type) {
        this(type.itemName);
    }

    public PrecursorRelicItem(String translationKey) {
        super(MiscUtils.rarity(Rarity.EPIC));
        this.translationKey = translationKey;
    }


    public static HashMap<BossType, RegistryObject<PrecursorRelicItem>> makeRegistry() {
        return ModItems.createRegistry(PrecursorRelicItem::new, BossType::getItemName, List.of(BossType.values()), ModItems.TabTypes.MOD_MATERIALS);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack p_41458_) {
        return Component.translatable("item.mysticcraft." + translationKey);
    }

    @SubscribeEvent
    public static void registerWitherMods(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof WitherBoss boss) {
            if (!BossType.alreadyAdded(boss)) {
                BossType type = MathUtils.pickRandom(List.of(BossType.values()));
                type.write(boss);
            }
        }
    }

    public enum BossType implements SaveAbleEnum {
        NECRON("diamantes_handle", "Necron", boss -> {
            AttributeInstance attackDamage = boss.getAttribute(Attributes.ATTACK_DAMAGE);
            AttributeInstance hp = boss.getAttribute(Attributes.MAX_HEALTH);
            if (attackDamage != null) {
                attackDamage.addPermanentModifier(new AttributeModifier(DAMAGE_BOOST, "Necron's Damage Boost", 1.8, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
            if (hp != null) {
                MysticcraftMod.sendInfo("adding Modifier");
                hp.addPermanentModifier(new AttributeModifier(HP_BOOST, "Necron's HP Boost", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }),
        GOLDOR("jolly_pink_rock", "Goldor", boss -> {
            AttributeInstance hp = boss.getAttribute(Attributes.MAX_HEALTH);
            if (hp != null) {
                MysticcraftMod.sendInfo("adding Modifier");
                hp.addPermanentModifier(new AttributeModifier(HP_BOOST, "Goldor's HP Boost", 2, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }),
        MAXOR("bigfoots_lasso", "Maxor", boss -> {}),
        STORM("lasrs_eye", "Storm", boss -> {});

        private final String name;
        private final Consumer<WitherBoss> toDo;
        private final String itemName;

        BossType(String itemName, String name, Consumer<WitherBoss> toDo) {
            this.itemName = itemName;
            this.name = name;
            this.toDo = toDo;
        }

        public String getItemName() {
            return itemName;
        }

        public void write(WitherBoss boss) {
            boss.getPersistentData().putString("WitherType", this.name);
            toDo.accept(boss);
            boss.setCustomName(Component.literal(this.name));
        }

        public Supplier<PrecursorRelicItem> getToDrop() {
            return ModItems.PRECURSOR_RELICTS.get(this);
        }

        static boolean alreadyAdded(WitherBoss boss) {
            return boss.getPersistentData().contains("WitherType", 8);
        }

        @Override
        public String getName() {
            return name;
        }

        public static BossType fromBoss(WitherBoss boss) {
            return byName(boss.getPersistentData().getString("WitherType"));
        }

        public static BossType byName(String name) {
            return SaveAbleEnum.getValue(MAXOR, name, values());
        }
    }
}
