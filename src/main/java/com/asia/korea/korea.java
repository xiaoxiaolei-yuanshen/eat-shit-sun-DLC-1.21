package com.asia.korea;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;

@Mod("korea")
public class korea {

    public static final String MODID = "korea";

    // 恩情效果启用状态
    public static boolean effectEnabled = true;
    // 中立生物跟随启用状态
    public static boolean followEnabled = true;
    // 当前播放的音乐（0表示没有播放，1-3表示northkorea中的三首音乐）
    public static int currentPlayingMusic = 0;
    // 忠诚效果启用状态（southkorea）
    public static boolean southKoreaEnabled = false;

    public korea() {
        // 注册事件处理器 - 使用新的方式避免过时警告
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // 注册事件监听器
        MinecraftForge.EVENT_BUS.register(this);

        // 初始化模组
        System.out.println("朝鲜模组初始化完成！");
    }
}