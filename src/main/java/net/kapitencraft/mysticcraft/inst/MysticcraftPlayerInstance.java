package net.kapitencraft.mysticcraft.inst;

import net.kapitencraft.mysticcraft.helpers.BonusHelper;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.IEventListener;
import net.kapitencraft.mysticcraft.item.item_bonus.MultiPieceBonus;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MysticcraftPlayerInstance {

    private final Map<EquipmentSlot, List<IEventListener>> listeners = new HashMap<>();
    private final List<IEventListener> multiPieceListeners = new ArrayList<>();
    private final Player player;

    public MysticcraftPlayerInstance(Player player) {
        this.player = player;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void accordEquipmentChanges(LivingEquipmentChangeEvent event) {
        if (event.getEntity() == this.player) {
            EquipmentSlot slot = event.getSlot();
            List<IEventListener> list = listeners.getOrDefault(slot, new ArrayList<>());
            List<IEventListener> toRemove = BonusHelper.getListenersFromStack(slot, event.getFrom(), this.player);
            toRemove.forEach(CollectionHelper.biUsage(this.player, IEventListener::onRemove));
            List<IEventListener> multiPieceToRemove = toRemove.stream().filter(listener -> listener instanceof MultiPieceBonus).toList();
            multiPieceListeners.removeAll(multiPieceToRemove);
            list.removeAll(toRemove);
            list.addAll(BonusHelper.getListenersFromStack(slot, event.getTo(), this.player));
            list.removeIf(multiPieceListeners::contains);
            list.forEach(CollectionHelper.biUsage(this.player, IEventListener::onApply));
            List<IEventListener> multiPieces = list.stream().filter(listener -> listener instanceof MultiPieceBonus).toList();
            list.removeAll(multiPieces);
            multiPieceListeners.addAll(multiPieces);
            if (!listeners.containsKey(slot)) listeners.put(slot, list);
        }
    }

    @SubscribeEvent
    public void itemBonusRegister(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        LivingEntity attacker = MiscHelper.getAttacker(source);
        MiscHelper.DamageType type = MiscHelper.getDamageType(source);
        LivingEntity killed = event.getEntity();
        if (attacker == this.player && attacker != null) {
            useAll(listener -> listener.onEntityKilled(killed, attacker, type));
        }
    }

    private void useAll(Consumer<IEventListener> consumer) {
        this.multiPieceListeners.forEach(consumer);
        this.listeners.values().forEach(CollectionHelper.biUsage(consumer, List::forEach));
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void DamageBonusRegister(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        DamageSource source = event.getSource();
        @Nullable LivingEntity attacker = MiscHelper.getAttacker(source);
        if (attacker == null) { return; }
        MiscHelper.DamageType type = MiscHelper.getDamageType(source);
        if (attacker == this.player) {
            useAll(listener ->  event.setAmount(listener.onEntityHurt(attacked, attacker, type, event.getAmount())));
        }
        if (attacked == this.player) {
            useAll(listener -> event.setAmount(listener.onTakeDamage(attacked, attacker, type, event.getAmount())));
        }
    }

    @SubscribeEvent
    public void tick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() == this.player) {
            useAll(listener -> listener.onTick(this.player.level, this.player));
        }
    }



    public void remove() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}