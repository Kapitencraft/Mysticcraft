package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.kap_lib.core.helpers.MathHelper;
import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.registry.ModStatTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@EventBusSubscriber
public class PrecursorRelicItem extends Item {
    public static final TabGroup PRECURSOR_GROUP = TabGroup.create(ModCreativeModTabs.MATERIALS);

    private final String translationKey;

    public PrecursorRelicItem(BossType type) {
        this(type.itemName);
    }

    public PrecursorRelicItem(String translationKey) {
        super(MiscHelper.rarity(Rarity.EPIC));
        this.translationKey = translationKey;
    }


    public static HashMap<BossType, DeferredItem<PrecursorRelicItem>> makeRegistry() {
        return ModItems.createRegistry(PrecursorRelicItem::new, BossType::getItemName, List.of(BossType.values()), PRECURSOR_GROUP);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack p_41458_) {
        return Component.translatable("item.mysticcraft." + translationKey);
    }

    @SubscribeEvent
    public static void registerWitherMods(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof WitherBoss boss) {
            if (!BossType.alreadyAdded(boss)) {
                BossType type = MathHelper.pickRandom(List.of(BossType.values()));
                type.write(boss);
            }
        }
    }

    public enum BossType implements StringRepresentable {
        NECRON(ModStatTypes.NECRONS_KILLED, "diamantes_handle", "Necron", boss -> {
            AttributeInstance attackDamage = boss.getAttribute(Attributes.ATTACK_DAMAGE);
            AttributeInstance hp = boss.getAttribute(Attributes.MAX_HEALTH);
            if (attackDamage != null) {
                attackDamage.addPermanentModifier(new AttributeModifier(MysticcraftMod.res("necron_attack_damage"), 1.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
            if (hp != null) {
                hp.addPermanentModifier(new AttributeModifier(MysticcraftMod.res("necron_hp"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
            boss.setHealth(boss.getMaxHealth());
        }),
        GOLDOR(ModStatTypes.GOLDORS_KILLED, "jolly_pink_rock", "Goldor", boss -> {
            AttributeInstance hp = boss.getAttribute(Attributes.MAX_HEALTH);
            if (hp != null) {
                hp.addPermanentModifier(new AttributeModifier(MysticcraftMod.res("goldor_hp"), 2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
            boss.setHealth(boss.getMaxHealth());
        }),
        MAXOR(ModStatTypes.MAXORS_KILLED, "bigfoots_lasso", "Maxor", boss -> {}),
        STORM(ModStatTypes.STORMS_KILLED, "lasrs_eye", "Storm", boss -> {});

        private static final EnumCodec<BossType> CODEC = StringRepresentable.fromEnum(BossType::values);

        private final String name;
        private final Consumer<WitherBoss> toDo;
        private final String itemName;
        private final Supplier<ResourceLocation> statLoc;

        BossType(Supplier<ResourceLocation> loc, String itemName, String name, Consumer<WitherBoss> toDo) {
            this.statLoc = loc;
            this.itemName = itemName;
            this.name = name;
            this.toDo = toDo;
        }

        public ResourceLocation getStatLoc() {
            return statLoc.get();
        }

        public String getItemName() {
            return itemName;
        }

        public void write(WitherBoss boss) {
            boss.getPersistentData().putString("WitherType", this.name);
            toDo.accept(boss);
            boss.setCustomName(Component.literal(this.name));
        }

        static boolean alreadyAdded(WitherBoss boss) {
            return boss.getPersistentData().contains("WitherType", 8);
        }

        public static BossType fromBoss(WitherBoss boss) {
            return byName(boss.getPersistentData().getString("WitherType"));
        }

        public static BossType byName(String name) {
            return CODEC.byName(name, MAXOR);
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
