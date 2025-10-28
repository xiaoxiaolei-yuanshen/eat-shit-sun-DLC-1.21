package com.asia.korea;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "korea", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        // 注册所有按键绑定
        event.register(KeyBindings.TOGGLE_EFFECT_KEY);
        event.register(KeyBindings.TOGGLE_FOLLOW_KEY);
        event.register(KeyBindings.TOGGLE_SOUTH_KOREA_KEY);
    }
}