package net.kapitencraft.mysticcraft.misc;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_SPELL = "key.category.mysticcraft.spell";
    public static final String KEY_NEXT_SPELL = "key.mysticcraft.next_spell";
    public static final String KEY_PREVIOUS_SPELL = "key.mysticcraft.previous_spell";

    public static final KeyMapping NEXT_SPELL_KEY = new KeyMapping(KEY_NEXT_SPELL, KeyConflictContext.GUI, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, KEY_CATEGORY_SPELL);
    public static final KeyMapping PREVIOUS_SPELL_KEY = new KeyMapping(KEY_PREVIOUS_SPELL, KeyConflictContext.GUI, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, KEY_CATEGORY_SPELL);
}
