package net.kapitencraft.mysticcraft.client.rpg.classes;

public class ClientClass {
    private static final ClientClass instance = new ClientClass();

    public static ClientClass getInstance() {
        return instance;
    }

    private int level;
    private float xp;

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(float xp) {
        this.xp = xp;
    }
}
