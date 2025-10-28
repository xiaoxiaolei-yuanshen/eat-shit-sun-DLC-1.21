package com.asia.korea;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;

public class KeyBindings {

    // 切换恩情效果按键 (V键)
    public static final KeyMapping TOGGLE_EFFECT_KEY = new KeyMapping(
            "key.korea.toggle_effect",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.categories.korea"
    );

    // 切换跟随按键 (B键)
    public static final KeyMapping TOGGLE_FOLLOW_KEY = new KeyMapping(
            "key.korea.toggle_follow",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "key.categories.korea"
    );

    // 切换忠诚效果按键 (C键)
    public static final KeyMapping TOGGLE_SOUTH_KOREA_KEY = new KeyMapping(
            "key.korea.toggle_south_korea",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "key.categories.korea"
    );
}