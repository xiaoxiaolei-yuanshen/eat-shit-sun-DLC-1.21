package com.asia.korea;

import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Arrays;

@Mod.EventBusSubscriber(modid = "korea")
public class southkorea {

    // 效果作用范围（格）
    public static final double EFFECT_RANGE = 20.0;
    // 检查间隔（刻）20刻 = 1秒
    public static final int CHECK_INTERVAL = 20;
    // 音乐时长（毫秒）3分28秒 = 208秒 = 208000毫秒
    public static final int MUSIC_DURATION = 208000;
    // 文本触发间隔（刻）10秒 = 200刻
    public static final int TEXT_TRIGGER_INTERVAL = 200;

    // 随机数生成器 - 使用 Minecraft 的 RandomSource
    public static final RandomSource RANDOM = RandomSource.create();
    // 音乐播放状态
    public static MusicState musicState = MusicState.STOPPED;
    // 当前播放音乐的卫道士ID
    public static int currentPlayingVindicator = -1;
    // 音乐开始播放的时间
    public static long musicStartTime = 0;
    // 记录忠诚效果上次的状态
    public static boolean lastLoyaltyState = true;
    // 上次触发文本的时间
    public static long lastTextTriggerTime = 0;

    // 音乐汉化名称
    public static final String MUSIC_NAME = "光州无限制格斗大赛";

    // 随机文本列表
    public static final List<String> RANDOM_TEXTS = Arrays.asList(
            "[生物名字]:不行，我们空输部队的颜面何在！",
            "[生物名字]:这已经不是一般的村民了，必须重拳出击！",
            "[村民名字]:三棍打散村庄魂，长官我是刌民人",
            "地图加载中……..",
            "[生物名字]:执法有温度，甩棍有力度，脚下有准度。",
            "[生物名字]:忠橙！",
            "1111 5！1111 5！",
            "[生物名字]:犯错就要认，挨打要立正"
    );

    // 音乐状态枚举
    public enum MusicState {
        STOPPED, PLAYING, PAUSED
    }

    // 创建 ResourceLocation 的辅助方法 - 使用反射
    public static ResourceLocation createResourceLocation(String namespace, String path) {
        try {
            // 使用反射来调用私有构造函数
            java.lang.reflect.Constructor<ResourceLocation> constructor =
                    ResourceLocation.class.getDeclaredConstructor(String.class, String.class);
            constructor.setAccessible(true);
            return constructor.newInstance(namespace, path);
        } catch (Exception e) {
            System.err.println("无法创建 ResourceLocation: " + namespace + ":" + path);
            e.printStackTrace();
            // 返回一个默认的音效作为回退
            try {
                java.lang.reflect.Constructor<ResourceLocation> fallbackConstructor =
                        ResourceLocation.class.getDeclaredConstructor(String.class, String.class);
                fallbackConstructor.setAccessible(true);
                return fallbackConstructor.newInstance("minecraft", "ambient.cave");
            } catch (Exception e2) {
                throw new RuntimeException("无法创建任何 ResourceLocation", e2);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 只在服务端执行，每20刻检查一次
        if (event.phase == TickEvent.Phase.END ||
                event.player.level().isClientSide ||
                event.player.tickCount % CHECK_INTERVAL != 0) {
            return;
        }

        Player player = event.player;

        // 检查玩家附近的卫道士、村民和铁傀儡（无论忠诚效果是否启用）
        checkVindicatorAndTargets(player);

        // 检查当前播放音乐的卫道士是否还活着
        if (korea.southKoreaEnabled) {
            checkVindicatorAlive(player);
        }
    }

    // 检查卫道士和其目标
    private static void checkVindicatorAndTargets(Player player) {
        // 获取玩家周围的所有卫道士
        List<Vindicator> nearbyVindicators = player.level().getEntitiesOfClass(
                Vindicator.class,
                player.getBoundingBox().inflate(EFFECT_RANGE)
        );

        for (Vindicator vindicator : nearbyVindicators) {
            // 检查卫道士是否持有下界合金斧
            if (isHoldingNetheriteAxe(vindicator)) {
                // 检查卫道士的目标
                LivingEntity target = vindicator.getTarget();

                // 如果目标是村民或铁傀儡，则播放音乐
                if ((target instanceof Villager || target instanceof IronGolem) &&
                        korea.southKoreaEnabled && musicState == MusicState.STOPPED) {
                    playGzpnMusic(player, vindicator);
                }

                // 检查是否触发随机文本
                if (target != null && korea.southKoreaEnabled &&
                        player.level().getGameTime() - lastTextTriggerTime > TEXT_TRIGGER_INTERVAL) {
                    triggerRandomText(player, vindicator, target);
                }
            }
        }
    }

    // 检查当前播放音乐的卫道士是否还活着
    private static void checkVindicatorAlive(Player player) {
        if (currentPlayingVindicator != -1) {
            // 通过ID查找卫道士
            net.minecraft.world.entity.Entity entity = player.level().getEntity(currentPlayingVindicator);

            // 如果卫道士不存在或已经死亡，停止音乐
            if (entity == null || !entity.isAlive() || !(entity instanceof Vindicator)) {
                stopGzpnMusic(player);
                currentPlayingVindicator = -1;
            }
        }
    }

    // 检查卫道士是否持有下界合金斧
    private static boolean isHoldingNetheriteAxe(Vindicator vindicator) {
        ItemStack mainHandItem = vindicator.getMainHandItem();
        return mainHandItem.getItem() == Items.NETHERITE_AXE;
    }

    // 播放光州无限制格斗大赛音乐
    private static void playGzpnMusic(Player player, Vindicator vindicator) {
        musicState = MusicState.PLAYING;
        currentPlayingVindicator = vindicator.getId();
        musicStartTime = System.currentTimeMillis();

        // 在聊天栏显示播放的音乐
        player.displayClientMessage(
                net.minecraft.network.chat.Component.literal("播放音乐: " + MUSIC_NAME + " (3分28秒)"),
                true
        );

        // 设置音乐自然结束计时器（简化版）
        new Thread(() -> {
            try {
                Thread.sleep(MUSIC_DURATION);
                if (musicState == MusicState.PLAYING) {
                    stopGzpnMusic(player);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 停止光州无限制格斗大赛音乐
    private static void stopGzpnMusic(Player player) {
        musicState = MusicState.STOPPED;
        currentPlayingVindicator = -1;

        // 在聊天栏显示音乐已停止
        if (player != null) {
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("音乐已停止: " + MUSIC_NAME),
                    true
            );
        }
    }

    // 触发随机文本
    private static void triggerRandomText(Player player, Vindicator vindicator, LivingEntity target) {
        // 随机选择一条文本
        String randomText = RANDOM_TEXTS.get(RANDOM.nextInt(RANDOM_TEXTS.size()));

        // 获取生物名称
        String vindicatorName = vindicator.getDisplayName().getString();
        String targetName = target.getDisplayName().getString();

        // 替换文本中的占位符
        randomText = randomText.replace("[生物名字]", vindicatorName);
        randomText = randomText.replace("[村民名字]", targetName);

        // 在聊天栏显示随机文本
        player.displayClientMessage(
                net.minecraft.network.chat.Component.literal(randomText),
                false
        );

        // 更新上次触发文本的时间
        lastTextTriggerTime = player.level().getGameTime();
    }
}