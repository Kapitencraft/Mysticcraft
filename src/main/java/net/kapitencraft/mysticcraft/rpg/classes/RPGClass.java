package net.kapitencraft.mysticcraft.rpg.classes;

import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.kapitencraft.mysticcraft.rpg.perks.ServerPerksManager;
import net.minecraft.server.level.ServerPlayer;

public class RPGClass {

    private final PerkTree tree;

    public RPGClass(PerkTree tree) {
        this.tree = tree;
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("tree", this.tree.id().toString());
        return object;
    }

    public void select(ServerPlayer player) {
        ServerPerksManager.getOrCreateInstance().getPerks(player).unlockTree(this.tree);
    }
}