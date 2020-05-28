package menu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.aylong.myapplication.R;


/**
 * @author JayZhao
 * @date 2020/5/25
 */
public class StreamDeskMenuView extends FrameLayout implements View.OnClickListener {
    private OnSettingMenuListener mMenuListener;
    private Context mContext;
    private final static String CONFIG_DATA = "netboom_data";
    private final static String MOUSE_COUNT_KEY = "netboom_mouse_count";
    private final static String PICTURE_QUALITY_KEY = "netboom_picture_quality";
    private ScrollView mScrollView;
    private TextView tv_pixel_low;
    private TextView tv_pixel_medium;
    private TextView tv_pixel_high;
    private TextView tv_pixel_superHigh;
    private TextView tv_mode_touch;
    private TextView tv_mode_mouse;
    private Switch switch_speed_monitor;
    private Switch switch_vibrate;
    private Switch switch_full_screen;
    private TextView tv_motion_shut_down;
    private TextView tv_motion_mode1;
    private TextView tv_motion_mode2;
    private LinearLayout ll_motion_sensitivity;


    public StreamDeskMenuView(Context context) {
        super(context);
        initView(context);
    }

    public StreamDeskMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public StreamDeskMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.netboom_stream_desk_menu_layout, null);
        addView(view);

        mScrollView = findViewById(R.id.child_menu);
        FirstLevelMenu firstLevelMenu = findViewById(R.id.first_menu);
        firstLevelMenu.setOnFirstMenuClickListener(menuClickListener);
        firstLevelMenu.setOnFirstMenuCloseListener(menuCloseListener);

        LinearLayout ll_leave_desktop = findViewById(R.id.ll_leave_desktop);
        ll_leave_desktop.setOnClickListener(this);

        LinearLayout ll_switch_process = findViewById(R.id.ll_switch_process);
        ll_switch_process.setOnClickListener(this);

        LinearLayout ll_task_manager = findViewById(R.id.ll_task_manager);
        ll_task_manager.setOnClickListener(this);

        // 画质
        tv_pixel_low = findViewById(R.id.tv_pixel_low);
        tv_pixel_low.setOnClickListener(this);
        tv_pixel_medium = findViewById(R.id.tv_pixel_medium);
        tv_pixel_medium.setOnClickListener(this);
        tv_pixel_high = findViewById(R.id.tv_pixel_high);
        tv_pixel_high.setOnClickListener(this);
        tv_pixel_superHigh = findViewById(R.id.tv_pixel_super_high);
        tv_pixel_superHigh.setOnClickListener(this);

        // 获取当前画质
        int quality = getPictureQuality();
        switch (quality) {
            case 0:
                tv_pixel_low.setSelected(true);
                break;
            case 1:
                tv_pixel_medium.setSelected(true);
                break;
            case 2:
                tv_pixel_high.setSelected(true);
                break;
            case 3:
                tv_pixel_superHigh.setSelected(true);
                break;
            default:
                break;
        }

        tv_mode_touch = findViewById(R.id.tv_mode_touch);
        tv_mode_touch.setOnClickListener(this);
        tv_mode_mouse = findViewById(R.id.tv_mode_mouse);
        tv_mode_mouse.setOnClickListener(this);

        boolean isUseMouse = SPController.getInstance().getBooleanValue(SPController.id.KEY_MOUSE_MODE, true);
        tv_mode_touch.setSelected(!isUseMouse);
        tv_mode_mouse.setSelected(isUseMouse);

        TextView tv_gesture_instruction = findViewById(R.id.tv_gesture_instruction);
        tv_gesture_instruction.setOnClickListener(this);

        AppCompatSeekBar seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        int mouse_process = getMouseSpeed();
        seekBar.setProgress(mouse_process);
        SPController.getInstance().setMouseSpeed(mouse_process);

        // 实时监测
        switch_speed_monitor = findViewById(R.id.switch_speed_monitor);
        switch_speed_monitor.setOnCheckedChangeListener(checkedChangeListener);
        // 获取旧设定值
        boolean isOpenMonitor = SPController.getInstance().getBooleanValue(SPController.id.KEY_ENABEL_REAL_TIME_MONITORING, false);
        switch_speed_monitor.setChecked(isOpenMonitor);

        switch_vibrate = findViewById(R.id.switch_vibrate);
        switch_vibrate.setOnCheckedChangeListener(checkedChangeListener);

        // 获取旧震动开关状态
        boolean isVibrate = SPController.getInstance().getBooleanValue(SPController.id.KEY_ENABLE_VIBRATEABLE, true);
        switch_vibrate.setChecked(isVibrate);

        switch_full_screen = findViewById(R.id.switch_full_screen);
        switch_full_screen.setOnCheckedChangeListener(checkedChangeListener);
        // 获取旧设定值
        boolean isFullScreen = SPController.getInstance().getBooleanValue(SPController.id.KEY_ENABEL_STRETCH_VIDEO, false);
        switch_full_screen.setChecked(isFullScreen);


        // 体感
        tv_motion_shut_down = findViewById(R.id.tv_shut_down);
        tv_motion_shut_down.setOnClickListener(this);
        tv_motion_mode1 = findViewById(R.id.tv_mode1);
        tv_motion_mode1.setOnClickListener(this);
        tv_motion_mode2 = findViewById(R.id.tv_mode2);
        tv_motion_mode2.setOnClickListener(this);

        ll_motion_sensitivity = findViewById(R.id.ll_motion_sensitivity);

        AppCompatSeekBar seekBar_motion = findViewById(R.id.seekbar_motion);
        seekBar_motion.setOnSeekBarChangeListener(seekBarChangeListener);
        // 获取默认体感度
        int defProcess = SPController.getInstance().getIntValue(SPController.id.KEY_GYROSCOPE_SENSITIVITY, 6);
        seekBar_motion.setProgress(defProcess - 1);

        // 获取默认体感模式及初始化
        int mode = SPController.getInstance().getIntValue(SPController.id.KEY_GYROSCOPE_MODE, GyroscopeManager.SENSOR_MODE_NONE);
        GyroscopeManager.getInstance().setSensorMode(mode);
        switch (mode) {
            case GyroscopeManager.SENSOR_MODE_NONE:
                tv_motion_shut_down.setSelected(true);
                break;
            case GyroscopeManager.SENSOR_MODE_RELATIVE:
                tv_motion_mode1.setSelected(true);
                ll_motion_sensitivity.setVisibility(VISIBLE);
                break;
            case GyroscopeManager.SENSOR_MODE_RELATIVE_REVERSE:
                tv_motion_mode2.setSelected(true);
                ll_motion_sensitivity.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            if (id == R.id.switch_speed_monitor) {
                SPController.getInstance().setBooleanValue(SPController.id.KEY_ENABEL_REAL_TIME_MONITORING, isChecked);
                if (mMenuListener != null) {
                    mMenuListener.onClickedRealTimeMonitor(isChecked);
                }
            } else if (id == R.id.switch_vibrate) {
                SPController.getInstance().setBooleanValue(SPController.id.KEY_ENABLE_VIBRATEABLE, isChecked);
                if (mMenuListener != null) {
                    mMenuListener.onClickedViberate(isChecked);
                }
            } else if (id == R.id.switch_full_screen) {
                SPController.getInstance().setBooleanValue(SPController.id.KEY_ENABEL_STRETCH_VIDEO, isChecked);
                if (mMenuListener != null) {
                    mMenuListener.onClickedStretchVideo(isChecked);
                }
            }
        }
    };

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (seekBar.getId() == R.id.seekbar) {
                setMouseSpeed(seekBar.getProgress());
                SPController.getInstance().setMouseSpeed(seekBar.getProgress());
            } else if (seekBar.getId() == R.id.seekbar_motion) {
                SPController.getInstance().setIntValue(SPController.id.KEY_GYROSCOPE_SENSITIVITY, seekBar.getProgress() + 1);
                GyroscopeManager.getInstance().setSensorSensitivity(seekBar.getProgress() + 1);
            }
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_leave_desktop) {
            if (mMenuListener != null) {
                mMenuListener.onClickedLeaveDesktop();
            }
        } else if (id == R.id.ll_switch_process) {
            if (mMenuListener != null) {
                mMenuListener.onClickedProcessSwitch();
            }
        } else if (id == R.id.ll_task_manager) {
            if (mMenuListener != null) {
                mMenuListener.onClickedTaskManager();
            }
        } else if (id == R.id.tv_shut_down) {
            if (tv_motion_shut_down.isSelected()) {
                return;
            }
            if (mMenuListener != null) {
                mMenuListener.onClickedSensor(GyroscopeManager.SENSOR_MODE_NONE);
                SPController.getInstance().setIntValue(SPController.id.KEY_GYROSCOPE_MODE, GyroscopeManager.SENSOR_MODE_NONE);
                resetMotionChooseState();
                tv_motion_shut_down.setSelected(true);
                ll_motion_sensitivity.setVisibility(GONE);
            }
        } else if (id == R.id.tv_mode1) {
            if (tv_motion_mode1.isSelected()) {
                return;
            }
            if (mMenuListener != null) {
                mMenuListener.onClickedSensor(GyroscopeManager.SENSOR_MODE_RELATIVE);
                SPController.getInstance().setIntValue(SPController.id.KEY_GYROSCOPE_MODE, GyroscopeManager.SENSOR_MODE_RELATIVE);
                resetMotionChooseState();
                tv_motion_mode1.setSelected(true);
                ll_motion_sensitivity.setVisibility(VISIBLE);
            }
        } else if (id == R.id.tv_mode2) {
            if (tv_motion_mode2.isSelected()) {
                return;
            }
            if (mMenuListener != null) {
                mMenuListener.onClickedSensor(GyroscopeManager.SENSOR_MODE_RELATIVE_REVERSE);
                SPController.getInstance().setIntValue(SPController.id.KEY_GYROSCOPE_MODE, GyroscopeManager.SENSOR_MODE_RELATIVE_REVERSE);
                resetMotionChooseState();
                tv_motion_mode2.setSelected(true);
                ll_motion_sensitivity.setVisibility(VISIBLE);
            }
        } else if (id == R.id.tv_pixel_low) {
            if (tv_pixel_low.isSelected()) {
                return;
            }
            if (mMenuListener != null) {
                resetPixelChooseState();
                tv_pixel_low.setSelected(true);
                setPictureQuality(0);
                mMenuListener.onClickedPictureQuality(0);
            }
        } else if (id == R.id.tv_pixel_medium) {
            if (tv_pixel_medium.isSelected()) {
                return;
            }
            if (mMenuListener != null) {
                resetPixelChooseState();
                tv_pixel_medium.setSelected(true);
                setPictureQuality(1);
                mMenuListener.onClickedPictureQuality(1);
            }
        } else if (id == R.id.tv_pixel_high) {
            if (tv_pixel_high.isSelected()) {
                return;
            }
            if (mMenuListener != null) {
                resetPixelChooseState();
                tv_pixel_high.setSelected(true);
                setPictureQuality(2);
                mMenuListener.onClickedPictureQuality(2);
            }
        } else if (id == R.id.tv_pixel_super_high) {
            if (tv_pixel_superHigh.isSelected()) {
                return;
            }
            if (mMenuListener != null) {
                resetPixelChooseState();
                tv_pixel_superHigh.setSelected(true);
                setPictureQuality(3);
                mMenuListener.onClickedPictureQuality(3);
            }
        } else if (id == R.id.tv_mode_touch) {
            if (tv_mode_touch.isSelected()) {
                return;
            }
            if (mMenuListener != null) {
                resetTouchModeChooseState();
                tv_mode_touch.setSelected(true);
                mMenuListener.onClickedMouseMode(false);
            }
        } else if (id == R.id.tv_mode_mouse) {
            if (tv_mode_mouse.isSelected()) {
                return;
            }
            if (mMenuListener != null) {
                resetTouchModeChooseState();
                tv_mode_mouse.setSelected(true);
                mMenuListener.onClickedMouseMode(true);
            }
        } else if (id == R.id.tv_gesture_instruction) {
            if (mMenuListener != null) {
                mMenuListener.onClickedGestureInstruction();
            }
        }
    }

    private void resetMotionChooseState() {
        tv_motion_shut_down.setSelected(false);
        tv_motion_mode1.setSelected(false);
        tv_motion_mode2.setSelected(false);
    }

    private void resetTouchModeChooseState() {
        tv_mode_touch.setSelected(false);
        tv_mode_mouse.setSelected(false);
    }

    private void resetPixelChooseState() {
        tv_pixel_low.setSelected(false);
        tv_pixel_medium.setSelected(false);
        tv_pixel_high.setSelected(false);
        tv_pixel_superHigh.setSelected(false);
    }

    /**
     * 关闭一级菜单的同时，若二级菜单显示则关闭二级菜单
     */
    private OnFirstMenuCloseListener menuCloseListener = new OnFirstMenuCloseListener() {
        @Override
        public void onFirstMenuClosed() {
            if (mScrollView != null && mScrollView.getVisibility() == VISIBLE) {
                mScrollView.setVisibility(GONE);
            }
        }
    };

    /**
     * 一级菜单点击监听
     */
    private FirstLevelMenu.OnFirstMenuClickListener menuClickListener = new FirstLevelMenu.OnFirstMenuClickListener() {
        @Override
        public void onMenuClick(int position) {
            switch (position) {
                case 0:
                    if (mMenuListener != null) {
                        mMenuListener.onCLickedExitUse();
                    }
                    break;
                case 1:
                    mScrollView.setVisibility(mScrollView.getVisibility() == VISIBLE ? GONE : VISIBLE);
                    break;
                case 2:
                    if (mMenuListener != null) {
                        mMenuListener.onClickedGameKeyboard();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置菜单点击监听
     *
     * @param listener
     */
    public void setOnSettingMenuListener(OnSettingMenuListener listener) {
        mMenuListener = listener;
    }


    public interface OnFirstMenuCloseListener {
        /**
         * 监听一级菜单关闭，同时关闭二级菜单
         */
        void onFirstMenuClosed();

    }

    /**
     * 保存鼠标值
     *
     * @param process
     */
    private void setMouseSpeed(int process) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MOUSE_COUNT_KEY, process);
        editor.commit();
    }

    /**
     * 获取鼠标值
     */
    private int getMouseSpeed() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(MOUSE_COUNT_KEY, 5);
    }

    /**
     * 保存画质
     *
     * @param quality
     */
    private void setPictureQuality(int quality) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PICTURE_QUALITY_KEY, quality);
        editor.commit();
    }

    /**
     * 获取鼠标值
     */
    private int getPictureQuality() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CONFIG_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PICTURE_QUALITY_KEY, 1);
    }
}
