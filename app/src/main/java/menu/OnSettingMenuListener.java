package menu;

import android.view.View;

/**
 * @author JayZhao
 * @date 2020/5/25
 **/
public interface OnSettingMenuListener {
    /**
     * 助手登录
     */
    void onClickedGaccountAssistant();

    /**
     * 充值
     */
    void onClickedReCharge();

    /**
     * 注销
     */
    void onCLickedExitUse();

    /**
     * 全屏模式
     * @param stretchVideo true-打开 false-关闭
     */
    void onClickedStretchVideo(boolean stretchVideo);

    /**
     * 实时监测
     * @param enable
     */
    void onClickedRealTimeMonitor(boolean enable);

    /**
     * 鼠标模式
     * @param mouseMode true：鼠标模式 false：触屏模式
     */
    void onClickedMouseMode(boolean mouseMode);

    /**
     * 显示手势说明引导
     */
    void onClickedGestureInstruction();

    /**
     * 任务管理器
     */
    void onClickedTaskManager();

    /**
     * 进程切换（窗口切换）
     */
    void onClickedProcessSwitch();

    /**
     * 按量转优惠模式
     * @param tip 提示语，如果不是云豆计量单位的，要注意格式
     */
    void onClickedDiscountPeriodTip(String tip);

    /**
     * 优惠模式转按量
     * @param tip
     */
    void onClickedToAnliang(String tip);

    /**
     * 离开桌面
     */
    void onClickedLeaveDesktop();

    /**
     * 打开虚拟键盘
     */
    void onClickedGameKeyboard();

    /**
     * 画质选择
     * @param bitrate 0:低；1-中； 2-高； 3-超高； 4-自动
     */
    void onClickedPictureQuality(int bitrate);

    /**
     * 鼠标速度
     */
    void onClickedMouseSpeed();

    /**
     * 语音开黑
     * @param isOpen 要打开/关闭
     * @param view 传入view，根据isOpen状态设置view是否selcted
     */
    void onClickedAudioSwitch(boolean isOpen, View view);

    /**
     * 智能键盘
     * @param isOpen 是否打开
     */
    void onClickedWordkeyboardSwitch(boolean isOpen);

    /**
     * 震动开关
     * @param isOpen 是否打开
     */
    void onClickedViberate(boolean isOpen);

    /**
     * 体感辅助
     * @param gyroMode 1-关闭；2-模式1；3-模式2
     */
    void onClickedSensor(int gyroMode);

    /**
     * 体感灵敏度
     * @param sensitivity 体感灵敏度 范围（1-10）
     */
    void onClickedSensorSensitivity(int sensitivity);
}
