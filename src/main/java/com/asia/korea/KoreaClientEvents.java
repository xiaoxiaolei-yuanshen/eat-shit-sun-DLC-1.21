package com.asia.korea;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "korea", value = Dist.CLIENT)
public class KoreaClientEvents {

    private static Minecraft minecraft = Minecraft.getInstance();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && minecraft.player != null) {

            // 检查V键是否被按下 - 切换恩情效果
            while (KeyBindings.TOGGLE_EFFECT_KEY.consumeClick()) {
                korea.effectEnabled = !korea.effectEnabled;

                // 在聊天栏显示状态消息
                String status = korea.effectEnabled ? "启用" : "禁用";
                minecraft.player.displayClientMessage(
                        net.minecraft.network.chat.Component.literal("恩情效果: " + status),
                        true
                );
            }

            // 检查B键是否被按下 - 切换跟随
            while (KeyBindings.TOGGLE_FOLLOW_KEY.consumeClick()) {
                korea.followEnabled = !korea.followEnabled;

                // 在聊天栏显示状态消息
                String status = korea.followEnabled ? "启用" : "禁用";
                minecraft.player.displayClientMessage(
                        net.minecraft.network.chat.Component.literal("中立生物跟随: " + status),
                        true
                );
            }

            // 检查C键是否被按下 - 切换忠诚效果
            while (KeyBindings.TOGGLE_SOUTH_KOREA_KEY.consumeClick()) {
                korea.southKoreaEnabled = !korea.southKoreaEnabled;

                // 在聊天栏显示状态消息
                String status = korea.southKoreaEnabled ? "启用" : "禁用";
                minecraft.player.displayClientMessage(
                        net.minecraft.network.chat.Component.literal("忠诚效果: " + status),
                        true
                );
            }
        }
    }
}