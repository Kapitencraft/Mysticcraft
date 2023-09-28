package net.kapitencraft.mysticcraft.misc;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {
    public static final String KEY_CATEGORY_INVENTORY = "key.category.mysticcraft.inv";
    public static final String KEY_NEXT = "key.mysticcraft.next_spell";
    public static final String KEY_PREVIOUS = "key.mysticcraft.previous_spell";
    public static final String KEY_STASH = "key.mysticcraft.stash";

    public static final KeyMapping STASH = new KeyMapping(KEY_STASH, KeyConflictContext.GUI, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_INVENTORY);
    public static final KeyMapping NEXT = new KeyMapping(KEY_NEXT, KeyConflictContext.GUI, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, KEY_CATEGORY_INVENTORY);
    public static final KeyMapping PREVIOUS = new KeyMapping(KEY_PREVIOUS, KeyConflictContext.GUI, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, KEY_CATEGORY_INVENTORY);
}
