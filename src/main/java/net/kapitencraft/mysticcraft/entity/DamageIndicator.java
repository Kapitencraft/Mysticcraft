package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class DamageIndicator extends Entity {
    protected static final int MAX_AGE = 30;
    private String color;
    private float damage;
    private int age = 0;

    public DamageIndicator(EntityType<DamageIndicator> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
        this.damage = 0f;
        this.color = FormattingCodes.RESET;
    }

    private DamageIndicator(float damage, String color, Level level) {
        this(ModEntityTypes.DAMAGE_INDICATOR.get(), level);
        this.damage = damage;
        this.color = color;
    }

    public static DamageIndicator createIndicator(float damage, String color, Level level) {
        return new DamageIndicator(damage, color, level);
    }

    public float getDamage() {
        return this.damage;
    }

    public String getColor() {
        return this.color;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.age = tag.getInt("age");
        this.color = tag.getString("color");
        this.damage = tag.getFloat("damage");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("age", this.age);
        tag.putString("color", this.color);
        tag.putFloat("damage", this.damage);
    }

    @Override
    public void tick() {
        if (this.age++ >= MAX_AGE || this.getY() < -64) {
            this.remove(RemovalReason.DISCARDED);
        }
    }
}
