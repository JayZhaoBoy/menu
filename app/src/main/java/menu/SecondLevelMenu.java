package menu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

/**
 * @author JayZhao
 * @date 2020/5/26
 **/
public class SecondLevelMenu extends LinearLayout {
//    leave the deskTop
//    switch process
//    task manager
//    motion assistant （shut down， mode1， mode2）
//    mouse speed （slow 1-10 high）
//    select pixel  (low, medium, high, super high)
//    operate mode (touch, mouse, gesture instruction)
//
//    vibrate
//    speed monitor
//    full screen


    private Context mContext;
    private List<View> mChildList;
    private int mType = -1;
    public final static int TYPE_LEAVE = 1001;
    public final static int TYPE_PROCESS = 1002;
    public final static int TYPE_TASK = 1003;
    public final static int TYPE_MOTION = 1004;
    public final static int TYPE_MOUSE = 1005;
    public final static int TYPE_PIXEL = 1006;
    public final static int TYPE_OPERATE = 1007;

    public final static int SWITCH_VIBRATE = 1008;
    public final static int SWITCH_MONITOR = 1009;
    public final static int SWITCH_FULL_SCREEN = 10010;
    /**
     * 是否包含子菜单
     */
    private boolean mIsContentChildMenu = false;

    /**
     * 子菜单是否已经展示
     */
    private boolean mIsChildMenuShow = false;

    /**
     * 菜单内容
     */
    private TextView mContentView;

    /**
     * 子菜单的容器，方便进行效果处理
     */
    private LinearLayout mLl_menuContent;

    public SecondLevelMenu(Context context) {
        super(context);
        initView(context);
    }

    public SecondLevelMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SecondLevelMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化view
     *
     * @param context
     */
    private void initView(Context context) {
        mContext = context;
        final TextView textView = new TextView(context);
        LayoutParams params_tv = new LayoutParams(MenuHelper.mSecondMenuWidth, MenuHelper.mHeight);

        textView.setBackgroundColor(Color.GREEN);
        params_tv.topMargin = 2;

        textView.setGravity(Gravity.CENTER);
        mContentView = textView;
        addView(textView, params_tv);
    }

    public void setType(int type) {
        mType = type;
    }

    /**
     * 改变是否选中状态
     */
    public void changeMenuState(boolean sel) {
        if (!mIsContentChildMenu) {
            return;
        }
        if (sel) {
            mContentView.setSelected(!mContentView.isSelected());
            mLl_menuContent.setVisibility(mLl_menuContent.getVisibility() == VISIBLE ? GONE : VISIBLE);
        } else {
            mContentView.setSelected(false);
            mLl_menuContent.setVisibility(GONE);
        }
    }

    /**
     * 重置状态
     */
    public void resetState() {
        mContentView.setSelected(false);
        mLl_menuContent.setVisibility(GONE);
    }

    /**
     * 设置菜单项名称
     *
     * @param content
     */
    public void setMenuContent(String content) {
        if (mContentView != null) {
            mContentView.setText(content);
        }
    }

    /**
     * 设置三级菜单项
     *
     * @param childList
     */
    public void setChildList(List<String> childList) {
        if (childList == null || childList.size() == 0) {
            return;
        }
        mIsContentChildMenu = true;
        int width = mChildMenuWidth * childList.size();
        LayoutParams params = new LinearLayout.LayoutParams(width, LayoutParams.MATCH_PARENT);
        mLl_menuContent = new LinearLayout(mContext);
        mLl_menuContent.setVisibility(GONE);
        addView(mLl_menuContent, params);
        mChildList = new ArrayList<>();
        for (int i = 0; i < childList.size(); i++) {
            TextView textView = new TextView(mContext);
            mChildList.add(textView);
            LayoutParams params_tv = new LinearLayout.LayoutParams(mChildMenuWidth, LayoutParams.MATCH_PARENT);
            textView.setGravity(Gravity.CENTER);
            textView.setText(childList.get(i));
            mLl_menuContent.addView(textView, params_tv);
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchChildMenuEvent(finalI);
                }
            });
        }
    }

    public void setChildSeekBar() {
        LayoutParams params = new LinearLayout.LayoutParams(mSeekBarWidth, LayoutParams.MATCH_PARENT);
        mLl_menuContent = new LinearLayout(mContext);
        addView(mLl_menuContent, params);
        SeekBar seekBar = new SeekBar(mContext);
        LayoutParams params_sk = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        seekBar.setMax(10);
        mLl_menuContent.addView(seekBar, params_sk);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 子菜单的点击处理
     *
     * @param position
     */
    private void dispatchChildMenuEvent(int position) {
        changeChildMenuState(position);
    }

    private void changeChildMenuState(int position) {
        if (mChildList == null || mChildList.size() == 0) {
            return;
        }
        for (int i = 0; i < mChildList.size(); i++) {

        }
    }

    // TODO: 2020/5/26 各种二级菜单type
    // TODO: 2020/5/26 设置拖拽进度条

}
